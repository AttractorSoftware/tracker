#!/usr/bin/env python
# -*- coding: utf-8 -*-


from setuptools import setup
import sys

PACKAGE = 'TracTracker'
VERSION = '1.0'

CLIENT_PACKAGE_NAME = 'tracker-1.0-SNAPSHOT-dist.zip'

setup(name=PACKAGE,
      version=VERSION,
      packages=['tracker', 'tracker.db'],
      entry_points={'trac.plugins': [
          'TracTracker.db_install = tracker.db_install',
          'TracTracker.comment = tracker.comment',
          'TracTracker.user_list = tracker.user_list',
          'TracTracker.active_tickets = tracker.active_tickets',
          'TracTracker.authorization = tracker.authorization',
          'TracTracker.screenshot_marker = tracker.screenshot_marker',
          'TracTracker.admin = tracker.admin'
      ]},
      package_data={
      'tracker': ['templates/*.html', 'templates/*.xml', 'htdocs/css/*.css', 'client/*.zip', 'htdocs/js/*.js']},
      install_requires=['trac'],
)