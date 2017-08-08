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
import com.ikohoo.dao.ReportGetDao;
import com.ikohoo.domain.Config;
import com.ikohoo.domain.ReportGet;
import com.ikohoo.domain.ReportGetBean;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.ReportGetService;

public class ReportGetServiceImpl implements ReportGetService {
	static Logger logger = Logger.getLogger(ReportGetServiceImpl.class);

	ReportGetDao dao = BasicFactory.getFactory()
			.getInstance(ReportGetDao.class);

	private Config config;

	@Override
	public ReportGet getReport(String batchNumber) {
		return null;// splitOneRecord(new GetBase(config).batchNumber));
	}

	@Override
	public List<ReportGet> getReport2() {

		String str;
		try {
			str = new GetBase(config).get(config.getCmdRept());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
		// TODO 测试使用，发布时改为上面的语句
		// .getTest("GetReport2");

		if (null == str || "".equals(str.trim())) {
			return null;
		}
		System.out.println(str);
		logger.info("<<<--->>> get report: " + str);

		String[] sp = str.split("[|]");

		List<ReportGet> list = new ArrayList<ReportGet>();
		for (int i = 0; i < sp.length; i++) {
			ReportGet rg = splitOneRecord(sp[i]);
			if (null != rg)
				list.add(rg);
		}

		return list;
	}

	/**
	 * 拆分一条状态报告，
	 * 
	 * @param str
	 *            格式为：2314357620085030624,13900000000,DELIVRD|
	 * @return
	 */
	private ReportGet splitOneRecord(String str) {
		if (null == str || "".equals(str) || str.length() < 10)
			return null;

		if (str.trim().endsWith("|"))
			str = str.substring(0, str.length() - 1); // trim 掉最后面的 "|"

		String[] sp = str.split(",");
		if (sp.length < 3)
			return null;

		ReportGet sr = new ReportGet();
		sr.setBatchNum(sp[0].trim());
		sr.setPhone(sp[1].trim());
		sr.setSendtime(sp[2].trim());
		sr.setStatus(sp[3].trim());

		return sr;
	}

	@Override
	public int insert2DB(List<ReportGetBean> list) {
		int[] a;
		try {
			//dao.setTable(config.getTableRept());
			a = dao.insert(list);

			if (null==a)
				return 0;
			
			int ret = 0;
			for (int i = 0; i < a.length; i++)
				ret += a[i];

			return ret;
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public List<ReportGetBean> getReport2Status() {
		List<ReportGet> list = getReport2();
		if (null == list || 0 == list.size()) {
			return null;
		}

		List<ReportGetBean> rst = new ArrayList<ReportGetBean>();
		ReportGetBean st = null;
		// Format fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ReportGet rg : list) {
			st = new ReportGetBean();
			st.setPhone(rg.getPhone());
			Date date = new Date();
			try {
				date = df.parse(rg.getSendtime().replaceAll("/", "-"));
			} catch (ParseException e) {
				logger.error("回执日期转换错误，回执时间"+rg.getSendtime(),e);
			}
			String format = df.format(date);
			st.setSendtime(Timestamp.valueOf(format));
//			st.setSendtime(Timestamp.valueOf(rg.getSendtime().replaceAll("/",
//					"-")));
			st.setSmsid(rg.getBatchNum());
			st.setStatcode(rg.getStatus());
			st.setStatmsg("");

			rst.add(st);
		}
		return rst;
	}

	@Override
	public void setConfig(Config config) {
		this.config = config;
		dao.setTable(config.getTableRept());
		dao.setReptid(config.getReptId());
		dao.setReptphone(config.getReptPhone());
		dao.setReptstatcode(config.getReptStatCode());
		dao.setReptstatmsg(config.getReptStatMsg());
		dao.setReptsmsid(config.getReptSmsId());
		dao.setReptsendtime(config.getReptSendTime());
		dao.setReptSeq(config.getStatSeqVal());
	}
	
	@Override
	public int insert2DBArr(List<String[]> list) {
		int[] a;
		try {
			//dao.setTable(config.getTableRept());
			a = dao.insertArr(list);

			if (null==a)
				return 0;
			
			int ret = 0;
			for (int i = 0; i < a.length; i++)
				ret += a[i];

			return ret;
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return 0;

	}
}
