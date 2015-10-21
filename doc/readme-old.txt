1. 运行程序
＃ (默认会启动：发送短信、收回复短信、查状态报告三个模块)
  java -jar SMSSwitch.jar

	激活某一个或某几个模块，可带参数执行
  		java -jar SMSSwitch.jar 参数1 参数2 ...
	参数可为：
		sendsms：发送短信
		getrecv：收回复短信
		getreport：查状态报告
		all：［慎用］包括写测试数据库的所有模块：＊＊  ，all参数将开启每秒向send表写100条记录
	例如，只开启收回复短信和查状态报告，则命令为：
 		java -jar SMSSwitch.jar getrecv getreport

2. 如修修改各种配置，需先将jar包解压，修改相应参数后重新封包
2.1 解开jar文件
	用解压软件解压SMSSwitch.jar
	
3. 修改数据库配置
3.1 修改c3p0-config.xml，
	jdbcUrl对应的为连接地址，如数据库服务器地址为192.168.0.10，
	则配置为<property name="jdbcUrl">jdbc:mysql:///SMSUtil</property>
	user用户名和password密码
	默认为：root/root
	
4. 修改发送、接收频率等参数
4.1 用记事本打开并修改project.property文件，内有详细说明
	
5. 重新封装jar包
	进入解压后的目录，一般目录名为SMSSwitch
	复制以下命令并执行：
   	jar cvfm ../SMSSwitch.jar META-INF/MANIFEST.MF .
   	则会在上一级目录重新生成SMSSwitch.jar
   	