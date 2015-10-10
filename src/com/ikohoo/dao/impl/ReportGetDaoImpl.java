package com.ikohoo.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import com.ikohoo.dao.ReportGetDao;
import com.ikohoo.domain.ReportGetBean;
import com.ikohoo.util.DaoUtils;

public class ReportGetDaoImpl implements ReportGetDao{
	
	static Logger logger = Logger.getLogger(ReportGetDaoImpl.class);

	@Override
	public int[] insert(List<ReportGetBean> list) {
								// id, phone,statcode,statmsg,smsid,sendtime
		String sql = " INSERT INTO  stat_sf VALUES (null, ?, ?, ?, ?, ?) ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][5];
			int i=0;
			for (ReportGetBean ss : list){
				params[i][0] = ss.getPhone();
				params[i][1] = ss.getStatcode();
				params[i][2] = ss.getStatmsg();
				params[i][3] = ss.getSmsid();
				params[i][4] = ss.getSendtime();
				i++;
			}
			return runner.batch(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
			throw new RuntimeException(e);
		}
	}

}
