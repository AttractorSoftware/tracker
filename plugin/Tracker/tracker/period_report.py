# -*- coding: utf-8 -*-
import re
import time
import calendar
import datetime
from trac.core import Component, implements
from trac.web import IRequestHandler
from trac.web.chrome import ITemplateProvider, add_stylesheet, add_script
from trac.mimeview import Context
from tracker.api import TrackerApi

class TrackerUserReportModule(Component):
    implements(ITemplateProvider, IRequestHandler)

    def get_htdocs_dirs(self):
        pass

    def get_templates_dirs(self):
        pass


    def match_request(self, req):
        match = re.match(r'/period_report$', req.path_info)
        return match

    def process_request(self, req):
        context = Context.from_request(req)
        db = self.env.get_db_cnx()
        context.cursor = db.cursor()

        sqlData = {}
        users=[]
        period = []

        if req.args.get('fromDMY') and req.args.get('toDMY'):
            fromDMY = int(time.mktime(time.strptime(req.args.get('fromDMY'), '%d-%m-%Y')))
            toDMY = int(time.mktime(time.strptime(req.args.get('toDMY'), '%d-%m-%Y')))

            date = {'from_date': fromDMY,
                    'to_date': toDMY+86400
            }

            period.append(req.args.get('fromDMY'))
            period.append(req.args.get('toDMY'))

            sqlData = TrackerApi().get_all_users_for_period(context, date)
            users = self.get_users_report(sqlData)

        else:
            if req.args.get('fromDMY'):
                date_start = datetime.datetime.strptime(req.args.get('fromDMY'), '%d-%m-%Y')
                date_end = datetime.datetime(date_start.year, date_start.month, calendar.mdays[date_start.month]).strftime('%d-%m-%Y')
                period.append(date_start.strftime("%B %Y"))
                fromDMY = int(time.mktime(time.strptime(req.args.get('fromDMY'), '%d-%m-%Y')))
                toDMY = int(time.mktime(time.strptime(date_end.__str__(), '%d-%m-%Y')))

                date = {'from_date': fromDMY,
                        'to_date': toDMY+86400
                }

                sqlData = TrackerApi().get_all_users_for_period(context, date)
                users = self.get_users_report(sqlData)

            else:
                last_date_start = datetime.date.today().replace(day=1, month=datetime.date.today().month-1)
                last_date_end = datetime.datetime(last_date_start.year, last_date_start.month, calendar.mdays[last_date_start.month]).strftime('%Y-%m-%d')
                period.append(last_date_start.strftime("%B %Y"))
                fromDMY = int(time.mktime(time.strptime(last_date_start.__str__(), '%Y-%m-%d')))
                toDMY = int(time.mktime(time.strptime(last_date_end.__str__(), '%Y-%m-%d')))

                date = {
                    'from_date': fromDMY,
                    'to_date': toDMY+86400
                }

                sqlData = TrackerApi().get_all_users_for_period(context, date)
                users = self.get_users_report(sqlData)

        req.data = {"users": users,
                    "date": period}
        add_script(req, 'common/js/jquery-ui.js')
        add_stylesheet(req, 'common/css/jquery-ui/jquery-ui.css')
        add_script(req, 'common/js/jquery-ui-addons.js')
        add_script(req, 'trac/js/period_report.js')
        add_stylesheet(req, 'common/css/jquery-ui-addons.css')
        add_stylesheet(req, 'trac/css/tracker.css')
        add_stylesheet(req, 'trac/css/period-report.css')
        return "period_report.html", req.data, None

    def get_users_report(self, sqlData):
        tickets = []
        users = []
        i = 0

        while i<len(sqlData):
            next=i+1
            if next<len(sqlData) and sqlData[i]['author'] == sqlData[next]['author']:
                ticket = self.get_ticket_info(sqlData[i])
                tickets.append(ticket)

            else:
                ticket = self.get_ticket_info(sqlData[i])
                tickets.append(ticket)
                total_time = self.calculate_time(tickets)
                user={'user': sqlData[i]['author'], 'tickets': tickets, 'total':total_time}
                users.append(user)
                tickets = []
            i += 1
        return users

    def get_ticket_info(self, dict):
        tracked = self.calculate_time(dict['count(ticket_id)*10'])

        ticket={
        'ticket': dict['ticket.id'],
        'summary': dict['summary'],
        'component': dict['component'],
        'milestone': dict['milestone'],
        'type': dict['type'],
        'priority': dict['priority'],
        'status': dict['status'],
        'minutes': dict['count(ticket_id)*10'],
        'tracked': tracked}
        return ticket

    def calculate_time(self, var):
        if type(var) is list:
            time = 0
            for ticket in var:
                time += ticket['minutes']
            var = time

        hours = var/60
        minutes = var % 60
        total_time = ""+str(hours)+" hs "+str(minutes)+" mins"
        return total_time
