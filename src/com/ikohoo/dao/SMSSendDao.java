package com.ikohoo.dao;

import java.sql.SQLException;
import java.util.List;

import com.ikohoo.domain.SMSSendBean;

public interface SMSSendDao {
	
//	public void setConfig(Config config);
	public void setTable(String table);
	
	/**
	 * 从发送表中取出待发送的内容
	 * @return
	 * @throws SQLException 
	 */
	public List<SMSSendBean> getNewSMS(int count) throws SQLException;

	/**
	 * 批量更新
	 * @param list
	 * @return
	 * @throws SQLException 
	 */
	public int[] updateSendState(List<SMSSendBean> list) throws SQLException;
	
	/**
	 * 删除已处理的记录
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public int[] delSentRec(List<SMSSendBean> list) throws SQLException;
	
	/**
	 * 将已发送的记录写入已发送表，另存
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public int[] insSentRec(List<SMSSendBean> list) throws SQLException;

	/**
	 * 插入一条记录到待发送表
	 * @param record
	 * @return
	 * @throws SQLException 
	 */
	public int insert(SMSSendBean record) throws SQLException;

	public int[] insert(List<SMSSendBean> list) throws SQLException;

	public List<SMSSendBean> getNewSMS(int count, int mod, int remainder) throws SQLException;
}
