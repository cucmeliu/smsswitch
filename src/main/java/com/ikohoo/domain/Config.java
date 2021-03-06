package com.ikohoo.domain;

public class Config {

	private String bizType;
	private int sendInterval;
	private int recvInterval;
	private int getReportInterval;

	private int sendCount;
	private int sendPause;
	private int sendThread;

	private int packMax;
	private int packMin;
	private int sendMax;
	
	private int isSign;
	private String sign;
	private int isSignHead;
	
	private int dbTestInterval;
	private int dbTestCount;
	private long dbTestPhone;
	private String dbTestContent;
	private String dbTestSign;

	//发送表名
	private String tableSend;
	//发送表字段
	private String sendId;
	private String sendPhone;
	private String sendContent;
	private String sendIntime;
	private String sendState;
	private String sendSendtime;
	private String sendStatcode;
	
	//接收表名
	private String tableRecv;
	//接收表字段
	private String recvId;
	private String recvPhone;
	private String recvContent;
	private String recvSendtime;
	private String recvSystime;
	
	//报告表名
	private String tableRept;
	//报告表字段
	private String reptId;
	private String reptPhone;
	private String reptStatCode;
	private String reptStatMsg;
	private String reptSmsId;
	private String reptSendTime;
	
	//云信使用。已发送表
	private String tableSent;

	private String url;
	private String userCode;
	private String userPass;
	private String channal;

	private String cmdSend;

	private String cmdSendIndiv;
	private String cmdRecv;
	private String cmdRept;
	
	//Oracle自增主键序列名称
	private String sendSeqVal;
	private String sendDoneSeqVal;
	private String receivesmsSeqVal;
	private String statSeqVal;

	public Config() {
		super();
		bizType = "yunxin";
		sendInterval = 2000;
		recvInterval = 2000;
		getReportInterval = 2000;

		sendCount = 100;
		sendPause = 2;
		sendThread = 8;

		packMax = 50;
		packMin = 10;
		sendMax = 500;
		
		dbTestInterval = 3000;
		dbTestCount = 500;
		dbTestPhone = 13000000000L;
		dbTestContent = "content msg";
		dbTestSign = "【sign】";

		url = "http://h.1069106.com:1210/Services/MsgSend.asmx/";
		userCode = "JSCS";
		userPass = "JSCS2015";

		tableSend = "send_sf";
		tableRecv = "receivesms_sf";
		tableRept = "stat_sf";

		cmdSend = "SendMsg";
		cmdSendIndiv = "SendIndividualMsg";
		cmdRecv = "GetMo2";
		cmdRept = "GetReport2";
		
		sendSeqVal = "send_sf_seq.nextval";
		sendDoneSeqVal = "send_sf_done_seq.nextval";
		receivesmsSeqVal = "receivesms_sf_seq.nextval";
		statSeqVal = "stat_sf_seq.nextval";
	}

	
	public int getDbTestInterval() {
		return dbTestInterval;
	}

	public void setDbTestInterval(int dbTestInterval) {
		this.dbTestInterval = dbTestInterval;
	}

	public int getDbTestCount() {
		return dbTestCount;
	}

	public void setDbTestCount(int dbTestCount) {
		this.dbTestCount = dbTestCount;
	}
	
	public String getCmdSend() {
		return cmdSend;
	}

	public void setCmdSend(String cmdSend) {
		this.cmdSend = cmdSend;
	}

	public String getCmdSendIndiv() {
		return cmdSendIndiv;
	}

	public void setCmdSendIndiv(String cmdSendIndiv) {
		this.cmdSendIndiv = cmdSendIndiv;
	}

	public String getCmdRecv() {
		return cmdRecv;
	}

	public void setCmdRecv(String cmdRecv) {
		this.cmdRecv = cmdRecv;
	}

	public String getCmdRept() {
		return cmdRept;
	}

	public void setCmdRept(String cmdRept) {
		this.cmdRept = cmdRept;
	}

	public String getTableSend() {
		return tableSend;
	}

	public void setTableSend(String tableSend) {
		this.tableSend = tableSend;
	}

	public String getTableRecv() {
		return tableRecv;
	}

	public void setTableRecv(String tableRecv) {
		this.tableRecv = tableRecv;
	}

	public String getTableRept() {
		return tableRept;
	}

	public void setTableRept(String tableRept) {
		this.tableRept = tableRept;
	}

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
		return "Config [bizType=" + bizType + ", sendInterval=" + sendInterval + ", recvInterval=" + recvInterval
				+ ", getReportInterval=" + getReportInterval + ", sendCount=" + sendCount + ", sendPause=" + sendPause
				+ ", sendThread=" + sendThread + ", packMax=" + packMax + ", packMin=" + packMin + ", sendMax="
				+ sendMax + ", dbTestInterval=" + dbTestInterval + ", dbTestCount=" + dbTestCount + ", dbTestPhone="
				+ dbTestPhone + ", dbTestContent=" + dbTestContent + ", dbTestSign=" + dbTestSign + ", tableSend="
				+ tableSend + ", tableRecv=" + tableRecv + ", tableRept=" + tableRept + ", tableSent=" + tableSent
				+ ", url=" + url + ", userCode=" + userCode + ", userPass=" + userPass + ", channal=" + channal
				+ ", cmdSend=" + cmdSend + ", cmdSendIndiv=" + cmdSendIndiv + ", cmdRecv=" + cmdRecv + ", cmdRept="
				+ cmdRept + "]";
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

