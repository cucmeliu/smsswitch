package com.ikohoo.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.ikohoo.dao.SMSSendDao;
import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.util.DaoUtils;

public class SMSSendDaoImpl implements SMSSendDao {

	static Logger logger = Logger.getLogger(SMSSendDaoImpl.class);
	
	@Override
	public List<SMSSendBean> getNewSMS(int count) {
		String sql = " SELECT * FROM send_sf WHERE state=0 ORDER BY id LIMIT "
				+ count;

		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanListHandler<SMSSendBean>(
					SMSSendBean.class));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
			throw new RuntimeException(e);
		}
	}

	@Override
	public int insert(SMSSendBean record) {
		String sql = " INSERT INTO send_sf VALUES(null, ?,?,?,?,?) ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner
					.update(sql, record.getPhone(), record.getContent(),
							record.getIntime(), record.getState(),
							record.getSendtime());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
			throw new RuntimeException(e);
		}
	}

	@Override
	public int[] insert(List<SMSSendBean> list) {
		// id, phone,statcode,statmsg,smsid,sendtime
		String sql = " INSERT INTO send_sf VALUES(null, ?, ?, ?, ?, null, null) ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][4];
			int i = 0;

			for (SMSSendBean ss : list) {
				params[i][0] = ss.getPhone();
				params[i][1] = ss.getContent();
				params[i][2] = ss.getIntime();
				params[i][3] = ss.getState();
				//params[i][4] = ss.getSendtime(); 
				i++;
			}
			return runner.batch(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
			throw new RuntimeException(e);
		}
	}

	@Override
	public int[] updateSendState(List<SMSSendBean> list) {
		String sql = " UPDATE send_sf SET state=?, sendtime=?, statcode=? WHERE id=? ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][4];
			int i = 0;

			for (SMSSendBean ss : list) {
				params[i][0] = ss.getState();
				params[i][1] = ss.getIntime();
				params[i][2] = ss.getStatcode();
				params[i][3] = ss.getId();
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
