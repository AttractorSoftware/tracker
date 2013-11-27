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
from tracker.lib.WorkLogTable import WorkLogTable


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
        data['logs'] = WorkLogTable.getByTicketId(self.env, data['ticket'].id)
        template = chrome.load_template('work_log.html')
        content_stream = template.generate(**(chrome.populate_data(req, data)))
        chrome.add_jquery_ui(req)
        add_stylesheet(req, 'trac/css/work-log.css')
        return Transformer('//div[@id="ticket"]').after(content_stream)