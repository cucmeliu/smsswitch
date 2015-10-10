package com.ikohoo.main;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.SMSSendService;

public class SendSMSBiz extends TimerTask {
	static Logger logger = Logger.getLogger(SendSMSBiz.class);

	SMSSendService service = BasicFactory.getFactory().getInstance(
			SMSSendService.class);

	private Config config;

	public SendSMSBiz(Config config) {
		super();
		this.config = config;
	}

	@Override
	public void run() {

		logAndPrint("--------------Send SMS-----------------");

		
		// get data from db, 每次取1000条
		service.setConfig(config);
		long start = System.currentTimeMillis();
		List<SMSSendBean> list = service.getNewSMS(config.getSendCount());
		if (null == list || 0 == list.size()) {
			logAndPrint("No new sms to send, wait for next loop...");
		} else {
			// 增加处理：列表中没有的才加入，避免重复
			System.out.println("--->>>get sms to send: ");
			for (SMSSendBean ssb : list) {
				logAndPrint(ssb.toString());
			}
			
			logAndPrint("Get new sms: [" + list.size() + "]" + ", Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");

			start = System.currentTimeMillis();
			int succCount = service.packSend(list);

			logAndPrint("  Send succ: " + succCount + ", fail: "
					+ (list.size() - succCount) + ", Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");

			start = System.currentTimeMillis();
			int num = service.dealSentSMS(list);

			if (list.size() != 0) {
				logAndPrint("deal left list: ");
				for (SMSSendBean ssb : list) {
					logAndPrint(ssb.toString());
				}
			}

			logAndPrint("  Update send state to DB: " + num + " ，Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");
		}
		logAndPrint("---------------------------------------\n");
	}

	private void logAndPrint(String msg) {
		logger.info(msg);
		System.out.println(msg);
	}

}
