package com.test.project.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@SpringBootApplication
@SuppressWarnings("serial")
@Component
public class EmailSender implements MailService {

   // private JavaMailSenderImpl mailSender;

   @Autowired
   JavaMailSender mailSender;


   public void setMailSender(JavaMailSenderImpl mailSender) {
      this.mailSender = mailSender;
   }
   
   //2초간격 2번 재시도
   @Retryable(
         value = {Exception.class},
         maxAttempts = 2,
         backoff = @Backoff(delay = 2000)) 
   @Async("mailExecutor")
   @Override
   public void sendMail(String subject, String text, String fromUser, String toUser, String[] toCC) {

      MimeMessage message = mailSender.createMimeMessage();
      
      try {
         MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
         messageHelper.setSubject(subject);
         messageHelper.setTo(toUser);
         messageHelper.setCc(toCC);
         messageHelper.setFrom(fromUser);
         messageHelper.setText(text, true);
         mailSender.send(message);

      } catch (MessagingException e) {
         e.printStackTrace();
      }

   }
   
   //마지막 에러나는 부분 수정해서 재전송할지 전송 실패여부를 업데이트 할지 결정
   @Recover
   private void recover(Exception e, String subject, String text, String fromUser, String toUser, String[] toCC) {
       System.out.println("test : " + subject);
       // 추후 발송 여부 업데이트 
   }



 
 

}
