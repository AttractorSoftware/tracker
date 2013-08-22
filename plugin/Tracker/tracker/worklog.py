from datetime import timedelta, datetime
import re
import os.path

from trac.core import Component, implements
from trac.util.datefmt import user_time, parse_date, to_datetime, to_timestamp
from trac.web import IRequestHandler
from trac.web.chrome import ITemplateProvider, add_stylesheet, Chrome


class WorkLogViewModule(Component):
    implements(ITemplateProvider, IRequestHandler)

    def match_request(self, req):
        # return re.match(r'/users/(\?user=).*$', req.path_info)
        return re.match(r'/(raw-|zip-)?users/\?user=[a-zA-Z0-9]+?$', req.path_info)

    def process_request(self, req):
        username = req.args.get('user')

        fromdate = datetime.now(req.tz)

        precisedate = None
        if 'date' in req.args:
            reqfromdate = req.args['date'].strip()
            if reqfromdate:
                precisedate = user_time(req, parse_date, reqfromdate)
                fromdate = precisedate.astimezone(req.tz)

        fromdate = to_datetime(datetime(fromdate.year, fromdate.month,
                                        fromdate.day, 23, 59, 59, 999999),
                               req.tz)

        from_date = to_timestamp(precisedate)
        to_date = to_timestamp(fromdate)

        date = {
            'from_date': from_date if from_date else to_timestamp(fromdate) - 86400,
            'to_date': to_date
        }

        screenshots = self.get_user_screenshots(username, date)
        data = {
            'username': username,
            'screenshots': screenshots,
            'fromdate': fromdate
        }

        add_stylesheet(req, 'trac/css/tracker.css')
        chrome = Chrome(self.env)
        chrome.add_jquery_ui(req)
        return 'user_worklog_view.html', data, None

    def get_templates_dirs(self):
        from pkg_resources import resource_filename

        print([resource_filename(__name__, 'templates')])
        return [resource_filename(__name__, 'templates')]

    def get_user_screenshots(self, username, date):

        query = "SELECT id  FROM tracker_screenshots where author ='" + username + "'"

        if date:
            query += " AND  time >= '" + str(date['from_date']) + "' AND time <='" + str(date['to_date']) + "'"

        screenshots = []

        for id in self.env.db_query(query):
            screenshot = {
                'id': id
            }

            screenshots.append(screenshot)

        return screenshots


class ScreenshotModule(Component):
    implements(IRequestHandler)

    def match_request(self, req):
        return re.match(r'/screenshot/.*$', req.path_info)

    def process_request(self, req):
        if req.method == "GET":
            idscreenshot = req.args.get('id')
            self.send_screenshots_with_id(req, idscreenshot)


    def send_screenshots_with_id(self, req, id):
        query = "SELECT id, path  FROM tracker_screenshots WHERE id ='" + id + "' LIMIT 1"

        for id, path in self.env.db_query(query):
            path_image_file = os.path.join(self.env.path, 'files', path)
            req.send_file(path_image_file, "image/jpg")





