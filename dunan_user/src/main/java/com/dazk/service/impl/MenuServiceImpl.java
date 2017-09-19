package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.dazk.common.util.GlobalParamsUtil;
import com.dazk.common.util.PubUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.service.MenuService;
import com.dazk.service.RolePermissionService;
import com.dazk.service.RoleService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	public final static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuMapper menuMapper;

	@Resource
	private RolePermissionService rolePermiService;

	@Resource
	private RoleService roleService;

	@Transactional
	@Override
	// 新增菜单，新增会给当前用户所属角色配置菜单权限并给超级管理员配置菜单权限（code为GlobalParamsUtil.ADMIN_CODE是超级管理员）
	public int addMenu(JSONObject obj) {
		logger.debug("begin add menu........");	
		int result = 0;
		int codeNum = 11;
		Long roleId = obj.getLong("role_id");
		String menuName = obj.getString("name");
		String uri = obj.getString("uri");
		String front_router = obj.getString("front_router");
		Menu record = new Menu();
		record.setIs_menu(null);
		record.setParent_id(null);
		record.setName(menuName);
		record.setIsdel(0);
		int exist = menuMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}
		
		record.setIs_menu(null);
		record.setParent_id(null);
		record.setName(null);
		record.setUri(uri);
		exist = menuMapper.selectCount(record);
		if (exist > 0) {
			return -2;
		}
				
		record.setUri(null);
		record.setFront_router(front_router);
		exist = menuMapper.selectCount(record);
		if (exist > 0) {
			return -3;
		}

		String code = PubUtil.genCode(codeNum);		
		record.setFront_router(null);
		record.setName(null);
		record.setIsdel(null);
		record.setCode(code);
		exist = menuMapper.selectCount(record);
		int i = 0;
		while (exist > 0) {
			code = PubUtil.genCode(codeNum);
			record.setCode(code);
			exist = menuMapper.selectCount(record);
			if (i == 100) {
				return -1;
			}
			i++;
		}

		record = JSON.parseObject(obj.toJSONString(), Menu.class);
		Integer menuType = obj.getInteger("is_menu");
		if (menuType == null) {
			menuType = 1;
		}
		record.setIs_menu(menuType);
		record.setCode(code);
		record.setCreated_at(System.currentTimeMillis() / 1000);
		result = menuMapper.insertSelective(record);

		JSONObject rolePermiParams = new JSONObject();
		JSONObject queryMenuPamams = new JSONObject();
		JSONObject queryRolePamams = new JSONObject();

		// 菜单如果添加成功,给菜单分配基础权限，即给菜单所属角色和超级管理员角色分配权限
		if (result != 0) {
			queryMenuPamams.put("name", menuName);
			Menu menu = this.queryMenuOne(queryMenuPamams);

			if (menu != null) {
				Long menuId = menu.getId();

				// 给当前用户所在角色添加菜单权限
				rolePermiParams.put("role_id", roleId);
				rolePermiParams.put("reso_id", menuId);
				int rsRolePermi = rolePermiService.addRolePermi(rolePermiParams);

				// 查询超级管理员角色是否存在
				queryRolePamams.put("code", GlobalParamsUtil.ADMIN_CODE);
				Role superRole = roleService.queryRoleOne(queryRolePamams);

				// 给超级管理员添加新菜单的权限
				if (superRole != null) {
					Long superRoleId = superRole.getId();

					rolePermiParams.put("role_id", superRoleId);
					rolePermiParams.put("reso_id", menuId);
					rsRolePermi = rolePermiService.addRolePermi(rolePermiParams);
				}
			}
		}

		return result;
	}

	@Override
	public int delMenu(JSONObject obj) {
		Menu record = new Menu();
		Long id = obj.getLong("id");
		record.setIs_menu(null);
		record.setParent_id(null);
		record.setId(id);
		record.setIsdel(1);
		

		// 查询当前菜单是否有子菜单
		record.setParent_id(id);
		record.setId(null);
		int exist = menuMapper.selectCount(record);
		if (exist != 0) {
			return -2;
		}

		try {
			// 创建example
			Example example = new Example(Menu.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("id", id).andEqualTo("isdel", 0);
			return menuMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int updateMenu(JSONObject obj) {
		Menu record = new Menu();
		Long id = obj.getLong("id");
		record.setIs_menu(null);
		record.setParent_id(null);
		record.setId(id);
		record.setIsdel(0);
		int exist = menuMapper.selectCount(record);
		if (exist > 1) {
			return -2;
		}

		if (exist == 0) {
			return 0;
		}

		record = JSON.parseObject(obj.toJSONString(), Menu.class);
		try {
			// 创建example
			Example example = new Example(Menu.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("id", id).andEqualTo("isdel", 0);
			record.setIs_menu(obj.getInteger("is_menu"));
			return menuMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}

	}

	private Menu queryMenuOne(JSONObject obj) {
		String menuName = obj.getString("name");
		Long menuId = obj.getLong("id");
		Menu menuParams = new Menu();
		menuParams.setIs_menu(null);
		menuParams.setParent_id(null);
		if (menuName != null && menuName.trim() != "") {

			menuParams.setName(menuName);
		} else if (menuName != null && menuName.trim() != "") {
			menuParams.setId(menuId);
		} else {
			System.out.println("参数错误");
			return null;
		}
		try {
			return menuMapper.selectOne(menuParams);
		} catch (Exception e) {
			logger.debug("菜单名重复或找不到菜单!menu name:" + menuName);
			return null;
		}

	}

	@Override
	public List<Menu> queryMenu(JSONObject obj) {
		JSONObject parentJs = new JSONObject();
		JSONObject childrenJs = new JSONObject();
		// 返回非Menu类型数据
		List rs = new ArrayList<>();
		List rsSubMenu = new ArrayList<>();

		Integer type = obj.getInteger("type");
		String search = obj.getString("search");

		Menu record = new Menu();
		record = JSON.parseObject(obj.toJSONString(), Menu.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}

		Example example = new Example(Menu.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		Example.Criteria subCriteria = example.createCriteria();

		// 按名称过滤或完全不过滤显示所有菜单

		String menuTypeStr = obj.getString("menuType");
		Integer menuType = obj.getInteger("menuType");
		if (type == 0) {
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0);
			} else {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType);
			}		
			return menuMapper.selectByExample(example);
		} else if (type == 1) {
			// 只查询父级菜单
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("parent_id", 0);
			} else {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andEqualTo("parent_id", 0);
			}
			return menuMapper.selectByExample(example);
		} else if (type == 2) {
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0).andLike("name", "%" + search + "%");
			} else {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andLike("name",
						"%" + search + "%");
			}	
			return menuMapper.selectByExample(example);
		} else if (type == 3) {
			// 查询子菜单
			// 查询包涵指定id的菜单及子菜单
			// 查父菜单
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("id", search).andEqualTo("isdel", 0);
			} else {
				recordCriteria.andEqualTo("id", search).andEqualTo("is_menu", menuType).andEqualTo("isdel", 0);
			}		
			List<Menu> parentMenu = menuMapper.selectByExample(example);

			// 查子菜单
			example.clear();

			if (RegexUtil.isNull(menuTypeStr)) {
				subCriteria.andEqualTo("parent_id", search).andEqualTo("isdel", 0);
			} else {
				subCriteria.andEqualTo("parent_id", search).andEqualTo("is_menu", menuType).andEqualTo("isdel", 0);
			}
		
			List<Menu> subMenu = menuMapper.selectByExample(example);

			// 解析主菜单
			if (parentMenu.isEmpty()) {
				return parentMenu;
			} else {
				for (Menu menu : parentMenu) {
					parentJs.put("id", menu.getId());
					parentJs.put("name", menu.getName());
					parentJs.put("uri", menu.getUri());
					if (menu.getFront_router() != null) {
						parentJs.put("front_router", menu.getFront_router());
					}
					parentJs.put("parent_id", menu.getParent_id());
					parentJs.put("is_menu", menu.getIs_menu());
				}
			}

			// 解析子菜单
			for (Menu menu : subMenu) {
				childrenJs.put("id", menu.getId());
				childrenJs.put("name", menu.getName());
				childrenJs.put("uri", menu.getUri());
				if (menu.getFront_router() != null) {
					childrenJs.put("front_router", menu.getFront_router());
				}
				childrenJs.put("parent_id", menu.getParent_id());
				childrenJs.put("is_menu", menu.getIs_menu());
				rsSubMenu.add(childrenJs);
			}
			// 插入子菜单信息
			parentJs.put("children", rsSubMenu);
			rs.add(parentJs);
			return rs;
		}
		return rs;
	}

	@Override
	public int queryMenuCount(JSONObject obj) {
		Integer type = obj.getInteger("type");
		String search = obj.getString("search");
		String menuTypeStr = obj.getString("menuType");
		Integer menuType = obj.getInteger("menuType");
		Example example = new Example(Menu.class);
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0);
			} else {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType);
			}	
		} else if (type == 1) {
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("parent_id", 0);
			} else {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andEqualTo("parent_id", 0);
			}
		} else if (type == 2) {
			recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andLike("name", "%" + search + "%");

			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0).andLike("name", "%" + search + "%");
			} else {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andLike("name",
						"%" + search + "%");
			}
		}
		return menuMapper.selectCountByExample(example);
	}

}
