package com.test.project.api.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.test.project.api.exception.ResultCode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResultVo {
   private boolean result = true;
   private int status = 200;
   private String code = "0000";
   private String message = "정상";
   private List<FieldError> fieldErrors = new ArrayList<>();

   ResultVo(final ResultCode code){
	   this.status = code.getStatus();
	   this.code = code.getCode();
	   this.message = code.getMessage();
	   
	   if(ResultCode.SUCCESS.equals(code)) this.result = true;
	   else this.result = false;
   }

   ResultVo(final ResultCode code, final List<FieldError> errors){
	   this.status = code.getStatus();
	   this.code = code.getCode();
	   this.message = code.getMessage();
       this.fieldErrors = errors;
	   
	   if(ResultCode.SUCCESS.equals(code)) this.result = true;
	   else this.result = false;
   }

   public static ResultVo of(final ResultCode code, final BindingResult bindingResult) {
       return new ResultVo(code, FieldError.of(bindingResult));
   }

   public static ResultVo of(final ResultCode code) {
       return new ResultVo(code);
   }

   public static ResultVo of(final ResultCode code, final List<FieldError> errors) {
       return new ResultVo(code, errors);
   }

   public static ResultVo of(MethodArgumentTypeMismatchException e) {
       final String value = e.getValue() == null ? "" : e.getValue().toString();
       final List<ResultVo.FieldError> errors = ResultVo.FieldError.of(e.getName(), value, e.getErrorCode());
       return new ResultVo(ResultCode.INVALID_TYPE_VALUE, errors);
   }
   
   @Getter
   public static class FieldError{
	   private String field;
	   private String message;

       private FieldError(final String field, final String value, final String message) {
           this.field = field;
           this.message = message;
       }

       public static List<FieldError> of(final String field, final String value, final String message) {
           List<FieldError> fieldErrors = new ArrayList<>();
           fieldErrors.add(new FieldError(field, value, message));
           return fieldErrors;
       }

       private static List<FieldError> of(final BindingResult bindingResult) {
           final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
           return fieldErrors.stream()
                   .map(error -> new FieldError(
                           error.getField(),
                           error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                           error.getDefaultMessage()))
                   .collect(Collectors.toList());
       }
   }
}
