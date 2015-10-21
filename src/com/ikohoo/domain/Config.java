package com.ikohoo.domain;

public class Config {

	private int sendInterval;
	private int sendCount;
	private int sendPause;
	private int sendThread;
	private int packMax;
	private int packMin;
	private int sendMax;
	private int recvInterval;
	private int getReportInterval;
	private String url;
	private String userCode;
	private String userPass;
	private String channal;
	
	
	public int getGetReportInterval() {
		return getReportInterval;
	}
	public void setGetReportInterval(int getReportInterval) {
		this.getReportInterval = getReportInterval;
	}
	public int getSendInterval() {
		return sendInterval;
	}
	public void setSendInterval(int sendInterval) {
		this.sendInterval = sendInterval;
	}
	public int getRecvInterval() {
		return recvInterval;
	}
	public void setRecvInterval(int recvInterval) {
		this.recvInterval = recvInterval;
	}
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public Config() {
		super();
		sendInterval = 2000;
		recvInterval = 2000;
		getReportInterval = 2000;
		sendCount = 100;
		sendPause = 2;
		packMax = 50;
		url = "http://h.1069106.com:1210/Services/MsgSend.asmx/";
		userCode = "JSCS";
		userPass = "JSCS2015";
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getSendCount() {
		return sendCount;
	}
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
	@Override
	public String toString() {
		return "Config [sendInterval=" + sendInterval + ", sendCount="
				+ sendCount + ", sendPause=" + sendPause + ", sendThread="
				+ sendThread + ", packMax=" + packMax + ", packMin=" + packMin
				+ ", sendMax=" + sendMax + ", recvInterval=" + recvInterval
				+ ", getReportInterval=" + getReportInterval + ", url=" + url
				+ ", userCode=" + userCode + ", userPass=" + userPass
				+ ", channal=" + channal + "]";
	}
	public int getSendPause() {
		return sendPause;
	}
	public void setSendPause(int sendPause) {
		this.sendPause = sendPause;
	}
	public int getPackMax() {
		return packMax;
	}
	public void setPackMax(int packMax) {
		this.packMax = packMax;
	}
	public int getSendThread() {
		return sendThread;
	}
	public void setSendThread(int sendThread) {
		this.sendThread = sendThread;
	}
	public String getChannal() {
		return channal;
	}
	public void setChannal(String channal) {
		this.channal = channal;
	}
	public int getPackMin() {
		return packMin;
	}
	public void setPackMin(int packMin) {
		this.packMin = packMin;
	}
	public int getSendMax() {
		return sendMax;
	}
	public void setSendMax(int sendMax) {
		this.sendMax = sendMax;
	}
	
}
