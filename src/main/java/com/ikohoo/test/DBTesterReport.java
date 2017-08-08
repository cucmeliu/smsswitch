package com.ikohoo.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.ReportGetBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.ReportGetService;
import com.ikohoo.service.SMSSendService;
import com.ikohoo.util.ConfigReader;

public class DBTesterReport extends TimerTask {
	static Logger logger = Logger.getLogger(DBTesterReport.class);

	static SMSSendService smsSendService = BasicFactory.getFactory().getInstance(SMSSendService.class);
	
	static ReportGetService reportGetService = BasicFactory.getFactory().getInstance(ReportGetService.class);
	static int i = 0;

	private Config config;
	// private SMSSendBean smsSend;// = new SMSSendBean();

	private String content;

	private String phone;

	private boolean sendOne=false;

	public DBTesterReport(Config config, boolean sendOnce) {
		this.config = config;
		this.sendOne = sendOnce;
		//phone = config.getDbTestPhone()+"";
		//content = config.getDbTestContent();
	}

	public static void main(String[] args) {

		System.out.println("~~~~start test thread ~~~  ");
		
		Timer t = new Timer();
		t.schedule(new DBTesterReport(ConfigReader.loadConfig(), true), 
				new Config().getDbTestInterval());
	}

	public void sendOnce() {
		this.sendOne = false;

		Config config = new Config();
		
		Timer t = new Timer();
		t.schedule(new DBTesterReport(config, sendOne),
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
		t.schedule(new DBTesterReport(config, sendOne), 
				config.getDbTestInterval());
	}

	@Override
	public void run() {
		reportGetService.setConfig(config);
		Random r = new Random();
		int sum = r.nextInt(config.getDbTestCount());
		/*List<SMSSendBean> list = new ArrayList<SMSSendBean>();
		SMSSendBean smsSend;

		if (sendOne) {
			smsSend = new SMSSendBean();
			smsSend.setPhone(config.getDbTestPhone()+"");
			smsSend.setState(0);
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
				smsSend.setContent(config.getDbTestContent() + "，第-" + i + "-条" + config.getDbTestSign());
				smsSend.setIntime(new Timestamp(System.currentTimeMillis()));
				list.add(smsSend);
			}
		}*/
		String[] msg = {"one", "two", "three", "four", "five"};
		List<ReportGetBean> list = new ArrayList<ReportGetBean>();
		ReportGetBean bean;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int j = 0; j < sum; j++) {
			i++;
			bean = new ReportGetBean();
			bean.setPhone(config.getDbTestPhone() + i + "");
			bean.setStatcode(String.valueOf(r.nextInt(100)));
			bean.setStatmsg(msg[r.nextInt(5)]);
			bean.setSmsid(String.valueOf(UUID.randomUUID().toString().hashCode()));
			bean.setSendtime(Timestamp.valueOf(df.format(new Date())));
			list.add(bean);
		}
		logger.info("--------------DB Tester----------------- ");
		long start = System.currentTimeMillis();
		logger.info("Insert test sms msg, count: " + reportGetService.insert2DB(list) + ", Cost time: "
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
