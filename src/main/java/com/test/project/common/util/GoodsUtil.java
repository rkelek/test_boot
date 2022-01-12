package com.test.project.common.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.test.project.jpa.table.GoodsStock;


@Component
public class GoodsUtil {
	
	@Autowired private StringRedisTemplate templateAuto;
	private static StringRedisTemplate template;
	
	
	@PostConstruct
    private void initLogService() {
		template = this.templateAuto;
	}
   
   private static final Logger logger = LoggerFactory.getLogger(GoodsUtil.class);
   
   //숙소 등록시 주중/주말에 따른 재고 리스트 / cnt1 - 주중, cnt2 - 주말
   public static List<GoodsStock> getStockInfo(String startdt, String enddt, int cnt1, int cnt2, int gdmgrno)
         throws Exception {
      
      SimpleDateFormat full = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
      
      Calendar cal = Calendar.getInstance();
      Date endDate = null;
      try {
         endDate = full.parse(enddt);
      } catch (ParseException e) {
          e.printStackTrace();
      }
      cal.setTime(endDate);
      cal.add(Calendar.DATE, -1);
      
      int enddtnum = StringUtil.convNull(full.format(cal.getTime()).replaceAll("-", ""), 0);
      startdt = startdt.replaceAll("-", "");
      
      int startYear = Integer.parseInt(startdt.substring(0,4));
      int startMonth= Integer.parseInt(startdt.substring(4,6));
      int startDay = Integer.parseInt(startdt.substring(6,8));
      
      cal.set(startYear, startMonth -1, startDay);
      
      
      
      List<GoodsStock> list = new ArrayList<GoodsStock>();
      
      while(true) {
          GoodsStock info = new GoodsStock();
          int cnt = 0;
          String calcDate = full.format(cal.getTime());
          
          int calcYear = Integer.parseInt(calcDate.replaceAll("-", "").substring(0,4));
          int calcMonth = Integer.parseInt(calcDate.replaceAll("-", "").substring(4,6));
          int calcDay = Integer.parseInt(calcDate.replaceAll("-", "").substring(6,8));
          
          LocalDate date = LocalDate.of(calcYear, calcMonth, calcDay);
          DayOfWeek dayOfWeek = date.getDayOfWeek();
          int dnum = dayOfWeek.getValue();
          // 6 토요일, 7 일요일
          if(dnum >= 6) cnt = cnt2;
          else cnt = cnt1;
          
          cal.add(Calendar.DATE, 1);
          
          info.setDt(calcDate);
          info.setCnt(cnt);
          info.setGdmgrno(gdmgrno);
          list.add(info);
          
          if(Integer.parseInt(simple.format(cal.getTime())) > enddtnum) break;
      }
      
      
      return list;
   }
   
   //숙소 예약시 실제 재고 차감되는 일자 1/1~1/3 예약시 실제 재고 차감되는 일자인 1/1~1/2 리스트 리턴
   public static List<String> getDtList(String startdt, String enddt)
         throws Exception {
      
      SimpleDateFormat full = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
      
      Calendar cal = Calendar.getInstance();
      Date endDate = null;
      try {
         endDate = full.parse(enddt);
      } catch (ParseException e) {
         e.printStackTrace();
      }
      cal.setTime(endDate);
      cal.add(Calendar.DATE, -1);
      
      int enddtnum = StringUtil.convNull(full.format(cal.getTime()).replaceAll("-", ""), 0);
      startdt = startdt.replaceAll("-", "");
      
      int startYear = Integer.parseInt(startdt.substring(0,4));
      int startMonth= Integer.parseInt(startdt.substring(4,6));
      int startDay = Integer.parseInt(startdt.substring(6,8));
      
      cal.set(startYear, startMonth -1, startDay);
      
      List<String> list = new ArrayList<String>();
      
      while(true) {
         String calcDate = full.format(cal.getTime());
         
         cal.add(Calendar.DATE, 1);
         list.add(calcDate);
         
         if(Integer.parseInt(simple.format(cal.getTime())) > enddtnum) break;
      }
      
      
      return list;
   }
   
