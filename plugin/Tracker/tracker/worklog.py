import re
from trac.core import Component, implements
from trac.web import IRequestHandler
from trac.web.chrome import ITemplateProvider, add_stylesheet


class WorkLogViewModule(Component):
    implements(ITemplateProvider, IRequestHandler)

    def match_request(self, req):
        return re.match(r'/users/worklog$', req.path_info)

    def process_request(self, req):
        data = {}
        add_stylesheet(req, 'trac/css/tracker.css')
        return 'user_worklog_view.html', data, None

    def get_templates_dirs(self):
        from pkg_resources import resource_filename

        print([resource_filename(__name__, 'templates')])
        return [resource_filename(__name__, 'templates')]

    def get_htdocs_dirs(self):
        from pkg_resources import resource_filename

        return [('trac', resource_filename(__name__, 'htdocs'))]