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
import com.prison.db.dao.ResourceDao;
import com.prison.db.dao.UserDao;
import com.prison.db.entity.ResourceEntity;
import com.prison.db.entity.UserEntity;
import com.prison.service.RoleResourceService;
import com.prison.service.UserService;

@Service
public class RoleResourceServiceImpl implements RoleResourceService { 
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public int add(JSONObject jsonObj) {
		return 0;
	}
	
	@Override
	public ResourceEntity query(JSONObject jsonObj){	
		Long resourceId = jsonObj.getLong("id");
		ResourceEntity resource = new ResourceEntity();		
		resource.setId(resourceId);

		if (resourceId != null) {
			resource.setId(resourceId);
		} else {
			System.out.println("参数错误!");
			return null;
		}

		try {
			return resourceDao.selectOne(resource);
		} catch (Exception e) {
			return null;
		}

	}
	
	
	
}
