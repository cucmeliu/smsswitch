package com.ikohoo.main;

import java.util.Timer;

import org.apache.log4j.Logger;

import com.ikohoo.domain.Config;
import com.ikohoo.test.DBTester;
import com.ikohoo.util.ConfigReader;

public class SMSMain {

	static Logger logger = Logger.getLogger(SMSMain.class);

	/**
	 * 1. 发送线程 1.1 定时查询数据库send表，取出未处理的（state=0)，加入到待发送列表中 1.2
	 * 查找处理已有返回值的，更新状态到数据库，并从列表中删除（低粒度线程锁定） 1.3 调用发送接口，并将返回的批次号更新列表 2. 查询状态线程
	 * 2.1 定时调用查询状态接口，处理返回的每批次的状态 2.2 更新列表的状态，有返回的状态，更新回数据库 3. 查询回复线程 3.1
	 * 定时调用查询回复接口，获取回复信息，并写入数据库recive表
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 先这样实现业务，后续可考虑用线程同步
		logger.info("=======================================");
		logger.info("==========program start================");

		Config config = ConfigReader.loadConfig();
		logger.info(config.toString());
		//System.out.println(config.toString());

		byte startModu = 0x0; // 要开启的功能，1 send, 2 recv, 4 rept, 8 test

		if (args.length == 0) {
			// 默认不开test，其他都开
			startModu = 0x7;
			System.out.println("start all true function");
		} else {
			for (int i = 0; i < args.length; i++) {
				if ("send".equals(args[i]))
					startModu |= 0x1;
				else if ("recv".equals(args[i]))
					startModu |= 0x2;
				else if ("rept".equals(args[i]))
					startModu |= 0x4;
				else if ("yunxin".equals(args[i]))
					startModu |= 0x8;
				else if ("test".equals(args[i]))
					startModu |= 0x10;
				else if ("all".equals(args[i]))
					startModu |= 0x1f;
			}
		}

		if ((startModu & 0x1) != 0) {
			// 处理发送短信进程，
			System.out.println("start send sms thread ---------->>>>> [ "
					+ config.getSendThread() + " ] thread");

			for (int i = 0; i < config.getSendThread(); i++) {
				Timer timerSendSMS = new Timer();
				SendSMSBiz biz = new SendSMSBiz(config);
				biz.setRemainder(i);
				timerSendSMS.schedule(biz, 0,
						config.getSendInterval());
			}
		}
		if ((startModu & 0x2) != 0) {
			// 处理回复短信
			System.out.println("start get reply thread <<<<<----------");
			Timer timerGetRecv = new Timer();
			timerGetRecv.schedule(new GetRecvBiz(config), 0,
					config.getRecvInterval());
		}
		if ((startModu & 0x4) != 0) {
			// 处理报告状态
			System.out.println("start get report thread <<<<<------>>>>>");
			Timer timerGetReport = new Timer();
			timerGetReport.schedule(new GetReportBiz(config), 0,
					config.getGetReportInterval());
		}

		if ((startModu & 0x8) != 0) {
			System.out.println("~~~~start yunxin thread ~~~  ");
			Timer t = new Timer();
			t.schedule(new SendSMSBizYunXin(config), 0, 
					config.getSendInterval());
		}
		
		if ((startModu & 0x10) != 0) {
			// TODO 发送信息表，测试，发布时去除下面的代码段
			System.out.println("~~~~start test thread ~~~  ");
			Timer t = new Timer();
			t.schedule(new DBTester(), 0, 3000);
		}

	}

}
