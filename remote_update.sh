#!/bin/sh

#
# Здесь описан скрипт сборки плагина на сервере демо,
# данный скрипт запускается скриптом deploy_on_demo.sh
# и запускается только на демонастрационной машине
#

PROJECTS_DIR=~/projects
DEMO_REPO_DIR=${PROJECTS_DIR}/tracker/repo
PLUGIN_CACHE_DIR=${PROJECTS_DIR}/demo/trac-env/plugins/.python-eggs-cache/TracTracker-1.0-py2.7.egg-tmp

cd ${DEMO_REPO_DIR}

echo ${JAVA_HOME}

git clean -fd
git reset --hard

rm -f ${PLUGIN_CACHE_DIR}/tracker/client/*.zip

cd desktop-client

mvn assembly:assembly -Dmaven.test.skip=true

mkdir ../plugin/Tracker/tracker/client
cp --remove-destination target/tracker-1.0-SNAPSHOT-dist.zip ../plugin/Tracker/tracker/client/

cd ../plugin/Tracker/

python setup.py bdist_egg

cp -u dist/*.egg ${PROJECTS_DIR}/demo/trac-env/plugins/

trac-admin ${PROJECTS_DIR}/demo/trac-env upgrade
