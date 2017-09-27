package com.dazk.db.dao;

import java.util.List;

import com.dazk.db.model.Menu;
import com.dazk.db.param.RoleMenuParam;


public interface RolePermissionMapperMy {	


	List<Menu> queryRoleMenus(RoleMenuParam paramBean);
	
	int queryRoleMenusCount(RoleMenuParam obj);
}
