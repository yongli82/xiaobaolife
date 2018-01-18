#!/bin/bash

DATE=$(date '+%Y%m%d')
WORK_DIR='/home/yongli/xiaobao'
LOG_FILE=${WORK_DIR}/app-${DATE}.log
WAR_NAME="xiaobaolife-0.0.1-SNAPSHOT.war"

function restart(){
    ps -ef | grep java | grep "${WAR_NAME}" | awk '{ print $2 }' | while read vara
    do
        kill -9 $vara
    done

    nohup ${WORK_DIR}/${WAR_NAME} 2>&1 > ${LOG_FILE} &
}

echo "Restart ${WAR_NAME} ..."
restart
echo "Complete"
exit 0


