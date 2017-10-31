/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月18日 下午6:27:13 * 
*/ 
package com.prison.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prison.common.util.MD5Util;
import com.prison.db.dao.UserDao;
import com.prison.db.entity.ResourceEntity;
import com.prison.db.entity.UserEntity;
import com.prison.service.UserService;

@Service
public class UserServiceImpl implements UserService { 
	
	@Autowired
	private UserDao userDao;

	@Override
	public int add(JSONObject jsonObj) {
		int rs = 0;		
		UserEntity record = new UserEntity();		
		// 用户名已存在则不添加
		String account = jsonObj.getString("account");
		record.setAccount(account);
		record.setSex(null);
		record.setIsdel(0);	
		int exist = userDao.selectCount(record);
		if (exist >= 1) {
			return -1;
		}

//		 邮箱已存在则不添加
		record.setAccount(null);
		record.setEmail(jsonObj.getString("email"));
		record.setIsdel(0);	
		exist = userDao.selectCount(record);
		if (exist >= 1) {
			return -2;
		}

		// 电话号码已存在则不添加
		String tel = jsonObj.getString("telephone");
		if (tel != null && !tel.isEmpty()) {
			record.setAccount(null);		
			record.setTelephone(tel);
			record.setIsdel(0);		
			exist = userDao.selectCount(record);
			if (exist >= 1) {
				return -3;
			}
		}

		// 身份证已存在则不添加
		String idcard = jsonObj.getString("idcard");
		if (idcard != null && !idcard.isEmpty()) {
			record.setAccount(null);
			record.setTelephone(null);
			record.setIdcard(idcard);
			record.setIsdel(0);		
			exist = userDao.selectCount(record);
			if (exist >= 1) {
				return -4;
			}
		}

		record = JSON.parseObject(jsonObj.toJSONString(), UserEntity.class);
		record.setIsdel(0);	
		record.setCreated_at(System.currentTimeMillis() / 1000);

		// MD5加密
		String password = record.getPassword();
		password = MD5Util.MD5Encode(password);
		record.setPassword(password);
		rs = userDao.insertSelective(record);
		return rs;
	}
	
	@Override
	public UserEntity query(JSONObject jsonObj){
		String account = jsonObj.getString("account");
		Long userId = jsonObj.getLong("id");
		UserEntity user = new UserEntity();	
		user.setSex(null);
		user.setIsdel(0);

		if (account != null && !account.trim().isEmpty()) {
			user.setUsername(account);
		} else if (userId != null) {
			user.setId(userId);
		} else {
			System.out.println("参数错误!");
			return null;
		}

		try {
			return userDao.selectOne(user);
		} catch (Exception e) {
			return null;
		}

	}
	
	@Override
	public UserEntity queryByAccount(String account){	
		UserEntity user = new UserEntity();	
		user.setSex(null);
		user.setIsdel(0);

		if (account != null && !account.trim().isEmpty()) {
			user.setUsername(account);		
		} else {
			System.out.println("参数错误!");
			return null;
		}

		try {
			return userDao.selectOne(user);
		} catch (Exception e) {
			return null;
		}

	}
	
	@Override
	public List<String> queryAllPerms(Long userId) {
		return userDao.queryAllPerms(userId);
	}

	
}
