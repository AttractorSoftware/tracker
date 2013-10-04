# -*- coding: utf-8 -*-

from datetime import datetime
import errno
import os
import re
import shutil

from genshi import Markup
from trac.config import IntOption
from trac.core import *
from trac.util.datefmt import utc, to_timestamp
from trac.web import IRequestHandler, RequestDone
from trac.web.chrome import INavigationContributor
from trac.util.datefmt import to_utimestamp
from trac.util.compat import sha1


class Screenshot(object):
    def __init__(self, env):
        self.env = env

    def insert(self, filename, fileobject, author, mouse_event_count = 0, keyboard_event_count = 0, task_id = 0, interval = 10):
        """
        Create a new Screenshot record and save the file content
        """

        screenshots_dir = os.path.join(os.path.normpath(self.env.path), 'files', 'screenshots', author)
        if not os.access(screenshots_dir, os.F_OK):
            os.makedirs(screenshots_dir)

        filename, extension = os.path.splitext(filename)
        filename, targetfile, screenshot_hashed_name = self._create_unique_screenshot(screenshots_dir, filename,
                                                                                      extension)
        file_dir = "screenshots" + "/" + author + "/" + screenshot_hashed_name

        time = to_timestamp(datetime.now(utc))

        with targetfile:
            with self.env.db_transaction as db:
                db("INSERT INTO tracker_screenshots(filename, author, path, time, mouse_event_count, keyboard_event_count, task_id, interval) VALUES(%s, %s, %s, %s, %s, %s, %s, %s )",
                   (filename, author, file_dir, time, mouse_event_count, keyboard_event_count, task_id, interval))
                shutil.copyfileobj(fileobject, targetfile)

    def _create_unique_screenshot(self, dir, filename, extension):
        parts = os.path.splitext(filename)
        flags = os.O_CREAT + os.O_WRONLY + os.O_EXCL
        if hasattr(os, 'O_BINARY'):
            flags += os.O_BINARY
        idx = 1
        while 1:
            hashed_screenshot_name = self._get_hashed_screenshot_name(filename)
            hashed_screenshot_name = hashed_screenshot_name + extension
            path = os.path.join(dir, hashed_screenshot_name)
            try:
                return filename, os.fdopen(os.open(path, flags, 0666), 'w'), hashed_screenshot_name
            except OSError, e:
                if e.errno != errno.EEXIST:
                    raise
                idx += 1
                #a sanity check
                if idx > 100:
                    raise Exception('Failed to create unique name: ' + path)
                filename = '%s.%d%s' % (parts[0], idx, parts[1])

    def _get_hashed_screenshot_name(self, filename):
        hash = sha1(encode("utf-8")).hexdigest()
        return hash


class TrackerUploaderAndCommentAdderModule(Component):
    implements(IRequestHandler, INavigationContributor)

    max_size = IntOption('screenshot', 'max_size', 262144,
                         """Maximum allowed screenshot size (in bytes)""")

    def get_active_navigation_item(self, req):
        return 'tracker/tickets/'

    def get_navigation_items(self, req):
        yield 'mainnav', 'tracker', Markup('<a href="%s">Tracker</a>' % (
            self.env.href.tracker()))

    def match_request(self, req):
        match = re.match(r'/(jar-)?tracker/?.*?$',
                         req.path_info)

        if match:
            req.args['format'] = ''
            args = match.groups()
            if args[0]:
                req.args['format'] = args[0][:-1]
            return True

    def _add_comment(self, req):
        timeNow = datetime.now(utc)
        with self.env.db_transaction as db:
            if (db("""INSERT INTO ticket_change (ticket, time, author, field, newvalue)
                          VALUES (%s, %s, %s, %s, %s)
                          """, (req.args['ticketId'],
                                to_utimestamp(timeNow),
                                req.args['author'],
                                "comment",
                                req.args['comment']))):
                return True
            else:
                return False

    def process_request(self, req):

        if req.method == 'POST':
            if req.args['action'] == 'addComment':
                self._add_comment(req)
            elif req.args['action'] == 'addScreenshot':
                self._do_save(req)
        else:
            if req.args['format'] == 'jar':
                self._download_client_file(req)

        req.redirect(req.href.wiki())

    def _do_save(self, req):

        upload = req.args['screenshot']
        username = req.args['username']

        if upload.file is None:
            raise TracError("File does not exist!")
        if upload.filename is None:
            raise TracError("Filename does not exist!")
        if username is None:
            raise TracError("Username does not exist!")

        screenshot = Screenshot(self.env)
        screenshot.insert(
            upload.filenam,
            upload.file,
            username,
            upload.mouse_event_count,
            upload.keyboard_event_count,
            upload.task_id,
            upload.interval
        )

    def _download_client_file(self, req):
        from pkg_resources import resource_filename

        filename = 'tracker-1.0-SNAPSHOT-dist.zip'
        path_to_file = resource_filename(__name__, 'client') + "/" + filename

        if os.path.exists(path_to_file):
            req.send_file(path_to_file, 'application/java-archive; charset=utf-8')
            raise RequestDone()

