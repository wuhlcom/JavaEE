package com.zhilu;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 
 *
 */
@RestController
public class HelloController {
	/**
	 * 使用@RequestMapping建立请求映射
	 * http://127.0.0.1:8080/hello
	 * @return
	 */
	@RequestMapping("/hello")
	public String hello(){
		return "hello world15";
	}
	
	@RequestMapping("/hello2")
	public String hello2(){
		return "hello2 world1543";
	}
	
	/**
	 * Spring Boot默认使用的json解析框架是jackson
	 * @return
	 */
	@RequestMapping("/getDemo")
	public Demo getDemo(){
		Demo demo = new Demo();
		demo.setId(1);
		demo.setName("michael");
		demo.setCreateTime(new Date());
		return demo;
	}
}
