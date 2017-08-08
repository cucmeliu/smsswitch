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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.ikohoo.constant.Constants;
import com.ikohoo.domain.Config;
import com.ikohoo.domain.ReportGetBean;
import com.ikohoo.domain.SMSRecvBean;
import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.main.GetRecvBiz;
import com.ikohoo.main.GetReportBiz;
import com.ikohoo.main.SendSMSBiz;
import com.ikohoo.service.ReportGetService;
import com.ikohoo.service.SMSRecvService;
import com.ikohoo.service.SMSSendService;
import com.ikohoo.util.ConfigReader;
import com.ikohoo.util.DateTimeUtils;

public class DBTesterSend extends TimerTask {
	static Logger logger = Logger.getLogger(DBTesterSend.class);

	static SMSSendService smsSendService = BasicFactory.getFactory().getInstance(SMSSendService.class);
	static ReportGetService reportGetService = BasicFactory.getFactory().getInstance(ReportGetService.class);
	static SMSRecvService smsRecvService = BasicFactory.getFactory().getInstance(SMSRecvService.class);
	static int i = 0;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Config config;
	// private SMSSendBean smsSend;// = new SMSSendBean();

	private String content;

	private String phone;

	private boolean sendOne=false;

	public DBTesterSend(Config config, boolean sendOnce) {
		this.config = config;
		this.sendOne = sendOnce;
		//phone = config.getDbTestPhone()+"";
		//content = config.getDbTestContent();
	}
	
	public static BlockingQueue<String> bq = new LinkedBlockingQueue<String>();

	public static void main(String[] args) {
		reportGetService.setConfig(ConfigReader.loadConfig());
		smsRecvService.setConfig(ConfigReader.loadConfig());
		System.out.println("~~~~start test thread ~~~  ");
		
		/*Timer rt = new Timer();
		List<SMSRecvBean> recvs = new ArrayList<SMSRecvBean>(1000);
		
		rt.schedule(new TimerTask(){

			@Override
			public void run() {
				for(int i = 0; i < 100000; i++){
					SMSRecvBean recv = new SMSRecvBean();
					recv.setMobile(18321704496L + i + "");
					recv.setContent("我回复了"+i);
					recv.setMo_time(DateTimeUtils.format(new Date()));
					recvs.add(recv);
					if(recvs.size() == 1000){
						long b= System.currentTimeMillis();
						smsRecvService.insert2DB(recvs);
						long a = System.currentTimeMillis();
						System.out.println(a-b);
						recvs.clear();
					}
				}
			}
			
		}, 
				2000);*/
		
		/*Timer rt = new Timer();
		List<ReportGetBean> reports = new ArrayList<ReportGetBean>(1000);
		
		rt.schedule(new TimerTask(){

			@Override
			public void run() {
				
				while(true){
					String val = bq.poll();
					if(val != null && !"".equals(val)){
						String[] vs = val.split(Constants.SEPARATOR_COMMA);
						ReportGetBean report = new ReportGetBean();
						report.setMobile(vs[0]);
						report.setRpt_code(vs[1]);
						report.setRpt_time(DateTimeUtils.format(new Date()));
						reports.add(report);
						if(reports.size() == 1000){
							reportGetService.insert2DB(reports);
							reports.clear();
						}
					}else{
						if(!reports.isEmpty()){
							reportGetService.insert2DB(reports);
							reports.clear();
						}
					}
				}
				
				
			}
			
		}, 
				20000, 1);*/
		
		Timer t = new Timer();
		/*t.schedule(new GetRecvBiz(ConfigReader.loadConfig()), 
				new Config().getDbTestInterval());*/
		/*t.schedule(new GetReportBiz(ConfigReader.loadConfig()), 
				new Config().getDbTestInterval());*/
		/*t.schedule(new SendSMSBiz(ConfigReader.loadConfig()), 
				new Config().getDbTestInterval());*/
		t.schedule(new DBTesterSend(ConfigReader.loadConfig(), false), 
				new Config().getDbTestInterval());
	}

	public void sendOnce() {
		this.sendOne = false;

		Config config = new Config();
		
		Timer t = new Timer();
		t.schedule(new DBTesterSend(config, sendOne),
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
		t.schedule(new DBTesterSend(config, sendOne), 
				config.getDbTestInterval());
	}

	@Override
	public void run() {
		smsSendService.setConfig(config);
		Random r = new Random();
		int sum = r.nextInt(config.getDbTestCount());
		List<SMSSendBean> list = new ArrayList<SMSSendBean>();
		
		//list = smsSendService.getNewSMS(200);
		//list = smsSendService.getNewSMS(100, 10, 3);
		/*SMSSendBean bean = new SMSSendBean();
		bean.setId(5);
		bean.setState(0);
		bean.setIntime(Timestamp.valueOf("2017-05-27 15:55:48"));
		bean.setStatcode("5");
		list.add(bean);
		bean = new SMSSendBean();
		bean.setId(6);
		bean.setState(0);
		bean.setIntime(Timestamp.valueOf("2017-05-27 15:55:48"));
		bean.setStatcode("6");
		list.add(bean);
		bean = new SMSSendBean();
		bean.setId(7);
		bean.setState(1);
		bean.setIntime(Timestamp.valueOf("2017-05-27 15:55:48"));
		bean.setStatcode("7");
		list.add(bean);
		bean = new SMSSendBean();
		bean.setId(8);
		bean.setState(1);
		bean.setIntime(Timestamp.valueOf("2017-05-27 15:55:49"));
		bean.setStatcode("8");
		list.add(bean);
		
		int[] stat = smsSendService.updateState2DB(list);*/
		
		//smsSendService.dealSentSMSYunXin(list);
		//System.out.println(list.size());
		SMSSendBean smsSend = null;
		
		if (sendOne) {
			smsSend = new SMSSendBean();
			smsSend.setPhone(config.getDbTestPhone()+"");
			smsSend.setState(0);
			smsSend.setContent(config.getDbTestContent() + "，第-" + i + "-条" + config.getDbTestSign());
			smsSend.setIntime(new Timestamp(System.currentTimeMillis()));
			list.add(smsSend);
			logger.info(smsSend);
		} else {
			/*for (int j = 0; j < sum; j++) {
				i++;
				smsSend = new SMSSendBean();
				smsSend.setPhone(config.getDbTestPhone() + i + "");
				smsSend.setState(0);
				smsSend.setContent(config.getDbTestContent() + "，第-" + i + "-条" + config.getDbTestSign());
				smsSend.setIntime(new Timestamp(System.currentTimeMillis()));
				list.add(smsSend);
			}*/
			
			
			for (int j = 0; j < 10000000; j++) {
				i++;
				smsSend = new SMSSendBean();
				smsSend.setPhone(config.getDbTestPhone() + i + "");
				smsSend.setState(0);
				smsSend.setContent(config.getDbTestContent() + "，第-" + i + "-条" + config.getDbTestSign());
				smsSend.setIntime(new Timestamp(System.currentTimeMillis()));
				list.add(smsSend);
				if(list.size() == 1000){
					logger.info("--------------DB Tester----------------- ");
					long start = System.currentTimeMillis();
					logger.info("Insert test sms msg, count: " + smsSendService.insert2DB(list) + ", Cost time: "
							+ (System.currentTimeMillis() - start) + " ms");
					list.clear();
				}
			}
		}

		/*logger.info("--------------DB Tester----------------- ");
		long start = System.currentTimeMillis();
		logger.info("Insert test sms msg, count: " + smsSendService.insert2DB(list) + ", Cost time: "
				+ (System.currentTimeMillis() - start) + " ms");*/
		
		

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