   //단일건 현재고 여부 체크 
   //재고 동시성 이슈  해결
   /*
    (결제 요청시 db상 재고 - 레디스 임시 판매 건수 - 구매 요청 개수) = 0개 이상 일때 결제 진행
         진행시 구매 요청개수 만큼 레디스에다가 임시 판매 건수를 추가 ( 5분간 가지고 있음 ) ( 키값은 상품 고유번호 or 일자별 재고 관리시에는 상품 고유번호 + 일자 )
         결제 도중 다른 결제 요청건이 있을시 5분간 임시재고를 레디스에서 가지고있어 재고 이상에 요청이 오는걸 방지 ( 레디스 - 싱글스레드 방식 )
         결제 도중 실패시 5분간 임시재고를 가지고 있음 // 단점 계속되는 결제 실패시 expire 시간이 계속 갱신이 되기때문에 임시재고에 수량이 증가됨
    
    
    */
   public static Boolean getStockChk(Map<String, Object> map)
         throws Exception {
      
      Boolean chkYn = false;
      
      String type = StringUtil.convNull( map.get("gdtyp"), "");
      int argcnt = StringUtil.convNull( map.get("cnt"), 0); // 구매 요청개수
      int gdmgrno = StringUtil.convNull( map.get("gdmgrno"), 0);
      String startdt = StringUtil.convNull( map.get("startdt"), "");
      String enddt = StringUtil.convNull( map.get("enddt"), "");
      List<GoodsStock> list = (List<GoodsStock>)map.get("stock"); // db 현재고
      
      
      if(type.equals("C")){
         
         int redisCnt = StringUtil.convNull(template.opsForValue().get(gdmgrno+""), 0);  
         int cnt = list.get(0).getCnt();
         if((cnt - redisCnt - argcnt) >= 0) chkYn = true;
         else{
            return chkYn;
         }
         template.opsForValue().set(gdmgrno+"", redisCnt+argcnt+"");
         template.expire(gdmgrno+"", 5, TimeUnit.MINUTES);
         
      }else if(type.equals("D")){
         SimpleDateFormat full = new SimpleDateFormat("yyyy-MM-dd");
         
         Calendar cal = Calendar.getInstance();
         Date endDate = null;
         try {
            endDate = full.parse(enddt);
         } catch (ParseException e) {
            e.printStackTrace();
         }
         cal.setTime(endDate);
         cal.add(Calendar.DATE, -1);
         
         startdt = startdt.replaceAll("-", "");
         
         int startYear = Integer.parseInt(startdt.substring(0,4));
         int startMonth= Integer.parseInt(startdt.substring(4,6));
         int startDay = Integer.parseInt(startdt.substring(6,8));
         
         cal.set(startYear, startMonth -1, startDay);
         
         ///////////////////////////////////////////////////////
         for(int j = 0; j < list.size(); j++){
            GoodsStock info = list.get(j);
            
            List<String> dtList = new ArrayList<String>();  
            try {
               dtList = GoodsUtil.getDtList(startdt, enddt);
            } catch (Exception e) {
               dtList = null;
            }
            
            if(dtList.size() != 0){
               for(int k = 0; k < dtList.size(); k++){
                  if(info.getDt().equals(dtList.get(k))){
                     int redisCnt = StringUtil.convNull(template.opsForValue().get(gdmgrno+dtList.get(k)), 0); // 레디스 임시 판매된 개수  
                     int cnt = info.getCnt(); // db상 현재고
                    
                     if((cnt - redisCnt - 1) >= 0) chkYn = true;
                     else{
                        chkYn = false;
                        break;
                     }
                     template.opsForValue().set(gdmgrno+dtList.get(k), redisCnt+argcnt+"");
                     template.expire(gdmgrno+dtList.get(k), 5, TimeUnit.MINUTES);
                  }
               }
            }
            
         }
         
      }
      
      
      return chkYn;
   }
   
