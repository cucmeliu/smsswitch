#!/bin/bash

desPath=/opt/xxx
fileName=startSmsSwitch.sh
desStartFile=${desPath}/${fileName}

type=""
if [ "$1" = "cf" ];then
	type="send"
elif [ "$1" = "yx" ];then
	type="yunxin"
else
	echo "no param given"
	exit -1
fi

## 启动脚本拷贝至安装路径
cp ${fileName} $desPath

## 启动脚本
sh ${desStartFile} $type

## 完成定时任务的配置
isCronDeploy=$(cat /etc/crontab | grep "$desStartFile")
if [ -z "$isCronDeploy" ];then
	echo "*/1 * * * * root sh ${desStartFile} $type" >> /etc/crontab
	service crond restart
fi

