package com.ikohoo.main;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.ReportGetBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.ReportGetService;

public class GetReportBiz extends TimerTask {
	static Logger logger = Logger.getLogger(GetReportBiz.class);
	
	ReportGetService service = BasicFactory.getFactory().getInstance(
			ReportGetService.class);
	

	private Config config;

	public GetReportBiz(Config config) {
		super();
		this.config = config;
	}

	@Override
	public void run() {
		logAndPrint("--------------Get Report---------------\n");
		
		// 获取报告状态
		service.setConfig(config);
		long start = System.currentTimeMillis();
		List<ReportGetBean> list = service.getReport2Status();

		if (null == list || 0 == list.size()) {
			logAndPrint("No new report, wait for next loop...");
		} else {
			logAndPrint("Get Report: " + list.size() + ", Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");
			// 更新状态
			
//			for (ReportGetBean ssb : list) {
//				logAndPrint(ssb.toString());
//			}

			// 插入数据库
			start = System.currentTimeMillis();
			service.insert2DB(list);
			
			logAndPrint("Insert rept into db, Cost time: "
					+ (System.currentTimeMillis() - start) + " ms\n");
		}
		logAndPrint("---------------------------------------\n");
	}
	
	private void logAndPrint(String msg) {
		logger.info(msg);
		System.out.println(msg);
	}

}