   //재고 차감 후 실재고가 0개인 상품들 확인 ( 
   /*
       	레디스에서 가지고있던 임시재고 차감 후 재고가 0개 면서 
       	레디스에서 가지고있던 임시재고 차감전 재고 - db상 가지고있던 재고 = 0개 일때 해당 상품을 장바구니에 담겨있을경우 매진처리 ( 전체회원 기준 )
       	0개가 아닐때는 db에 재고 차감    
   */
   public static Map<String, Object> getStockListChk(List<Map<String, Object>> stockList)
         throws Exception {
      
      Map<String, Object> returnMap = new HashMap<String, Object>();
      
      List<Integer> gdmgrnos = new ArrayList<Integer>();
      List<Integer> seqs = new ArrayList<Integer>();
      String chkYn = "N";
      
      
      
      
      for(int i = 0; i < stockList.size(); i++){
         Map<String, Object> map = stockList.get(i);
         
         String type = StringUtil.convNull( map.get("gdtyp"), "");
         int argcnt = StringUtil.convNull( map.get("cnt"), 0);
         int gdmgrno = StringUtil.convNull( map.get("gdmgrno"), 0);
         String startdt = StringUtil.convNull( map.get("startdt"), "");
         String enddt = StringUtil.convNull( map.get("enddt"), "");
         List<GoodsStock> list = (List<GoodsStock>)map.get("stock");
         
         
         if(type.equals("C")){
            
            int redisCnt = StringUtil.convNull(template.opsForValue().get(gdmgrno+""), 0);  
            int cnt = list.get(0).getCnt();
            template.opsForValue().set(gdmgrno+"", redisCnt-argcnt+"");
            template.expire(gdmgrno+"", 5, TimeUnit.MINUTES);
            int afterRedisCnt = StringUtil.convNull(template.opsForValue().get(gdmgrno+""), 0);  
            if(afterRedisCnt == 0 && (cnt - redisCnt) == 0) gdmgrnos.add(gdmgrno);
            else if(afterRedisCnt < 0){
               chkYn = "Y";
               break;
            }
               
         }else if(type.equals("D")){
            
            for(int j = 0; j < list.size(); j++){
               GoodsStock info = list.get(j);
               
               List<String> dtList = new ArrayList<String>();  
               try {
                  dtList = GoodsUtil.getDtList(startdt, enddt);
               } catch (Exception e) {
                  dtList = null;
               }
               
               if(dtList.size() != 0){
                  for(int k = 0; k < dtList.size(); k++){
                     if(info.getDt().equals(dtList.get(k))){
                        int redisCnt = StringUtil.convNull(template.opsForValue().get(gdmgrno+dtList.get(k)), 0); // 레디스 임시 판매된 개수  
                        int cnt = info.getCnt(); // db상 현재고
                       
                        //argcnt -> 사용자가 구매한개수 ( 숙소인경우 1 )
                        template.opsForValue().set(gdmgrno+dtList.get(k), redisCnt-argcnt+"");
                        template.expire(gdmgrno+dtList.get(k), 5, TimeUnit.MINUTES);
                        int afterRedisCnt = StringUtil.convNull(template.opsForValue().get(gdmgrno+dtList.get(k)), 0);  
                        if(afterRedisCnt == 0 && (cnt - redisCnt) == 0) seqs.add(info.getSeq());
                        else if(afterRedisCnt < 0){
                           chkYn = "Y";
                           break;
                        }
                     }
                  }
               }
               
            }
         }
      }
      
      returnMap.put("chkYn", chkYn);
      returnMap.put("gdmgrnos", gdmgrnos);
      returnMap.put("seqs", seqs);
      returnMap.put("seqsYn", seqs.size() == 0 ? "N" : "Y");
      returnMap.put("gdmgrnosYn", gdmgrnos.size() == 0 ? "N" : "Y");
      
      return returnMap;
   }
}