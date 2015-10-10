package com.ikohoo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.ikohoo.domain.SMSSendBean;

public class SMSUtils {
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static final long RETURN_ERROR_UNKNOWN = -1000; // 未知错误
	public static final long RETURN_ERROR_NULL_DOC = -999; // 返回的XML Doc 为空
	public static final long RETURN_ERROR_NULL_ELT = -998; // 文档的根节点为空
	public static final long RETURN_ERROR_NULL_TXT = -997; // 根节点的值内容为空
	public static final long RETURN_ERROR_TYPE_NO_LONG = -996; // 返回的值不是长整型
	
	//public static final int PACK_SMS_MAX=50;

	// public static final long RETURN_ERROR_NULL_DOC=-999;


	//static Document doc=null;
	
	public static String parseReturnDom(String str) {
//		Document doc = null;
		try {
			long start = System.currentTimeMillis();
			Document doc = new SAXReader().read(new InputSource(new StringReader(str))); //DocumentHelper.parseText(str);  // 将字符串转为XML
			System.out.println("parse str: "+str+", cost: " + (System.currentTimeMillis()-start) + " ms");

//			StringReader sr = new StringReader(str);
//			InputSource is = new InputSource(sr);
//			Document doc = (new SAXBuilder()).build(is);
			
//			org.w3c.dom.Document doc = DocumentBuilderFactory
//					.newInstance()
//					.newDocumentBuilder()
//					.parse(new InputSource(new StringReader(str)));
//			doc.getElementById();
			
			if (doc == null)
				return null;// RETURN_ERROR_NULL_DOC;

			Element rootElt = doc.getRootElement(); // 获取根节点
			if (rootElt == null)
				return null; // RETURN_ERROR_NULL_ELT;
			// System.out.println("根节点的值：" + rootElt.getText()); // 拿到根节点的名称
			if (rootElt.getText() == null || "".equals(rootElt.getText()))
				return null; // RETURN_ERROR_NULL_TXT;

			return rootElt.getText();

		} catch (DocumentException e) {
			e.printStackTrace();
			return null; // RETURN_ERROR_NULL_DOC;
		} catch (Exception e) {
			e.printStackTrace();
			return null; // RETURN_ERROR_TYPE_NO_LONG;
		}

	}
	
	public static String parseReturnSAX(String str) {
		System.out.println("parseReturnSAX" + str.replaceAll(".+;>", "").replaceAll("", ""));
		
		return "";
		
	}
	public static String parseReturnStrRep(String str) {
		//确定是否所有返回都是这样的格式，如果是，就可以 <string xmlns="http://tempuri.org/"> xxxx  </string>
//		return str.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "")
//				.replace("<string xmlns=\"http://tempuri.org/\"/>", "")
//				.replace("</string>", "")
//				.trim();
		
		String regex = "<[^>]*>";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(str);
		return mat.replaceAll("").trim();
	}
	/**
	 * 解析出返回XML中的值内容
	 * 
	 * @param str
	 * @return
	 * # TODO 待确定方法是否能解析所有情况
	 */
	public static String parseReturn(String str) {
		// parseReturnDom
		// parseReturnSAX
		return parseReturnStrRep(str);
	}

	public static long parseSendSMSReturn(String str) {
		String ret = parseReturnStrRep(str); //parseReturn(str);
		System.out.println("parse result: " + ret);
		if (null == ret || "".equals(ret)) {
			return RETURN_ERROR_UNKNOWN;
		} else
			return Long.parseLong(ret);

	}
	
	/**
	 * 二分查找并插入数据
	 * @param list
	 * @param smsSend
	 */
	public static void binaryInsSMS(List<SMSSendBean> list, SMSSendBean smsSend) {
		// TODO 算法实现
		list.add(smsSend);
		
	}

	/**
	 * 二分法查找列表中是否存在当前短信（以id查）
	 * 
	 * @param list
	 * @param smsSend
	 * @return
	 */
	public static int binarySearchSMS(List<SMSSendBean> list, SMSSendBean smsSend) {
		int low = 0;
		int high = list.size();
		long value = smsSend.getId();

		while (low <= high) {
			int middle = (low + high) / 2;
			if (value == list.get(middle).getId()) {
				return middle;
			} else
			if (value > list.get(middle).getId()) {
				low = middle + 1;
			} else
			if (value < list.get(middle).getId()) {
				high = middle - 1;
			}
		}
		
		return -1;
	}
}
