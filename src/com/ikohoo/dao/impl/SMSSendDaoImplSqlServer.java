package com.ikohoo.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ikohoo.dao.SMSSendDao;
import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.util.DaoUtils;

public class SMSSendDaoImplSqlServer implements SMSSendDao {

	private String table = "send_sf";
	private String tableSent = "send_sf_done";

	// private Config config;

	// static Logger logger = Logger.getLogger(SMSSendDaoImpl.class);

	@Override
	public List<SMSSendBean> getNewSMS(int count) throws SQLException {
		String sql = " SELECT TOP " + count+ " * FROM " + table + " WHERE state=0  ";

		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanListHandler<SMSSendBean>(
					SMSSendBean.class));
		} catch (SQLException e1) {
			// e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SMSSendBean> getNewSMS(int count, int mod, int remainder)
			throws SQLException {
		String sql = " SELECT TOP " + count+ "  * FROM " + table + "  WHERE state=0 and id%?=?  "
				;

		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanListHandler<SMSSendBean>(
					SMSSendBean.class), mod, remainder);
		} catch (SQLException e1) {
			// e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public int insert(SMSSendBean record) throws SQLException {
		String sql = " INSERT INTO " + table + " (phone, content, intime, state, sendtime) VALUES(?,?,?,?,?) ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner
					.update(sql, record.getPhone(), record.getContent(),
							record.getIntime(), record.getState(),
							record.getSendtime());
		} catch (SQLException e1) {
			// e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			// e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int[] insert(List<SMSSendBean> list) throws SQLException {
		// id, phone,statcode,statmsg,smsid,sendtime
		String sql = " INSERT INTO " + table + " (phone, content, intime, state) VALUES(?, ?, ?, ?) ";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][4];
			int i = 0;

			for (SMSSendBean ss : list) {
				params[i][0] = ss.getPhone();
				params[i][1] = ss.getContent();
				params[i][2] = ss.getIntime();
				params[i][3] = ss.getState();
				// params[i][4] = ss.getSendtime();
				i++;
			}
			return runner.batch(sql, params);
		} catch (SQLException e1) {
			// e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public int[] updateSendState(List<SMSSendBean> list) throws SQLException {
		String sql = " UPDATE " + table + "  SET state=?, sendtime=?, statcode=? WHERE id=? ";
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
			// e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public int[] delSentRec(List<SMSSendBean> list) throws SQLException {
		String sql = " DELETE FROM " + table + "  WHERE id=? ";
		
		//System.out.println("delete sql: " + sql);
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
			// e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public int[] insSentRec(List<SMSSendBean> list) throws SQLException {
		// id, phone, content, intime, state, sendtime, statcode
		String sql = " INSERT INTO "+ tableSent +" (phone, content, intime, state, sendtime, statcode) VALUES( ?, ?, ?, ?, ?, ?) ";
		//System.out.println("insert sql: " + sql);
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][6];
			int i = 0;

			for (SMSSendBean ss : list) {
				//params[i][0] = ss.getId();
				params[i][0] = ss.getPhone();
				params[i][1] = ss.getContent();
				params[i][2] = ss.getIntime();
				params[i][3] = ss.getState();
				params[i][4] = ss.getSendtime();
				params[i][5] = ss.getStatcode();
				i++;
			}
			return runner.batch(sql, params);
		} catch (SQLException e1) {
			// e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setTable(String table) {
		this.table = table;
	}

//	public String getTableSent() {
//		return tableSent;
//	}

	@Override
	public void setTableSent(String table) {
		this.tableSent = table;
	}

	// @Override
	// public void setConfig(Config config) {
	// this.config = config;
	//
	// }

}
