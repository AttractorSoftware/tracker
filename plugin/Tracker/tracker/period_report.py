# -*- coding: utf-8 -*-
import re
import time

from trac.core import Component, implements, TracError
from trac.web import IRequestHandler
from trac.web.chrome import ITemplateProvider, add_stylesheet, add_script
from trac.mimeview import Context
from tracker.lib.WorkLogTable import WorkLogTable


class TrackerUserReportModule(Component):
    implements(ITemplateProvider, IRequestHandler)

    def get_htdocs_dirs(self):
        pass

    def get_templates_dirs(self):
        pass


    def match_request(self, req):
        match = re.match(r'/period_report?(?:/(\d{2}-\d{2}-\d{4}))?(?:/(\d{2}-\d{2}-\d{4}))?$', req.path_info)
        if match:
            matches = match.groups()
            req.args['fromDMY'] = False if not matches[0] else matches[0]
            req.args['toDMY'] = False if not matches[1] else matches[1]
            return True
        return match

    def process_request(self, req):
        context = Context.from_request(req)
        db = self.env.get_db_cnx()
        context.cursor = db.cursor()

        if req.args.get('fromDMY') and req.args.get('toDMY'):
            fromDMY = int(time.mktime(time.strptime(req.args.get('fromDMY'), '%d-%m-%Y'))).__str__()
            toDMY = int(time.mktime(time.strptime(req.args.get('toDMY'), '%d-%m-%Y'))).__str__()
            all_tickets_with_worklogs=[]
            tickets_id = self._get_tickets_for_period(fromDMY, toDMY)

            for ticket in tickets_id:
                worked_time=0
                worklogByTicket=WorkLogTable.getByTicketId(self.env, ticket['ticketId'])
                for worklog in worklogByTicket:
                    worked_time+=worklog['time_spent']
                worklog = {
                'ticketId': ticket['ticketId'],
                'summary':ticket['summary'],
                'worked_time':worked_time,
                'worked_time_hours':worked_time/60,
                'worked_time_minutes':worked_time%60,
                'worklogs': worklogByTicket
                }
                if worklog['worklogs']!=[]:
                    all_tickets_with_worklogs.append(worklog)

            total_time=0
            for tickets in all_tickets_with_worklogs:
                total_time+=tickets['worked_time']

            req.data = {
                'fromDMY': req.args.get('fromDMY'),
                'toDMY': req.args.get('toDMY'),
                'tickets_with_worklogs':all_tickets_with_worklogs,
                'total_time_hours':total_time/60,
                'total_time_minutes':total_time%60
            }

            add_stylesheet(req, 'trac/css/tracker.css')
            return "period_report.html", req.data, None

        else:
            req.data = {}
            add_script(req, 'common/js/jquery-ui.js')
            add_stylesheet(req, 'common/css/jquery-ui/jquery-ui.css')
            add_script(req, 'common/js/jquery-ui-addons.js')
            add_stylesheet(req, 'common/css/jquery-ui-addons.css')
            add_stylesheet(req, 'trac/css/tracker.css')
            return "report_date_picker.html", req.data, None


    def _get_tickets_for_period(self, fromDMY, toDMY):
        tickets = []
        query = "SELECT s.id as ticketId, t.summary " \
                "FROM time_slot s " \
                "INNER JOIN ticket t ON t.id = s.ticket_id " \
                "WHERE s.time > '" + fromDMY + "' AND s.time < '" + toDMY + "'"
        for ticketId, summary in self.env.db_query(query):
            ticket = {
                'ticketId': ticketId,
                'summary': summary
            }
            tickets.append(ticket)
        return tickets