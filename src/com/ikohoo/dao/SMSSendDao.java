package com.ikohoo.dao;

import java.util.List;
import com.ikohoo.domain.SMSSendBean;

public interface SMSSendDao {
	/**
	 * 从发送表中取出待发送的内容
	 * @return
	 */
	public List<SMSSendBean> getNewSMS(int count);

	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	public int[] updateSendState(List<SMSSendBean> list);

	/**
	 * 插入一条记录到待发送表
	 * @param record
	 * @return
	 */
	public int insert(SMSSendBean record);

	public int[] insert(List<SMSSendBean> list);
}
