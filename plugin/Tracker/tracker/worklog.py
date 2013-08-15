import re
from twisted.python.hashlib import sha1
from trac.mimeview.api import Mimeview
from trac.resource import Resource, ResourceNotFound, resource_exists, get_resource_url, IResourceManager, get_resource_name
from trac.core import Component, implements
from trac.util.text import unicode_unquote
from trac.web import IRequestHandler, Href
from trac.web.chrome import ITemplateProvider, add_stylesheet
import os.path, posixpath

class WorkLogViewModule(Component):
    implements(ITemplateProvider, IRequestHandler)

    def match_request(self, req):
        return re.match(r'/users/.*$', req.path_info)

    def process_request(self, req):
        username = req.args.get('user')
        screenshots = self.get_user_screenshots(username, req)
        data = {
            'username'   : username,
            'screenshots': screenshots
        }

        add_stylesheet(req, 'trac/css/tracker.css')
        return 'user_worklog_view.html', data, None

    def get_templates_dirs(self):
        from pkg_resources import resource_filename

        print([resource_filename(__name__, 'templates')])
        return [resource_filename(__name__, 'templates')]

    def get_user_screenshots(self, username, req):

        query = "SELECT id  FROM tracker_screenshots where author ='" + username + "'"
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


    def send_screenshots_with_id(self,req, id):
        query = "SELECT id, path  FROM tracker_screenshots WHERE id ='" + id + "' LIMIT 1"

        for id, path in self.env.db_query(query):
            path_image_file = os.path.join(self.env.path, 'files', path)
            req.send_file(path_image_file, "image/jpg")





