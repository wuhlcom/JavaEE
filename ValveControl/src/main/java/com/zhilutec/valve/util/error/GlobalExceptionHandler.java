package com.zhilutec.valve.util.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zhilutec.valve.util.error.ErrorResponeMsgBody;
import com.zhilutec.valve.util.error.GlobalErrorException;

/**
*
* 统一错误码异常处理
*
* Created by miaorenjun on 2017/8/9.
*/

@RestControllerAdvice
public class GlobalExceptionHandler {
	
   @ExceptionHandler(value = GlobalErrorException.class)
   public ErrorResponeMsgBody errorHandlerOverJson(HttpServletRequest request, GlobalErrorException exception) {
       ErrorCode errorInfo = exception.getErrorCode();
       ErrorResponeMsgBody result = new ErrorResponeMsgBody(errorInfo);
       return result;
   }
}
