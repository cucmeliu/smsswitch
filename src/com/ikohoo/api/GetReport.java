package com.ikohoo.api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.ikohoo.domain.Config;
import com.ikohoo.util.SMSUtils;

/**
 * 
 * @author apple
 * @deprecated
 */
public class GetReport {
	private String url;// = "http://h.1069106.com:1210/Services/MsgSend.asmx/GetReport";
	private Config config;

	public GetReport(Config config) {
		super();
		this.config = config;
		url = config.getUrl() + "GetReport";
	}
	
	/**
	 * 打桩测试
	 * @param batchNumber
	 * @return
	 * @deprecated
	 */
	public String getTest(String batchNumber) {
		return "2314437910933839404,15618658829,Error:4|";
	}

	/** 获取状态 **/
	public  String get(String batchNumber) {
		String str = "";
		
        try {
        	// 创建HttpClient实例     
            HttpClient httpclient =new DefaultHttpClient();
           
             //构造一个post对象
             HttpPost httpPost = new HttpPost(url);
             //添加所需要的post内容
             List<NameValuePair> nvps = new ArrayList<NameValuePair>();
             nvps.add(new BasicNameValuePair("userCode", config.getUserCode()));
             nvps.add(new BasicNameValuePair("userPass", config.getUserPass()));
             nvps.add(new BasicNameValuePair("batchNumber", batchNumber));
              
             httpPost.setEntity( new UrlEncodedFormEntity(nvps, "UTF-8") );
             HttpResponse response = httpclient.execute(httpPost);
             HttpEntity entity = response.getEntity();
             if (entity != null) {
            	 InputStream instreams = entity.getContent();
            	 str = SMSUtils.convertStreamToString(instreams); 
             }  
            
             return SMSUtils.parseReturn(str);
             
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
}
