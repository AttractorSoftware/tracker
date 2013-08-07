__author__ = 'esdp'

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

    def match_request(self, req):
        return req.path_info == '/TracCommenter'

    def process_request(self, req):

        if req.method == 'POST':
            print("Hello World")
        else:
            print 'GET'
        req.redirect(req.href.wiki())