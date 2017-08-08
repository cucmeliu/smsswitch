package com.ikohoo.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.ikohoo.constant.Constants;
import com.ikohoo.dao.SMSRecvDao;
import com.ikohoo.domain.SMSRecvBean;
import com.ikohoo.util.DaoUtils;

public class SMSRecvDaoImplOracle implements SMSRecvDao {
	// 接收表名
	private String table = "rept";
	// 接收表字段
	private String recvid;
	private String recvphone;
	private String recvcontent;
	private String recvsendtime;
	private String recvsystime;
	
	//接收表主键自增序列值
	private String recvSeq;

	// static Logger logger = Logger.getLogger(SMSRecvDaoImpl.class);

	@Override
	public int[] insert(List<SMSRecvBean> list) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(table).append("(").append(recvid)
				.append(Constants.SEPARATOR_COMMA).append(recvphone).append(Constants.SEPARATOR_COMMA)
				.append(recvcontent).append(Constants.SEPARATOR_COMMA).append(recvsendtime)
				.append(Constants.SEPARATOR_COMMA).append(recvsystime).append(") ")
				.append("VALUES (").append(recvSeq).append(", ?, ?, ?, ?)");
		String sql = sqlBuilder.toString();
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][4];
			int i = 0;
			// Timestamp ts = new Timestamp(System.currentTimeMillis());
			for (SMSRecvBean el : list) {
				params[i][0] = el.getPhone();
				params[i][1] = el.getContent();
				params[i][2] = el.getSendtime();
				params[i][3] = el.getSystime();
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
	public int[] insertArr(List<String[]> list) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(table).append("(").append(recvid)
				.append(Constants.SEPARATOR_COMMA).append(recvphone).append(Constants.SEPARATOR_COMMA)
				.append(recvcontent).append(Constants.SEPARATOR_COMMA).append(recvsendtime)
				.append(Constants.SEPARATOR_COMMA).append(recvsystime).append(") ")
				.append("VALUES (").append(recvSeq).append(", ?, ?, ?, ?)");
		String sql = sqlBuilder.toString();
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][4];
			int i = 0;
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			for (String[] el : list) {
				params[i][0] = el[0];
				params[i][1] = el[1];
				params[i][2] = Timestamp.valueOf(el[2].replace(Constants.SEPARATOR_SLASH, Constants.SEPARATOR_MINUS));
				params[i][3] = ts;
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

	@Override
	public void setRecvid(String recvid) {
		this.recvid = recvid;
	}

	@Override
	public void setRecvphone(String recvphone) {
		this.recvphone = recvphone;
	}

	@Override
	public void setRecvcontent(String recvcontent) {
		this.recvcontent = recvcontent;
	}

	@Override
	public void setRecvsendtime(String recvsendtime) {
		this.recvsendtime = recvsendtime;
	}

	@Override
	public void setRecvsystime(String recvsystime) {
		this.recvsystime = recvsystime;
	}

	@Override
	public void setRecvSeq(String recvSeq) {
		this.recvSeq = recvSeq;
	}

}
