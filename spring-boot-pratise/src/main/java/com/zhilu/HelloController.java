package com.zhilu;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping("/hello")
	public String hello(){
		return "Hello Spring boot32";
	}
	
	@RequestMapping("/hello2")
	public String hello2(){
		return "Hello2 Spring boot";
	}
	
	
	@RequestMapping("/demo")
	public Demo getDemo(){
		Demo demo =new Demo();
		demo.setId(1);
		demo.setName("张三");
		demo.setCreateTime(new Date());
		demo.setRemarks("aha");
		return demo;
	}
	
}
