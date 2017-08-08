#!/bin/bash

Usage() 
{
	echo 'Usage: '
	echo '	./startSms.sh param '
	echo '	which param should be: chufa or yunxin '
}

if [ $# -eq 0 ];then	
	Usage 
	exit -1
fi

type=""

if [ "$1" = "yunxin" ];then
	type="yunxin"
elif [ "$1" = "chufa" ];then
	type="send"
else
	Usage
	exit -1
fi

#baseDir=$(cd "$(dirname "$0")"; pwd)
baseDir=.
LIB=${baseDir}
for lib in `ls ${baseDir}/lib`
do
    LIB=${LIB}:${baseDir}/lib/${lib}
done

isSendExist=$(ps -efww | grep "com.ikohoo.main.SMSMain $type" | grep -v grep)
if [ -z "$isSendExist" ];then
	echo "start send"
	java -Dfile.encoding=UTF-8 -classpath $LIB com.ikohoo.main.SMSMain $type &
fi

isRecvExist=$(ps -efww | grep "com.ikohoo.main.SMSMain recv" | grep -v grep)
if [ -z "$isRecvExist" ];then
	echo "start recv"
	java -Dfile.encoding=UTF-8 -classpath $LIB com.ikohoo.main.SMSMain recv &
fi

isReptExist=$(ps -efww | grep "com.ikohoo.main.SMSMain rept" | grep -v grep)
if [ -z "$isRecvExist" ];then
	echo "start rept"
	java -Dfile.encoding=UTF-8 -classpath $LIB com.ikohoo.main.SMSMain rept &
fi

