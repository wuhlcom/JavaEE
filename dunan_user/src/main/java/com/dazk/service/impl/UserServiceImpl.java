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
import com.dazk.db.dao.UserMapperMy;
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
	private UserMapperMy userMapperMy;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public int addUser(JSONObject obj) {
		User record = new User();
		// 用户名已存在则不添加
		record.setUsername(obj.getString("username"));
		record.setSex(null);
		record.setIsdel(0);
		record.setDisused(1);
		int exist = userMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}

		// 邮箱已存在则不添加
		record.setUsername(null);
		record.setEmail(obj.getString("email"));
		record.setIsdel(0);
		record.setDisused(1);
		exist = userMapper.selectCount(record);
		if (exist > 0) {
			return -3;
		}

		// 电话号码已存在则不添加
		record.setUsername(null);
		record.setEmail(null);
		record.setTelephone(obj.getString("telephone"));
		record.setIsdel(0);
		record.setDisused(0);
		exist = userMapper.selectCount(record);
		if (exist > 0) {
			return -4;
		}

		// 身份证已存在则不添加
		record.setUsername(null);
		record.setEmail(null);
		record.setIdcard(obj.getString("idcard"));
		record.setIsdel(0);
		record.setDisused(0);
		exist = userMapper.selectCount(record);
		if (exist > 0) {
			return -5;
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
		exist = userMapper.selectCount(record);

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
		

	// 更新用户信息
	@Override
	public int updateUser(JSONObject obj) {
		User record = new User();
		record.setSex(null);
		Long parent_user = obj.getLong("parent_user");
		String username = obj.getString("username");
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
		// 创建example
		Example paramsExample = new Example(User.class);
		// 创建查询条件
		Example.Criteria paramsCriteria = paramsExample.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		paramsCriteria.andNotEqualTo("id", id).andEqualTo("isdel", 0).andEqualTo("email", email);
		paramsExample.and(paramsCriteria);
		int exist = userMapper.selectCountByExample(paramsExample);
		if (exist >= 1) {
			return -3;
		}

		String telephone = obj.getString("telephone");
		paramsExample.clear();
		paramsCriteria.andNotEqualTo("id", id).andEqualTo("isdel", 0).andEqualTo("telephone", telephone);
		paramsExample.and(paramsCriteria);

		exist = userMapper.selectCountByExample(paramsExample);
		if (exist >= 1) {
			return -4;
		}

		String idcard = obj.getString("idcard");
		paramsExample.clear();
		paramsCriteria.andNotEqualTo("id", id).andEqualTo("isdel", 0).andEqualTo("idcard", idcard);
		paramsExample.and(paramsCriteria);
		exist = userMapper.selectCountByExample(paramsExample);
		if (exist >= 1) {
			return -5;
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
	 * @Title: js2UesrParam @Description: TODO @param @param
	 *         obj @param @return @return UserParam @throws
	 */
	private UserParam js2UesrParam(JSONObject obj) {
		Integer type = obj.getInteger("type");
		String userName = null;
		String roleName = null;
		if (type == 1) {
			roleName = obj.getString("search");
		} else if (type == 2) {
			userName = obj.getString("search");
		}

		Long parentUser = obj.getLong("parentUser");
		Integer page = obj.getInteger("page");
		Integer listRows = obj.getInteger("listRows");
		UserParam paramBean = new UserParam(type, parentUser, userName, roleName, page, listRows);
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

	// 查询用户信息
	@Override
	public List<User> queryUser(JSONObject obj) {
		UserParam paramBean = js2UesrParam(obj);
		System.out.println(paramBean);
		List<User> userResult = userMapper.queryUser(paramBean);
		userResult = queryUserResult(userResult);
		return userResult;
	}

	// 统计数量
	@Override
	public int queryUserCount(JSONObject obj) {
		UserParam paramBean = js2UesrParam(obj);
		return userMapper.queryUserCount(paramBean);
	}

}
