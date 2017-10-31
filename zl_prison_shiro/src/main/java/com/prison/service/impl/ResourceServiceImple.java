/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月18日 下午6:20:47 * 
*/
package com.prison.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.prison.db.dao.ResourceDao;
import com.prison.db.entity.ResourceEntity;
import com.prison.service.ResourceService;
import com.prison.service.RoleResourceService;
import com.prison.service.UserService;

@Service
public class ResourceServiceImple implements ResourceService {
	
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleResourceService roleResourceService;

	@Override
	public int add(JSONObject jsonObj) {
		return 0;
	}
	
	@Override
	public Set<String> getUserPermissions(Long userId) {
		List<String> permsList;

		//系统管理员，拥有最高权限
		if(userId == 1){
			List<ResourceEntity> menuList = queryList(new HashMap<>());
			permsList = new ArrayList<>(menuList.size());
			for(ResourceEntity menu : menuList){
				permsList.add(menu.getPremission());
			}
		}else{
			permsList = userService.queryAllPerms(userId);
		}

		//用户权限列表
		Set<String> permsSet = new HashSet<String>();
		for(String perms : permsList){
			if(StringUtils.isBlank(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		return permsSet;
	}
	
	@Override
	public List<ResourceEntity> queryList(Map<String, Object> map) {
		return resourceDao.queryList(map);
	}
	
}
