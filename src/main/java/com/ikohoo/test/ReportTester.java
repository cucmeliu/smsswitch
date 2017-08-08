package com.ikohoo.test;

import java.util.List;

import org.apache.log4j.Logger;

import com.ikohoo.domain.ReportGet;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.main.SMSMain;
import com.ikohoo.service.ReportGetService;

public class ReportTester {
	
	static Logger logger = Logger.getLogger(SMSMain.class);

	public static void main(String[] args) {
		ReportGetService service = BasicFactory.getFactory().getInstance(ReportGetService.class);

		List<ReportGet> list = service.getReport2();
		for (ReportGet sr : list) {
			System.out.println(sr.toString());
		}
		logger.info("test logger");
	}

}
