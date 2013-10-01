python setup.py bdist_egg
cp -i /home/ashedrin/projects/tracker/plugin/Tracker/dist/*.egg /home/ashedrin/projects/trac-env/plugins/
trac-admin /home/ashedrin/projects/trac-env upgrade