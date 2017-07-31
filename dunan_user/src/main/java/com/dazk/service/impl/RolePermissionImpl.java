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
import com.dazk.db.dao.MenuMapper;
import com.dazk.db.dao.RoleMapper;
import com.dazk.db.dao.RolePermissionMapper;
import com.dazk.db.model.Menu;
import com.dazk.db.model.Role;
import com.dazk.db.model.RolePermission;
import com.dazk.service.RolePermissionService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class RolePermissionImpl implements RolePermissionService {

	@Autowired
	private RolePermissionMapper rolePermiMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<RolePermission> queryRolePermission(JSONObject obj) {
		String role_code = obj.getString("role_code");
		RolePermission record = new RolePermission();
		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
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

		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}

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

		// 找不到角色
		Role roleRecord = new Role();
		roleRecord.setCode(role_code);
		roleRecord.setIsdel(0);
		int exist1 = roleMapper.selectCount(roleRecord);
		if (exist1 > 1) {
			return -2;
		}

		if (exist1 == 0) {
			return 0;
		}

		// 找不到菜单
		String reso_code = obj.getString("reso_code");
		if (reso_code != null) {
			Menu menuRecord = new Menu();
			menuRecord.setCode(reso_code);
			menuRecord.setIsdel(0);
			int exist2 = menuMapper.selectCount(menuRecord);
			if (exist2 > 1) {
				return -2;
			}

			if (exist2 == 0) {
				return 0;
			}
		}

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
		RolePermission record = new RolePermission();
		String role_code = obj.getString("role_code");

		// 找不到角色就不添加
		Role roleRecord = new Role();
		roleRecord.setCode(role_code);
		roleRecord.setIsdel(0);
		int exist1 = roleMapper.selectCount(roleRecord);
		if (exist1 > 0) {
			return -1;
		}

		// 找不到菜单不添加
		String reso_code = obj.getString("reso_code");
		Menu menuRecord = new Menu();
		menuRecord.setCode(reso_code);
		menuRecord.setIsdel(0);
		int exist2 = menuMapper.selectCount(menuRecord);
		if (exist2 > 0) {
			return -1;
		}

		record.setRole_code(role_code);
		record.setReso_code(reso_code);
		record.setDisused(0);
		int exist = rolePermiMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);
		return rolePermiMapper.insertSelective(record);
	}

	@Override
	public int delRolePermi(JSONObject obj) {
		RolePermission record = new RolePermission();
		String role_code = obj.getString("role_code");
		String reso_code = obj.getString("reso_code");
		record.setRole_code(role_code);
		record.setReso_code(reso_code);
		record.setDisused(1);
		int exist = rolePermiMapper.selectCount(record);
		if (exist >= 1) {
			return 1;
		}

		try {
			// 创建example
			Example example = new Example(RolePermission.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("role_code", role_code).andEqualTo("reso_code", reso_code).andEqualTo("disused", 0);
			return rolePermiMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

}
