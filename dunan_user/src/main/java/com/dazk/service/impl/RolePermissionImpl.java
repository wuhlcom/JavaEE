/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 下午2:45:53 * 
*/
package com.dazk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.RolePermissionMapper;
import com.dazk.db.model.Menu;
import com.dazk.db.model.RolePermission;
import com.dazk.service.RolePermissionService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class RolePermissionImpl implements RolePermissionService {

	@Autowired
	private RolePermissionMapper rolePermiMapper;

	@Override
	public List<RolePermission> queryRolePermission(JSONObject obj) {
		String role_code = obj.getString("role_code");

		RolePermission record = new RolePermission();
		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		}else if(record.getPage() == null && record.getListRows() != null){
			PageHelper.startPage(1, record.getListRows());
		}else if (record.getListRows() == null){
			PageHelper.startPage(1, 0);
		}	

		Example example = new Example(RolePermission.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		recordCriteria.andEqualTo("role_code", role_code).andEqualTo("disused", 0);
		example.and(recordCriteria);
		return rolePermiMapper.selectByExample(example);
	}
	
	@Override
	public int queryRolePermiCount(JSONObject obj) {
		String role_code = obj.getString("role_code");
		RolePermission record = new RolePermission();
		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);
		Example example = new Example(RolePermission.class);	
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		Example.Criteria recordCriteria = example.createCriteria();
		recordCriteria.andEqualTo("role_code", role_code).andEqualTo("disused", 0);
		example.and(recordCriteria);
		return rolePermiMapper.selectCountByExample(example);
	}
	
	@Override
	public int updateRolePermi(JSONObject obj) {
		RolePermission record = new RolePermission();
		String role_code = obj.getString("role_code");
		record.setRole_code(role_code);
		record.setDisused(0);
		int exist = rolePermiMapper.selectCount(record);
		if (exist > 1) {
			return -2;
		}

		if (exist == 0) {
			return 0;
		}

		try {
			// 创建example
			Example example = new Example(RolePermission.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("role_code", role_code).andEqualTo("disused", 0);
			return rolePermiMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int addRolePermi(JSONObject obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delRolePermi(JSONObject obj) {
		RolePermission record = new RolePermission();
		record.setRole_code(obj.getString("role_code"));
		record.setDisused(0);
		int exist = rolePermiMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);		
		return rolePermiMapper.insertSelective(record);
	}
	
	

}
