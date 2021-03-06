package com.zhilu.demo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhilu.demo.bean.Cat;
import com.zhilu.demo.repository.CatRepository;
import com.zhilu.demo.result.ResultMessage;
import com.zhilu.demo.result.ResultStatusCode;
import com.zhilu.demo.service.CatService;

@RestController
@RequestMapping("/cat")
public class CatController {
//	@Resource
//	private CatService catService;
//
//	@RequestMapping("/save")
//	public String save() {
//		Cat cat = new Cat();
//		cat.setCatName("tom");
//		cat.setCatAge(13);
//		catService.save(cat);
//		return "save  ok";
//	}
//
//	@RequestMapping("/delete")
//	public String delete() {
//		catService.delete(1);
//		return "delete ok";
//	}
//
//	@RequestMapping("/getAll")
//	public Iterable<Cat> getAll() {
//		return catService.getAll();
//	}

	
	@Autowired
	private CatRepository catRepositoy;

	@RequestMapping("getcat")
	public Object getUser(int id) {
		Cat catEntity = catRepositoy.findCatById(id);
		ResultMessage resultMsg = new ResultMessage(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),
				catEntity);
		return resultMsg;
	}

	@RequestMapping("getcats")
	public Object getUsers(String name) {
		List<Cat> catEntities = catRepositoy.findCatByName(name);
		ResultMessage resultMsg = new ResultMessage(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),
				catEntities);
		return resultMsg;
	}

	@Modifying
	@RequestMapping("addcat")
	public Object addCat(@RequestBody Cat catEntity) {
		catRepositoy.save(catEntity);
		ResultMessage resultMsg = new ResultMessage(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),
				catEntity);
		return resultMsg;
	}

	@Modifying
	@RequestMapping("updatecat")
	public Object updateUser(@RequestBody Cat catEntity) {
		Cat cat = catRepositoy.findCatById(catEntity.getId());
		if (cat != null) {
			cat.setCatName(catEntity.getCatName());
			catRepositoy.save(cat);
		}
		ResultMessage resultMsg = new ResultMessage(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),
				null);
		return resultMsg;
	}

	@Modifying
	@RequestMapping("deletecat")
	public Object deleteUser(int id) {
		catRepositoy.delete(id);
		ResultMessage resultMsg = new ResultMessage(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),
				null);
		return resultMsg;
	}

}
