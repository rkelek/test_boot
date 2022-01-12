package com.test.project.api.exception;

public class ExceptionCode {
  public static final String[] unknown = {"9999", "처리중 오류가 발생하였습니다."};
  
  // http status
  public static final int BadRequest = 400; // 잘못된 요청
  public static final int Unauthroized = 401; // 비로그인상태
  public static final int Forbidden = 403; // 권한없음
  public static final int Notfound = 404; // 자원을 찾을 수 없음
  public static final int ServerError = 500; // 서버에러
  
  
  public static final String[] keepAliveTime = {"1101", "토큰 생성 유지시간 설정이 올바르지 않습니다."};
  public static final String[] tokenEncrypt = {"1102", "토큰정보가 올바르지 않습니다."};
  public static final String[] NotFoundAccount = {"1103", "사용자를 확인할 수 없습니다."};
  public static final String[] NotFoundNote = {"1104", "가계부를 확인할 수 없습니다."};
  public static final String[] ExpiredToken = {"1105", "토큰이 만료되었습니다."};
  public static final String[] StopAccount = {"1106", "사용이 중지된 계정입니다."};
  public static final String[] StopNote = {"1107", "사용이 중지된 가계부입니다."};
  public static final String[] UserIdOverlap = {"1108", "이미 사용중인 아이디입니다."};

  //정규식
  public static final String[] InvalidCertifyMethod = {"2001", "인증방식이 올바르지 않습니다."};
  public static final String[] InvalidKind = {"2002", "처리가 불가능한 타입니다."};
  public static final String[] emptyData = {"2003", "공백 혹은 빈값이 있습니다."};
  public static final String[] InvalidCode = {"2004", "코드가 올바르지 않습니다."};
  
  // 서비스 이용
  public static final String[] NormalServiceError = {"3001", "서비스 처리오류 입니다."};
  public static final String[] NotNoteMaster = {"3003", "운영자가 아닙니다."};
  public static final String[] ImpossibleMaster = {"3004", "운영자는 처리가 불가능합니다."};
  public static final String[] NotMatchPasswd = {"3005", "이전비밀번호가 일치하지 않습니다."};
  public static final String[] NotPermission = {"3006", "권한이 없습니다."};
  public static final String[] Magam = {"3007", "마감된 일자입니다."};
  public static final String[] TransDate = {"3008", "입력불가능한 거래일자입니다."};
  
  // 기타오류
  public static final String[] EtcError = {"4001"};
  
  // 9000
  public static final String[] System = {"9000", "시스템 처리중 오류가 발생하였습니다."};
}
