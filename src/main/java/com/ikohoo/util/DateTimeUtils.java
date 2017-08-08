package com.ikohoo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

	/** yyyy-MM-dd HH:mm:ss */
	public static final DateFormat FORMAT_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** yyyy/MM/dd HH:mm:ss */
	public static final DateFormat FORMAT_FULL_SLASH = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	/** yyyy-MM-dd HH:mm:ss */
	public static String format(Date date){
		return FORMAT_FULL.format(date);
	}
	
	public static String format(Date date, DateFormat format){
		return format.format(date);
	}
}
