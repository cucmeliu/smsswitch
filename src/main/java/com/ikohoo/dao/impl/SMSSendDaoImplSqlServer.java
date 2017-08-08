package com.ikohoo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ikohoo.constant.Constants;
import com.ikohoo.dao.SMSSendDao;
import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.util.DaoUtils;

public class SMSSendDaoImplSqlServer implements SMSSendDao {
	// 发送表
	private String table = "send_sf";
	// 已发送表
	private String tableSent = "send_sf_done";

	// 发送表字段
	private String sendid;
	private String sendphone;
	private String sendcontent;
	private String sendintime;
	private String sendstate;
	private String sendsendtime;
	private String sendstatcode;
	// private Config config;

	// static Logger logger = Logger.getLogger(SMSSendDaoImpl.class);

	@Override
	public List<SMSSendBean> getNewSMS(int count) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder("SELECT TOP ").append(count).append(" * FROM ").append(table)
				.append(" WHERE ").append(sendstate).append(" = 0");
		String sql = sqlBuilder.toString();

		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanListHandler<SMSSendBean>(SMSSendBean.class));
		} catch (SQLException e1) {
			// e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SMSSendBean> getNewSMS(int count, int mod, int remainder) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder("SELECT TOP ").append(count).append(" * FROM ").append(table).append(" WHERE ").append(sendstate)
				.append(" = 0 and ").append(sendid).append("%?=?");
		String sql = sqlBuilder.toString();

		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanListHandler<SMSSendBean>(SMSSendBean.class), mod, remainder);
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
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(table).append("(").append(sendphone)
				.append(Constants.SEPARATOR_COMMA).append(sendcontent).append(Constants.SEPARATOR_COMMA)
				.append(sendintime).append(Constants.SEPARATOR_COMMA).append(sendstate)
				.append(Constants.SEPARATOR_COMMA).append(sendsendtime).append(") ").append("VALUES (?, ?, ?, ?, ?)");
		String sql = sqlBuilder.toString();
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.update(sql, record.getPhone(), record.getContent(), record.getIntime(), record.getState(),
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
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(table).append("(").append(sendphone)
				.append(Constants.SEPARATOR_COMMA).append(sendcontent).append(Constants.SEPARATOR_COMMA)
				.append(sendintime).append(Constants.SEPARATOR_COMMA).append(sendstate).append(") ")
				.append("VALUES (?, ?, ?, ?)");
		String sql = sqlBuilder.toString();
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
		StringBuilder sqlBuilder = new StringBuilder("UPDATE ").append(table).append(" SET ").append(sendstate)
				.append("=?,").append(sendsendtime).append("=?,").append(sendstatcode).append("=? WHERE ")
				.append(sendid).append("=?");
		String sql = sqlBuilder.toString();
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
		StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ").append(table).append(" WHERE ").append(sendid)
				.append("=?");
		String sql = sqlBuilder.toString();
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
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(tableSent).append("(").append(sendphone)
				.append(Constants.SEPARATOR_COMMA).append(sendcontent).append(Constants.SEPARATOR_COMMA)
				.append(sendintime).append(Constants.SEPARATOR_COMMA).append(sendstate)
				.append(Constants.SEPARATOR_COMMA).append(sendsendtime).append(Constants.SEPARATOR_COMMA)
				.append(sendstatcode).append(") ").append("VALUES (?, ?, ?, ?, ?, ?)");
		String sql = sqlBuilder.toString();
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][6];
			int i = 0;

			for (SMSSendBean ss : list) {
				// params[i][0] = ss.getId();
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

	// public String getTableSent() {
	// return tableSent;
	// }

	@Override
	public void setTableSent(String table) {
		this.tableSent = table;
	}

	@Override
	public int delSentRec1(List<SMSSendBean> list) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ").append(table).append(" WHERE ").append(sendid)
				.append(" in (");
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sqlBuilder.append(list.get(i).getId() + "");
			} else {
				sqlBuilder.append(list.get(i).getId() + ",");
			}
		}
		sqlBuilder.append(")  ");
		String sql = sqlBuilder.toString();
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.update(sql);
		} catch (SQLException e1) {
			// e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			// e.printStackTrace();
			return 0;
		}
	}

	// @Override
	// public void setConfig(Config config) {
	// this.config = config;
	//
	// }
	// @Override
	// public int[] insSentRec(List<SMSSendBean> list){
	// Connection conn = DaoUtils.getConn();
	// int[] executeBatch = null;
	// try {
	//// long a=System.currentTimeMillis();
	// conn.setAutoCommit(false);
	// String sql = " INSERT INTO "+ tableSent +" VALUES(null, ?, ?, ?, ?, ?, ?)
	// ";
	// PreparedStatement prest = conn.prepareStatement(sql);
	//// int i = 0;
	// for (SMSSendBean ss : list) {
	// prest.setString(1, ss.getPhone());
	// prest.setString(2, ss.getContent());
	// prest.setTimestamp(3, ss.getIntime());
	// prest.setInt(4, ss.getState());
	// prest.setTimestamp(5, ss.getSendtime());
	// prest.setString(6, ss.getStatcode());
	// prest.addBatch();
	//// i++;
	// }
	//
	// //conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	// executeBatch = prest.executeBatch();
	// conn.commit();
	// /*prest.executeBatch();
	// conn.commit();*/
	//
	// conn.setAutoCommit(true);
	// //for(int x = 0; x < 2000000; x++){
	//
	//// long b=System.currentTimeMillis();
	//// System.out.println("用时"+ (b-a)+" ms");
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// finally{
	// try {
	// if(conn!=null)conn.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// return executeBatch;
	// }
	@Override
	public void setSendid(String sendid) {
		this.sendid = sendid;
	}

	@Override
	public void setSendphone(String sendphone) {
		this.sendphone = sendphone;
	}

	@Override
	public void setSendcontent(String sendcontent) {
		this.sendcontent = sendcontent;
	}

	@Override
	public void setSendintime(String sendintime) {
		this.sendintime = sendintime;
	}

	@Override
	public void setSendstate(String sendstate) {
		this.sendstate = sendstate;
	}

	@Override
	public void setSendsendtime(String sendsendtime) {
		this.sendsendtime = sendsendtime;
	}

	@Override
	public void setSendstatcode(String sendstatcode) {
		this.sendstatcode = sendstatcode;
	}

	@Override
	public void setSendSeq(String sendSeq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSendDoneSeq(String sendDoneSeq) {
		// TODO Auto-generated method stub
		
	}
}
