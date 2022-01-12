package com.test.project.common.util;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RefundCalcUtil {
	
	
	/*
	// 서버 올라갈때 한번만 실행함
	public static Map<Integer, Object> testRate1;
	@Value("${refund.rate.test1}")
	public void setTestRate1(String value) { 
		testRate1 = getRateData(value); 
	}*/
	
	public static String testRate1;
	@Value("${refund.rate.test1}")
	public void setTestRate1(String value) { testRate1 = value; }
	
	public static String testRate2;
	@Value("${refund.rate.test2}")
	public void setTestRate2(String value) { testRate2 = value; }
	
	
	// {4d:100,2d:90,1d:80} => 맵 형식으로 변환
	public static Map<Integer, Object> getRateData(String rateText){
		ObjectMapper mapper = new ObjectMapper();
		//" 없어도 무시
		mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			map = mapper.readValue(rateText, new TypeReference<Map<Object, Object>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Map<Integer, Object> convert = convertMap(map);
		
		//순서 잘못되어있을경우 대비 정렬 추가
		TreeMap<Integer, Object> treeMap = new TreeMap<Integer, Object>(Collections.reverseOrder());
		treeMap.putAll(convert);
		
		return treeMap;
		
	}
	
	public static Map<Integer, Object> convertMap(Map<Object, Object> param){
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		
		for (Map.Entry<Object, Object> entrySet : param.entrySet()) {
			
			String key = StringUtil.convNull(entrySet.getKey(), "").toLowerCase();
			if(key.contains("d")){
				key = key.replaceAll("d", "");
				boolean isNumeric = key.chars().allMatch( Character::isDigit );
				if(isNumeric) map.put(Integer.parseInt(key)*24, entrySet.getValue());
			}else if(key.contains("h")){
				key = key.replaceAll("h", "");
				boolean isNumeric = key.chars().allMatch( Character::isDigit );
				if(isNumeric) map.put(Integer.parseInt(key), entrySet.getValue());
			}else if(key.contains("w")){
				key = key.replaceAll("w", "");
				boolean isNumeric = key.chars().allMatch( Character::isDigit );
				if(isNumeric) map.put(Integer.parseInt(key)*24*7, entrySet.getValue());
			}else{
				boolean isNumeric = key.chars().allMatch( Character::isDigit );
				if(isNumeric) map.put(Integer.parseInt(key), entrySet.getValue());
			}
		}
		
		return map;
	}
	
	//환불될 금액을 리턴해줌 // format => 날짜형식 "yyyy-MM-dd HH:mm:ss"
	public static int getRefundPrice(String ymd, int price, String kind) throws Exception {  
		int refundPrice = 0;
		String format = "";
		Map<Integer, Object> data = new HashMap<Integer, Object>();
		//testRate1 => {4d:100,2d:90,1d:80}
		if(kind.equals("test1")){
			data = getRateData(testRate1);
			format = "yyyy-MM-dd HH:mm:ss";
		}else if(kind.equals("test2")){
			data = getRateData(testRate2);
			format = "yyyy-MM-dd HH:mm:ss";
		}
		
		System.out.println("data : " + data);
		
		SimpleDateFormat sp = new SimpleDateFormat(format);
		try {
			if(data != null && !ymd.equals("")){
				Date st = sp.parse(ymd);
				Date compare = new Date();
				long calDate = st.getTime() - compare.getTime(); 
				if(calDate < 0){ // 예약일이 지났을경우
					refundPrice = 0;
				}else{ // 예약일이 지나지 않았을경우 남은시간 계산
					long calDateTimes = calDate / (60*60*1000);  // 남은시간 계산
					calDateTimes = Math.abs(calDateTimes);
					
					double rate = 0;
					for (Map.Entry<Integer, Object> entrySet : data.entrySet()) {
						if(calDateTimes >= entrySet.getKey()){
							rate = StringUtil.convNull(entrySet.getValue(), 0);
							if(rate != 0) rate = rate/100;
							break;
						}
					}
					refundPrice = (int) Math.round(price*rate);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			refundPrice = 0;
		}
		
		return refundPrice;
	}
	

}