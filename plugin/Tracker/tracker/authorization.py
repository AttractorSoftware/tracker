import base64
import re
from genshi.builder import tag
from trac.core import Component, implements
from trac.web import IRequestHandler
from trac.web.chrome import add_stylesheet


class TrackerAuthorizationModule(Component):
    implements(IRequestHandler)

    def match_request(self, req):
        return re.match(r'/authenticate.*$', req.path_info)

    def process_request(self, req):

        if req.session.authenticated and req.authname != 'anonymous':
            abuffer = 'Success'
            req.send_header('Content-Type', 'text/plain')
            req.send_header('Content-length', str(len(abuffer)))
            req.end_headers()
            req.write(abuffer)
        else:
            abuffer = 'Failed'
            req.send_header('Content-Type', 'text/plain')
            req.send_header('Content-length', str(len(abuffer)))
            req.end_headers()
            req.write(abuffer)

    def get_templates_dirs(self):
        from pkg_resources import resource_filename

        print([resource_filename(__name__, 'templates')])
        return [resource_filename(__name__, 'templates')]

    def get_htdocs_dirs(self):
        from pkg_resources import resource_filename

        return [('trac', resource_filename(__name__, 'htdocs'))]




