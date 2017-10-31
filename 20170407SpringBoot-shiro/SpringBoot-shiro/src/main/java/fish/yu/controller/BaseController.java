package fish.yu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * 统一 异常处理类
 * @author yuliang-ds1
 *
 */
public class BaseController {
	
	  /** 基于@ExceptionHandler异常处理 */
	   @ExceptionHandler
	   public String exp(HttpServletRequest request, Exception ex) {
	      request.setAttribute("ex", ex);
	         // 根据不同错误转向不同页面  
	         return "403";
	      
	   }

}
