#!/usr/bin/env python
# -*- coding: utf-8 -*-


from setuptools import setup
import sys

PACKAGE = 'TracTracker'
VERSION = '1.0dev.'

setup(name=PACKAGE,
      version=VERSION,
      packages=['tracker', 'tracker.db'],
      entry_points={'trac.plugins': [
          'TracTracker.db_install = tracker.db_install',
          'TracTracker.comment = tracker.comment',
          'TracTracker.user_list = tracker.user_list',
          'TracTracker.user_report = tracker.user_report',
          'TracTracker.active_tickets = tracker.active_tickets',
          'TracTracker.authorization = tracker.authorization',
          'TracTracker.screenshot_marker = tracker.screenshot_marker',
          'TracTracker.admin = tracker.admin'
      ]},
      package_data={
      'tracker': ['templates/*.html', 'templates/*.xml', 'htdocs/css/*.css', 'htdocs/img/*.png', 'client/*.zip', 'htdocs/js/*.js']},
      install_requires=['trac'],
)