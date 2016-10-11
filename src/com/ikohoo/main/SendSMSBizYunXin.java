package com.ikohoo.main;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.SMSSendService;

public class SendSMSBizYunXin extends TimerTask{

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

	public SendSMSBizYunXin(Config config) {
		super();
		this.config = config;
	}

	@Override
	public void run() {

		logAndPrint("thread # [ "+ remainder +" ] --------Send SMS YunXin--------");

		
		// get data from db, 每次取1000条
		service.setConfig(config);
		long start = System.currentTimeMillis();
		//List<SMSSendBean> list = service.getNewSMS(config.getSendCount());
		//
		List<SMSSendBean> list = service.getNewSMS(config.getSendCount(), 
				config.getSendThread(), 
				remainder);
		
		
		if (null == list || 0 == list.size()) {
			logAndPrint("# [ "+ remainder +" ] No new sms to send, wait for next loop...");
		} else {
//			StringBuilder sb = new StringBuilder();
//			for (SMSSendBean ssb : list) {
//				sb.append(ssb.toString()).append("\n");
//			}
//			logAndPrint("# [ "+ remainder +" ] get: " + sb.toString());
			
			logAndPrint("# [ "+ remainder +" ] Get new sms: [" + list.size() + "]" + ", Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");
			
			int pos=0;
			String signName="";
			
			
			for (SMSSendBean ssb: list) {
				//System.out.println("rep--cont: " + ssb.getContent().replace(">", "-") );
				// 网聚宝需求，前置提交转后置，并把>转为》
				ssb.setContent(ssb.getContent().replace(">", "》")
						.replace("\\u005c", "、"));
				
				// 有的用户懒的签名都不想带，X
				if (0==config.getIsSign())
					ssb.setContent(ssb.getContent()+config.getSign());
				
				// 前置簽名轉為后置
				if (1==config.getIsSignHead())
					if (ssb.getContent().startsWith("【")) {
						pos=ssb.getContent().indexOf("】");
						signName = ssb.getContent().substring(0, pos+1);
	
						ssb.setContent((ssb.getContent().substring(pos+1, 
								ssb.getContent().length())+signName)
								
								);
					}
				//System.out.println("sign name invert: " + ssb.toString());
			}


			start = System.currentTimeMillis();
			
			int succCount = service.sendYunXin(list);

			logAndPrint("# [ "+ remainder +" ]    Send succ: " + succCount + ", fail: "
					+ (list.size() - succCount) + ", Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");

			start = System.currentTimeMillis();
			
			// 删除原记录，插入新表
			int num = service.dealSentSMSYunXin(list);
			
			// 更新原记录
			//int num = service.dealSentSMS(list);

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
