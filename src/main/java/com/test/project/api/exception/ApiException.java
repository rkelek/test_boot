package com.test.project.api.exception;

public class ApiException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   private ResultCode code;
   
   public ApiException(ResultCode code){
       super(code.getMessage());       
       this.code = code;
   }
   
   public ApiException(String message, ResultCode code){
       super(message);
       this.code = code;
   }
  
   public ApiException(String code, String message) {
      super(message);
      this.code = ResultCode.valueOf(code);
   }

   public ResultCode getResultCode() {
       return code;
   }
  
}
