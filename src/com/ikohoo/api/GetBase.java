package com.ikohoo.api;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.ikohoo.domain.Config;
import com.ikohoo.util.SMSUtils;

/**
 * 获取信息的基类，可扩展
 * @author apple
 *
 */
public class GetBase {
	//static Logger logger = Logger.getLogger(GetBase.class);
	private Config config;
	private String url;
	
	private static HttpClient httpclient;

	
	static {
		httpclient = HttpClientBuilder.create().build();
	}
	
	public GetBase(Config config) {
		this.config = config;
	}
	
	/**
	 * 
	 * @param method: GetMo, GetMo2, GetReport, GetReport2
	 * @return
	 * @throws Exception 
	 */
	public String get(String method) throws Exception {
		
		url = config.getUrl() + method;
		
		String str = "";
        try {
        	// 创建HttpClient实例     
            //HttpClient httpclient = new DefaultHttpClient();

             //构造一个post对象
             HttpPost httpPost = new HttpPost(url);
             //添加所需要的post内容
             List<NameValuePair> nvps = new ArrayList<NameValuePair>();
             nvps.add(new BasicNameValuePair("userCode", config.getUserCode()));
             nvps.add(new BasicNameValuePair("userPass", config.getUserPass()));
             
             httpPost.setEntity( new UrlEncodedFormEntity(nvps, "UTF-8") );
             HttpResponse response = httpclient.execute(httpPost);
             HttpEntity entity = response.getEntity();
             if (entity != null) {    
            	 InputStream instreams = entity.getContent();    
            	 str = SMSUtils.convertStreamToString(instreams);  
             }  
            
             return SMSUtils.parseReturn(str);
             
        } catch (Exception e) {
        	//e.printStackTrace();
        	//logger.error(e);
            throw e;
        	//return null;
        }
	}
	
	
	
	/**
	 * 打桩
	 * @param method
	 * @return
	 * @deprecated
	 */
	public String getTest(String method) {
		if ("GetMo".equals(method)) {
			return "18651650023|,|A回复内容|,|2015/10/3 16:25:10|;|15618658829|,|B回复内容|,|2015/10/3 16:25:15|;|";
		} 
		else if ("GetMo2".equals(method)) {
			Random r = new Random();
			int sum = r.nextInt(500);
			//System.out.println("GetMo2: " + sum);
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<sum; i++) {
				sb.append(13900000000L+i)
					.append("|,|")
					.append("回复内容"+i)
					.append("|,|")
					.append(new Timestamp(System.currentTimeMillis()))
					.append("|,|")
					.append(2314357620085030624L+i)
					.append("|;|");
			}
			
			return sb.toString();
		} 
		else if ("GetReport".equals(method)) {
			return "2314437910933839404,15618658829,Error:4|";
		} 
		else if ("GetReport2".equals(method)) {
			Random r = new Random();
			int sum = r.nextInt(500);
			//System.out.println("getTest size=" + sum);
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<sum; i++) {
				sb.append(2314357620085030624L+i)
					.append(",")
					.append(13900000000L+i)
					.append(",")
					.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
					.append(",");
				
				if (r.nextBoolean())
					sb.append("DELIVRD");
				else 
					sb.append("UNDELIVRD");
				sb.append("|");
			}
			//System.out.println("getTest: " + sb.toString());
			return sb.toString();
		}
		
		return null;
	}

}
