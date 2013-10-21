python setup.py bdist_egg
cp -i dist/*.egg ../../../trac-env/plugins/
trac-admin ../../../trac-env upgrade