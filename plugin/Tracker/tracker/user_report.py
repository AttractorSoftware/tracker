# -*- coding: utf-8 -*-
import re
import time

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
        match = re.match(r'/user_report(?:/(\D+))?(?:/(\d{2}-\d{2}-\d{4}))?(?:/(\d{2}-\d{2}-\d{4}))?$', req.path_info)
        if match:
            matches = match.groups()
            req.args['username'] = False if not matches[0] else matches[0]
            req.args['fromDMY'] = False if not matches[1] else matches[1]
            req.args['toDMY'] = False if not matches[2] else matches[2]
            return True

    def process_request(self, req):
        api = TrackerApi
        context = Context.from_request(req)
        db = self.env.get_db_cnx()
        context.cursor = db.cursor()
        actions = self._get_actions(context)

        if req.args.get('fromDMY') and req.args.get('toDMY') and req.args.get('username'):
            username = req.args.get('username')
            fromDMY = int(time.mktime(time.strptime(req.args.get('fromDMY'), '%d-%m-%Y'))).__str__()
            toDMY = int(time.mktime(time.strptime(req.args.get('toDMY'), '%d-%m-%Y'))).__str__()

            DEFAULT_TIME_VALUE = 10
            screenshots = self._get_users_screenshots(username, fromDMY, toDMY)
            summaryWorkedTimeInMinutes = len(screenshots) * DEFAULT_TIME_VALUE
            temp_tasks = {}
            for screenshot in screenshots:
                if screenshot['ticketId'] in temp_tasks.keys():
                    temp_tasks[screenshot['ticketId']]['minutes'] += DEFAULT_TIME_VALUE
                else:
                    temp_tasks[screenshot['ticketId']] = {'minutes': DEFAULT_TIME_VALUE, 'name': screenshot['summary']}
            tasks = []
            for key, temp_task in temp_tasks.iteritems():
                task = {
                    'hours': int(temp_task['minutes'] / 60),
                    'minutes': temp_task['minutes'] % 60,
                    'name': temp_task['name']
                }
                tasks.append(task)
            req.data = {
                'screenshots': screenshots,
                'tasks': tasks,
                'summaryWorkedTimeHours': int(summaryWorkedTimeInMinutes / 60),
                'summaryWorkedTimeMinutes': summaryWorkedTimeInMinutes % 60,
                'fromDMY': req.args.get('fromDMY'),
                'toDMY': req.args.get('toDMY'),
                'username': req.args.get('username')
            }

            add_stylesheet(req, 'trac/css/tracker.css')
            return "user_report.html", req.data, None
        else:
            add_script(req, 'common/js/jquery-ui.js')
            add_stylesheet(req, 'common/css/jquery-ui/jquery-ui.css')
            add_script(req, 'common/js/jquery-ui-addons.js')
            add_stylesheet(req, 'common/css/jquery-ui-addons.css')
            req.data = {
                'users': api.get_users(TrackerApi(), context)
            }
            return "user_report_date_picker.html", req.data, None

    def _get_actions(self, context):
        pass


    def _do_actions(self, context, actions):
        pass

    def _get_users_screenshots(self, username, fromDMY, toDMY):
        screenshots = []
        query = "SELECT s.id, t.id as ticketId, t.summary, s.interval, s.mouse_event_count, " \
                "s.keyboard_event_count, s.filename, s.author, s.path, s.time " \
                "FROM tracker_screenshots s " \
                "INNER JOIN ticket t ON t.id = s.ticket_id " \
                "WHERE s.author = '" + username + "' AND s.time > '" + fromDMY + "' AND s.time < '" + toDMY + "'"
        for id, ticketId, summary, interval, mouse_event_count, keyboard_event_count, filename, author, path, time in self.env.db_query(query):
            screenshot = {
                'id': id,
                'ticketId': ticketId,
                'summary': summary,
                'interval': interval,
                'mouse_event_count': mouse_event_count,
                'keyboard_event_count': keyboard_event_count,
                'filename': filename,
                'author': author,
                'path': path,
                'time': time
            }
            screenshots.append(screenshot)
        return screenshots