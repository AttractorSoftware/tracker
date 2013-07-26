from setuptools import setup

PACKAGE = 'TracTracker'
VERSION = '1.0'

setup(name=PACKAGE,
      version=VERSION,
      packages=['tracker'],
      entry_points={'trac.plugins': '%s = tracker' % PACKAGE},
      requires=['trac', 'configglue', 'twisted'],
      package_data={'tracker': ['templates/*.html']},
)