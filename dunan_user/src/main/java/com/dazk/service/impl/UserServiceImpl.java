/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 上午10:54:24 * 
*/
package com.dazk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.PubFunction;
import com.dazk.db.dao.RoleMapper;
import com.dazk.db.dao.UserMapper;
import com.dazk.db.model.Role;
import com.dazk.db.model.User;
import com.dazk.service.UserService;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {
	// Encrypt(String sSrc, String sKey)
	final static String ENCRY_KEY = "q1w2e3r7i7o4i3uu";
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public int addUser(JSONObject obj) {
		User record = new User();
		// 用户名已存在则不添加
		record.setLogin_name(obj.getString("login_name"));
		record.setIsdel(0);
		record.setDisused(0);
		int exist = userMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}

		// 邮箱已存在则不添加
		User record1 = new User();
		record1.setEmail(obj.getString("email"));
		record1.setIsdel(0);
		record1.setDisused(0);
		exist = userMapper.selectCount(record1);
		if (exist > 0) {
			return -1;
		}

		// 电话号码已存在则不添加
		User record2 = new User();
		record2.setTelephone(obj.getString("telephone"));
		record2.setIsdel(0);
		record2.setDisused(0);
		exist = userMapper.selectCount(record2);
		if (exist > 0) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), User.class);
		record.setCreated_at(System.currentTimeMillis() / 1000);

		// // 加密
		// String pw = record.getPassword();
		// try {
		// String encryPw = PubFunction.encrypt(pw, ENCRY_KEY);
		// System.out.println("5-----------addUser-----");
		// record.setPassword(encryPw);
		// } catch (Exception e) {
		// e.printStackTrace();
		// System.out.println("6-----------addUser-----");
		// return -1;
		// }
		// record.setPassword(encryPw);
		return userMapper.insertSelective(record);
	}

	@Override
	public int delUser(JSONObject obj) {
		User record = new User();
		String login_name = obj.getString("login_name");
		Long id = obj.getLong("id");

		int exist = 0;
		if (login_name != null) {
			record.setLogin_name(login_name);
			record.setIsdel(1);
			exist = userMapper.selectCount(record);
		} else if (id != null) {
			record.setId(id);
			record.setIsdel(1);
			exist = userMapper.selectCount(record);
		}

		if (exist != 0) {
			return 1;
		}

		// 创建example
		Example example = new Example(User.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询

		try {
			if (login_name != null) {
				recordCriteria.andEqualTo("login_name", login_name).andEqualTo("isdel", 0);
				example.and(recordCriteria);
			} else if (id != null) {
				recordCriteria.andEqualTo("id", id).andEqualTo("isdel", 0);
				example.and(recordCriteria);
			}
			return userMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public List<User> queryUser(JSONObject obj) {
		User record = new User();
		String login_name = obj.getString("login_name");
		Long id = obj.getLong("id");
		Example example = new Example(User.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		if (login_name != null) {
			recordCriteria.andEqualTo("login_name", login_name).andEqualTo("isdel", 0);
			example.and(recordCriteria);
		} else if (id != null) {
			recordCriteria.andEqualTo("id", id).andEqualTo("isdel", 0);
			example.and(recordCriteria);
		}

		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}
		return userMapper.selectByExample(example);
	}

	@Override
	public Object queryUserByRole(JSONObject obj) {
		Integer type = obj.getInteger("type");
		String search = obj.getString("search");

		Example roleExample = new Example(Role.class);
		Example userExample = new Example(User.class);
		// 创建查询条件
		Example.Criteria roleCriteria = roleExample.createCriteria();
		Example.Criteria userCriteria = userExample.createCriteria();
		List<Role> roles = null;
		List<User> users = null;
		if (type == 0) {
			roleCriteria.andEqualTo("isdel", 0).andEqualTo("disused", 0);
			roleExample.and(roleCriteria);

			userCriteria.andEqualTo("isdel", 0).andEqualTo("disused", 0);
			userExample.and(userCriteria);

			roles = roleMapper.selectByExample(roleExample);
			users = userMapper.selectByExample(userExample);
		} else if (type == 1) {
			roleCriteria.andLike("name", "%" + search + "%").andEqualTo("isdel", 0).andEqualTo("disused", 0);
			roleExample.and(roleCriteria);

			userCriteria.andEqualTo("isdel", 0).andEqualTo("disused", 0);
			userExample.and(userCriteria);
			roles = roleMapper.selectByExample(roleExample);
			users = userMapper.selectByExample(userExample);
		}
		if (roles == null || users == null) {
			return null;
		}
		return queryUserAllByRole(roles, users);
	}

	/**
	 * 通过角色查询用户信息
	 * 
	 */
	private Object queryUserAllByRole(List<Role> roles, List<User> users) {
		List<Object> list = new ArrayList<>();
		String userName = new String();
		Long userId = 0L;
		String userLoginName = "";
		String userRemark = "";
		String roleCode = "";
		String roleName = "";
		for (Role role : roles) {
			roleCode = role.getCode();
			roleName = role.getName();
			Map<String, Object> roleMap = new HashMap<>();
			List<Object> userList = new ArrayList<>();
			for (User user : users) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				if (user.getRole_code().equals(roleCode)) {
					userName = user.getName();
					userId = user.getId();
					userLoginName = user.getLogin_name();
					userRemark = user.getRemark();
					userMap.put("id", userId);
					userMap.put("name", userName);
					userMap.put("login_name", userLoginName);
					userMap.put("remark", userRemark);
				}
				if (!userMap.isEmpty()) {
					userList.add(userMap);
				}
			}
			if (!userList.isEmpty()) {
				roleMap.put(roleName, userList);
				list.add(roleMap);
			}
		}
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	@Override
	public int updateUser(JSONObject obj) {
		User record = new User();
		String login_name = obj.getString("login_name");
		record.setLogin_name(login_name);
		record.setIsdel(0);
		int exist = userMapper.selectCount(record);
		if (exist > 1) {
			return -2;
		}

		String email = obj.getString("email");
		record.setEmail(email);
		record.setIsdel(0);
		exist = userMapper.selectCount(record);
		if (exist > 1) {
			return -2;
		}

		String telephone = obj.getString("telephone");
		record.setTelephone(telephone);
		record.setIsdel(0);
		exist = userMapper.selectCount(record);
		if (exist > 1) {
			return -2;
		}

		if (exist == 0) {
			return 0;
		}

		record = JSON.parseObject(obj.toJSONString(), User.class);
		try {
			// 创建example
			Example example = new Example(User.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("login_name", login_name).andEqualTo("isdel", 0);
			return userMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int queryUserCount(JSONObject obj) {
		// TODO Auto-generated method stub
		return 0;
	}

}
