package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.service.MenuService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public int addMenu(JSONObject obj) {
		Menu record = new Menu();
		record.setName(obj.getString("name"));
		record.setIsdel(0);
		int exist = menuMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}

		Menu record2 = new Menu();
		record2.setCode(obj.getString("code"));
		record2.setIsdel(0);
		int exist2 = menuMapper.selectCount(record2);
		if (exist2 > 0) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), Menu.class);
		record.setIs_menu(obj.getInteger("type"));
		record.setCreated_at(System.currentTimeMillis() / 1000);
		return menuMapper.insertSelective(record);
	}

	@Override
	public int delMenu(JSONObject obj) {
		Menu record = new Menu();
		String code = obj.getString("code");
		record.setCode(code);
		record.setIsdel(1);

		int exist = menuMapper.selectCount(record);
		if (exist != 0) {
			return 1;
		}
		try {
			// 创建example
			Example example = new Example(Menu.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("code", code).andEqualTo("isdel", 0);
			return menuMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public List<Menu> queryMenu(JSONObject obj) {
		Integer type = obj.getInteger("type");
		String search = obj.getString("search");

		Menu record = new Menu();
		record = JSON.parseObject(obj.toJSONString(), Menu.class);
		
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		}else if(record.getPage() == null && record.getListRows() != null){
			PageHelper.startPage(1, record.getListRows());
		}else if (record.getListRows() == null){
			PageHelper.startPage(1, 0);
		}		

		Example example = new Example(Menu.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			recordCriteria.andEqualTo("isdel", 0);
			example.and(recordCriteria);

		} else if (type == 1) {
			recordCriteria.andEqualTo("code", search).andEqualTo("isdel", 0);
			example.and(recordCriteria);
		}
		return menuMapper.selectByExample(example);
	}

	@Override
	public int queryMenuCount(JSONObject obj) {
		Integer type = obj.getInteger("type");	
		String search = obj.getString("search");
		Menu record = new Menu();
		record = JSON.parseObject(obj.toJSONString(), Menu.class);
		Example example = new Example(Menu.class);
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {
			recordCriteria.andEqualTo("isdel", 0);
			example.and(recordCriteria);
		} else if (type == 1) {
			recordCriteria.andEqualTo("code", search).andEqualTo("isdel", 0);
			example.and(recordCriteria);			
		}
		return menuMapper.selectCountByExample(example);
	}

	@Override
	public int updateMenu(JSONObject obj) {
		Menu record = new Menu();
		String code = obj.getString("code");
		record.setCode(code);
		record.setIsdel(0);
		int exist = menuMapper.selectCount(record);
		if (exist > 1) {
			return -2;
		}

		if (exist == 0) {
			return 0;
		}				
		
		record = JSON.parseObject(obj.toJSONString(), Menu.class);
		try {
			// 创建example
			Example example = new Example(Menu.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("code", code).andEqualTo("isdel", 0);
			record.setIs_menu(obj.getInteger("type"));
			return menuMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}

	}
}
