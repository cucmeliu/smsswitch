package com.ikohoo.dao;

import java.sql.SQLException;
import java.util.List;

import com.ikohoo.domain.SMSRecvBean;

public interface SMSRecvDao {
	
	public void setTable(String table);

	int[] insert(List<SMSRecvBean> list) throws SQLException;
	
	public void setRecvid(String recvid);

	public void setRecvphone(String recvphone);

	public void setRecvcontent(String recvcontent);
	
	public void setRecvsendtime(String recvsendtime);
	
	public void setRecvsystime(String recvsystime);
	
	/**
	 * 设置接收表主键自增序列值
	 * @param recvSeq
	 */
	public void setRecvSeq(String recvSeq);
	
	/**
	 * 批量入库，集合中元素为推送结果拆分后的字符串数组
	 * <br>数组中存储四项值，[0]:手机号,[1]:回复内容,[2]:回复时间,[3]:通道号码
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	int[] insertArr(List<String[]> list) throws SQLException;

}
