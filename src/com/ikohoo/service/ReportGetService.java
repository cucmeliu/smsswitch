package com.ikohoo.service;

import java.util.List;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.ReportGet;
import com.ikohoo.domain.ReportGetBean;

public interface ReportGetService {
	public ReportGet getReport(String batchNumber);
	
	/**
	 * 返回直接解析的状态报告
	 * @return
	 */
	public List<ReportGet> getReport2();
	
	/**
	 * 返回与状态报告表对应的状态
	 * @return
	 */
	public List<ReportGetBean> getReport2Status();
	
	public int insert2DB(List<ReportGetBean> list);
	
	public void setConfig(Config config);
	

}
