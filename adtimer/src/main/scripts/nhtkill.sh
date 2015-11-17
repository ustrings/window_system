#!/bin/sh

PID=`ps -ef |grep NHTAutoChangeTimerDriver |grep -v grep |awk '{print $2}' |head -n 1`
echo $PID;
if [ -n "$PID" ];
then
kill -9 $PID
else
echo 'no server'
exit 0
fi
