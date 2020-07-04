#!/bin/bash

# rebuild commons
path=`pwd`
cd /usr/local/projects/commons
git pull --all
mvn clean install
cd $path

# rebuild project
cd ..
git pull --all
mvn clean package
cd run
. ./stop.sh
. ./update.sh
. ./start.sh
