package com.ikohoo.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import com.ikohoo.domain.Config;

public class ConfigReader {
	static Logger logger = Logger.getLogger(ConfigReader.class);

	// private static final String xmlfilename = "proj-config.xml";
	private static final String propfilename = "/project.properties";
	private static Config config;

	public static Config loadConfig() {
		if (config == null)
			config = getProp(); // getXML();
		return config;
	}

	private static Config getProp() {

		Config config = new Config();
		Properties prop = new Properties();
		try {

			// 读jar包外面的配置
			// String proFilePath = System.getProperty("user.dir") +
			// "/project.properties";
			// InputStream in = new BufferedInputStream(new
			// FileInputStream(proFilePath));
			// System.out.println("Prop file path: " + proFilePath);
			// ResourceBundle resourceBundle = new PropertyResourceBundle(in);

			// 读jar包内的配置
			InputStream in = ConfigReader.class
					.getResourceAsStream(propfilename);

			prop.load(in);
			config.setSendInterval(Integer.parseInt(prop.getProperty(
					"send-interval", 2000 + "")));
			config.setRecvInterval(Integer.parseInt(prop.getProperty(
					"recv-interval", 2000 + "")));
			config.setGetReportInterval(Integer.parseInt(prop.getProperty(
					"getReport-interval", 2000 + "")));

			config.setSendThread(Integer.parseInt(prop.getProperty(
					"send-thread", 8 + "")));
			config.setSendCount(Integer.parseInt(prop.getProperty("send-count",
					1000 + "")));
			config.setSendPause(Integer.parseInt(prop.getProperty("send-pause",
					10 + "")));

			config.setPackMax(Integer.parseInt(prop.getProperty("pack-max",
					50 + "")));
			config.setPackMin(Integer.parseInt(prop.getProperty("pack-min",
					50 + "")));
			config.setSendMax(Integer.parseInt(prop.getProperty("send-max",
					500 + "")));

			config.setUrl(prop.getProperty("url",
					"http://h.1069106.com:1210/Services/MsgSend.asmx/"));
			config.setUserCode(prop.getProperty("usercode", "JSCS"));
			config.setUserPass(prop.getProperty("userpass", "JSCS2015"));

			config.setChannal(prop.getProperty("channal", "0"));
			config.setCmdRecv(prop.getProperty("cmdRecv", "GetMo2"));
			config.setCmdRept(prop.getProperty("cmdRept", "GetReport2"));
			config.setCmdSend(prop.getProperty("cmdSend", "SendMsg"));
			config.setCmdSendIndiv(prop.getProperty("cmdSendIndiv",
					"SendIndividualMsg"));

			config.setTableSend(prop.getProperty("table-send", "send_sf"));
			config.setTableRecv(prop.getProperty("table-recv", "receivesms_sf"));
			config.setTableRept(prop.getProperty("table-rept", "stat_sf"));

		} catch (Exception e) {
			e.printStackTrace();
			;
			logger.error(e);
		}

		return config;
	}

	/**
	 * 
	 * @return
	 * 
	 */
	// private static Config getXML() {
	// Config config = new Config();
	// try {
	// File f = new File(xmlfilename);
	// if (!f.exists()) {
	// System.out
	// .println("Error: config file doesnot exist, use default config!");
	// logger.error("Error: config file doesnot exist, use default config!");
	// }
	// System.out.println("config file load successfully.");
	//
	// SAXReader reader = new SAXReader();
	// Document doc = reader.read(f);
	// Element root = doc.getRootElement();
	// Element data;
	// Iterator<?> it = root.elementIterator("common");
	// data = (Element) it.next();
	//
	// config.setSendInterval(Integer.parseInt(data.elementText(
	// "send-interval").trim()));
	// config.setSendCount(Integer.parseInt(data.elementText("send-count")
	// .trim()));
	// config.setRecvInterval(Integer.parseInt(data.elementText(
	// "recv-interval").trim()));
	// config.setGetReportInterval(Integer.parseInt(data.elementText(
	// "getReport-interval").trim()));
	// config.setUrl(data.elementText("url").trim());
	// config.setUserCode(data.elementText("usercode").trim());
	// config.setUserPass(data.elementText("userpass").trim());
	// // System.out.println("config: " + config.toString());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error(e);
	// //throw new RuntimeException(e);
	// }
	//
	// return config;
	// }

}
