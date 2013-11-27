import re
from time import strftime
from datetime import datetime
import pkg_resources
import trac
from trac.web.chrome import Chrome, add_javascript, add_stylesheet
from trac.core import *
from genshi.filters import Transformer
from trac.web.api import IRequestHandler, ITemplateStreamFilter, IRequestFilter
from trac.web.chrome import ITemplateProvider
from trac.ticket.api import ITicketChangeListener


class TrackerWorkLogModule(Component):
    implements(ITemplateStreamFilter)

    def filter_stream(self, req, method, filename, stream, original_data):
        transformer = self.get_transformer_for(req, method, filename)
        if transformer is None:
            return stream
        return stream | transformer.get_stream(req,method,filename,stream,original_data)

    def get_transformer_for(self, req, method, template_name):
        filter = None
        if re.match(r'/ticket', req.path_info) and template_name == 'ticket.html':
            filter = TicketWorkLogTransformer(self.env)

        return filter


class TicketWorkLogTransformer(object):

    def __init__(self, env):
        self.env = env

    def get_stream(self, req, method, filename, stream, original_data):

        chrome = Chrome(self.env)
        data = original_data
        data['components'] = [component.name for component in trac.ticket.model.Component.select(self.env)]
        data['logs'] = self.get_logs(data['ticket'].id)
        template = chrome.load_template('work_log.html')
        content_stream = template.generate(**(chrome.populate_data(req, data)))
        chrome.add_jquery_ui(req)
        add_stylesheet(req, 'trac/css/work-log.css')
        return Transformer('//div[@id="ticket"]').after(content_stream)

    def get_logs(self, ticket_id):
        db = self.env.get_read_db()
        cursor = db.cursor()

        cursor.execute("SELECT * FROM work_log where ticket_id = " + str(ticket_id) + " ORDER BY time ASC")
        logs = []

        for row in cursor:
            log_time = str(row[3])[0:10]
            log_data = {
                'id': row[0],
                'ticket_id': row[1],
                'author': row[2],
                'time': datetime.fromtimestamp(int(log_time)).strftime("%d.%m.%Y %H:%M:%S"),
                'comment': row[4]
            }
            logs.append(log_data)
        return logs