package com.ikohoo.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
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
		return splitOneRecord(new GetBase(config).getTest(batchNumber));
	}

	@Override
	public List<ReportGet> getReport2() {

		String str = new GetBase(config)
			.get("GetReport2");
		// TODO 测试使用，发布时改为上面的语句
			//.getTest("GetReport2");

		if (null == str || "".equals(str)) {
			return null;
		}
		System.out.println(str);
		logger.info("<<<--->>> get report: " + str);

		String[] sp = str.split("[|]");

		List<ReportGet> list = new ArrayList<ReportGet>();
		for (int i = 0; i < sp.length; i++) {
			list.add(splitOneRecord(sp[i]));
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
		int[] a = dao.insert(list);
		int ret = 0;
		for (int i = 0; i < a.length; i++)
			ret += a[i];

		return ret;
	}

	@Override
	public List<ReportGetBean> getReport2Status() {
		List<ReportGet> list = getReport2();
		if (null == list || 0 == list.size()) {
			return null;
		}

		List<ReportGetBean> rst = new ArrayList<ReportGetBean>();
		ReportGetBean st = null;
		//Format fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (ReportGet rg : list) {
			st = new ReportGetBean();
			st.setPhone(rg.getPhone());
			st.setSendtime(Timestamp.valueOf(rg.getSendtime().replaceAll("/", "-")));
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

	}
}
