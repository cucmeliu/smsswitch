使用说明：
1. 运行程序
	将程序压缩包smsSwitch.tar.gz复制到任意有权限访问的路径，如/home/，使用命令以下命令解压：
		tar xzvf smsSwitch.tar.gz
	在Linux的shell模式下，进入解压目录，执行以下命令运行程序
	使用触发接口
		./startSmsSwitch.sh
	使用云信接口
		./startYunxinSmsSwitch.sh
 ~~## 停止程序  
 --注意：同一台服务器上运行多个本程序时，不能使用本命令
 		./stopSmsSwitch

2. 如需修改各种配置，直接在解压目录下进行修改

3. 修改数据库配置
3.1 修改c3p0-config.xml，
	jdbcUrl对应的为连接地址，
	数据库和本程序运行在同一台机器时，配置为：
		<property name="jdbcUrl">jdbc:mysql:///SMSSwitch</property>

	如数据库服务器地址为192.168.0.10，则配置为：
		<property name="jdbcUrl">jdbc:mysql://192.168.0.10/SMSUtil</property>
	user用户名和password密码
	默认为：root/root

4. 修改发送、接收频率等参数
    用记事本打开并修改project.property文件，内有详细说明
