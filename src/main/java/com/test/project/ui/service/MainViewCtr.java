package com.test.project.ui.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewCtr {
   private String prefix = "/service/main";
  
   @GetMapping("")
   public String re(HttpServletRequest req, HttpServletResponse rep, Model model) throws Exception {
      
	   return "redirect:/main";
   }    
   
   @GetMapping("/main")
   public String main(HttpServletRequest req, HttpServletResponse rep, Model model) throws Exception {
      System.out.println("메인");
      return prefix + "/main";
   }
   


}