	public String getTableSent() {
		return tableSent;
	}

	public void setTableSent(String tableSent) {
		this.tableSent = tableSent;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}


	public String getDbTestSign() {
		return dbTestSign;
	}


	public void setDbTestSign(String dbTestSign) {
		this.dbTestSign = dbTestSign;
	}


	public String getDbTestContent() {
		return dbTestContent;
	}


	public void setDbTestContent(String dbTestContent) {
		this.dbTestContent = dbTestContent;
	}


	public long getDbTestPhone() {
		return dbTestPhone;
	}


	public void setDbTestPhone(long dbTestPhone) {
		this.dbTestPhone = dbTestPhone;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public int getIsSign() {
		return isSign;
	}


	public void setIsSign(int isSign) {
		this.isSign = isSign;
	}


	public int getIsSignHead() {
		return isSignHead;
	}


	public void setIsSignHead(int isSignHead) {
		this.isSignHead = isSignHead;
	}


	public String getSendId() {
		return sendId;
	}


	public void setSendId(String sendId) {
		this.sendId = sendId;
	}


	public String getSendPhone() {
		return sendPhone;
	}


	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}


	public String getSendContent() {
		return sendContent;
	}


	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}


	public String getSendIntime() {
		return sendIntime;
	}


	public void setSendIntime(String sendIntime) {
		this.sendIntime = sendIntime;
	}


	public String getRecvId() {
		return recvId;
	}


	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}


	public String getRecvPhone() {
		return recvPhone;
	}


	public void setRecvPhone(String recvPhone) {
		this.recvPhone = recvPhone;
	}


	public String getRecvContent() {
		return recvContent;
	}


	public void setRecvContent(String recvContent) {
		this.recvContent = recvContent;
	}


	public String getReptId() {
		return reptId;
	}


	public void setReptId(String reptId) {
		this.reptId = reptId;
	}


	public String getReptPhone() {
		return reptPhone;
	}


	public void setReptPhone(String reptPhone) {
		this.reptPhone = reptPhone;
	}


	public String getReptStatCode() {
		return reptStatCode;
	}


	public void setReptStatCode(String reptStatCode) {
		this.reptStatCode = reptStatCode;
	}


	public String getReptStatMsg() {
		return reptStatMsg;
	}


	public void setReptStatMsg(String reptStatMsg) {
		this.reptStatMsg = reptStatMsg;
	}


	public String getReptSmsId() {
		return reptSmsId;
	}


	public void setReptSmsId(String reptSmsId) {
		this.reptSmsId = reptSmsId;
	}


	public String getReptSendTime() {
		return reptSendTime;
	}


	public void setReptSendTime(String reptSendTime) {
		this.reptSendTime = reptSendTime;
	}

	
	public String getSendState() {
		return sendState;
	}


	public void setSendState(String sendState) {
		this.sendState = sendState;
	}


	public String getSendSendtime() {
		return sendSendtime;
	}


	public void setSendSendtime(String sendSendtime) {
		this.sendSendtime = sendSendtime;
	}


	public String getSendStatcode() {
		return sendStatcode;
	}


	public void setSendStatcode(String sendStatcode) {
		this.sendStatcode = sendStatcode;
	}


	public String getRecvSendtime() {
		return recvSendtime;
	}


	public void setRecvSendtime(String recvSendtime) {
		this.recvSendtime = recvSendtime;
	}


	public String getRecvSystime() {
		return recvSystime;
	}


	public void setRecvSystime(String recvSystime) {
		this.recvSystime = recvSystime;
	}


	public String getSendSeqVal() {
		return sendSeqVal;
	}


	public void setSendSeqVal(String sendSeqVal) {
		this.sendSeqVal = sendSeqVal;
	}


	public String getSendDoneSeqVal() {
		return sendDoneSeqVal;
	}


	public void setSendDoneSeqVal(String sendDoneSeqVal) {
		this.sendDoneSeqVal = sendDoneSeqVal;
	}


	public String getReceivesmsSeqVal() {
		return receivesmsSeqVal;
	}


	public void setReceivesmsSeqVal(String receivesmsSeqVal) {
		this.receivesmsSeqVal = receivesmsSeqVal;
	}


	public String getStatSeqVal() {
		return statSeqVal;
	}


	public void setStatSeqVal(String statSeqVal) {
		this.statSeqVal = statSeqVal;
	}

}
