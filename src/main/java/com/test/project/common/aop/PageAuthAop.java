package com.test.project.common.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.project.api.exception.ApiException;
import com.test.project.api.exception.ExceptionCode;
import com.test.project.api.schema.ResultVo;

@Component
@Aspect
@Order(2)
public class PageAuthAop {
   private static final Logger logger = LoggerFactory.getLogger(PageAuthAop.class);
   
  
  @Pointcut("execution(* com.test.project.ui..*.*Ctr.*(..)) || execution(* com.test.project.admin.ui..*.*Ctr.*(..))")
  private void pageLoginCheck() {}
  
  

  /**
   * @param joinPoint
   * @return
   * @comment 회원등급 확인
   * @throws Throwable
   */
  @Around(value = "pageLoginCheck()")
  @ResponseBody
  public Object pageLoginCheck(ProceedingJoinPoint joinPoint) throws Throwable {
     System.out.println("====>> Api Auth Check");
     ResultVo resultVo = new ResultVo();
     HttpServletResponse rep = null;
     HttpServletRequest req = null;
     
     for (Object o : joinPoint.getArgs()) {
        if (o instanceof HttpServletResponse) {
           rep = (HttpServletResponse) o;
        }
        
        if (o instanceof HttpServletRequest) {
           req = (HttpServletRequest) o;
        }
     }
     
     
     //String uri = StringUtil.convNull(req.getRequestURI(), "");
     String uri = javax.servlet.http.HttpUtils.getRequestURL(req).toString();
     
     //cp업체 세팅
     Boolean urlChk = false;
     
     System.out.println("uri : " + uri);
     
     
     System.out.println("urlChk : " + urlChk);
     //urlChk 리다이렉트 여부
     if(urlChk){ // 도메인 변경시
        //추가 처리가 필요한 경우
        //rep.sendRedirect("/");
        //return null;        
     }else{
			 
     }
     
     
     
     
     Signature signature =  joinPoint.getSignature();
     
     // body
     try {
        return joinPoint.proceed();
        
     } catch (Throwable e) {
        resultVo.setResult(false);
        
        if(e instanceof ApiException){      
           //resultVo.setCode(((ApiException)e).getCode());
           resultVo.setMessage(((ApiException)e).getMessage());
        }else{
           e.printStackTrace();
           resultVo.setCode(ExceptionCode.unknown[0]);
           resultVo.setMessage(ExceptionCode.unknown[1]);
        }
        
        return resultVo;
     }
  }
  
  

  @Around("execution(public * com.test.project.api..*.*Ctr.*(..))")
  public Object exception(ProceedingJoinPoint joinPoint) throws Throwable{
    System.out.println("====>> Api Exception");

    ResultVo resultVo = new ResultVo();

    HttpServletResponse rep = null;
    HttpServletRequest req = null;
    for (Object o : joinPoint.getArgs()) {
      if (o instanceof HttpServletResponse) {
        rep = (HttpServletResponse) o;
      }
      if (o instanceof HttpServletRequest) {
        req = (HttpServletRequest) o;
      }      
    }
        
    // body
    try {
      
        return joinPoint.proceed();
        
    } catch (Throwable e) {
      resultVo.setResult(false);
      
      if(e instanceof ApiException){      
        //resultVo.setCode(((ApiException)e).getCode());
        resultVo.setMessage(((ApiException)e).getMessage());
      }else{
        e.printStackTrace();
        resultVo.setCode(ExceptionCode.unknown[0]);
        resultVo.setMessage(ExceptionCode.unknown[1]);
      }
      
      return resultVo;
    }   
  }
}
