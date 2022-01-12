package com.test.project.api.exception;

public class TokenEncryptException extends ApiException {
  private static final long serialVersionUID = 1L;
  
  public TokenEncryptException() {
    super(ExceptionCode.tokenEncrypt[0], ExceptionCode.tokenEncrypt[1]);
  }
}
