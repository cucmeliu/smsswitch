#!/bin/bash

Usage() (
	echo 'Usage: '
	echo '	./startSms.sh param '
	echo '	which param should be: cf or yx '
)

if [ $# -eq 0 ]
then	
	Usage 
	exit -1
fi

type=""

if [ $1='cf' ]
then 
	type="send"
elif [ $1='yx' ]
then
	type="yunxin"
else
	Usage
	exit -1
fi

echo "start send"
java -Dfile.encoding=UTF-8 -classpath .:./lib/c3p0-0.9.1.2.jar:./lib/commons-beanutils-1.8.3.jar:./lib/commons-codec-1.6.jar:./lib/commons-dbutils-1.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-logging-1.1.3.jar:./lib/dom4j-1.6.1.jar:./lib/fluent-hc-4.3.3.jar:./lib/httpclient-4.3.3.jar:./lib/httpclient-cache-4.3.3.jar:./lib/httpcore-4.3.2.jar:./lib/httpmime-4.3.3.jar:./lib/log4j-1.2.17.jar:./lib/mysql-connector-java-5.0.8-bin.jar com.ikohoo.main.SMSMain $type &

echo "start recv"
java -Dfile.encoding=UTF-8 -classpath .:./lib/c3p0-0.9.1.2.jar:./lib/commons-beanutils-1.8.3.jar:./lib/commons-codec-1.6.jar:./lib/commons-dbutils-1.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-logging-1.1.3.jar:./lib/dom4j-1.6.1.jar:./lib/fluent-hc-4.3.3.jar:./lib/httpclient-4.3.3.jar:./lib/httpclient-cache-4.3.3.jar:./lib/httpcore-4.3.2.jar:./lib/httpmime-4.3.3.jar:./lib/log4j-1.2.17.jar:./lib/mysql-connector-java-5.0.8-bin.jar com.ikohoo.main.SMSMain recv &

echo "start rept"
java -Dfile.encoding=UTF-8 -classpath .:./lib/c3p0-0.9.1.2.jar:./lib/commons-beanutils-1.8.3.jar:./lib/commons-codec-1.6.jar:./lib/commons-dbutils-1.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-logging-1.1.3.jar:./lib/dom4j-1.6.1.jar:./lib/fluent-hc-4.3.3.jar:./lib/httpclient-4.3.3.jar:./lib/httpclient-cache-4.3.3.jar:./lib/httpcore-4.3.2.jar:./lib/httpmime-4.3.3.jar:./lib/log4j-1.2.17.jar:./lib/mysql-connector-java-5.0.8-bin.jar com.ikohoo.main.SMSMain rept &

# deal with check
path="./"
file=${path}checksmsswitch.sh
echo $file
# replace cron.d
shellStr="aaa"  #'*/1 * * * * root '${file} 
echo $shellStr > /etc/cron.d/checksms

# write to check file
touch $file
chmod +x $file
echo '#!/bin/sh' > $file

shellStr="count=\`ps -ef | grep 'SMSMain' | grep $type | grep -v 'grep' | wc -l\` \n
if [ \$count -eq 0 ] \n
then \n
	echo \"starting send process......\" \n
	java -Dfile.encoding=UTF-8 -classpath .:./lib/c3p0-0.9.1.2.jar:./lib/commons-beanutils-1.8.3.jar:./lib/commons-codec-1.6.jar:./lib/commons-dbutils-1.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-logging-1.1.3.jar:./lib/dom4j-1.6.1.jar:./lib/fluent-hc-4.3.3.jar:./lib/httpclient-4.3.3.jar:./lib/httpclient-cache-4.3.3.jar:./lib/httpcore-4.3.2.jar:./lib/httpmime-4.3.3.jar:./lib/log4j-1.2.17.jar:./lib/mysql-connector-java-5.0.8-bin.jar com.ikohoo.main.SMSMain $type & \n
fi"
echo -e  $shellStr >> $file

shellStr="count=\`ps -ef | grep 'SMSMain' | grep 'recv' | grep -v 'grep' | wc -l\` \n
if [ \$count -eq 0 ] \n
then \n
	echo \"starting recv process......\" \n
	java -Dfile.encoding=UTF-8 -classpath .:./lib/c3p0-0.9.1.2.jar:./lib/commons-beanutils-1.8.3.jar:./lib/commons-codec-1.6.jar:./lib/commons-dbutils-1.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-logging-1.1.3.jar:./lib/dom4j-1.6.1.jar:./lib/fluent-hc-4.3.3.jar:./lib/httpclient-4.3.3.jar:./lib/httpclient-cache-4.3.3.jar:./lib/httpcore-4.3.2.jar:./lib/httpmime-4.3.3.jar:./lib/log4j-1.2.17.jar:./lib/mysql-connector-java-5.0.8-bin.jar com.ikohoo.main.SMSMain recv & \n
fi"
echo -e  $shellStr >> $file

shellStr="count=\`ps -ef | grep 'SMSMain' | grep 'rept' | grep -v 'grep' | wc -l\` \n
if [ \$count -eq 0 ] \n
then \n
	echo \"starting rept process......\" \n
	java -Dfile.encoding=UTF-8 -classpath .:./lib/c3p0-0.9.1.2.jar:./lib/commons-beanutils-1.8.3.jar:./lib/commons-codec-1.6.jar:./lib/commons-dbutils-1.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-logging-1.1.3.jar:./lib/dom4j-1.6.1.jar:./lib/fluent-hc-4.3.3.jar:./lib/httpclient-4.3.3.jar:./lib/httpclient-cache-4.3.3.jar:./lib/httpcore-4.3.2.jar:./lib/httpmime-4.3.3.jar:./lib/log4j-1.2.17.jar:./lib/mysql-connector-java-5.0.8-bin.jar com.ikohoo.main.SMSMain rept & \n
fi"
echo -e  $shellStr >> $file

