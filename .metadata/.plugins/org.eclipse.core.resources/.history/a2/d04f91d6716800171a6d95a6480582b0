package com.zhilu.demo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhilu.demo.bean.Cat;
import com.zhilu.demo.service.CatService;

@RestController
@RequestMapping("/cat")
public class CatController {
	@Resource
	private CatService catService;
	
	@RequestMapping("/save")
	public String save(){
		Cat cat = new Cat();
		cat.setCatName("tom");
		cat.setCatAge(13);
		catService.save(cat);
		return "save  ok";
	}
	
	@RequestMapping("/delete")
	public String delete(){
		catService.delete(1);
		return "delete ok";
	}
	
	@RequestMapping("/getAll")
	public Iterable<Cat> getAll(){
		return catService.getAll();
	}
	
}
