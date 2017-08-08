package com.ikohoo.domain;

public class ReportGet {
	private String batchNum;
	private String phone;
	private String sendtime;
	private String status;
	
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ReportGet [batchNum=" + batchNum + ", phone=" + phone
				+ ", sendtime=" + sendtime + ", status=" + status + "]";
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	

}
