package com.ikohoo.main;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSRecvBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.SMSRecvService;

public class GetRecvBiz extends TimerTask {
	static Logger logger = Logger.getLogger(GetRecvBiz.class);

	SMSRecvService service = BasicFactory.getFactory().getInstance(
			SMSRecvService.class);

	private Config config;

	public GetRecvBiz(Config config) {
		super();
		this.config = config;
	}

	@Override
	public void run() {
		logAndPrint("--------------Get Recv SMS---------------\n");

		// 通过接口取回复的信息
		service.setConfig(config);
		long start = System.currentTimeMillis();
		List<SMSRecvBean> list = service.getRecvListBean();
		
		if (null == list || 0 == list.size()) {
			logAndPrint("No new received, wait for next loop...");
		} else {
			logAndPrint("Get recv sms: " + list.size() + ", Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");

			// 解析后的数据
//			for (SMSRecvBean ssb : list) {
//				logAndPrint(ssb.toString());
//			}

			// 插入数据库
			start = System.currentTimeMillis();
			service.insert2DB(list);
			logAndPrint("Insert recv into db Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");
		}
		//logAndPrint("---------------------------------------\n");
	}
	
	private void logAndPrint(String msg) {
		logger.info(msg);
		System.out.println(msg);
	}

}
