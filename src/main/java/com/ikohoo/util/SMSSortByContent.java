package com.ikohoo.util;

import java.util.Comparator;

import com.ikohoo.domain.SMSSendBean;

public class SMSSortByContent implements Comparator<SMSSendBean> {

	@Override
	public int compare(SMSSendBean o1, SMSSendBean o2) {
		return o1.getContent().compareTo(o2.getContent());
	}


}
