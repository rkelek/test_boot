package com.test.project.api.exception;

public class EtcErrorException extends ApiException {
  private static final long serialVersionUID = 1L;
  
  public static final String NotFoundMethod = "처리할 데이터가 없습니다.(거래수단 확인)";

  public EtcErrorException(String msg) {
    super(ExceptionCode.EtcError[0], msg);
  }
  
  public EtcErrorException(String msg, String param) {
    super(ExceptionCode.EtcError[0], "[" + param + "] " + msg);
  }
  
}

