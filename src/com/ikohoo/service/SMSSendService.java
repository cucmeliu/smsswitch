package com.ikohoo.service;

import java.util.List;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSSendBean;

public interface SMSSendService {

	/**
	 * 从发送表中取出待发送的内容
	 * @return
	 */
	public List<SMSSendBean> getNewSMS(int count);
	
	/**
	 * 批量更新状态
	 * @param list：待更新的列表
	 * @return 更新成功的数量
	 */
	public int[] updateState2DB(List<SMSSendBean> list);
	
	/**
	 * 插入一条记录到待发送表
	 * @param record
	 */
	public int insert2DB(SMSSendBean record);
	
	public int insert2DB(List<SMSSendBean> list);
	
	/**
	 * 逐条发送短信
	 * @param list
	 * @return 发送成功的条数
	 * @deprecated
	 */
	public int sendSMSList(List<SMSSendBean> list);
	
	/**
	 * 打包发送短信
	 * @param list 待发送的一包
	 * @return
	 */
	public int sendOnePack(List<SMSSendBean> list);
	
	/**
	 * 按打包方式发送的业务逻辑，包括组包过程
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	public int packSend(List<SMSSendBean> list);
	
	/**
	 * 按id号排序，将新从数据库中取出来的待发列表，并入内存列表
	 * @param total
	 * @param newList
	 */
	public void addNewListToTotal(List<SMSSendBean> total, List<SMSSendBean> newList);
	
	/**
	 * 处理掉已经提交过的短信：插入数据库、从待发列表删除
	 * @param list：待发列表，包括已提交和未提交的短信
	 * @return：返回本次处理的条数
	 */
	public int dealSentSMS(List<SMSSendBean> list);
	
	public void setConfig(Config config);
}
