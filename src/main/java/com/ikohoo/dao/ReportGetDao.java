package com.ikohoo.dao;

import java.sql.SQLException;
import java.util.List;

import com.ikohoo.domain.ReportGetBean;

public interface ReportGetDao {
	public void setTable(String table);
	
	public int[] insert(List<ReportGetBean> list) throws SQLException;
	
	public void setReptid(String reptid);

	public void setReptphone(String reptphone);

	public void setReptstatcode(String reptstatcode);
	
	public void setReptstatmsg(String reptstatmsg);

	public void setReptsmsid(String reptsmsid);

	public void setReptsendtime(String reptsendtime);
	//public int[] updateSend(List<ReportGetBean> list);
	
	/**
	 * 设置报告表主键自增序列值
	 * @param reptSeq
	 */
	public void setReptSeq(String reptSeq);
	
	/**
	 * 批量入库，集合中元素为推送结果拆分后的字符串数组
	 * <br>数组中存储四项值，[0]:批次号,[1]:手机号,[2]:时间,[3]:状态
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public int[] insertArr(List<String[]> list) throws SQLException;

}
