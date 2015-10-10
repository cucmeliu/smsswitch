package com.ikohoo.factory;

import java.util.Properties;

import org.apache.log4j.Logger;

public class BasicFactory {
	private static BasicFactory factory = new BasicFactory();
	private static Properties prop = null;
	private static final String propfilename = "/config.properties";
	
	static Logger logger = Logger.getLogger(BasicFactory.class);
	
	private BasicFactory(){}
	
	static{
		try{
			prop = new Properties();  //ConfigReader.class.getResourceAsStream("/project.properties"); 
			
			//prop.load(new FileReader(BasicFactory.class.getResource("/config.properties").getPath()));  
			//prop.load(new FileReader(BasicFactory.class.getClassLoader().getResource("config.properties").getPath()));
			prop.load(BasicFactory.class.getResourceAsStream(propfilename));
			System.out.println("Factory path: "+BasicFactory.class.getResource(propfilename).getPath());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public static BasicFactory getFactory(){
		return factory;
	}
	
	public <T> T getInstance(Class<T> clazz){
		try{
		String infName = clazz.getSimpleName();
		String implName = prop.getProperty(infName);
		return (T) Class.forName(implName).newInstance();
		} catch(Exception e) {
			e.printStackTrace();
			//logger.error(e);
			throw new RuntimeException(e);
		}
		
	}

}
