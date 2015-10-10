package com.ikohoo.dao;

import java.util.List;

import com.ikohoo.domain.ReportGetBean;

public interface ReportGetDao {
	public int[] insert(List<ReportGetBean> list);
	
	//public int[] updateSend(List<ReportGetBean> list);

}
