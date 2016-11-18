package com.ikohoo.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.SMSSendService;

public class DBTester extends TimerTask {
	static Logger logger = Logger.getLogger(DBTester.class);

	static SMSSendService smsSendService = BasicFactory.getFactory().getInstance(SMSSendService.class);
	static int i = 0;

	private Config config;
	// private SMSSendBean smsSend;// = new SMSSendBean();

	private String content;

	private String phone;

	private boolean sendOne=false;

	public DBTester(Config config, boolean sendOnce) {
		this.config = config;
		this.sendOne = sendOnce;
		//phone = config.getDbTestPhone()+"";
		//content = config.getDbTestContent();
	}

	public static void main(String[] args) {

		System.out.println("~~~~start test thread ~~~  ");
		Timer t = new Timer();
		t.schedule(new DBTester(new Config(), true), 
				new Config().getDbTestInterval());
	}

	public void sendOnce() {
		this.sendOne = false;

		Config config = new Config();
		
		Timer t = new Timer();
		t.schedule(new DBTester(config, sendOne),
				config.getDbTestInterval());
	}
	
	/**
	 * 發一條短信
	 * @param phone
	 * @param content
	 */
	public void sendOne(String phone, String content) {
		this.sendOne = true;

		Config config = new Config();
		config.setDbTestPhone(Long.parseLong(phone));
		config.setDbTestContent(content);
		
		Timer t = new Timer();
		t.schedule(new DBTester(config, sendOne), 
				config.getDbTestInterval());
	}

	@Override
	public void run() {

		Random r = new Random();
		int sum = r.nextInt(config.getDbTestCount());
		List<SMSSendBean> list = new ArrayList<SMSSendBean>();
		SMSSendBean smsSend;

		if (sendOne) {
			smsSend = new SMSSendBean();
			smsSend.setPhone(config.getDbTestPhone()+"");
			smsSend.setState(0);
			if (0==config.getDbTestNo())
				smsSend.setContent(config.getDbTestContent() + config.getDbTestSign());
			else
				smsSend.setContent(config.getDbTestContent() + "，第-" + i + "-条" + config.getDbTestSign());
			smsSend.setIntime(new Timestamp(System.currentTimeMillis()));
			list.add(smsSend);
			logger.info(smsSend);
		} else {
			for (int j = 0; j < sum; j++) {
				i++;
				smsSend = new SMSSendBean();
				smsSend.setPhone(config.getDbTestPhone() + i + "");
				smsSend.setState(0);
				if (0==config.getDbTestNo())
					smsSend.setContent(config.getDbTestContent() + config.getDbTestSign());
				else
					smsSend.setContent(config.getDbTestContent() + "，第-" + i + "-条" + config.getDbTestSign());
				smsSend.setIntime(new Timestamp(System.currentTimeMillis()));
				list.add(smsSend);
			}
		}

		logger.info("--------------DB Tester----------------- ");
		long start = System.currentTimeMillis();
		logger.info("Insert test sms msg, count: " + smsSendService.insert2DB(list) + ", Cost time: "
				+ (System.currentTimeMillis() - start) + " ms");

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
