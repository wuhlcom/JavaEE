/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月18日 下午6:27:13 * 
*/ 
package com.prison.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.prison.db.dao.RoleDao;
import com.prison.db.entity.RoleEntity;
import com.prison.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService { 
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public int add(JSONObject jsonObj) {		
		return 0;
	}
	
	@Override 
	public RoleEntity queryOne(JSONObject jsonObj){
		return null;
		
	}
}
