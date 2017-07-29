/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午11:23:55 * 
*/ 
package com.dazk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.RoleMapper;
import com.dazk.db.model.Menu;
import com.dazk.db.model.Role;
import com.dazk.service.RoleService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public int addRole(JSONObject obj) {
		Role record = new Role();
		record.setName(obj.getString("name"));
		record.setIsdel(0);
		int exist = roleMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}

		Role record2 = new Role();
		record2.setCode(obj.getString("code"));
		record2.setIsdel(0);
		int exist2 = roleMapper.selectCount(record2);
		if (exist2 > 0) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), Role.class);	
		record.setDisused(0);
		record.setCreated_at(System.currentTimeMillis() / 1000);
		return roleMapper.insertSelective(record);
	}

	@Override
	public int delRole(JSONObject obj) {
		Role record = new Role();
		String code = obj.getString("code");
		record.setCode(code);
		record.setIsdel(1);

		int exist = roleMapper.selectCount(record);
		if (exist == 1) {
			return 1;
		}
		try {
			// 创建example
			Example example = new Example(Role.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("code", code).andEqualTo("isdel", 0);
			return roleMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}
		
	@Override
	public List<Role> queryRole(JSONObject obj) {
		Integer type = obj.getInteger("type");
		String search = obj.getString("search");

		Role record = new Role();
		record = JSON.parseObject(obj.toJSONString(), Role.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		}else if(record.getPage() == null && record.getListRows() != null){
			PageHelper.startPage(1, record.getListRows());
		}else if (record.getListRows() == null){
			PageHelper.startPage(1, 0);
		}	

		Example example = new Example(Role.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			recordCriteria.andEqualTo("isdel", 0);
			example.and(recordCriteria);

		} else if (type == 1) {
			  Example.Criteria criteria = example.createCriteria();
              //设置查询条件 多个andEqualTo串联表示 and条件查询        
			recordCriteria.andLike("name","%"+search+"%").andEqualTo("isdel", 0);
			example.and(recordCriteria);
		}
		return roleMapper.selectByExample(example);
	}

	@Override
	public int queryRoleCount(JSONObject obj) {
		String type = obj.getString("type");
		String search = obj.getString("search");
		Role record = new Role();
		record = JSON.parseObject(obj.toJSONString(), Role.class);
		Example example = new Example(Role.class);
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == "0") {
			recordCriteria.andEqualTo("isdel", 0);
			example.and(recordCriteria);
		} else if (type == "1") {
			recordCriteria.andEqualTo("name", search).andEqualTo("isdel", 0);
			example.and(recordCriteria);			
		}
		return roleMapper.selectCountByExample(example);
	}

	
	@Override
	public int updateRole(JSONObject obj) {	
		Role record = new Role();
		String code = obj.getString("code");
		record.setCode(code);
		record.setIsdel(0);
		int exist = roleMapper.selectCount(record);

		if (exist > 1) {
			return -2;
		}
	
		if (exist == 0) {
			return 0;
		}

		record = JSON.parseObject(obj.toJSONString(), Role.class);
		try {
			// 创建example
			Example example = new Example(Role.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("code", code).andEqualTo("isdel", 0);			
			return roleMapper.updateByExampleSelective(record, example);			
		} catch (Exception e) {
			return -1;
		}

	}

	
}
