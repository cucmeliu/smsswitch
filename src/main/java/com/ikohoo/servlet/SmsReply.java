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
import com.ikohoo.service.SMSRecvService;
import com.ikohoo.util.ConfigReader;

@WebServlet("/smsReply")
public class SmsReply extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8756123072987197447L;

	private Logger logger = Logger.getLogger(SmsReceipt.class);

	private Config config;
	
	private BlockingQueue<String> queue;
	
	private SMSRecvService smsRecvService;
	
	private List<String[]> recvs;
	
	private ExecutorService es;
	
	private String usercode;

	//测试使用
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String account = req.getParameter("account"); 
		String getMo = req.getParameter("GetMo");
		resp.setContentType("text/html;charset=UTF-8");
		resp.getWriter().write("account : " + account + "\n" + "getMo : " + getMo + "\n queue count : " + queue.size());
	}

	//推送信息入队列
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String account = req.getParameter("account");
		//推送账号与用户账号不一致，不处理
		if(StringUtils.isBlank(account) || StringUtils.isBlank(usercode) || !account.equals(usercode))
			return;
		String getMo = req.getParameter("GetMo");
		if(StringUtils.isBlank(getMo)) 
			return;
		String[] getMos = getMo.split(Constants.SEPARATOR_REPLY_OBJ);
		for(String mo : getMos){
			try {
				queue.put(mo);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}

	public void init() throws ServletException {
		config = ConfigReader.loadConfig();
		queue = new LinkedBlockingQueue<String>();
		smsRecvService = BasicFactory.getFactory().getInstance(SMSRecvService.class);
		smsRecvService.setConfig(config);
		recvs = new ArrayList<String[]>(1000);
		es = Executors.newFixedThreadPool(1);
		usercode = config.getUserCode();
		//从列队中获取数据，满1000条批量入库，取不到时将之前数据批量入库
		es.execute(new Runnable(){
			@Override
			public void run() {
				while(true){
					String recvStr = null;
					try {
						recvStr = queue.poll(1, TimeUnit.SECONDS);
						
						if(StringUtils.isBlank(recvStr)){
							if(!recvs.isEmpty()){
								saveReply();
							}
						}else{
							String[] recvInfo = recvStr.split(Constants.SEPARATOR_REPLY_IN);
							recvs.add(recvInfo);
							if(recvs.size() == 1000){
								saveReply();
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
	
	private void saveReply(){
		smsRecvService.insert2DBArr(recvs);
		recvs.clear();
	}

}
