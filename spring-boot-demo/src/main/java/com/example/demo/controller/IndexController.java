package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {
	@RequestMapping
	public String hello(){
		return "index";
	}
	
	@RequestMapping(value="get")
	public Map<String,String> get(@RequestParam String name){
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("value", "hello world");
		return map;
	}
}
