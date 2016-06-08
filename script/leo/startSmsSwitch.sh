#!/bin/bash

Usage() 
{
	echo 'Usage: '
	echo '	./startSms.sh param '
	echo '	which param should be: cf or yx '
}

if [ $# -eq 0 ];then	
	Usage 
	exit -1
fi

type=""

if [ "$1" = "cf" ];then
	type="send"
elif [ "$1" = "yx" ];then
	type="yunxin"
else
	Usage
	exit -1
fi

#baseDir=$(cd "$(dirname "$0")"; pwd)
baseDir=.
LIB=${baseDir}
for lib in `ls ${baseDir}/lib`
do
    LIB=${LIB}:${lib}
done


echo "start send"
isSendExist=$(ps -efww | grep "com.ikohoo.main.SMSMain $type" | grep -v grep)
if [ -z "$isSendExist" ];then
	java -Dfile.encoding=UTF-8 -classpath $LIB com.ikohoo.main.SMSMain $type &
fi


echo "start recv"
isRecvExist=$(ps -efww | grep "com.ikohoo.main.SMSMain recv" | grep -v grep)
if [ -z "$isRecvExist" ];then
	java -Dfile.encoding=UTF-8 -classpath $LIB com.ikohoo.main.SMSMain recv &
fi

echo "start rept"
isReptExist=$(ps -efww | grep "com.ikohoo.main.SMSMain rept" | grep -v grep)
if [ -z "$isRecvExist" ];then
	java -Dfile.encoding=UTF-8 -classpath $LIB com.ikohoo.main.SMSMain rept &
fi

