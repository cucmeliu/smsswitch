package com.ikohoo.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.ikohoo.constant.Constants;
import com.ikohoo.dao.ReportGetDao;
import com.ikohoo.domain.ReportGetBean;
import com.ikohoo.util.DaoUtils;

public class ReportGetDaoImplOracle implements ReportGetDao {

	private String table = "rept"; //报告表名

	// 报告表字段
	private String reptid;
	private String reptphone;
	private String reptstatcode;
	private String reptstatmsg;
	private String reptsmsid;
	private String reptsendtime;
	
	//报告表主键自增序列值
	private String reptSeq; 

	// static Logger logger = Logger.getLogger(ReportGetDaoImpl.class);

	@Override
	public int[] insert(List<ReportGetBean> list) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(table).append("(").append(reptid).append(Constants.SEPARATOR_COMMA)
				.append(reptphone).append(Constants.SEPARATOR_COMMA).append(reptstatcode)
				.append(Constants.SEPARATOR_COMMA).append(reptstatmsg).append(Constants.SEPARATOR_COMMA)
				.append(reptsmsid).append(Constants.SEPARATOR_COMMA).append(reptsendtime).append(") ")
				.append("VALUES (").append(reptSeq).append(", ?, ?, ?, ?, ?)");
		String sql = sqlBuilder.toString();
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][5];
			int i = 0;
			for (ReportGetBean ss : list) {
				params[i][0] = ss.getPhone();
				params[i][1] = ss.getStatcode();
				params[i][2] = ss.getStatmsg();
				params[i][3] = ss.getSmsid();
				params[i][4] = ss.getSendtime();
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
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(table).append("(").append(reptid).append(Constants.SEPARATOR_COMMA)
				.append(reptphone).append(Constants.SEPARATOR_COMMA).append(reptstatcode)
				.append(Constants.SEPARATOR_COMMA).append(reptstatmsg).append(Constants.SEPARATOR_COMMA)
				.append(reptsmsid).append(Constants.SEPARATOR_COMMA).append(reptsendtime).append(") ")
				.append("VALUES (").append(reptSeq).append(", ?, ?, ?, ?, ?)");
		String sql = sqlBuilder.toString();
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			Object[][] params = new Object[list.size()][5];
			int i = 0;
			for (String[] ss : list) {
				params[i][0] = ss[1];
				params[i][1] = ss[3];
				params[i][2] = "";
				params[i][3] = ss[0];
				params[i][4] = Timestamp.valueOf(ss[2].replace(Constants.SEPARATOR_SLASH, Constants.SEPARATOR_MINUS));
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
	public void setReptid(String reptid) {
		this.reptid = reptid;
	}

	@Override
	public void setReptphone(String reptphone) {
		this.reptphone = reptphone;
	}

	@Override
	public void setReptstatcode(String reptstatcode) {
		this.reptstatcode = reptstatcode;
	}

	@Override
	public void setReptstatmsg(String reptstatmsg) {
		this.reptstatmsg = reptstatmsg;
	}

	@Override
	public void setReptsmsid(String reptsmsid) {
		this.reptsmsid = reptsmsid;
	}

	@Override
	public void setReptsendtime(String reptsendtime) {
		this.reptsendtime = reptsendtime;
	}

	@Override
	public void setReptSeq(String reptSeq) {
		this.reptSeq = reptSeq;
	}
}
