package com.ikohoo.domain;

import java.sql.Timestamp;

public class SMSRecvBean {
	private long id;
	private String phone;
	private String content;
	private Timestamp sendtime;
	private Timestamp systime;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getSendtime() {
		return sendtime;
	}
	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}
	public Timestamp getSystime() {
		return systime;
	}
	public void setSystime(Timestamp systime) {
		this.systime = systime;
	}
	@Override
	public String toString() {
		return "SMSRecv [id=" + id + ", phone=" + phone + ", content="
				+ content + ", sendtime=" + sendtime + ", systime=" + systime
				+ "]";
	}
	

}
