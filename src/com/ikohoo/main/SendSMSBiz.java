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

	public int getRemainder() {
		return remainder;
	}

	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}

	private int remainder = 0;  // 取模的余数
	//private int mod = 1;   // 对mod取模

	public SendSMSBiz(Config config) {
		super();
		this.config = config;
	}

	@Override
	public void run() {

		logAndPrint("thread # [ "+ remainder +" ] --------Send SMS--------");

		
		// get data from db, 每次取1000条
		service.setConfig(config);
		long start = System.currentTimeMillis();
		//List<SMSSendBean> list = service.getNewSMS(config.getSendCount());
		//
		List<SMSSendBean> list = service.getNewSMS(config.getSendCount(), config.getSendThread(), remainder);
		
		
		if (null == list || 0 == list.size()) {
			logAndPrint("# [ "+ remainder +" ] No new sms to send, wait for next loop...");
		} else {
			// 增加处理：列表中没有的才加入，避免重复
			//System.out.println("--->>>get sms to send: ");
//			StringBuilder sb = new StringBuilder();
//			for (SMSSendBean ssb : list) {
//				sb.append(ssb.toString()).append("\n");
//				
//			}
//			logAndPrint("# [ "+ remainder +" ] get: " + sb.toString());
			
			logAndPrint("# [ "+ remainder +" ] Get new sms: [" + list.size() + "]" + ", Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");

			start = System.currentTimeMillis();
			//int succCount = service.packSend(list);
			int succCount = service.sendSMSList(list);

			logAndPrint("# [ "+ remainder +" ]    Send succ: " + succCount + ", fail: "
					+ (list.size() - succCount) + ", Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");

			start = System.currentTimeMillis();
			int num = service.dealSentSMS(list);

			if (list.size() != 0) {
				logAndPrint("# [ "+ remainder +" ]    deal left list: ");
				for (SMSSendBean ssb : list) {
					logAndPrint(ssb.toString());
				}
			}

			logAndPrint("# [ "+ remainder +" ]    Update send state to DB: " + num + ", Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");
		}
		logAndPrint("---------------------------------------\n");
	}

	private void logAndPrint(String msg) {
		logger.info(msg);
		//System.out.println(msg);
	}

}
