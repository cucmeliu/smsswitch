package com.ikohoo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ikohoo.constant.Constants;
import com.ikohoo.domain.Config;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.ReportGetService;
import com.ikohoo.util.ConfigReader;

@WebServlet("/smsReceipt")
public class SmsReceipt extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8418834757423027704L;
	
	private Logger logger = Logger.getLogger(SmsReceipt.class);

	private Config config;
	
	private BlockingQueue<String> queue;
	
	private ReportGetService reportGetService;
	
	private List<String[]> reports;
	
	private ExecutorService es;
	
	private String usercode;
	
	//测试使用
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String account = req.getParameter("account"); 
		String getReport = req.getParameter("GetReport");
		resp.setContentType("text/html;charset=UTF-8");
		resp.getWriter().write("account : " + account + "\n" + "getReport : " + getReport + "\n queue count : " + queue.size());
	}
	
	//推送信息入队列
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String account = req.getParameter("account");
		//推送账号与用户账号不一致，不处理
		if(StringUtils.isBlank(account) || StringUtils.isBlank(usercode) || !account.equals(usercode))
			return;
		String getReport = req.getParameter("GetReport");
		if(StringUtils.isBlank(getReport)) 
			return;
		String[] reports = getReport.split(Constants.SEPARATOR_VERTICAL);
		for(String report : reports){
			try {
				queue.put(report);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}

	public void init() throws ServletException {
		config = ConfigReader.loadConfig();
		queue = new LinkedBlockingQueue<String>();
		reportGetService = BasicFactory.getFactory().getInstance(ReportGetService.class);
		reportGetService.setConfig(config);
		reports = new ArrayList<String[]>(1000);
		es = Executors.newFixedThreadPool(1);
		usercode = config.getUserCode();
		//从列队中获取数据，满1000条批量入库，取不到时将之前数据批量入库
		es.execute(new Runnable(){
			@Override
			public void run() {
				while(true){
					String reportStr = null;
					try {
						reportStr = queue.poll(1, TimeUnit.SECONDS);
						
						if(StringUtils.isBlank(reportStr)){
							if(!reports.isEmpty()){
								saveReceipt();
							}
						}else{
							String[] reportInfo = reportStr.split(Constants.SEPARATOR_COMMA);
							reports.add(reportInfo);
							if(reports.size() == 1000){
								saveReceipt();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e);
					}
					
				}
			}
		});
	}
	
	private void saveReceipt(){
		reportGetService.insert2DBArr(reports);
		reports.clear();
	}
	
}
