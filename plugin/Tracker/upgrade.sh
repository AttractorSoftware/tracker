rm ~/trac-env/plugins/TracTracker-1.0dev.-py2.7.egg
python setup.py bdist_egg
cp -i dist/*.egg ~/trac-env/plugins/
trac-admin ~/trac-env upgrade

tracd -p 8000 --basic-auth="trac-env,../../../../trac-env/.htpasswd,Dev Env Rem" ~/trac-env -s

