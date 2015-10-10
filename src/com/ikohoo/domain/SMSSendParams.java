package com.ikohoo.domain;

public class SMSSendParams {
	
	public static final String channelSMS = "0";
	public static final String channelGLB = "999";
	
//	private String userCode;
//	private String userPass;
	private String destNo;
	private String msg;
	private String channel;
	
//	public String getUserCode() {
//		return userCode;
//	}
//	public void setUserCode(String userCode) {
//		this.userCode = userCode;
//	}
//	public String getUserPass() {
//		return userPass;
//	}
//	public void setUserPass(String userPass) {
//		this.userPass = userPass;
//	}
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
