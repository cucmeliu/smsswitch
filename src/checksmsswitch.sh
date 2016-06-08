#!/bin/bash

count=`ps -ef | grep SMSMain | grep yunxin | -v 'grep' | wc -l`
if [ $count -eq 0 ]
then
	echo 'yunxin process doesnot start, starting yunxin process......'
	java -Dfile.encoding=UTF-8 -classpath .:./lib/c3p0-0.9.1.2.jar:./lib/commons-beanutils-1.8.3.jar:./lib/commons-codec-1.6.jar:./lib/commons-dbutils-1.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-logging-1.1.3.jar:./lib/dom4j-1.6.1.jar:./lib/fluent-hc-4.3.3.jar:./lib/httpclient-4.3.3.jar:./lib/httpclient-cache-4.3.3.jar:./lib/httpcore-4.3.2.jar:./lib/httpmime-4.3.3.jar:./lib/log4j-1.2.17.jar:./lib/mysql-connector-java-5.0.8-bin.jar com.ikohoo.main.SMSMain yunxin &
fi

count=`ps -ef | grep SMSMain | grep rept | -v 'grep' | wc -l`
if [ $count -eq 0 ]
then
	echo 'rept process doesnot start, starting rept process......'
	java -Dfile.encoding=UTF-8 -classpath .:./lib/c3p0-0.9.1.2.jar:./lib/commons-beanutils-1.8.3.jar:./lib/commons-codec-1.6.jar:./lib/commons-dbutils-1.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-logging-1.1.3.jar:./lib/dom4j-1.6.1.jar:./lib/fluent-hc-4.3.3.jar:./lib/httpclient-4.3.3.jar:./lib/httpclient-cache-4.3.3.jar:./lib/httpcore-4.3.2.jar:./lib/httpmime-4.3.3.jar:./lib/log4j-1.2.17.jar:./lib/mysql-connector-java-5.0.8-bin.jar com.ikohoo.main.SMSMain rept &
fi

count=`ps -ef | grep SMSMain | grep recv | -v 'grep' | wc -l`
if [ $count -eq 0 ]
then
	cho 'recv process doesnot start, starting recv process......'
        java -Dfile.encoding=UTF-8 -classpath .:./lib/c3p0-0.9.1.2.jar:./lib/commons-beanutils-1.8.3.jar:./lib/commons-codec-1.6.jar:./lib/commons-dbutils-1.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-logging-1.1.3.jar:./lib/dom4j-1.6.1.jar:./lib/fluent-hc-4.3.3.jar:./lib/httpclient-4.3.3.jar:./lib/httpclient-cache-4.3.3.jar:./lib/httpcore-4.3.2.jar:./lib/httpmime-4.3.3.jar:./lib/log4j-1.2.17.jar:./lib/mysql-connector-java-5.0.8-bin.jar com.ikohoo.main.SMSMain recv &
fi

