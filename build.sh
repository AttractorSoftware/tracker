#!/bin/sh
PROJECTS_HOME=~/projects

# building plugin and starting server
TRACKER_HOME=$PROJECTS_HOME/tracker/plugin/Tracker
cd $TRACKER_HOME
python setup.py bdist_egg

rm $PROJECTS_HOME/trac-env/plugins/*.egg

cp -rf $TRACKER_HOME/dist/*.egg $PROJECTS_HOME/trac-env/plugins/

trac-admin $PROJECTS_HOME/trac-env upgrade
tracd -p 8000 --basic-auth="${PROJECTS_HOME}/trac-env,${PROJECTS_HOME}/trac-env/.htpasswd,trac-env" $PROJECTS_HOME/trac-env &

# build client and start tests

PROJECTS_HOME=~/projects/tracker
CLIENT_HOME=$PROJECTS_HOME/desktop-client

cd $CLIENT_HOME

mvn clean test





