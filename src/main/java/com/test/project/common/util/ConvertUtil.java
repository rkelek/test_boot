package com.test.project.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertUtil {

	/********************************************************************************/
	/***** Static fields and Initializers   *****************************************/
	/********************************************************************************/
	private static final Logger logger = LoggerFactory.getLogger(ConvertUtil.class);
	
	//request -> map으로 변환
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> convertParameterToMap(HttpServletRequest request) throws Exception{
		Enumeration parameterNames = request.getParameterNames();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		int idx = 0;
		while (parameterNames.hasMoreElements()) {
			String key = parameterNames.nextElement().toString();
			String[] values = request.getParameterValues(key);
			String value="";
			
			for(int i=0; i<values.length; i++){
				value += values[i]+",";
			}
			
			value = value.substring(0, value.lastIndexOf(","));
			
			idx++;
			paramMap.put(key, value);
			logger.debug("[user parameter #{}] {} = {}", idx, key, value);
		}
		
		
		return paramMap;
	}
	
	//request -> map으로 변환 + 포스트그레용 키값들 소문자로 변경
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> convertParameterToMapLow(HttpServletRequest request) throws Exception{
	   Enumeration parameterNames = request.getParameterNames();
	   Map<String, Object> paramMap = new HashMap<String, Object>();
	   
	   int idx = 0;
	   while (parameterNames.hasMoreElements()) {
	      String key = parameterNames.nextElement().toString();
	      String[] values = request.getParameterValues(key);
	      String value="";
	      
	      for(int i=0; i<values.length; i++){
	         value += values[i]+",";
	      }
	      
	      value = value.substring(0, value.lastIndexOf(","));
	      
	      idx++;
	      paramMap.put(key.toLowerCase(), value);
	      logger.debug("[user parameter #{}] {} = {}", idx, key, value);
	   }
	   
	   
	   return paramMap;
	}
	
	//request -> json으로 변환
	public static JSONObject convertParameterToJSON(HttpServletRequest request) throws Exception{
	   Enumeration parameterNames = request.getParameterNames();
	   JSONObject json = new JSONObject();
	   
	   int idx = 0;
	   while (parameterNames.hasMoreElements()) {
	      String key = parameterNames.nextElement().toString();
	      String[] values = request.getParameterValues(key);
	      String value="";
	      
	      for(int i=0; i<values.length; i++){
	         value += values[i]+",";
	      }
	      
	      value = value.substring(0, value.lastIndexOf(","));
	      
	      idx++;
	      json.put(key, value);
	      logger.debug("[user parameter #{}] {} = {}", idx, key, value);
	   }
	   
	   
	   return json;
	}
	
	//request -> map으로 변환 + 파라미터로 넘긴 map에 담기 ( key가 중복될경우 request데이터로 들어감 )
	public static Map<String, Object> convertParameterToMapAdd(HttpServletRequest request, Map<String, Object> param) throws Exception{
	   Enumeration parameterNames = request.getParameterNames();
	   if(param == null){
	      param = new HashMap<String, Object>();
	   }
	   
	   int idx = 0;
	   while (parameterNames.hasMoreElements()) {
	      String key = parameterNames.nextElement().toString();
	      String[] values = request.getParameterValues(key);
	      String value="";
	      
	      for(int i=0; i<values.length; i++){
	         value += values[i]+",";
	      }
	      
	      value = value.substring(0, value.lastIndexOf(","));
	      
	      idx++;
	      param.put(key, value);
	      logger.debug("[user parameter #{}] {} = {}", idx, key, value);
	   }
	   
	   
	   return param;
	}
	
	//queryString -> map으로 변환
    public static Map<String, Object> convertQueryStringToMap(String queryString) throws Exception {
	    Map<String, Object> param = new HashMap<String, Object>();
	    System.out.println("queryString : " + queryString);
	    queryString = queryString.replaceAll("\"", "");
	    for (int i = 0; i < queryString.split("&").length; i++) {
	       
	        String key = "";
	        String val = "";

	        key = queryString.split("&")[i].split("=")[0];
	        if (queryString.split("&")[i].split("=").length == 2) {
	            val = queryString.split("&")[i].split("=")[1];
	            val = URLDecoder.decode(val, "UTF-8");
	        }

	        param.put(key, val);

	    }
      
      
	    return param;
	}
    
    //request -> dto(vo)로 변환
    // 숫자 자료형 맞지 않을경우 0으로 들어감
	// int a  -> a=2.2 -> 0
	// int a  -> a=가나다 -> 0
	// null 0 / "" 처리
    public static <T> T convertRequestToVo(HttpServletRequest req, Class<T> type) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException{
       
        if (type == null) {
            throw new NullPointerException("Class cannot be null");
        }
        
        T instance = type.getConstructor().newInstance();
        Field[] fields = type.getDeclaredFields();
        
        for (Field field : fields) {
            field.setAccessible(true);
            
            String fieldName = field.getName();
            
            if(field.getType() == java.lang.String.class){
               field.set(instance, StringUtil.convNull(req.getParameter(fieldName), ""));
            }else{
               try {
                  field.set(instance, (StringUtil.convNull(req.getParameter(fieldName), 0)));
               } catch (java.lang.IllegalArgumentException e) {
                  if(field.getType() == java.util.Date.class){
                     field.set(instance, (StringUtil.convNull(req.getParameter(fieldName), new Date())));
                  }else if(field.getType() == java.util.List.class){
                     field.set(instance, null);
                  }else{
                     field.set(instance, 0);
                  }
                  
               }
               
            }
            
        }
       
       
        return instance;
    }
    //request -> dto(vo)로 변환 + 포스트그레용 키값들 소문자로 변경
    // 숫자 자료형 맞지 않을경우 0으로 들어감
    // int a  -> a=2.2 -> 0
    // int a  -> a=가나다 -> 0
    // null 0 / "" 처리
    public static <T> T convertRequestToVoLow(HttpServletRequest req, Class<T> type) throws Exception{
       
       if (type == null) {
          throw new NullPointerException("Class cannot be null");
       }
       
       Map<String, Object> map = convertParameterToMapLow(req);
       
       T instance = type.getConstructor().newInstance();
       
       if (map == null || map.isEmpty()) {
          return instance;
       }
       
       for (Map.Entry<String, Object> entrySet : map.entrySet()) {
          Field[] fields = type.getDeclaredFields();
          
          for (Field field : fields) {
             field.setAccessible(true);
             
             String fieldName = field.getName();
             
             boolean isSameType = entrySet.getValue().getClass().equals(field.getType());
             boolean isSameName = entrySet.getKey().toLowerCase().equals(fieldName);
             
             if (isSameType && isSameName) {
                field.set(instance, map.get(entrySet.getKey()));
             }else if(isSameName && field.getType() == int.class){                
                try {
                   String val = StringUtil.convNull(entrySet.getValue(), "");
                   val = StringUtil.removeComma(val);
                   boolean st = val.chars().allMatch( Character::isDigit );
                   
                   System.out.println(entrySet.getKey().toLowerCase());
                   
                   if(st && isSameName) field.set(instance, Integer.parseInt(val));
               } catch (Exception e) {
                  // TODO: handle exception
               }
             }
          }
       }
       
       return instance;
    }
   
   
   //queryString -> dto(vo)로 변환
   /*
              사용법
       String queryString = "name=가나다&no=2425&tel=010-2222-5555";
       TestVO vo = new TestVO();
       vo = ConvertUtil.convertStringToVo(queryString, vo.getClass());
    */
    public static <T> T convertStringToVo(String queryString, Class<T> type)
         throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (type == null) {
            throw new NullPointerException("Class cannot be null");
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = convertQueryStringToMap(queryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
   
        T instance = type.getConstructor().newInstance();
   
        if (map == null || map.isEmpty()) {
            return instance;
        }
   
        for (Map.Entry<String, Object> entrySet : map.entrySet()) {
            Field[] fields = type.getDeclaredFields();
   
            for (Field field : fields) {
                field.setAccessible(true);
                
                String fieldName = field.getName();
   
                boolean isSameType = entrySet.getValue().getClass().equals(field.getType());
                boolean isSameName = entrySet.getKey().equals(fieldName);
   
                if (isSameType && isSameName) {
                    field.set(instance, map.get(fieldName));
                }
            }
        }
        return instance;
    }
    
    //queryString -> vo(dto)로 변환 + 포스트그레용 키값들 소문자로 변경
    /*
              사용법 / 쿼리스트링 대문자를 소문자로 변경
       String queryString = "name=가나다&no=2425&tel=010-2222-5555";
       TestVO vo = new TestVO();
       vo = ConvertUtil.convertStringToVo(queryString, vo.getClass());
     */
    public static <T> T convertStringToVoLow(String paramString, Class<T> type)
          throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
       if (type == null) {
          throw new NullPointerException("Class cannot be null");
       }
       
       Map<String, Object> map = new HashMap<String, Object>();
       try {
          map = convertQueryStringToMap(paramString);
       } catch (Exception e) {
          e.printStackTrace();
       }
       
       T instance = type.getConstructor().newInstance();
       
       if (map == null || map.isEmpty()) {
          return instance;
       }
       
       for (Map.Entry<String, Object> entrySet : map.entrySet()) {
          Field[] fields = type.getDeclaredFields();
          
          for (Field field : fields) {
             field.setAccessible(true);
             
             String fieldName = field.getName();
             
             boolean isSameType = entrySet.getValue().getClass().equals(field.getType());
             boolean isSameName = entrySet.getKey().toLowerCase().equals(fieldName);
             
             if (isSameType && isSameName) {
                field.set(instance, map.get(entrySet.getKey()));
             }else if(isSameName && field.getType() == int.class || isSameName && field.getType() == Integer.class
                   || isSameName && field.getType() == float.class){                
                try {
                   String val = StringUtil.convNull(entrySet.getValue(), "");
                   val = StringUtil.removeComma(val);
                   boolean st = val.chars().allMatch( Character::isDigit );
                   
                   System.out.println(entrySet.getKey().toLowerCase());
                   
                   if(st && isSameName) field.set(instance, Integer.parseInt(val));
               } catch (Exception e) {
                  // TODO: handle exception
               }
             }
          }
       }
       return instance;
    }
   
   //map -> vo로 변환
   /*
      사용법
   TestVO vo = new TestVO();
   vo = ConvertUtil.convertStringToVo(map, vo.getClass());
   */
   public static <T> T convertMapToVo(Map<String, Object> map, Class<T> type)
         throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
      if (type == null) {
         throw new NullPointerException("Class cannot be null");
      }
      
      T instance = type.getConstructor().newInstance();
      
      if (map == null || map.isEmpty()) {
         return instance;
      }
      
      for (Map.Entry<String, Object> entrySet : map.entrySet()) {
         Field[] fields = type.getDeclaredFields();
         
         for (Field field : fields) {
            field.setAccessible(true);
            
            String fieldName = field.getName();
            
            boolean isSameType = entrySet.getValue().getClass().equals(field.getType());
            boolean isSameName = entrySet.getKey().equals(fieldName);
            
            if (isSameType && isSameName) {
               field.set(instance, map.get(fieldName));
            }
         }
      }
      return instance;
   }
   //map -> vo로 변환 + 포스트그레용 키값들 소문자로 변경 
   /*
      사용법
   TestVO vo = new TestVO();
   vo = ConvertUtil.convertStringToVo(map, vo.getClass());
    */
   public static <T> T convertMapToVoLow(Map<String, Object> map, Class<T> type)
         throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
      if (type == null) {
         throw new NullPointerException("Class cannot be null");
      }
      
      T instance = type.getConstructor().newInstance();
      
      if (map == null || map.isEmpty()) {
         return instance;
      }
      
      for (Map.Entry<String, Object> entrySet : map.entrySet()) {
         Field[] fields = type.getDeclaredFields();
         
         for (Field field : fields) {
            field.setAccessible(true);
            
            String fieldName = field.getName();
            
            boolean isSameType = entrySet.getValue().getClass().equals(field.getType());
            boolean isSameName = entrySet.getKey().toLowerCase().equals(fieldName);
            
            if (isSameType && isSameName) {
               field.set(instance, map.get(entrySet.getKey()));
            }else if(isSameName && field.getType() == int.class){                
               try {
                  String val = StringUtil.convNull(entrySet.getValue(), "");
                  val = StringUtil.removeComma(val);
                  boolean st = val.chars().allMatch( Character::isDigit );
                  
                  System.out.println(entrySet.getKey().toLowerCase());
                  
                  if(st && isSameName) field.set(instance, Integer.parseInt(val));
              } catch (Exception e) {
                 // TODO: handle exception
              }
            }
         }
      }
      return instance;
   }
	
   //map -> querystring 으로 변환 ( 값중에 url이 있을시 사용하는곳에서 replace 필요 )
   public static String convertMapToQueryString(Map<String, Object> params) throws Exception {

     StringBuilder sb = new StringBuilder();

     Iterator<?> iter = params.entrySet().iterator();
     while (iter.hasNext()) {
         if (sb.length() > 0) {
             sb.append('&');
         }
         Entry<?, ?> entry = (Entry<?, ?>) iter.next();
         String val = StringUtil.convNull(entry.getValue(), "");
         if(val.contains("http")){            
            sb.append(entry.getKey()).append("=").append(val.replaceAll("=", "@urlinfo@"));
         }else{
            sb.append(entry.getKey()).append("=").append(entry.getValue());
         }
         
     }

     return sb.toString();
 }
   
   // VO -> camelcase로 변경
   public static Map<String, Object> voToCamelMap( HttpServletRequest req , Object obj, String[] key ){
      
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> camelMap = new HashMap<String, Object>();
   
      if(obj != null) camelMap = objectMapper.convertValue(obj, Map.class);
      else throw new NullPointerException();
      
      Map<String, Object> returnMap = new HashMap<String, Object>();
      
      for (int i = 0; i < key.length; i++) {
         returnMap.put(key[i], camelMap.get(key[i].toLowerCase()));
     }
      
      return returnMap;
   }
   
   //jpa 값 수정 불가로 어레이리스트 복사
   public static ArrayList<Map<String,Object>> arrayListCopy(ArrayList<Map<String,Object>> list) throws Exception {

	   ArrayList<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
	     for(int i = 0; i < list.size(); i++){
	    	
	    	 Map<String,Object> newMap = new HashMap<String,Object>();
	    	 
	    	 Map<String,Object> map = list.get(i);
	    	
	    	 Iterator<?> iter = map.entrySet().iterator();
		     while (iter.hasNext()) {
		         Entry<?, ?> entry = (Entry<?, ?>) iter.next();
		         String val = StringUtil.convNull(entry.getValue(), "");
		         newMap.put(entry.getKey()+"", val);
		     }
		     newList.add(newMap);
	     }
	     
	     

	     return newList;
	 }

	
	
}