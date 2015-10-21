package com.ikohoo.dao;

import java.sql.SQLException;
import java.util.List;

import com.ikohoo.domain.ReportGetBean;

public interface ReportGetDao {
	public int[] insert(List<ReportGetBean> list) throws SQLException;
	
	//public int[] updateSend(List<ReportGetBean> list);

}
