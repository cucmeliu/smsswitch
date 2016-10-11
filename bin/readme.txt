使用说明：

【重要】执行程序前请先按2、3、4配置


1. 运行程序

Windows下运行窗口界面模式运行
解压后双击执行
	startMainFrame.bat

Linux & Mac 窗口界面模式运行
执行
	./startMainFrame.sh

Linux & Mac 后台服务模式运行
	a. 将程序压缩包smsSwitch.tar.gz复制到任意有权限访问的路径，如/home/，使用命令以下命令解压：
		tar xzvf smsSwitch.tar.gz
	b. 在Linux的shell模式下，进入解压目录，按说明2，3修改配置，
		* 每个目录只能配置成触发 或 云信接口所需的参数
	
	c. 执行以下命令部署并运行程序，部署后程序会加到系统监控列表。
	触发接口
		./deploy_cf.sh
	云信接口
		./deploy_yx.sh
	
	也可以不部署，直接运行
	使用触发接口
		./startChuFa.sh
	使用云信接口
		./startYunXin.sh
 	d. 停止程序  
 --~~## 注意：会停止同一台服务器上运行的所有本程序，慎用
 		./stopSms

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
    提供了project.property.cf.tmpl, project.property.yx.tmpl，
    分别作为触发接口和云信接口的配置模板，可修改所需的之的之后，将其重命名为project.property
    
