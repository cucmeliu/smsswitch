package com.ikohoo.service;

import java.util.List;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSRecv;
import com.ikohoo.domain.SMSRecvBean;

public interface SMSRecvService {
	
	/**
	 * 直接返回格式
	 * @return
	 */
	public List<SMSRecv> getRecvList();
	
	/**
	 * 以数据库字段列表形式，返回回复信息
	 * @return
	 */
	public List<SMSRecvBean> getRecvListBean();
	
	public int insert2DB(List<SMSRecvBean> list);
	
	public void setConfig(Config config);

}
