package com.test.project.common.util;

import java.util.Date;

public class StringUtil {

	public static Date convNull(Object obj, Date def) {
		if (obj == null)
			return def;
		else
		return (Date)obj;
	}
	
	public static String convNull(String str, String def) {
		if (isNull(str) || isEmpty(str))
			return def;
		return str;
	}
	
	public static String convNull(Object obj, String def) {
		if (obj == null)
			return def;
		else
			return String.valueOf(obj);
	}
	
	public static int convNull(Object obj, int def) {
		if (obj == null || obj.equals(""))
			return def;
		else
		return Integer.parseInt(obj.toString());
	}
	
	public static double convNull(Object obj, double def) {
		if (obj == null || obj.equals(""))
			return def;
		else
			return Double.parseDouble(obj.toString());
	}
	
	public static Long convNull(Object obj, Long def) {
		if (obj == null || obj.equals(""))
			return def;
		else
			return Long.parseLong(obj.toString());
    }
    
	public static boolean isNull(String str) {

		boolean nRet = false;

		if ( str == null ) { nRet = true; }

		return nRet;
	}

	public static boolean isEmpty(String str) {
		boolean nRet = false;
		if (str == null || str.equals("") ) { nRet = true; }
		return nRet;
	}
	
	public static String removeComma(String number){
		String removeNum = "0";
		if(number != null && !number.equals("")){
			try{
				removeNum = number.replace(",", "");
			}catch(IllegalArgumentException e){
				removeNum = "0";
			}
		}

		return removeNum;
	}
	  
}
