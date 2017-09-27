/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 上午10:54:24 * 
*/
package com.dazk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.GlobalParamsUtil;
import com.dazk.common.util.MD5Util;

import com.dazk.common.util.UserCompareUtil;
import com.dazk.db.dao.RoleMapper;
import com.dazk.db.dao.UserMapper;
import com.dazk.db.model.Role;
import com.dazk.db.model.User;
import com.dazk.db.param.UserParam;
import com.dazk.service.DataPermissionService;
import com.dazk.service.RoleService;
import com.dazk.service.UserService;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	public final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Resource
	private RoleService roleService;

	@Resource
	private DataPermissionService dataPerService;

	// 添加超级管理员时必须给账户加数据权限
	// 添加超级管理员时parent_user为0
	@Override
	@Transactional
	public int addUser(JSONObject obj) {
		int rs = 0;
		User record = new User();
		// 用户名已存在则不添加
		String username = obj.getString("username");
		record.setUsername(username);
		record.setSex(null);
		record.setIsdel(0);
		record.setDisused(1);
		int exist = userMapper.selectCount(record);
		if (exist >= 1) {
			return -1;
		}

		// 邮箱已存在则不添加
		record.setUsername(null);
		record.setEmail(obj.getString("email"));
		record.setIsdel(0);
		record.setDisused(1);
		exist = userMapper.selectCount(record);
		if (exist >= 1) {
			return -3;
		}

		// 电话号码已存在则不添加
		String tel = obj.getString("telephone");
		if (tel != null && !tel.isEmpty()) {
			record.setUsername(null);
			record.setEmail(null);
			record.setTelephone(tel);
			record.setIsdel(0);
			record.setDisused(1);
			exist = userMapper.selectCount(record);
			if (exist >= 1) {
				return -4;
			}
		}

		// 身份证已存在则不添加
		String idcard = obj.getString("idcard");
		if (idcard != null && !idcard.isEmpty()) {
			record.setUsername(null);
			record.setEmail(null);
			record.setIdcard(idcard);
			record.setIsdel(0);
			record.setDisused(1);
			exist = userMapper.selectCount(record);
			if (exist >= 1) {
				return -5;
			}
		}

		// 查询角色信息，判断角色是否为超级管理员
		Long role_id = obj.getLong("role_id");
		JSONObject roleJson = new JSONObject();
		roleJson.put("role_id", role_id);
		Role role = roleService.queryRoleOne(roleJson);

		String code = role.getCode();

		record = JSON.parseObject(obj.toJSONString(), User.class);

		// 用来修改用户的父id如果父id改为0则为超级管理员
		// if (code == GlobalParamsUtil.ADMIN_CODE) {
		// record.setParent_user(0L);
		// }

		record.setIsdel(0);
		record.setDisused(1);
		record.setCreated_at(System.currentTimeMillis() / 1000);

		// MD5加密
		String password = record.getPassword();
		password = MD5Util.MD5Encode(password);
		record.setPassword(password);
		rs = userMapper.insertSelective(record);

		// 如果用户是超级管理员角色则添加所有数据权限
		// *****************************************
		// *****************************************
		// if (code == GlobalParamsUtil.ADMIN_CODE) {
		// JSONObject userJson = new JSONObject();
		// userJson.put("username", username);
		// User user = queryUserOne(userJson);
		// if (user == null) {
		// return -6;
		// }
		//
		// Long user_id = user.getId();
		//
		// // 添加超级管理员数据权限
		// JSONObject dataJson = new JSONObject();
		// dataJson.put("user_id", user_id);
		// dataJson.put("code_value", "");
		// dataJson.put("data_type", 1);
		// dataJson.put("code_type", 1);
		// rs = dataPerService.addDataPermi(dataJson);
		// if (rs == -1) {
		// return -7;
		// }
		// }
		// *****************************************
		// *****************************************
		return rs;
	}

	@Override
	public int delUser(JSONObject obj) {
		User record = new User();
		record.setSex(null);
		String username = obj.getString("username");
		Long parent_user = obj.getLong("parent_user");
		Long id = obj.getLong("id");

		int exist = 0;
		if (username != null) {
			record.setUsername(username);
			record.setIsdel(1);
		} else if (id != null) {
			record.setId(id);
			record.setIsdel(1);
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
			} else if (id != null) {
				recordCriteria.andEqualTo("id", id).andEqualTo("parent_user", parent_user).andEqualTo("isdel", 0);
			}
			return userMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	// 更新用户信息
	@Override
	public int updateUser(JSONObject obj) {
		User record = new User();
		record.setSex(null);
		Long parent_user = obj.getLong("parent_user");
		String username = obj.getString("username");
		String password = obj.getString("password");// 密码为空时不修改密码

		Long id = obj.getLong("id");
		if (username != null) {
			record.setUsername(username);
			record.setIsdel(0);
			int exist = userMapper.selectCount(record);
			if (exist > 1) {
				return -2;
			}
		} else if (id != null) {
			record.setId(id);
			record.setIsdel(0);
			int exist = userMapper.selectCount(record);
			if (exist > 1) {
				return -2;
			}
		}

		String email = obj.getString("email");
		Example paramsExample = new Example(User.class);
		Example.Criteria paramsCriteria = paramsExample.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		paramsCriteria.andNotEqualTo("id", id).andEqualTo("isdel", 0).andEqualTo("email", email);
		int exist = userMapper.selectCountByExample(paramsExample);
		if (exist >= 1) {
			return -3;
		}

		String telephone = obj.getString("telephone");
		if (telephone != null && !telephone.trim().isEmpty()) {
			Example paramsExample1 = new Example(User.class);
			Example.Criteria paramsCriteria1 = paramsExample1.createCriteria();
			paramsCriteria1.andNotEqualTo("id", id).andEqualTo("isdel", 0).andEqualTo("telephone", telephone);
			exist = userMapper.selectCountByExample(paramsExample);
			if (exist >= 1) {
				return -4;
			}
		}

		String idcard = obj.getString("idcard");
		if (idcard != null && !idcard.trim().isEmpty()) {
			Example paramsExample2 = new Example(User.class);
			Example.Criteria paramsCriteria2 = paramsExample2.createCriteria();
			paramsCriteria2 = paramsExample2.createCriteria();
			paramsCriteria2.andNotEqualTo("id", id).andEqualTo("isdel", 0).andEqualTo("idcard", idcard);
			exist = userMapper.selectCountByExample(paramsExample2);
			if (exist >= 1) {
				return -5;
			}
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

			if (password == null || password.trim() == "" || password.isEmpty()) {
				record.setPassword(null);
			} else {
				// MD5加密
				password = MD5Util.MD5Encode(password);
				record.setPassword(password);
			}

			return userMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: js2UesrParam @Description: TODO @param @param
	 *         obj @param @return @return UserParam @throws
	 */
	private UserParam js2UesrParam(JSONObject obj) {
		Integer type = obj.getInteger("type");
		String userName = null;
		String roleName = null;
		Long roleId = null;
		if (type == 1) {
			roleName = obj.getString("search");
		} else if (type == 2) {
			userName = obj.getString("search");
		} else if (type == 3) {
			roleId = obj.getLong("search");
		}

		Long parentUser = obj.getLong("parent_user");
		Integer page = obj.getInteger("page");
		Integer listRows = obj.getInteger("listRows");
		UserParam paramBean = new UserParam(type, parentUser, parentUser, roleId, userName, roleName, page, listRows,
				null);
		return paramBean;
	}

	// 处理返回结果
	/**
	 * @Title: queryUserResult @Description: TODO @param @param result @return
	 *         void @throws
	 */
	private List<User> queryUserResult(List<User> result) {
		for (int i = 0; i < result.size(); i++) {
			result.get(i).setIsdel(null);
			result.get(i).setDisused(null);
			if (result.get(i).getAge() == null) {
				result.get(i).setAge(0);
			}

			if (result.get(i).getSex() == null) {
				result.get(i).setSex(0);
			}

			if (result.get(i).getName() == null) {
				result.get(i).setName("");
			}
			if (result.get(i).getRoleName() == null) {
				result.get(i).setRoleName("");
			}

			if (result.get(i).getLv() == null) {
				result.get(i).setLv("");
			}

			if (result.get(i).getEmail() == null) {
				result.get(i).setEmail("");
			}

			if (result.get(i).getTelephone() == null) {
				result.get(i).setTelephone("");
			}

			if (result.get(i).getCompany() == null) {
				result.get(i).setCompany("");
			}

			if (result.get(i).getAddress() == null) {
				result.get(i).setAddress("");
			}

			if (result.get(i).getPosition() == null) {
				result.get(i).setPosition("");
			}

			if (result.get(i).getIdcard() == null) {
				result.get(i).setIdcard("");
			}

			if (result.get(i).getRemark() == null) {
				result.get(i).setRemark("");
			}

		}
		return result;
	}

	private Set<User> queryUserResult(Set<User> result) {
		for (User user : result) {
			user.setIsdel(null);
			user.setDisused(null);
			if (user.getAge() == null) {
				user.setAge(0);
			}

			if (user.getSex() == null) {
				user.setSex(0);
			}

			if (user.getName() == null) {
				user.setName("");
			}
			if (user.getRoleName() == null) {
				user.setRoleName("");
			}

			if (user.getLv() == null) {
				user.setLv("");
			}

			if (user.getEmail() == null) {
				user.setEmail("");
			}

			if (user.getTelephone() == null) {
				user.setTelephone("");
			}

			if (user.getCompany() == null) {
				user.setCompany("");
			}

			if (user.getAddress() == null) {
				user.setAddress("");
			}

			if (user.getPosition() == null) {
				user.setPosition("");
			}

			if (user.getIdcard() == null) {
				user.setIdcard("");
			}

			if (user.getRemark() == null) {
				user.setRemark("");
			}

		}

		return result;
	}

	// 查询单个有效用户
	@Override
	public User queryUserOne(JSONObject obj) {
		String username = obj.getString("username");
		Long userId = obj.getLong("id");
		User user = new User();
		user.setAge(null);
		user.setSex(null);
		user.setDisused(null);
		user.setIsdel(0);

		if (username != null && !username.trim().isEmpty()) {
			user.setUsername(username);
		} else if (userId != null) {
			user.setId(userId);
		} else {
			System.out.println("参数错误!");
			return null;
		}

		try {
			return userMapper.selectOne(user);
		} catch (Exception e) {
			return null;
		}

	}

	// 查询用户信息
	// 查询当前用户及其子用户
	public List<User> queryUserOld(JSONObject obj) {
		Set<User> users = new TreeSet<>(new UserCompareUtil());
		UserParam paramBean = js2UesrParam(obj);
		List<User> userResult = new ArrayList<>();
		// type按角色名来查询
		if (paramBean.getType() == 1) {
			userResult = userMapper.queryUserByRole(paramBean);
		} else {
			userResult = userMapper.queryUser(paramBean);
		}
		userResult = queryUserResult(userResult);
		return userResult;
	}

	private Set<Long> getUserIds(List<Long> ids, Set<Long> userIds) {
		if (ids != null && ids.size() > 0) {
			Example userExample = new Example(User.class);
			Example.Criteria userCriteria = userExample.createCriteria();
			userCriteria.andIn("parent_user", ids).andEqualTo("isdel", 0);

			List<User> users = userMapper.selectByExample(userExample);
			List<Long> uIds = new ArrayList<Long>();

			for (User user : users) {
				Long uid = user.getId();
				uIds.add(uid);
				userIds.add(uid);
			}
			getUserIds(uIds, userIds);
		}
		return userIds;
	}

	// 查询用户信息
	// 查询当前用户及其子用户
	@Override
	public List<User> queryUser(JSONObject obj) {	
		List<Long> initUserId = new ArrayList<>();
		List<Long> uIds = new ArrayList<>();
		List<User> result = new ArrayList<>();
		Set<Long> allUids = new TreeSet<>();

		UserParam paramBean = js2UesrParam(obj);
		Long userId = paramBean.getParentUser();
		allUids.add(userId);
		initUserId.add(userId);
		Set<Long> userIds = this.getUserIds(initUserId, allUids);

		uIds.addAll(userIds);
		paramBean.setUserIds(uIds);

		result = userMapper.queryUserInIds(paramBean);

		result = queryUserResult(result);
		return result;
	}

	// 统计数量
	@Override
	public Integer queryUserCount(JSONObject obj) {
		UserParam paramBean = js2UesrParam(obj);
		Integer number = 0;
		List<Long> initUserId = new ArrayList<>();
		List<Long> uIds = new ArrayList<>();
		List<User> result = new ArrayList<>();
		Set<Long> allUids = new TreeSet<>();
		
		Long userId = paramBean.getParentUser();
		allUids.add(userId);
		initUserId.add(userId);
		Set<Long> userIds = this.getUserIds(initUserId, allUids);

		uIds.addAll(userIds);
		paramBean.setUserIds(uIds);
		
		number = userMapper.queryUserInIdsCount(paramBean);
		return number;
	}

	// 统计数量
	public Integer queryUserCountOld(JSONObject obj) {
		UserParam paramBean = js2UesrParam(obj);
		Integer number = 0;
		if (paramBean.getType() == 1) {
			number = userMapper.queryUserCountByRole(paramBean);
		} else {
			number = userMapper.queryUserCount(paramBean);
		}
		return number;
	}

	// 将密码重置为123456
	@Override
	public int resetPasswd(JSONObject obj) {
		Long user_id = obj.getLong("id");
		Long parent_user = obj.getLong("parent_user");

		// 查询该用户当前操作用户是否为超级管理员
		// JSONObject queryUser=new JSONObject();
		// queryUser.put("id",parent_user);
		// this.queryUserOne(queryUser);

		// 查询角色下的用户
		Example userExample1 = new Example(User.class);
		// 创建查询条件
		Example.Criteria userCriteria1 = userExample1.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		userCriteria1.andEqualTo("id", user_id).andEqualTo("isdel", 0);
		userExample1.and(userCriteria1);
		List<User> user = userMapper.selectByExample(userExample1);
		if (user.size() > 0) {
			User rsUser = user.get(0);
			// MD5加密
			String password = MD5Util.MD5Encode(GlobalParamsUtil.DEFAULT_PASSWD);
			rsUser.setPassword(password);
			// updateByExampleSelective使用null更新,因此使用updateByExample来更新
			return userMapper.updateByExample(rsUser, userExample1);
		} else {
			return 0;
		}

	}

}
