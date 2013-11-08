#!/bin/sh

#
# Здесь описан скрипт сборки плагина на сервере демо,
# данный скрипт запускается скриптом deploy_on_demo.sh
# и запускается только на демонастрационной машине
#

PROJECTS_DIR=~/projects
DEMO_REPO_DIR=${PROJECTS_DIR}/tracker/repo
PLUGIN_CACHE_DIR=${PROJECTS_DIR}/demo/trac-env/plugins/.python-eggs-cache/TracTracker-1.0-py2.7.egg-tmp
BUILD_NUMBER=`curl http://77.95.60.63:8080/job/tracker/lastBuild/buildNumber`

cd ${DEMO_REPO_DIR}

git clean -fd
git reset --hard
git pull
COMMIT_ID=`git log --pretty=format:'%h' -n 1`

rm -f ${PLUGIN_CACHE_DIR}/tracker/client/*.zip

cd desktop-client

mvn package -Dmaven.test.skip=true -Dproject.version=${BUILD_NUMBER}.${COMMIT_ID}

mkdir ../plugin/Tracker/tracker/client

cp --remove-destination target/*.zip ../plugin/Tracker/tracker/client/

cd ../plugin/Tracker/

python setup.py egg_info -rb${BUILD_NUMBER}.${COMMIT_ID} bdist_egg

rm ${PROJECTS_DIR}/demo/trac-env/plugins/*

cp dist/*.egg ${PROJECTS_DIR}/demo/trac-env/plugins/

trac-admin ${PROJECTS_DIR}/demo/trac-env upgrade
