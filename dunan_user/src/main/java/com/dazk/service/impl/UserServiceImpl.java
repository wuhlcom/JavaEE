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
import com.dazk.db.dao.RoleMapper;
import com.dazk.db.dao.UserMapper;
import com.dazk.db.model.Menu;
import com.dazk.db.model.Role;
import com.dazk.db.model.User;
import com.dazk.db.param.UserParam;
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
		record.setUsername(obj.getString("username"));
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
		String username = obj.getString("username");
		Long parent_user = obj.getLong("parent_user");
		Long id = obj.getLong("id");

		int exist = 0;
		if (username != null) {
			record.setUsername(username);
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
			if (username != null) {
				recordCriteria.andEqualTo("username", username).andEqualTo("parent_user", parent_user)
						.andEqualTo("isdel", 0);
				example.and(recordCriteria);
			} else if (id != null) {
				recordCriteria.andEqualTo("id", id).andEqualTo("parent_user", parent_user).andEqualTo("isdel", 0);
				example.and(recordCriteria);
			}
			return userMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	public List<User> queryUserOld(JSONObject obj) {
		User record = new User();
		String username = obj.getString("username");
		Long parent_user = obj.getLong("parent_user");
		Long id = obj.getLong("id");

		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}

		Example example = new Example(User.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		if (username != null) {
			recordCriteria.andEqualTo("username", username).andEqualTo("parent_user", parent_user).andEqualTo("isdel",
					0);
			example.and(recordCriteria);
		} else if (id != null) {
			recordCriteria.andEqualTo("id", id).andEqualTo("parent_user", parent_user).andEqualTo("isdel", 0);
			example.and(recordCriteria);
		}

		return userMapper.selectByExample(example);
	}

	// 根据条件查询用户信息
	public Object query(JSONObject obj) {
		Integer type = obj.getInteger("type");
		String search = obj.getString("search");
		Long parent_user = obj.getLong("parent_user");

		User record = new User();
		record = JSON.parseObject(obj.toJSONString(), User.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}

		Example userExample = new Example(User.class);
		// 创建查询条件
		Example.Criteria userCriteria = userExample.createCriteria();
		List<User> users = null;
		if (type == 0) {
			userCriteria.andEqualTo("isdel", 0).andEqualTo("disused", 0).andEqualTo("parent_user", parent_user);
			userExample.and(userCriteria);

			users = userMapper.selectByExample(userExample);
		} else if (type == 1) {
			userCriteria.andLike("username", "%" + search + "%").andEqualTo("isdel", 0).andEqualTo("disused", 0)
					.andEqualTo("parent_user", parent_user);
			;
			userExample.and(userCriteria);
			users = userMapper.selectByExample(userExample);
		}

		if (users == null) {
			return null;
		}
		return userRole(users);
	}

	/**
	 * 生成用户和角色信息
	 * 
	 */
	private Object userRole(List<User> users) {
		List<Object> list = new ArrayList<>();
		List<Role> roleRecored;
		Map<String, Object> userMap = new HashMap<>();
		Role role = new Role();
		String nickName;
		String userName;
		String roleName;
		Long userId;
		Long role_id;
		for (User user : users) {
			role_id = user.getRole_id();
			role.setId(role_id);
			role.setIsdel(0);
			int exist = roleMapper.selectCount(role);
			if (exist == 0) {
				return -2;
			}

			Example example = new Example(Role.class);
			// 创建查询条件
			Example.Criteria recordCriteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			recordCriteria.andEqualTo("id", role_id).andEqualTo("isdel", 0);
			example.and(recordCriteria);
			roleRecored = roleMapper.selectByExample(example);
			roleName = roleRecored.get(0).getName();
			nickName = user.getNickname();
			userName = user.getUsername();
			userId = user.getId();
			userMap.put("id", userId);
			userMap.put("nickName", nickName);
			userMap.put("username", userName);
			userMap.put("roleName", roleName);
			list.add(userMap);
		}
		return list;
	}

	@Override
	public Object queryUserByRole(JSONObject obj) {
		Integer type = obj.getInteger("type");
		String search = obj.getString("search");
		Long parent_user = obj.getLong("parent_user");

		Role record = new Role();
		record = JSON.parseObject(obj.toJSONString(), Role.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}

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

			userCriteria.andEqualTo("isdel", 0).andEqualTo("disused", 0).andEqualTo("parent_user", parent_user);
			userExample.and(userCriteria);

			roles = roleMapper.selectByExample(roleExample);
			users = userMapper.selectByExample(userExample);
		} else if (type == 1) {
			roleCriteria.andLike("username", "%" + search + "%").andEqualTo("isdel", 0).andEqualTo("disused", 0)
					.andEqualTo("parent_user", parent_user);
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
		Long userId;
		String userName;
		String nickName;
		String userRemark;
		Long roleId;
		String roleName = "";
		for (Role role : roles) {
			roleId = role.getId();
			roleName = role.getName();
			List<Object> userList = new ArrayList<>();
			for (User user : users) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				if ((user.getRole_id() != null) && user.getRole_id().equals(roleId)) {
					nickName = user.getNickname();
					userId = user.getId();
					userName = user.getUsername();
					userRemark = user.getRemark();
					userMap.put("id", userId);
					userMap.put("nickname", nickName);
					userMap.put("username", userName);
					userMap.put("rolename", roleName);
					userMap.put("remark", userRemark);
				}
				if (!userMap.isEmpty()) {
					userList.add(userMap);
				}
			}
			if (!userList.isEmpty()) {
				list.add(userList);
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
		Long parent_user = obj.getLong("parent_user");
		String username = obj.getString("username");
		if (username != null) {
			record.setUsername(username);
			record.setIsdel(0);
			int exist = userMapper.selectCount(record);
			if (exist > 1) {
				return -2;
			}
		}

		Long id = obj.getLong("id");
		if (id != null) {
			record.setId(id);
			record.setIsdel(0);
			int exist = userMapper.selectCount(record);
			if (exist > 1) {
				return -2;
			}
		}

		String email = obj.getString("email");
		record.setEmail(email);
		record.setIsdel(0);
		int exist = userMapper.selectCount(record);
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
			if (id != null) {
				criteria.andEqualTo("id", id).andEqualTo("isdel", 0).andEqualTo("parent_user", parent_user);
			} else if (username != null) {
				criteria.andEqualTo("username", username).andEqualTo("isdel", 0).andEqualTo("parent_user", parent_user);
			} else {
				return 0;
			}
			return userMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	/**  
	* @Title: js2UesrParam  
	* @Description: TODO 
	* @param @param obj
	* @param @return   
	* @return UserParam   
	* @throws  
	*/
	private UserParam js2UesrParam(JSONObject obj) {
		Integer type = obj.getInteger("type");
		Long parentUser = obj.getLong("parentUser");
		String userName = obj.getString("userName");
		String roleName = obj.getString("search");
		Integer page = obj.getInteger("page");
		Integer listRows = obj.getInteger("listRows");
		UserParam paramBean = new UserParam(type, parentUser, userName, roleName, page, listRows);
		return paramBean;
	}


	@Override
	public List<User> queryUser(JSONObject obj) {
		UserParam paramBean = js2UesrParam(obj);
		System.out.println(paramBean);
		return userMapper.queryUser(paramBean);
	}
	

	@Override
	public int queryUserCount(JSONObject obj) {
		UserParam paramBean = js2UesrParam(obj);		
		return userMapper.queryUserCount(paramBean);
	}

}
