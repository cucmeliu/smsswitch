package com.ikohoo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.ikohoo.api.SendSMSCF;
import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSSendParams;
import com.ikohoo.util.ConfigReader;

public class SendSMSTester {
	// public static void main(String[] args) {
	// SMSSendService service =
	// BasicFactory.getFactory().getInstance(SMSSendService.class);
	//
	// System.out.println("---------------------------------------");
	// System.out.println("--------------get list from db-----------------");
	// long start = System.currentTimeMillis();
	// List<SMSSendBean> list = service.getNewSMS(1000);
	// System.out.println(" time cost: " + (System.currentTimeMillis() - start)
	// + " ms");
	// System.out.println(" total: " + list.size());
	// System.out.println(" Start at: " + new Timestamp(start));
	// service.sendSMSList(list);
	// System.out.println(" Finish at: " + new
	// Timestamp(System.currentTimeMillis()));
	// System.out.println(" Total time cost: " + (System.currentTimeMillis() -
	// start) + " ms");
	// System.out.println("---------------------------------------");
	//
	// }

	public static void main(String[] args) throws Exception {

		Config config = ConfigReader.loadConfig();
		SendSMSCF sendSMS = new SendSMSCF(config);

		List<String> phones = new ArrayList<String>();
		phones.add("18651650023");
		phones.add("15618658829");
		phones.add("18101948472");
		phones.add("18121411523");
		phones.add("18121412761");
		phones.add("18251909662");
		phones.add("13952002475");

		SMSSendParams msg = new SMSSendParams();
		for (String p : phones) {
			msg.setChannel("0");
			msg.setDestNo(p);
			msg.setMsg("测试短信，你好，这是测试短信2【b】");
			msg.setSmsType("103");
			sendSMS.send(msg, "SendMsg");
		}

	}

	Timer timer;
	
//	{
//		timer = new Timer();
//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				System.out.println("go go go");
//				
//			}}, 0, 1000);
//		
//	}
	
	class MyTask extends TimerTask {

		public boolean pause = false;

		@Override
		public void run() {
			
			if (pause)
				return ;
			
			System.out.println("go go go");
			
		}
		
	}

	public void startTimer() {

		if (null == timer) {
			System.out.println("start timer...");
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					System.out.println("go go go");
					
				}}, 0, 1000);
		}
		//timer.schedule(, 0, 1000);

	}

	public void stopTimer() {

		if (null != timer) {
				timer.cancel();
				timer = null;
		}
	
	}

}
