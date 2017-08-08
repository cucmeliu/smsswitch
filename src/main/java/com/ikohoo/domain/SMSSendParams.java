package com.ikohoo.domain;

public class SMSSendParams {
	
	private String destNo;
	private String msg;
	private String channel;
	private String smsType;
	
	public String getDestNo() {
		return destNo;
	}
	public void setDestNo(String destNo) {
		this.destNo = destNo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Override
	public String toString() {
		return "SMSSendParams [destNo=" + destNo + ", msg=" + msg
				+ ", channel=" + channel + ", smsType=" + smsType + "]";
	}
	public String getSmsType() {
		return smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	

}
