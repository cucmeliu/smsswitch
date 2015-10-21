package com.ikohoo.domain;

public class SMSSendParams {
	
	private String destNo;
	private String msg;
	private String channel;
	
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
		return "SendMsgParams [destNo=" + destNo + ", msg=" + msg
				+ ", channel=" + channel + "]";
	}
	

}
