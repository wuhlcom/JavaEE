package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.dazk.common.util.CompareUtil;
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

		// 菜单uri必须唯一
		record.setIs_menu(null);
		record.setParent_id(null);
		record.setName(null);
		record.setUri(uri);
		exist = menuMapper.selectCount(record);
		if (exist > 0) {
			return -2;
		}

		// 菜单前置路由必须唯一
		record.setUri(null);
		record.setFront_router(front_router);
		exist = menuMapper.selectCount(record);
		if (exist > 0) {
			return -3;
		}

		// 菜单code必须唯一
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
				queryRolePamams.put("role_code", GlobalParamsUtil.ADMIN_CODE);
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
		// 查询当前菜单是否有子菜单
		record.setParent_id(id);
		record.setIsdel(0);
		int exist = menuMapper.selectCount(record);
		if (exist != 0) {
			return -2;
		}

		record.setParent_id(null);
		record.setId(id);
		record.setIsdel(1);
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
		menuParams.setIsdel(0);

		if (menuName != null && !menuName.trim().isEmpty()) {
			menuParams.setName(menuName);
		} else if (menuId != null) {
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

	// 查询所有有效菜单并分组{name:parentMenu,isdel:0 children:[{},{}]}
	public List queryMenus(List<Menu> menus) {
		Map resultMap = new HashMap();
		// 保存返回值
		List<JSONObject> parentLs = new ArrayList<JSONObject>();
		if (menus==null||menus.isEmpty()||menus.size()==0) {
			return parentLs;
		}
		// 保存父子级菜单
		JSONObject parentJson = new JSONObject();

		// 将菜单分类，有parent_id为0的为topMenu,不为0则为subMenu并将分别保存到List
		Set<Menu> topMenus = new TreeSet<Menu>();
		Set<Menu> subMenus = new TreeSet<Menu>();

//		JSONObject menuParams = new JSONObject();
//		menuParams.put("type", 0);
//		List<Menu> menus = this.queryMenu(menuParams);
		for (Menu menu : menus) {
			Long pid = menu.getParent_id();
			if (pid == 0) {
				topMenus.add(menu);
			} else {
				subMenus.add(menu);
			}
		}

		// 将topMenu与subMenu组成{"name":xx,id:xx,children:[{subMenu1},{subMenu2}]}格式的json对象
		for (Menu topMenu : topMenus) {
			Long topMenuId = topMenu.getId();
			parentJson = new JSONObject();
			parentJson.put("id", topMenuId);
			parentJson.put("name", topMenu.getName());
			parentJson.put("uri", topMenu.getUri());
			if (topMenu.getFront_router() == null || topMenu.getFront_router().isEmpty()) {
				parentJson.put("front_router", "");
			} else {
				parentJson.put("front_router", topMenu.getFront_router());
			}
			parentJson.put("parent_id", topMenu.getParent_id());
			parentJson.put("is_menu", topMenu.getIs_menu());
			if (topMenu.getMenuicon() == null || topMenu.getMenuicon().isEmpty()) {
				parentJson.put("menuicon", "");
			} else {
				parentJson.put("menuicon", topMenu.getMenuicon());
			}

			Set<Menu> children = new TreeSet<>(new CompareUtil());
			for (Menu subMenu : subMenus) {
				if (subMenu.getParent_id() == topMenuId) {
					subMenu.setCode(null);
					subMenu.setLv(null);
					subMenu.setInclude_url(null);
					subMenu.setCreated_at(null);
					subMenu.setInclude_url(null);
					if (subMenu.getFront_router() == null || subMenu.getFront_router().isEmpty()) {
						subMenu.setFront_router("");
					}
					if (subMenu.getMenuicon() == null || subMenu.getMenuicon().isEmpty()) {
						subMenu.setMenuicon("");
					}
					children.add(subMenu);
				}
			}

			parentJson.put("children", children);
			parentLs.add(parentJson);
		}
		return parentLs;
	}

	// 查询所有菜单包括已删除
	@Override
	public List<Menu> queryMenuAll() {
		List<Menu> menus = menuMapper.selectAll();
		return menus;
	}

	// 查询类型，取值范围：[0：查询全部，1: 查询所有父级菜单
	// 2 按菜单名从所有菜单中过滤菜单
	// 3：按菜单ID查询]，按ID查询时，查询该菜单下所有下级子菜单
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
		Example.Criteria recordCriteria = example.createCriteria();
		example.setOrderByClause("id asc");
		// 按名称过滤或完全不过滤显示所有菜单
		String menuTypeStr = obj.getString("menuType");
		Integer menuType = obj.getInteger("menuType");
		if (type == 0) {
			// 所有类型菜单
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0);
			} else {
				// 按指定类型查询
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType);
			}		
			return menuMapper.selectByExample(example);					
		} else if (type == 1) {
			// 只查询父级菜单
			// 所有类型菜单
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("parent_id", 0);
			} else {
				// 按指定类型查询
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andEqualTo("parent_id", 0);
			}		
			
			List<Menu> parentMenus = menuMapper.selectByExample(example);
			for (Menu menu : parentMenus) {
				parentJs = new JSONObject();
				parentJs.put("id", menu.getId());
				parentJs.put("name", menu.getName());
				parentJs.put("uri", menu.getUri());
				if (menu.getFront_router() == null || menu.getFront_router().isEmpty()) {
					parentJs.put("front_router", "");
				} else {
					parentJs.put("front_router", menu.getFront_router());
				}
				parentJs.put("parent_id", menu.getParent_id());
				parentJs.put("is_menu", menu.getIs_menu());
				parentJs.put("menuicon", menu.getMenuicon());

				if (menu.getMenuicon() == null || menu.getMenuicon().isEmpty()) {
					parentJs.put("menuicon", "");
				} else {
					parentJs.put("menuicon", menu.getMenuicon());
				}				
				
				Long pid =menu.getId();
				
				Example subExample = new Example(Menu.class);
				Example.Criteria subCriteria = subExample.createCriteria();
				if (RegexUtil.isNull(menuTypeStr)) {
					subCriteria.andEqualTo("isdel", 0).andEqualTo("parent_id", pid);
				} else {
					// 按指定类型查询
					subCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andEqualTo("parent_id", pid);
				}
				subExample.setOrderByClause("id asc");				
				//查询子菜单
				List<Menu> subMenus = menuMapper.selectByExample(subExample);					
				Set<Menu> children = new TreeSet<>(new CompareUtil());
				
				//处理子菜单中不需要返回的列值
				for (Menu subMenu : subMenus) {					
						subMenu.setCode(null);
						subMenu.setLv(null);
						subMenu.setInclude_url(null);
						subMenu.setCreated_at(null);
						subMenu.setInclude_url(null);
						if (subMenu.getFront_router() == null || subMenu.getFront_router().isEmpty()) {
							subMenu.setFront_router("");
						}
						if (subMenu.getMenuicon() == null || subMenu.getMenuicon().isEmpty()) {
							subMenu.setMenuicon("");
						}
						children.add(subMenu);			
				}
				parentJs.put("children", children);
				rs.add(parentJs);
			}			
			return rs;			
		} else if (type == 2) {
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0).andLike("name", "%" + search + "%");
			} else {
				// 按指定类型查询
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andLike("name",
						"%" + search + "%");
			}
			return menuMapper.selectByExample(example);
		} else if (type == 3) {
			// 查询包涵指定id的菜单及子菜单
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("id", search).andEqualTo("isdel", 0);
			} else {
				recordCriteria.andEqualTo("id", search).andEqualTo("is_menu", menuType).andEqualTo("isdel", 0);
			}
			List<Menu> parentMenu = menuMapper.selectByExample(example);

			// 查子菜单
			example.clear();

			Example subExample = new Example(Menu.class);
			// 创建查询条件
			Example.Criteria subCriteria = subExample.createCriteria();
			if (RegexUtil.isNull(menuTypeStr)) {
				subCriteria.andEqualTo("parent_id", search).andEqualTo("isdel", 0);
			} else {
				subCriteria.andEqualTo("parent_id", search).andEqualTo("is_menu", menuType).andEqualTo("isdel", 0);
			}

			List<Menu> subMenu = menuMapper.selectByExample(subExample);

			// 解析主菜单
			if (parentMenu.isEmpty()) {
				return parentMenu;
			} else {
				for (Menu menu : parentMenu) {
					parentJs.put("id", menu.getId());
					parentJs.put("name", menu.getName());
					parentJs.put("uri", menu.getUri());
					if (menu.getFront_router() == null || menu.getFront_router().isEmpty()) {
						parentJs.put("front_router", "");
					} else {
						parentJs.put("front_router", menu.getFront_router());
					}
					parentJs.put("parent_id", menu.getParent_id());
					parentJs.put("is_menu", menu.getIs_menu());
					parentJs.put("menuicon", menu.getMenuicon());

					if (menu.getMenuicon() == null || menu.getMenuicon().isEmpty()) {
						parentJs.put("menuicon", "");
					} else {
						parentJs.put("menuicon", menu.getMenuicon());
					}
				}
			}

			// 解析子菜单
			for (Menu menu : subMenu) {
				childrenJs.put("id", menu.getId());
				childrenJs.put("name", menu.getName());
				childrenJs.put("uri", menu.getUri());
				if (menu.getFront_router() == null || menu.getFront_router().isEmpty()) {
					childrenJs.put("front_router", "");
				} else {
					childrenJs.put("front_router", menu.getFront_router());
				}
				childrenJs.put("parent_id", menu.getParent_id());
				childrenJs.put("is_menu", menu.getIs_menu());
				if (menu.getMenuicon() == null || menu.getMenuicon().isEmpty()) {
					childrenJs.put("menuicon", "");
				} else {
					childrenJs.put("menuicon", menu.getMenuicon());
				}
				rsSubMenu.add(childrenJs);
			}
			// 插入子菜单信息
			parentJs.put("children", rsSubMenu);
			rs.add(parentJs);
			return rs;
		}
		return rs;
	}

	// 查询类型，取值范围：[0：查询全部，1: 查询所有父级菜单
	// 2 按菜单名从所有菜单中过滤菜单
	// 3：按菜单ID查询]，按ID查询时，查询该菜单下所有下级子菜单
	public List<Menu> queryMenuOld(JSONObject obj) {
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
		Example.Criteria recordCriteria = example.createCriteria();

		// 按名称过滤或完全不过滤显示所有菜单
		String menuTypeStr = obj.getString("menuType");
		Integer menuType = obj.getInteger("menuType");
		if (type == 0) {
			// 所有类型菜单
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0);
			} else {
				// 按指定类型查询
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType);
			}
			return menuMapper.selectByExample(example);
		} else if (type == 1) {
			// 只查询父级菜单
			// 所有类型菜单
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("parent_id", 0);
			} else {
				// 按指定类型查询
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andEqualTo("parent_id", 0);
			}
			return menuMapper.selectByExample(example);
		} else if (type == 2) {
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("isdel", 0).andLike("name", "%" + search + "%");
			} else {
				// 按指定类型查询
				recordCriteria.andEqualTo("isdel", 0).andEqualTo("is_menu", menuType).andLike("name",
						"%" + search + "%");
			}
			return menuMapper.selectByExample(example);
		} else if (type == 3) {
			// 查询包涵指定id的菜单及子菜单
			if (RegexUtil.isNull(menuTypeStr)) {
				recordCriteria.andEqualTo("id", search).andEqualTo("isdel", 0);
			} else {
				recordCriteria.andEqualTo("id", search).andEqualTo("is_menu", menuType).andEqualTo("isdel", 0);
			}
			List<Menu> parentMenu = menuMapper.selectByExample(example);

			// 查子菜单
			example.clear();

			Example subExample = new Example(Menu.class);
			// 创建查询条件
			Example.Criteria subCriteria = subExample.createCriteria();
			if (RegexUtil.isNull(menuTypeStr)) {
				subCriteria.andEqualTo("parent_id", search).andEqualTo("isdel", 0);
			} else {
				subCriteria.andEqualTo("parent_id", search).andEqualTo("is_menu", menuType).andEqualTo("isdel", 0);
			}

			List<Menu> subMenu = menuMapper.selectByExample(subExample);

			// 解析主菜单
			if (parentMenu.isEmpty()) {
				return parentMenu;
			} else {
				for (Menu menu : parentMenu) {
					parentJs.put("id", menu.getId());
					parentJs.put("name", menu.getName());
					parentJs.put("uri", menu.getUri());
					if (menu.getFront_router() == null || menu.getFront_router().isEmpty()) {
						parentJs.put("front_router", "");
					} else {
						parentJs.put("front_router", menu.getFront_router());
					}
					parentJs.put("parent_id", menu.getParent_id());
					parentJs.put("is_menu", menu.getIs_menu());
					parentJs.put("menuicon", menu.getMenuicon());

					if (menu.getMenuicon() == null || menu.getMenuicon().isEmpty()) {
						parentJs.put("menuicon", "");
					} else {
						parentJs.put("menuicon", menu.getMenuicon());
					}

				}
			}

			// 解析子菜单
			for (Menu menu : subMenu) {
				childrenJs.put("id", menu.getId());
				childrenJs.put("name", menu.getName());
				childrenJs.put("uri", menu.getUri());
				if (menu.getFront_router() == null || menu.getFront_router().isEmpty()) {
					childrenJs.put("front_router", "");
				} else {
					childrenJs.put("front_router", menu.getFront_router());
				}
				childrenJs.put("parent_id", menu.getParent_id());
				childrenJs.put("is_menu", menu.getIs_menu());
				if (menu.getMenuicon() == null || menu.getMenuicon().isEmpty()) {
					childrenJs.put("menuicon", "");
				} else {
					childrenJs.put("menuicon", menu.getMenuicon());
				}
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
