package com.ikohoo.dao;

import java.sql.SQLException;
import java.util.List;

import com.ikohoo.domain.SMSRecvBean;

public interface SMSRecvDao {
	
	public void setTable(String table);

	int[] insert(List<SMSRecvBean> list) throws SQLException;

}
