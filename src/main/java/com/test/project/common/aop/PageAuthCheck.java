package com.test.project.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageAuthCheck {
  public enum Auth {
    READ("read", "읽기"), WRITE("write", "쓰기"), DELETE("delete", "삭제"), EDIT("edit", "수정"), DOWNLOAD("download", "다운로드"), PRINT("print", "인쇄"), TEMP("temp", "임시"), FUNCTION_ADD("function", "추가기능");

    private String value;
    private String name;
    private Auth(String value, String name){
        this.value = value;
        this.name = name;
    }
    public String getValue(){
      return this.value;
    }
    public String getText(){
      return this.name;
    }
  }
  
  String code();
  Auth auth() default Auth.READ;
}