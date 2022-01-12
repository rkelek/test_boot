package com.test.project.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component("frontPreparer")
public class FrontPreparer implements ViewPreparer {

   @Override
   public void execute(Request tilesContext, AttributeContext attributeContext) {
      // TODO Auto-generated method stub
      HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      HttpServletResponse rep = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
      
   }   
}