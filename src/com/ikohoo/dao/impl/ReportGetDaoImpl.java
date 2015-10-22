package com.ikohoo.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.ikohoo.dao.ReportGetDao;
import com.ikohoo.domain.ReportGetBean;
import com.ikohoo.util.DaoUtils;

public class ReportGetDaoImpl implements ReportGetDao {

	private String table="rept";

	//static Logger logger = Logger.getLogger(ReportGetDaoImpl.class);

	@Override
	public int[] insert(List<ReportGetBean> list) throws SQLException {
		// id, phone,statcode,statmsg,smsid,sendtime
		String sql = " INSERT INTO   " + table + "  VALUES (null, ?, ?, ?, ?, ?) ";
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
			//e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setTable(String table) {
		this.table = table;
	}

}
