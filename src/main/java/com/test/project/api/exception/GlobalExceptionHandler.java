package com.test.project.api.exception;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.test.project.api.schema.ResultVo;
import com.test.project.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<ResultVo> handlerApiException(ApiException e){
        log.info("### handlerApiException ### : " + e.getMessage());
        final ResultCode code = e.getResultCode();
        final ResultVo response = ResultVo.of(code);
        return new ResponseEntity<>(response, HttpStatus.valueOf(code.getStatus()));
	}
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ResultVo> handlerParameterException(HttpServletRequest req, MissingServletRequestParameterException e){
        log.error("handlerParameterException", e);
        final ResultVo response = ResultVo.of(ResultCode.EMPTY_REQUEST_VALUE);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);       
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ResultVo> handlerParameterException(HttpServletRequest req, MethodArgumentTypeMismatchException e){
        log.error("MethodArgumentTypeMismatchException", e);
        final ResultVo response = ResultVo.of(ResultCode.INVALID_TYPE_VALUE);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);       
    }
    
    @ExceptionHandler(Exception.class)
    protected Object handlerException(HttpServletRequest req, Exception e, HttpServletResponse res){
        log.error("handlerException", e);
        final ResultVo response = ResultVo.of(ResultCode.INTERNAL_SERVER_ERROR);

        boolean isAjax = true;
        if(isAjax) return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            Cookie testCookie = new Cookie("LOIF", null);
            testCookie.setMaxAge(0);
            testCookie.setPath("/");

            res.addCookie(testCookie);

            return "redirect:/error/500.html";
        }
    }
}
