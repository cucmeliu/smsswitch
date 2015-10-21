package com.ikohoo.test;

import java.util.ArrayList;
import java.util.List;

import com.ikohoo.api.SendSMSCF;
import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSSendParams;
import com.ikohoo.util.ConfigReader;

public class SendSMSTester {
//	public static void main(String[] args) {
//		SMSSendService service = BasicFactory.getFactory().getInstance(SMSSendService.class);
//		
//		System.out.println("---------------------------------------");
//		System.out.println("--------------get list from db-----------------");
//		long start = System.currentTimeMillis();
//		List<SMSSendBean> list = service.getNewSMS(1000);
//		System.out.println(" time cost: " + (System.currentTimeMillis() - start) + " ms");
//		System.out.println(" total: " + list.size());
//		System.out.println("  Start at: " + new Timestamp(start));
//		service.sendSMSList(list);
//		System.out.println("  Finish at: " + new Timestamp(System.currentTimeMillis()));
//		System.out.println("  Total time cost: " + (System.currentTimeMillis() - start) + " ms");
//		System.out.println("---------------------------------------");
//		
//	}
	
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
			msg.setMsg("测试程序中，收到的话不用回复我，你的验证码是6666【云信留客】");
			sendSMS.send(msg, "SendMsg");
		}
		

	}

}
