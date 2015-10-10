package com.ikohoo.dao;

import java.util.List;

import com.ikohoo.domain.SMSRecvBean;

public interface SMSRecvDao {

	int[] insert(List<SMSRecvBean> list);

}
