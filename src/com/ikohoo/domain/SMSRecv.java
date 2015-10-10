package com.ikohoo.domain;

import java.sql.Timestamp;

public class SMSRecv {
	private String phone;
	private String content;
	private Timestamp replytime;
	private long batchNumber;
	
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
	public Timestamp getReplytime() {
		return replytime;
	}
	public void setReplytime(Timestamp replytime) {
		this.replytime = replytime;
	}
	public long getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(long batchNumber) {
		this.batchNumber = batchNumber;
	}
	@Override
	public String toString() {
		return "SMSRecv [phone=" + phone + ", content=" + content
				+ ", replytime=" + replytime + ", batchNumber=" + batchNumber
				+ "]";
	}
	
	

}
