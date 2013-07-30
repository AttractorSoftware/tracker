import os
import shutil
from twisted.python.hashlib import sha1
import errno
from genshi import Markup

from trac.config import IntOption
from trac.core import *
from trac.web import IRequestHandler, RequestDone
from trac.web.chrome import INavigationContributor


class TrackerModule(Component):
    implements(IRequestHandler, INavigationContributor)

    max_size = IntOption('screenshot', 'max_size', 262144,
                         """Maximum allowed screenshot size (in bytes)""")

    def get_active_navigation_item(self, req):
        return 'tracker'

    def get_navigation_items(self, req):
        yield 'mainnav', 'tracker', Markup('<a href="%s">Tracker</a>' % (
            self.env.href.tracker()))

    def match_request(self, req):
        return req.path_info == '/tracker'

    def process_request(self, req):

        if req.method == 'POST':
            self._do_save(req)
        else:
            print 'GET'
        req.redirect(req.href.wiki())

    def _do_save(self, req):
        upload = req.args['screenshot']
        username = req.args['username']
        screenshots_dir = os.path.join(os.path.normpath(self.env.path), 'files', 'screenshots', username)

        if not os.access(screenshots_dir, os.F_OK):
            os.makedirs(screenshots_dir)
        filename, targetfile = self._create_unique_file(screenshots_dir, upload.filename)
        shutil.copyfileobj(upload.file, targetfile)

    def _create_unique_file(self, dir, filename):
        parts = os.path.splitext(filename)
        flags = os.O_CREAT + os.O_WRONLY + os.O_EXCL
        if hasattr(os, 'O_BINARY'):
            flags += os.O_BINARY
        idx = 1
        while 1:
            path = os.path.join(dir, self._get_hashed_filename(filename))
            try:
                return filename, os.fdopen(os.open(path, flags, 0666), 'w')
            except OSError, e:
                if e.errno != errno.EEXIST:
                    raise
                idx += 1
                #a sanity check
                if idx > 100:
                    raise Exception('Failed to create unique name: ' + path)
                filename = '%s.%d%s' % (parts[0], idx, parts[1])

    def _get_hashed_filename(self, filename):
        hash = sha1(filename.encode('utf-8')).hexdigest()
        return hash