package com.ikohoo.domain;

import java.sql.Timestamp;

public class ReportGetBean {
	@Override
	public String toString() {
		return "ReportGetBean [id=" + id + ", phone=" + phone + ", statcode="
				+ statcode + ", statmsg=" + statmsg + ", smsid=" + smsid
				+ ", sendtime=" + sendtime + "]";
	}
	private long id;
	private String phone;
	private String statcode;
	private String statmsg;
	private String smsid;
	private Timestamp sendtime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatcode() {
		return statcode;
	}
	public void setStatcode(String statcode) {
		this.statcode = statcode;
	}
	public String getStatmsg() {
		return statmsg;
	}
	public void setStatmsg(String statmsg) {
		this.statmsg = statmsg;
	}
	public String getSmsid() {
		return smsid;
	}
	public void setSmsid(String smsid) {
		this.smsid = smsid;
	}
	public Timestamp getSendtime() {
		return sendtime;
	}
	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}
	
	

}
