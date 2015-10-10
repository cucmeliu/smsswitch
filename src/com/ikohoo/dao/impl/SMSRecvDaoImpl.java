package com.ikohoo.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import com.ikohoo.dao.SMSRecvDao;
import com.ikohoo.domain.SMSRecvBean;
import com.ikohoo.util.DaoUtils;

public class SMSRecvDaoImpl implements SMSRecvDao {
	static Logger logger = Logger.getLogger(SMSRecvDaoImpl.class);

	@Override
	public int[] insert(List<SMSRecvBean> list) {
								// id, phone,content,sendtime, systime
		String sql = " INSERT INTO  receivesms_sf VALUES (null, ?, ?, ?, ?) ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][4];
			int i = 0;
			//Timestamp ts = new Timestamp(System.currentTimeMillis());
			for (SMSRecvBean el : list) {
				params[i][0] = el.getPhone();
				params[i][1] = el.getContent();
				params[i][2] = el.getSendtime();
				params[i][3] = el.getSystime();
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
