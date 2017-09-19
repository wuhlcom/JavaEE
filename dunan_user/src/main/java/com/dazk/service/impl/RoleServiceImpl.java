/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午11:23:55 * 
*/
package com.dazk.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.GlobalParamsUtil;
import com.dazk.common.util.PubUtil;
import com.dazk.db.dao.RoleMapper;
import com.dazk.db.dao.RolePermissionMapper;
import com.dazk.db.dao.UserMapper;
import com.dazk.db.model.Role;
import com.dazk.db.model.RolePermission;
import com.dazk.db.model.User;
import com.dazk.service.RoleService;
import com.dazk.service.UserService;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	public final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RolePermissionMapper rolePermiMapper;

	@Autowired
	private UserMapper userMapper;

	@Resource
	private UserService userService;

	@Override
	public int addRole(JSONObject obj) {
		int codeNum = 11;
		Role record = new Role();
		String roleName = obj.getString("name");
		record.setName(roleName);
		record.setIsdel(0);
		int exist = roleMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}

		String code = PubUtil.genCode(codeNum);
		Role record2 = new Role();
		record2.setCode(code);
		exist = roleMapper.selectCount(record2);
		int i = 0;
		// 反复生成code直到得到无重复的code
		while (exist > 0) {
			code = PubUtil.genCode(codeNum);
			record2.setCode(code);
			exist = roleMapper.selectCount(record2);
			if (i == 100) {
				return -2;
			}
			i++;
		}

		record = JSON.parseObject(obj.toJSONString(), Role.class);
		record.setDisused(1);
		record.setCode(code);
		record.setCreated_at(System.currentTimeMillis() / 1000);
		return roleMapper.insertSelective(record);
	}

	@Override
	public int addSuperRole(JSONObject obj) {
		Role record = new Role();
		String roleName = obj.getString("name");
		record.setName(roleName);
		record.setIsdel(0);
		int exist = roleMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}

		Role record2 = new Role();
		record2.setCode(GlobalParamsUtil.ADMIN_CODE);
		exist = roleMapper.selectCount(record2);
		if (exist > 0) {
			return -2;
		}

		record.setDisused(1);
		record.setCode(GlobalParamsUtil.ADMIN_CODE);
		record.setCreated_at(System.currentTimeMillis() / 1000);
		return roleMapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int delRole(JSONObject obj) {
		Role record = new Role();
		Long role_id = obj.getLong("id");
		record.setId(role_id);
		record.setIsdel(1);

		JSONObject userParams = new JSONObject();
		userParams.put("type", 3);
		userParams.put("search", role_id);
		// 角色下有用户时不允许删除
		List<User> users = userService.queryUser(userParams);
		if (users.size() > 0) {
			return -1;
		}

		// 删除角色菜单权限
		RolePermission permiRecord = new RolePermission();
		permiRecord.setRole_id(role_id);
		permiRecord.setDisused(0);
		Example permiExample = new Example(RolePermission.class);
		// 创建查询条件
		Example.Criteria permiCriteria = permiExample.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		permiCriteria.andEqualTo("role_id", role_id).andEqualTo("disused", 1);
		int rs = rolePermiMapper.updateByExampleSelective(permiRecord, permiExample);

		// 查询角色下的用户
		Example userExample1 = new Example(User.class);
		// 创建查询条件
		Example.Criteria userCriteria1 = userExample1.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		userCriteria1.andEqualTo("role_id", role_id).andEqualTo("isdel", 0);
		userExample1.and(userCriteria1);
		List<User> roleUsers = userMapper.selectByExample(userExample1);
	
		if (roleUsers.size() > 0) {
			for (User user : roleUsers) {
				// 修改User实例的中Role_id为null
				user.setRole_id(null);
				// updateByExampleSelective使用null更新,因此使用updateByExample来更新
				rs = userMapper.updateByExample(user, userExample1);
			}
		}

		// 删除角色
		// 创建example
		Example example = new Example(Role.class);
		// 创建查询条件
		Example.Criteria criteria = example.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		criteria.andEqualTo("id", role_id).andEqualTo("isdel", 0);
		rs = roleMapper.updateByExampleSelective(record, example);
		if (rs == 0) {
			throw new RuntimeErrorException(null, "删除角色失败");
		}

		return rs;
	}

	@Override
	public int updateRole(JSONObject obj) {
		Role record = new Role();
		Long id = obj.getLong("id");
		record.setId(id);
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
			criteria.andEqualTo("id", id).andEqualTo("isdel", 0);		
			return roleMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	// 查询单个角色信息
	@Override
	public Role queryRoleOne(JSONObject obj) {	
		String roleName = obj.getString("roleName");
		Long roleId = obj.getLong("role_id");
		String roleCode = obj.getString("role_code");
        		
		Role roleParams = new Role();
		if (roleName != null && roleName.trim() != "") {
			roleParams.setName(roleName);
		} else if (roleId != null) {
			roleParams.setId(roleId);
		} else if (roleCode != null && roleCode.trim() != "") {
			roleParams.setCode(roleCode);
		}else{
			System.out.println("参数错误");
			return null;
		}

		try {
			return roleMapper.selectOne(roleParams);
		} catch (Exception e) {		
			return null;
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
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}

		Example example = new Example(Role.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			recordCriteria.andEqualTo("isdel", 0);			
		} else if (type == 1) {
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			recordCriteria.andLike("name", "%" + search + "%").andEqualTo("isdel", 0);		
		} else if (type == 2) {
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			recordCriteria.andEqualTo("user_id", search).andEqualTo("isdel", 0);	
		}
		return roleMapper.selectByExample(example);
	}

	@Override
	public int queryRoleCount(JSONObject obj) {
		String type = obj.getString("type");
		Example example = new Example(Role.class);
		Example.Criteria recordCriteria = example.createCriteria();
		if (type.equals("0")) {
			recordCriteria.andEqualTo("isdel", 0);			
		} else if (type.equals("1")) {
			String search = obj.getString("search");
			recordCriteria.andLike("name", "%" + search + "%").andEqualTo("isdel", 0);		
		} else if (type.equals("2")) {
			Long search = obj.getLong("search");
			recordCriteria.andEqualTo("user_id", search).andEqualTo("isdel", 0);		
		}
		return roleMapper.selectCountByExample(example);
	}

}
