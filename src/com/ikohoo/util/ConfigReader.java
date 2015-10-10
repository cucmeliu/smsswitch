package com.ikohoo.util;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ikohoo.domain.Config;

public class ConfigReader {
	static Logger logger = Logger.getLogger(ConfigReader.class);

	private static final String xmlfilename = "proj-config.xml";
	private static final String propfilename = "/project.properties";
	private static Config config;

	public static Config loadConfig() {
		if (config == null)
			config = getProp(); //getXML();
		return config;
	}

	private static Config getProp() {

		Config config = new Config();
		Properties prop = new Properties();
		try {
			InputStream in = ConfigReader.class.getResourceAsStream(propfilename);
			//ConfigReader.class.getClass().getClassLoader().getResourceAsStream("/project.properties"); 
			// new BufferedInputStream(new FileInputStream("project.properties"));
			//System.out.println("property path: " + ConfigReader.class.getResource(propfilename).getPath());
			prop.load(in);
			config.setSendInterval(Integer.parseInt(prop.getProperty(
					"send-interval", 2000 + "")));
			config.setRecvInterval(Integer.parseInt(prop.getProperty(
					"recv-interval", 2000 + "")));
			config.setGetReportInterval(Integer.parseInt(prop.getProperty(
					"getReport-interval", 2000 + "")));
			
			config.setSendCount(Integer.parseInt(prop.getProperty(
					"send-count", 1000 + "")));
			config.setSendPause(Integer.parseInt(prop.getProperty(
					"send-pause", 10 + "")));
			config.setPackMax(Integer.parseInt(prop.getProperty(
					"pack-max", 50 + "")));
			
			config.setUrl(prop.getProperty(
					"url", "http://h.1069106.com:1210/Services/MsgSend.asmx/"));
			config.setUserCode(prop.getProperty(
					"usercode", "JSCS"));
			config.setUserPass(prop.getProperty(
					"userpass", "JSCS2015"));

		} catch (Exception e) {
			System.out
					.println("Error: config file doesnot exist, use default config!");
			logger.error("Error: config file doesnot exist, use default config!"
					+e.getStackTrace());
		}

		return config;
	}
	/**
	 * 
	 * @return
	 * 
	 */
	private static Config getXML() {
		Config config = new Config();
		try {
			File f = new File(xmlfilename);
			if (!f.exists()) {
				System.out
						.println("Error: config file doesnot exist, use default config!");
				logger.error("Error: config file doesnot exist, use default config!");
			}
			System.out.println("config file load successfully.");

			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element data;
			Iterator<?> it = root.elementIterator("common");
			data = (Element) it.next();

			config.setSendInterval(Integer.parseInt(data.elementText(
					"send-interval").trim()));
			config.setSendCount(Integer.parseInt(data.elementText("send-count")
					.trim()));
			config.setRecvInterval(Integer.parseInt(data.elementText(
					"recv-interval").trim()));
			config.setGetReportInterval(Integer.parseInt(data.elementText(
					"getReport-interval").trim()));
			config.setUrl(data.elementText("url").trim());
			config.setUserCode(data.elementText("usercode").trim());
			config.setUserPass(data.elementText("userpass").trim());
			// System.out.println("config: " + config.toString());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
			throw new RuntimeException(e);
		}

		return config;
	}

}
