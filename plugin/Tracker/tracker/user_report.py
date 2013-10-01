# -*- coding: utf-8 -*-
import re

from trac.core import Component, implements
from trac.web import IRequestHandler
from trac.web.chrome import ITemplateProvider, add_stylesheet
from trac.mimeview import Context, Mimeview


class TrackerUserReportModule(Component):
    implements(ITemplateProvider, IRequestHandler)

    def get_htdocs_dirs(self):
        pass

    def get_templates_dirs(self):
        pass


    def match_request(self, req):
        match = re.match(r'/user_report(?:/(\D+))?(?:/(\d+))?$', req.path_info)
        if match:
            username = match.groups()
            req.args['username'] = username[0]
            # req['username'] = username[0]
            return True

    def process_request(self, req):
        context = Context.from_request(req)
        req.data = {}

        db = self.env.get_db_cnx()
        context.cursor = db.cursor()
        actions = self._get_actions(context)

        # template, content_type = self._do_actions(context, actions)

        add_stylesheet(req, 'trac/css/tracker.css')


        return "user_report.html", req.data, None




    def _get_actions(self, context):
        pass


    def _do_actions(self, context, actions):
        pass