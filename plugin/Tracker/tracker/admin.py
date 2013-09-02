from trac.resource import Resource
from trac.web.chrome import add_warning
from trac.perm import IPermissionRequestor
from trac.admin.api import IAdminPanelProvider
from trac.core import *


class TrackerAdminPlugin(Component):
    implements(IAdminPanelProvider, IPermissionRequestor)

    min_screenshot_interval_time = 5
    max_screenshot_interval_time = 30
    default_screenshot_interval_time = 10
    minutes_in_hour = 60

    def get_permission_actions(self):
        return ['TRACKER_ADMIN']

    def get_admin_panels(self, req):
        if 'TRACKER_ADMIN' in req.perm('tracker'):
            yield ('tracker', "Tracker", 'settings', 'Basic Settings')

    def render_admin_panel(self, req, cat, page, path_info):
        req.perm(Resource('tracker', None)).require('TRACKER_ADMIN')

        data = {'view': 'list', 'time_interval': self.default_screenshot_interval_time}

        intervals = []
        for x in range(self.min_screenshot_interval_time, self.minutes_in_hour + 1):
            if self.minutes_in_hour % x == 0:
                intervals.append(x)

        data['intervals'] = intervals

        if req.method == 'POST':
            if req.args.get('add_time_interval'):
                time_interval = req.args.get('time_interval')

                try:
                    interval = int(time_interval)

                    self.env.config.set('tracker', 'time_interval',
                                        interval)

                except ValueError:
                    add_warning(req, "Error storing time interval in database. Not saved.")
        interval = self.env.config.getint('tracker', 'time_interval')
        data['time_interval'] = interval if interval else self.default_screenshot_interval_time

        return 'tracker_admin.html', data