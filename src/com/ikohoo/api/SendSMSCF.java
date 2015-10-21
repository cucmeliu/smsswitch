package com.ikohoo.api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.*;
import org.apache.log4j.Logger;

import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSSendParams;
import com.ikohoo.util.SMSUtils;

public class SendSMSCF {
	static Logger logger = Logger.getLogger(SendSMSCF.class);
	private String url; // "http://h.1069106.com:1210/Services/MsgSend.asmx/SendMsg";
	private Config config;
	
	/**
	 * 触发发送短信命令
	 */
	public static final String SendMsg = "SendMsg";
	/**
	 * 触发发送个性化短信命令
	 */
	public static final String SendIndividualMsg = "SendIndividualMsg";
	
	/** 云信url到http://yes.itissm.com/api/
	 *  云信发送短信命令
	 */
	public static final String sendMes = "MsgSend.asmx/sendMes";
	/**
	 *  云信发送个性化短信命令
	 */
	public static final String IndividualSm = "IndividualSm.aspx";
	

	public SendSMSCF(Config config) {
		super();
		this.config = config;
		//url = config.getUrl() + "SendMsg";
	}

	/**
	 * 发送短信
	 * @param msg
	 * @return 批次号
	 * @throws Exception 
	 */
	public String send(SMSSendParams msg, String cmd) throws Exception {
		url = config.getUrl() +  cmd;//"SendMsg";
		
		String str = "";
		try {
			System.out.println("Sending: " + msg.toString());

			// 创建HttpClient实例
			HttpClient httpclient = new DefaultHttpClient();

			// 构造一个post对象
			HttpPost httpPost = new HttpPost(url);
			// 添加所需要的post内容
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("userCode", config.getUserCode())); // msg.getUserCode()));
			nvps.add(new BasicNameValuePair("userPass", config.getUserPass())); // msg.getUserPass()));
			nvps.add(new BasicNameValuePair("DesNo", msg.getDestNo()));
			nvps.add(new BasicNameValuePair("Msg", msg.getMsg()));
			nvps.add(new BasicNameValuePair("Channel", msg.getChannel()));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				str = SMSUtils.convertStreamToString(instreams);
			}

			return SMSUtils.parseSendSMSReturn(str);

		} catch (Exception e) {
			//e.printStackTrace();
			//logger.error(e);
			throw e;
			//return SMSUtils.RETURN_ERROR_UNKNOWN;
		}
	}

	/**
	 * 打包发送
	 * @param msg
	 * @return 批次号
	 * @throws Exception 
	 */
	public String sendPack(SMSSendParams msg, String cmd) throws Exception {
		
		url = config.getUrl() +  cmd; //"SendIndividualMsg";
		String str = "";
		try {
			System.out.println("Sending pack: " + msg.toString());

			// 创建HttpClient实例
			HttpClient httpclient = new DefaultHttpClient();

			// 构造一个post对象
			HttpPost httpPost = new HttpPost(url);
			// 添加所需要的post内容
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("userCode", config.getUserCode()));
			nvps.add(new BasicNameValuePair("userPass", config.getUserPass())); 
			nvps.add(new BasicNameValuePair("Msg", msg.getMsg()));
			nvps.add(new BasicNameValuePair("Channel", msg.getChannel()));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				str = SMSUtils.convertStreamToString(instreams);
			}

			return SMSUtils.parseSendSMSReturn(str);

		} catch (Exception e) {
			//e.printStackTrace();
			//logger.error(e);
			throw e;
			//return SMSUtils.RETURN_ERROR_UNKNOWN;
		}
	}

	/**
	 * 打桩测试
	 * 
	 * @param msg
	 * @return
	 * @deprecated
	 */
	public String sendTest(SMSSendParams msg) {
		// System.out.println("send msg: " + msg.toString());
		String str = null;
		Random r = new Random();
		if (r.nextBoolean()) {
			str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					 "<string xmlns=\"http://tempuri.org/\">2314437910941733540</string>";
		} else {
			str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<string xmlns=\"http://tempuri.org/\">"+(-1-r.nextInt(17))+"</string>";
		}
//		long start = System.currentTimeMillis();
		String ret = SMSUtils.parseSendSMSReturn(str);
//		System.out.println("parse cost: " + (System.currentTimeMillis()-start) + " ms");
		// System.out.println("return: " + ret);
		return ret;
	}
	
	
}
