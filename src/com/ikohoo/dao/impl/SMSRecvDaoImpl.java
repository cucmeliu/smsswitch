package com.ikohoo.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.ikohoo.dao.SMSRecvDao;
import com.ikohoo.domain.SMSRecvBean;
import com.ikohoo.util.DaoUtils;

public class SMSRecvDaoImpl implements SMSRecvDao {
	private String table = "rept";

	//static Logger logger = Logger.getLogger(SMSRecvDaoImpl.class);

	@Override
	public int[] insert(List<SMSRecvBean> list) throws SQLException {
								// id, phone,content,sendtime, systime
		String sql = " INSERT INTO   " + table + "  VALUES (null, ?, ?, ?, ?) ";
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
		this.table  = table;
	}

}
