package com.ikohoo.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SomeTest {
	
	public static void main(String[] args){
		
		String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\"/> 这里是要的内容</string>";
		System.out.println("before replace: " + str);
		
		String regex = "<[^>]*>";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(str);
		str = mat.replaceAll("");
		
		//str.replace("<[^>]*>", "");
		System.out.println("after replace: " + str);
		
	}

}
