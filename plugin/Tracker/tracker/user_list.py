# -*- coding: utf-8 -*-

import re
from genshi.builder import tag
from trac.core import Component, implements
from trac.web import IRequestHandler
from trac.web.chrome import INavigationContributor, ITemplateProvider, add_stylesheet


class TrackerUserListModule(Component):
    implements(INavigationContributor, ITemplateProvider, IRequestHandler)

    def get_active_navigation_item(self, req):
        return 'tracker'

    def get_navigation_items(self, req):
        yield ('mainnav', 'tracker',
               tag.a('Tracker', href=req.href.users()))

    def match_request(self, req):
        return re.match(r'/users$', req.path_info)

    def process_request(self, req):

        data = {
            'users': self.users(),
            'client': {'download_href': 'jar-tracker/tracker.jar'}
        }
        add_stylesheet(req, 'trac/css/tracker.css')
        return 'user_list.html', data, None

    def get_templates_dirs(self):
        from pkg_resources import resource_filename
        return [resource_filename(__name__, 'templates')]

    def get_htdocs_dirs(self):
        from pkg_resources import resource_filename
        return [('trac', resource_filename(__name__, 'htdocs'))]

    def users_list(self):

        data = {
            'users': self.users()
        }

        return data

    def users(self):
        users = []
        for row in self.env.db_query("""
                SELECT distinct author
                FROM tracker_screenshots order by author
                """):
            users.append(row)
        return users