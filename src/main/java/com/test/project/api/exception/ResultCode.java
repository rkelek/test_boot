package com.test.project.api.exception;

import lombok.Getter;

public enum ResultCode {
   /*
    * 200 : 정상
    * 400 : 요청이 올바르지 않음 (Bad Request)
    * 401 : 인증되지 않은 사용자 접근  (Unauthorized)
    * 403 : 인증(로그인)은 됬으나 권한이 없는 사용자 (Forbidden)
    * 404 : 요청에 대한 페이지를 찾을 수 없음 (Not Found)
    * 405 : 요청한 메소드가 올바르지 않음(Method Not Allowed)
    * 500 : 서버 처리중 오류 발생 (Internal Server Error)
    */
  
   // 일반(Http상태코드, 에러코드, 에러메세지)
   SUCCESS(200, "0000", "정상"),
   FILE_NOT_FOUND(404, "F001", "파일을 찾을 수 없습니다."),
        
   // 권한
   USER_NOT_FOUND(401, "M001", "사용자를 확인할 수 없습니다."),
   PERMISSION_NOT_FOUND(403, "M002", "권한이 없습니다."),
   INVALID_PASSWORD(400, "M003", "비밀번호가 일치하지 않습니다."),
   FAILED_DECRYPT(400, "M004", "데이터 복호화 오류"),
   NOT_USER(401, "M005", "회원만 이용가능합니다."),
   NOT_MASTER_USER(401, "M007", "관리자 회원이 아닙니다."),
   
   // 데이터 
   DUPLICATE_PROCESS(400, "D001", "이미 처리된 데이터 입니다."),
   EMPTY_REQUEST_VALUE(400, "1002", "요청 파라미터가 없습니다."),
   INVALID_VALUE(400, "1001", "데이터가 올바르지 않습니다."),
   INVALID_TYPE_VALUE(400, "1003", "타입이 올바르지 않습니다."),
   
   // 에러   
   INTERNAL_SERVER_ERROR(500, "S999", "오류가 발생하였습니다."),   
   
   
   // 시리얼 필수입력여부
   SERIAL_REQUIRED_NO(400, "S001", "시리얼 필수입력여부가 사용안함으로 되어 있습니다"),

   // 구글 미인증
   GOOGLE_DRIVE_UNCERTIFIED(400, "G001","구글 드라이브 인증 후 등록 가능합니다."),
   // 파일 형식 오류
   FILE_TYPE_ERROR(400,"G002", "파일 형식이 올바르지 않습니다."),
   
   //거래처
   CUTOMER_USING_ERROR(400, "C001", "사용중인 거래처입니다."),
   
   // 작업지시서 삭제
   JOB_ORDER_USING_ERROR(400, "P001",
         "해당 작업지시서는 \n 생산/소모 전표와 연동되어 삭제할 수 없습니다. \n 삭제할 경우 생산/소모 전표 삭제 후 \n 작업지시서를 삭제 할 수 있습니다."),
   
   //
   EXCEL_NO_DATA(400, "E001", "조회된 데이터가 없습니다."),
   
   RESERVE_NO_DATA(400, "R001", "조회된 예약내역이 없습니다."),
   KORAIL_API_ERROR(400, "R002", "코레일 서버와 오류로 인해 결제가 취소되었습니다."),
   
   API_LOGIN_FAIL(400, "A001", "오류: 'API_KEY_MISSING'\n 관리자에게 문의해주세요."),
   NO_API_DATA(503, "A002", "세션이 만료되었거나 서버응답이 느려서 메인화면으로 이동합니다."),
   ZZIMCAR_SERVICE_ERROR(502, "A003" ,"불편을 드려 죄송합니다. \n 렌트카 서버의 오류로 인해 결제가 취소되었습니다."),
   ONDA_SERVICE_ERROR(400, "R003" ,"불편을 드려 죄송합니다. \n 숙박 서버의 오류로 인해 결제가 취소되었습니다."),
   
   NO_SHEET(400, "E001", "해당 파일에 선택한 시트가 존재하지 않습니다."),
	
   //토스
   PAY_FAIL(400, "T001", "결제가 정상적으로 처리되지 않았습니다.");
   
   @Getter int status;
   @Getter String code;
   @Getter String message;
   
   ResultCode(int status, String code, String message){
       this.status = status;
       this.code = code;
       this.message = message;
   }
   
   
}
