/**
 * 添加菜单
 * @param json
 * @return
 * @author wuhongliang
 */
package com.dazk.service;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.Menu;

public interface MenuService {
	public int addMenu(JSONObject json);

	public int delMenu(JSONObject json);

	public List<Menu> queryMenu(JSONObject json);

	public int updateMenu(JSONObject json);
	
	public int queryMenuCount(JSONObject obj);

	List<Menu> queryMenuAll();
}
