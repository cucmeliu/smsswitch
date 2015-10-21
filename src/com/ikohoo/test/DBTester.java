package com.ikohoo.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.SMSSendService;

public class DBTester extends TimerTask {
	static Logger logger = Logger.getLogger(DBTester.class);
	
	static SMSSendService smsSendService = BasicFactory.getFactory()
			.getInstance(SMSSendService.class);
	static int i = 0;
	//private SMSSendBean smsSend;// = new SMSSendBean();

	public static void main(String[] args) {

		System.out.println("~~~~start test thread ~~~  ");
		Timer t = new Timer();
		t.schedule(new DBTester(),  2000);
	}

	@Override
	public void run() {
		Random r = new Random();
		int sum = r.nextInt(1000);
		List<SMSSendBean> list = new ArrayList<SMSSendBean>();
		SMSSendBean smsSend;
		for (int j = 0; j < sum; j++) {
			i++;
			smsSend = new SMSSendBean();
			smsSend.setPhone(13311111111L + i + "");
			smsSend.setState(0);
			smsSend.setContent("content-" + i);
			smsSend.setIntime(new Timestamp(System.currentTimeMillis()));
			list.add(smsSend);
		}
		
		logger.info("--------------DB Tester----------------- ");
		long start = System.currentTimeMillis();
		logger.info("Insert test sms msg, count: "
				+ smsSendService.insert2DB(list)
				+ ", Cost time: " + (System.currentTimeMillis()-start) + " ms");

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
