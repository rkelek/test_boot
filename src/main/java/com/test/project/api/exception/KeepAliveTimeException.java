package com.test.project.api.exception;

public class KeepAliveTimeException extends ApiException {
  private static final long serialVersionUID = 1L;

  public KeepAliveTimeException() {
    super(ExceptionCode.keepAliveTime[1], ExceptionCode.keepAliveTime[0]);
  }
}
