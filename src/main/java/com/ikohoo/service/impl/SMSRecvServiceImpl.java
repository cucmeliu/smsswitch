package com.ikohoo.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ikohoo.api.GetBase;
import com.ikohoo.dao.SMSRecvDao;
import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSRecv;
import com.ikohoo.domain.SMSRecvBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.SMSRecvService;

public class SMSRecvServiceImpl implements SMSRecvService {
	static Logger logger = Logger.getLogger(SMSRecvServiceImpl.class);
	
	SMSRecvDao dao = BasicFactory.getFactory().getInstance(SMSRecvDao.class);

	private Config config;

	@Override
	public List<SMSRecv> getRecvList() {
		String str;
		try {
			str = new GetBase(config)
				.get(config.getCmdRecv());
			// TODO 测试使用，发布时改为上面的语句
		 	//.getTest("GetMo2");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
		
		
		if (null == str || "".equals(str)) {
			return null;
		}
		System.out.println("<<<---get recv sms: " + str);
		logger.info("<<<---get recv sms: " + str);

		String[] sp = str.split("[|];[|]");

		List<SMSRecv> list = new ArrayList<SMSRecv>();

		for (int i = 0; i < sp.length; i++) {
			SMSRecv sr = splitOneRecord(sp[i]);
			if (null != sr)
				list.add(sr);
		}

		return list;
	}

	private SMSRecv splitOneRecord(String str) {
		// System.out.println("split: " + str);
		if (null == str || "".equals(str) || str.length() < 10)
			return null;

		if (str.trim().endsWith("|"))
			str = str.substring(0, str.length() - 1); // trim 掉最后面的 "|"

		String[] sp = str.split("[|],[|]");
		if (sp.length < 3)
			return null;

		SMSRecv sr = new SMSRecv();
		sr.setPhone(sp[0].trim());
		sr.setContent(sp[1].trim());
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//随便怎么转都可以的
		//Date date = format.parse(time);
		//String dateString = formatter.format(date); 
		//Timestamp.valueOf(rg.getSendtime().replaceAll("/","-"))
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = df.parse(sp[2].trim().replaceAll("/", "-"));
		} catch (ParseException e) {
			logger.error("回复日期转换错误，回执时间"+sp[2].trim(),e);
		}
		String format = df.format(date);
		sr.setReplytime(Timestamp.valueOf(format));
//		sr.setReplytime(Timestamp.valueOf(sp[2].trim().replaceAll("/","-")));
		
		return sr;
	}

	@Override
	public int insert2DB(List<SMSRecvBean> list) {
		int[] a;
		try {
			
			a = dao.insert(list);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			return 0;
		}
		
		int sum = 0;
		for (int i = 0; i < a.length; i++)
			sum += a[i];
		return sum;
	}

	@Override
	public List<SMSRecvBean> getRecvListBean() {
		List<SMSRecv> list = getRecvList();

		if (null == list || 0 == list.size())
			return null;

		List<SMSRecvBean> rst = new ArrayList<SMSRecvBean>();
		// System.out.println("list size: " + list.size());

		SMSRecvBean st = null;
		for (SMSRecv el : list) {
			st = new SMSRecvBean();
			st.setPhone(el.getPhone());
			st.setContent(el.getContent());
			st.setSendtime(el.getReplytime());
			st.setSystime(new Timestamp(System.currentTimeMillis()));

			rst.add(st);
		}
		return rst;
	}

	@Override
	public void setConfig(Config config) {
		this.config = config;
		dao.setTable(config.getTableRecv());
		dao.setRecvid(config.getRecvId());
		dao.setRecvphone(config.getRecvPhone());
		dao.setRecvcontent(config.getRecvContent());
		dao.setRecvsendtime(config.getRecvSendtime());
		dao.setRecvsystime(config.getRecvSystime());
		dao.setRecvSeq(config.getReceivesmsSeqVal());
	}

	@Override
	public int insert2DBArr(List<String[]> list) {
		int[] a;
		try {
			
			a = dao.insertArr(list);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			return 0;
		}
		
		int sum = 0;
		for (int i = 0; i < a.length; i++)
			sum += a[i];
		return sum;
	}
}
