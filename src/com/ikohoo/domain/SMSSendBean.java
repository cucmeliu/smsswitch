package com.ikohoo.domain;

import java.sql.Timestamp;

public class SMSSendBean {
	private long id;
	private String phone;
	private String content;
	private Timestamp intime;
	private int state;
	private Timestamp sendtime;
	
	private long statcode;

	public long getStatcode() {
		return statcode;
	}
	public void setStatcode(long statcode) {
		this.statcode = statcode;
	}
	/**
	 * 0,待发送
	 */
	public static final byte STATE_TOBESENT = 0;
	/**
	 * 1提交成功
	 */
	public static final byte STATE_SUBSUCC = 1;
	/**
	 * 2提交失败
	 */
	public static final byte STATE_SUBFAIL = 2;
	/**
	 * 3发送成功
	 */
	public static final byte STATE_SENDSUCC = 3;
	/**
	 * 4发送失败
	 */
	public static final byte STATE_SENDFAIL = 4;
	/**
	 * 5黑字典过滤
	 */
	public static final byte STATE_BLKDICT = 5;
	/**
	 * 6黑名单过滤
	 */
	public static final byte STATE_BLKLIST = 6;
	
	
	
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
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Timestamp getSendtime() {
		return sendtime;
	}
	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}
	@Override
	public String toString() {
		return "SMSSend [id=" + id + ", phone=" + phone + ", content="
				+ content + ", intime=" + intime + ", state=" + state
				+ ", sendtime=" + sendtime + "]";
	}
	
	
}
