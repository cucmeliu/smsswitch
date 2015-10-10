package com.ikohoo.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.SMSSendService;

public class DBTester extends TimerTask {
	static SMSSendService smsSendService = BasicFactory.getFactory()
			.getInstance(SMSSendService.class);
//	static int i = 0;
	//private SMSSendBean smsSend;// = new SMSSendBean();

	public static void main(String[] args) {

		System.out.println("~~~~start test thread ~~~  ");
		Timer t = new Timer();
		t.schedule(new DBTester(),  2000);
	}

	@Override
	public void run() {
		Random r = new Random();
		int sum = r.nextInt(100);
		List<SMSSendBean> list = new ArrayList<SMSSendBean>();
		SMSSendBean smsSend;
		for (int j = 0; j < sum; j++) {
			smsSend = new SMSSendBean();
			smsSend.setPhone(13311111111L + j + "");
			smsSend.setState(0);
			smsSend.setContent("content-" + j);
			smsSend.setIntime(new Timestamp(System.currentTimeMillis()));
			list.add(smsSend);
		}
		
		System.out.println("--------------DB Tester----------------- \n "
				+ "Insert test sms msg, count: "
				+ smsSendService.insert2DB(list)
				+ "\n----------------------------");

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
