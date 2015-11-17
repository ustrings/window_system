#!/bin/sh

S=`readlink -f $0`
D=`dirname $S`


export LC_ALL=zh_CN.utf8

for f in ${D}/lib/*.jar 
do
  export CLASSPATH=$f:$CLASSPATH
done

[ -d $D/classes ] && export CLASSPATH=$D/classes:$CLASSPATH
[ -d $HOME/bin  ] && export CLASSPATH=$HOME/bin:$CLASSPATH
[ -d $D/bin     ] && export CLASSPATH=$D/bin:$CLASSPATH
  
echo $CLASSPATH

nohup  java com.vaolan.adtimer.tdriver.NHTAutoChangeTimerDriver &
