package com.ikohoo.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.ikohoo.dao.SMSSendDao;
import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.util.DaoUtils;

public class SMSSendDaoImpl implements SMSSendDao {

	//static Logger logger = Logger.getLogger(SMSSendDaoImpl.class);
	
	@Override
	public List<SMSSendBean> getNewSMS(int count) throws SQLException {
		String sql = " SELECT * FROM send_sf WHERE state=0 LIMIT "
				+ count;

		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanListHandler<SMSSendBean>(
					SMSSendBean.class));
		} catch (SQLException e1) {
			//e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SMSSendBean> getNewSMS(int count, int mod, int remainder)
			throws SQLException {
		String sql = " SELECT * FROM send_sf WHERE state=0 and id%?=? LIMIT "
				+ count;

		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanListHandler<SMSSendBean>(
					SMSSendBean.class), mod, remainder);
		} catch (SQLException e1) {
			//e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public int insert(SMSSendBean record) throws SQLException {
		String sql = " INSERT INTO send_sf VALUES(null, ?,?,?,?,?) ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner
					.update(sql, record.getPhone(), record.getContent(),
							record.getIntime(), record.getState(),
							record.getSendtime());
		} catch (SQLException e1) {
			//e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			//e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int[] insert(List<SMSSendBean> list) throws SQLException {
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
		} catch (SQLException e1) {
			//e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	public int[] updateSendState(List<SMSSendBean> list) throws SQLException {
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
		} catch (SQLException e1) {
			//e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	public int[] delSentRec(List<SMSSendBean> list) throws SQLException {
		String sql = " Delete from send_sf WHERE id=? ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][1];
			int i = 0;

			for (SMSSendBean ss : list) {
				params[i][0] = ss.getId();
				i++;
			}
			return runner.batch(sql, params);
		} catch (SQLException e1) {
			//e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	public int[] insSentRec(List<SMSSendBean> list) throws SQLException {
						   // id, phone, content, intime, state, sendtime, statcode
		String sql = " INSERT INTO send_sf_done VALUES(?, ?, ?, ?, ?, ?, ?) ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][7];
			int i = 0;

			for (SMSSendBean ss : list) {
				params[i][0] = ss.getId();
				params[i][1] =  ss.getPhone();  
				params[i][2] = ss.getContent();
				params[i][3] = ss.getIntime();
				params[i][4] = ss.getState();
				params[i][5] = ss.getSendtime();
				params[i][6] = ss.getStatcode();
				i++;
			}
			return runner.batch(sql, params);
		} catch (SQLException e1) {
			//e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}



}
