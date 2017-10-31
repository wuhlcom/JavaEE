package fish.yu.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import fish.yu.aop.AccessDeniedException;
 

@ControllerAdvice
public class GlobalExceptionHandler {
          // @ExceptionHandler//处理所有异常
	      @ExceptionHandler(RuntimeException.class)
	      @ResponseBody //在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
	      public String exceptionHandler(RuntimeException e, HttpServletResponse response) {
	    	  
	    	  
	         return "403";
	    }

}
