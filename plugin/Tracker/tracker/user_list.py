# -*- coding: utf-8 -*-

import datetime
import re
import os.path
import os

from trac.core import Component, implements, TracError
from trac.util.datefmt import user_time, parse_date, to_datetime, to_timestamp
from trac.web import IRequestHandler
from trac.web.chrome import ITemplateProvider, add_stylesheet, add_script, Chrome
from trac.config import Option
from genshi.builder import tag
from trac.core import ExtensionPoint
from trac.web.chrome import INavigationContributor
from trac.mimeview import Context, Mimeview


from tracker.api import ITrackerScreenshotsRenderer, TrackerApi
from tracker.utils import calculate_client_package_name


class TrackerUserListModule(Component):
    implements(INavigationContributor, ITemplateProvider, IRequestHandler)

    renderers = ExtensionPoint(ITrackerScreenshotsRenderer)

    default_format = Option('screenshots', 'default_format', 'html',
                            """Default format for screenshot download links.""")

    def __init__(self):
        self.path = os.path.join(os.path.normpath(self.env.path), 'files')

    def get_active_navigation_item(self, req):
        return 'tracker'

    def get_navigation_items(self, req):
        yield ('mainnav', 'tracker',
               tag.a('Tracker', href=req.href.users()))

    def match_request(self, req):
        match = re.match(r'/users(?:/(\D+))?(?:/(\d+))?$', req.path_info)
        if match:
            username, s_id = match.groups()
            req.args['username'] = username
            req.args['id'] = s_id

            if username and s_id:
                req.args['action'] = 'get-file'
            elif username and not s_id:
                req.args['action'] = 'view'
            elif not username and not s_id:
                req.args['action'] = 'get-users'
            else:
                return False

            return True

    def process_request(self, req):

        context = Context.from_request(req)
        req.data = {}

        db = self.env.get_db_cnx()
        context.cursor = db.cursor()
        actions = self._get_actions(context)

        template, content_type = self._do_actions(context, actions)

        add_stylesheet(req, 'trac/css/tracker.css')

        return req.data['template'], req.data, None

    def get_templates_dirs(self):
        from pkg_resources import resource_filename
        return [resource_filename(__name__, 'templates')]

    def get_htdocs_dirs(self):
        from pkg_resources import resource_filename
        return [('trac', resource_filename(__name__, 'htdocs'))]

    def _get_actions(self, context):
        action = context.req.args.get('action')
        if action == 'get-file':
            return ['get-file']
        elif action == 'get-users':
            return ['get-users']
        else:
            return ['view']

    def _do_actions(self, context, actions):

        api = TrackerApi()

        time_interval = self.env.config.getint('tracker', 'time_interval', 10)
        time_separate = 1
        minutes_interval=0
        screenshotsWithHourse = []
        screenshotsWithMinutes = []
        template_hourse = []
        minute = 0
        min_hourse = 0
        max_hourse = 0
        allScreenshots=[]
        total_time=0

        for action in actions:
            if action == 'view':
                date = datetime.datetime.now(context.req.tz)
                if 'date' in context.req.args:
                    date_from_calendar = context.req.args['date'].strip()
                    if date_from_calendar:
                        precisedate = user_time(context.req, parse_date, date_from_calendar)
                        date = precisedate.astimezone(context.req.tz)
                to_date = to_datetime(datetime.datetime(date.year, date.month, date.day,
                                               23, 59, 59, 999999), context.req.tz)
                to_date_timestamp = to_timestamp(to_date)

                full_date = {
                    'from_date': to_date_timestamp - 86400,
                    'to_date'  : to_date_timestamp
                }

                context.req.data['fromdate'] = to_date
                context.req.data['username'] = context.req.args.get('username')

                screenshot_id = int(context.req.args.get('id') or 0)
                screenshots = api.get_screenshots(context, context.req.args.get('username'), full_date)

                context.req.data['id'] = screenshot_id

                for hourse in range(0, 24):
                    for screenshot in screenshots:
                        screenshot["hourse"] = datetime.datetime.fromtimestamp(screenshot["time_slot.time"]).strftime('%H')
                        if (int(screenshot["hourse"]) == hourse):
                            if min_hourse == 0:
                                min_hourse = hourse
                            elif min_hourse > hourse:
                                min_hourse = hourse
                            if max_hourse < hourse:
                                max_hourse = hourse
                            screenshotsWithHourse.append({hourse:screenshot})

                while (minute <= 59):
                    for screenshotsAll in screenshotsWithHourse:

                        for index in screenshotsAll:

                            screenshotMinute = datetime.datetime.fromtimestamp(float(screenshotsAll[index]["time_slot.time"])).strftime('%M')
                            if int(screenshotMinute) == minute:
                                screenshotHourse = datetime.datetime.fromtimestamp(screenshotsAll[index]["time_slot.time"]).strftime('%H')
                                if int(screenshotHourse) not in template_hourse:
                                    template_hourse.append(int(screenshotHourse))
                                screenshotsAll[index]['hourse'] = int(screenshotHourse)
                                screenshotsAll[index]['minute'] = int(screenshotMinute)
                                if len(screenshotsWithMinutes)>0 and screenshotsWithMinutes[0]['minute']==screenshotsAll[index]['minute']:
                                    screenshotsWithMinutes.pop()
                                screenshotsWithMinutes.append(screenshotsAll[index])
                    minute += 10
                for hourse in template_hourse:
                    for screenshot in screenshotsWithMinutes:
                        if screenshot['hourse']==hourse:
                            while screenshot['minute']!=minutes_interval:
                                allScreenshots.append({"hourse":hourse,"screen":None,"minute":minutes_interval})
                                minutes_interval+=10
                            screenshot["screen"]=1
                            allScreenshots.append(screenshot)
                            minutes_interval+=10
                    while (minutes_interval!=60):
                        allScreenshots.append({"hourse":hourse,"screen":None,"minute":minutes_interval})
                        minutes_interval+=10
                    minutes_interval=0

                for screenshot in screenshots:
                    total_time += 10

                hours = total_time/60
                minutes = total_time%60

                context.req.data['hours'] = hours
                context.req.data['minutes'] = minutes
                context.req.data['allScreenshots'] = allScreenshots
                context.req.data['template_hourse'] = range(int(min_hourse), int(max_hourse)+time_separate)
                context.req.data['time_interval'] = time_interval
                context.req.data['time_separate'] = time_separate
                context.req.data['template'] = 'user_worklog_view.html'
                add_stylesheet(context.req, 'trac/css/tracker.css')
                add_script(context.req, 'trac/js/activity_feed.js')
                chrome = Chrome(self.env)
                chrome.add_jquery_ui(context.req)

                return 'screenshots', None

            if action == 'get-file':

                screenshot_id = int(context.req.args.get('id') or 0)
                format = context.req.args.get('format') or self.default_format

                screenshot = api.get_screenshot(context, screenshot_id)

                if format == 'html':
                    context.req.data['screenshot'] = screenshot
                    return 'screenshot', None
                else:
                    screenshot_path = screenshot[0]['path']
                    filename = self.path + '/' + screenshot_path

                    file = open(filename.encode('utf-8'), "r")
                    file_data = file.read(1000)
                    file.close()

                    mimeview = Mimeview(self.env)
                    mime_type = mimeview.get_mimetype(filename, file_data)
                    if not mime_type:
                        mime_type = 'application/octet-stream'
                    if 'charset=' not in mime_type:
                        charset = mimeview.get_charset(file_data, mime_type)
                        mime_type = mime_type + '; charset=' + charset

                    context.req.send_file(filename.encode('utf-8'), mime_type)
            elif action == 'get-users':

                context.req.data['users'] = api.get_users(context)
                context.req.data['template'] = 'user_list.html'
                context.req.data['client'] = {'download_href': 'jar-tracker/' + calculate_client_package_name()}
                return 'screenshots', None
            else:
                return 'screenshots', None

    def _get_screenshosts_per_by_hours(self, args_with_hourse_sourse_index):
        pass
