#!/bin/bash

DATE=$(date '+%Y%m%d')
WORK_DIR='/home/yongli/xiaobao'
LOG_FILE=${WORK_DIR}/app-${DATE}.log
WAR_NAME="xiaobaolife-0.0.1-SNAPSHOT.war"

echo "build a runnable package..."
./gradlew -Pprod bootRepackage

echo "scp package to remote server"
sshpass -p justdoit scp ./build/libs/xiaobaolife-0.0.1-SNAPSHOT.war  yongli@203.76.208.13:~/xiaobao/

echo "restart war"
sshpass -p justdoit ssh yongli@203.76.208.13 "cd /home/yongli/xiaobao;bash restart.sh"

echo "Complete"

sleep 120

open "http://xiaobao.life/"

