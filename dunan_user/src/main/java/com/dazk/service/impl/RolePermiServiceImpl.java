/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 下午2:45:53 * 
*/
package com.dazk.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.MenuMapper;
import com.dazk.db.dao.RoleMapper;
import com.dazk.db.dao.RolePermissionMapper;
import com.dazk.db.dao.RolePermissionMapperMy;
import com.dazk.db.model.Menu;
import com.dazk.db.model.Role;
import com.dazk.db.model.RolePermission;
import com.dazk.db.param.RoleMenuParam;

import com.dazk.service.RolePermissionService;
import com.dazk.service.RoleService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import com.dazk.common.util.*;

@Service
@Transactional
public class RolePermiServiceImpl implements RolePermissionService {

	@Autowired
	private RolePermissionMapper rolePermiMapper;

	@Autowired
	private RolePermissionMapperMy rolePermiMapperMy;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Resource
	private RoleService roleService;

	@Override
	public int addRolePermi(JSONObject obj) {	
		Long role_id = obj.getLong("role_id");

		// 找不到角色就不添加
		Role roleRecord = new Role();
		roleRecord.setId(role_id);
		roleRecord.setIsdel(0);
		int exist1 = roleMapper.selectCount(roleRecord);
		if (exist1 == 0) {
			return -2;
		}

		// 找不到菜单不添加
		Long reso_id = obj.getLong("reso_id");
		Menu menuRecord = new Menu();
		menuRecord.setIs_menu(null);
		menuRecord.setParent_id(null);
		menuRecord.setId(reso_id);
		menuRecord.setIsdel(0);
		int exist2 = menuMapper.selectCount(menuRecord);
		if (exist2 ==0) {
			return -3;
		}

		// 角色权限已存在不添加
		RolePermission record = new RolePermission();
		record.setRole_id(role_id);
		record.setReso_id(reso_id);
		record.setDisused(1);		
		int exist = rolePermiMapper.selectCount(record);
		if (exist > 0) {
			return 1;
		}
	
		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);	
		return rolePermiMapper.insertSelective(record);
	}

	@Override
	@Transactional
	// record.setDisused(1)表示使用
	public int delRoleMenu(JSONObject obj) {
		RolePermission record = new RolePermission();
		Long role_id = obj.getLong("role_id");
		String menus = obj.getString("menus");
		String[] menuIds = menus.split(",");
		for (String menuId : menuIds) {
			Long meId = Long.parseLong(menuId);
			record.setRole_id(role_id);
			record.setReso_id(meId);
			record.setDisused(0);
			try {
				// 创建example
				Example example = new Example(RolePermission.class);
				// 创建查询条件
				Example.Criteria criteria = example.createCriteria();
				// 设置查询条件 多个andEqualTo串联表示 and条件查询
				criteria.andEqualTo("role_id", role_id).andEqualTo("reso_id", meId).andEqualTo("disused", 1);
				return rolePermiMapper.updateByExampleSelective(record, example);
			} catch (Exception e) {
				return -1;
			}
		}
		return 1;
	}

	// record.setDisused(1)表示使用
	// 删除角色的单个菜单
	public int delRolePermi(JSONObject obj) {
		Long role_id = obj.getLong("role_id");
		Long reso_id = obj.getLong("reso_id");
		RolePermission record = new RolePermission();
		record.setRole_id(role_id);
		record.setReso_id(reso_id);
		record.setDisused(1);
		int exist = rolePermiMapper.selectCount(record);
		if (exist >= 1) {
			return 1;
		}

		try {
			// 创建example
			Example example = new Example(RolePermission.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("role_id", role_id).andEqualTo("reso_id", reso_id).andEqualTo("disused", 0);	
			return rolePermiMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	// 删除角色对应所有菜单权限
	public int delRolePermi(Long role_id) {
		RolePermission record = new RolePermission();
		record.setRole_id(role_id);
		record.setDisused(0);
		try {
			// 创建example
			Example example = new Example(RolePermission.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("role_id", role_id).andEqualTo("disused", 1);
			return rolePermiMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int updateRolePermi(JSONObject obj) {
		RolePermission record = new RolePermission();
		Long role_id = obj.getLong("role_id");

		// 找不到角色
		Role roleRecord = new Role();
		roleRecord.setId(role_id);
		roleRecord.setIsdel(0);
		int exist1 = roleMapper.selectCount(roleRecord);
		if (exist1 > 1) {
			return -2;
		}

		if (exist1 == 0) {
			return 0;
		}

		// 找不到菜单
		Long reso_id = obj.getLong("reso_id");
		if (reso_id != null) {
			Menu menuRecord = new Menu();
			menuRecord.setIs_menu(null);
			menuRecord.setParent_id(null);
			menuRecord.setId(reso_id);
			menuRecord.setIsdel(0);
			int exist2 = menuMapper.selectCount(menuRecord);
			if (exist2 > 1) {
				return -2;
			}

			if (exist2 == 0) {
				return 0;
			}
		}

		record.setRole_id(role_id);
		record.setDisused(0);
		int exist = rolePermiMapper.selectCount(record);
		if (exist > 1) {
			return -2;
		}

		if (exist == 0) {
			return 0;
		}

		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);
		try {
			// 创建example
			Example example = new Example(RolePermission.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("role_id", role_id).andEqualTo("disused", 1);
			return rolePermiMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

	// 查询角色菜单权限
	@Override
	public List<RolePermission> queryRolePermi(JSONObject obj) {
		Integer type = obj.getInteger("type");
		String search = obj.getString("search");

		RolePermission record = new RolePermission();
		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}

		Example example = new Example(Role.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			recordCriteria.andEqualTo("disused", 1);		
		} else if (type == 1) {
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			recordCriteria.andEqualTo("role_id", search).andEqualTo("disused", 1);
		}
		return rolePermiMapper.selectByExample(example);
	}

	// 查询角色菜单数量
	@Override
	public int queryRolePermiCount(JSONObject obj) {
		String role_id = obj.getString("role_id");
		Example example = new Example(RolePermission.class);
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		Example.Criteria recordCriteria = example.createCriteria();
		recordCriteria.andEqualTo("role_id", role_id).andEqualTo("disused", 1);	
		return rolePermiMapper.selectCountByExample(example);
	}

	// 添加角色菜单
	@Override
	@Transactional()
	public int addRoleMenu(JSONObject obj) {
		String roleName = obj.getString("name");
		int rsInsert = 0;
		int rs = roleService.addRole(obj);
		if (rs != 1) {
			return rs; // 角色添加失败
		}

		Example example = new Example(Role.class);
		// 创建查询条件
		Example.Criteria roleCriteria = example.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		roleCriteria.andEqualTo("name", roleName).andEqualTo("isdel", 0);	
		List<Role> role = roleMapper.selectByExample(example);

		// 获取角色id
		Long role_id = role.get(0).getId();

		String menuIds = obj.getString("menus");
		// 不添加任何菜单
		if (menuIds == null || menuIds.isEmpty()) {
			return 1;
		} else {
			// 分割menu id成数组
			String[] menuIdArray = menuIds.split(",");

			// 去除重复
			TreeSet<String> menuTreeSet = new TreeSet<String>(Arrays.asList(menuIdArray));
		
			Iterator<String> iterator = menuTreeSet.iterator();
			while (iterator.hasNext()) {
				String menuId = iterator.next();
				Long meId = Long.valueOf(menuId);
				RolePermission record = new RolePermission();
				record.setRole_id(role_id);
				record.setReso_id(meId);
				record.setDisused(1);
				int exist = rolePermiMapper.selectCount(record);
				if (exist > 0) {
					continue; // 角色菜单已添加
				}
				rsInsert = rolePermiMapper.insertSelective(record);
				if (rsInsert == 0) {
					throw new NullPointerException("插入菜单数据过程有错误");
				}
			}
		}

		return rsInsert;
	}

	// 更改角色的菜单和角色信息
	@Override
	@Transactional()
	public int updateRoleMenu(JSONObject obj) {		
		int rsInsert = 0;
		Long roleId = obj.getLong("role_id");
		Role roleRecord = new Role();
		roleRecord.setId(roleId);
		roleRecord.setIsdel(0);
		int exist = roleMapper.selectCount(roleRecord);
		// 角色不存在
		if (exist == 0) {
			return 0;
		}

		// 更新角色信息
		String roleName = obj.getString("name");
		String roleRemark = obj.getString("remark");
		JSONObject roleJson = new JSONObject();
		roleJson.put("id", roleId);
		roleJson.put("name", roleName);
		roleJson.put("remark", roleRemark);
		rsInsert = roleService.updateRole(roleJson);

		//rsInsert:-2数据库中角色名重复了
		//rsInsert:0找不到角色
		//rsInsert:-1出现异常
		if (rsInsert==-2||rsInsert==0||rsInsert==-1){
			return 0;
		}
		String menuIds = obj.getString("menus");
		// menus:""删除所有菜单权限
		if (menuIds.isEmpty()) {
			rsInsert = this.delRolePermi(roleId);
			//此处rsInsert为0表示菜单权限已删除状态
			//此处rsInsert大于等于1表示删除了菜单权限
			if (rsInsert==0||rsInsert>=1){
				rsInsert=1;
			}
		} else {
			// 分割menu id成数组
			String[] menuIdArray = menuIds.split(",");
			// 先将角色原有菜单权限删除再重新添加菜单权限
			int del = this.delRolePermi(roleId);	

			// 去除重复菜单Id
			TreeSet<String> menuTreeSet = new TreeSet<String>(Arrays.asList(menuIdArray));			
			Iterator<String> iterator = menuTreeSet.iterator();
			while (iterator.hasNext()) {
				String menuId = iterator.next();
				Long meId = Long.valueOf(menuId);
				RolePermission record = new RolePermission();
				record.setRole_id(roleId);
				record.setReso_id(meId);
				record.setDisused(1);
				exist = rolePermiMapper.selectCount(record);
				if (exist > 0) {
					continue; // 角色菜单已添加
				}
				rsInsert = rolePermiMapper.insertSelective(record);
				if (rsInsert == 0) {
					throw new RuntimeErrorException(null, "更新角色菜单权限出错");
				}
			}
		}
		return rsInsert;
	}
	
	// 查询角色菜单，即按角色名秋查询菜单分成两级
	@Override
	public List queryRoleMenu(JSONObject obj) {		
		Long role_id = obj.getLong("role_id");
		// 保存返回值
		List <JSONObject> parentLs = new ArrayList<JSONObject>();
		// 保存父子级菜单
		JSONObject parentJson = new JSONObject();	
		// 保存子级菜单
		JSONObject subJson = new JSONObject();	
		// 判断角色是否存在
		Role roleRecord = new Role();
		roleRecord.setId(role_id);
		roleRecord.setIsdel(0);
		int exist1 = roleMapper.selectCount(roleRecord);
		if (exist1 == 0) {
			return null;
		}
			
		// 查询角色菜单
		Example recordExample = new Example(RolePermission.class);
		// 创建查询条件
		Example.Criteria recordCriteria = recordExample.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询,disuerd 1表示使用，0表示不使用		
		recordCriteria.andEqualTo("role_id", role_id).andEqualTo("disused", 1);
		recordExample.and(recordCriteria);
		recordExample.setOrderByClause("id asc");
		List<RolePermission> roleMenus = rolePermiMapper.selectByExample(recordExample);	
	
		// 将菜单分类，有parent_id为0的为topMenu,不为0则为subMenu并将分别保存到List		
		Set<Menu> topMenus = new TreeSet<Menu>();
		Set<Menu> subMenus = new TreeSet<Menu>();	

		int exist = 0;
		for (int i = 0; i < roleMenus.size(); i++) {
			RolePermission roleMenu = roleMenus.get(i);
			// 菜单ID
			Long menuId = roleMenu.getReso_id();
			Menu menuRecord = new Menu();
			menuRecord.setIs_menu(null);
			menuRecord.setParent_id(null);
			menuRecord.setId(menuId);
			menuRecord.setIsdel(0);
			exist = menuMapper.selectCount(menuRecord);
			
			if (exist == 0) {
				// 菜单不存在,跳过
				System.out.println("---找不到菜单---------------:" + menuId);
				continue;
			} else {
				Example menuExample = new Example(Menu.class);
				Example.Criteria menuCriteria = menuExample.createCriteria();
				menuCriteria.andEqualTo("id", menuId).andEqualTo("isdel", 0);
				menuExample.and(menuCriteria);
				List<Menu> menus = menuMapper.selectByExample(menuExample);
				Menu menu = menus.get(0);
				Long pid = menu.getParent_id();
				if (pid == 0) {
					topMenus.add(menu);
				} else {
					subMenus.add(menu);
				}

			}
		}	

		
		// 将topMenu与subMenu组成{"name":xx,id:xx,children:[{subMenu1},{subMenu2}]}格式的json对象
		for (Menu topMenu : topMenus) {
			Long topMenuId = topMenu.getId();
			parentJson = new JSONObject();
			parentJson.put("id", topMenuId);
			parentJson.put("name", topMenu.getName());
			parentJson.put("uri", topMenu.getUri());
			if (topMenu.getFront_router() != null) {
				parentJson.put("front_router", topMenu.getFront_router());
			}
			parentJson.put("parent_id", topMenu.getParent_id());
			parentJson.put("is_menu", topMenu.getIs_menu());			
			Set<Menu> children = new TreeSet<>(new CompareUtil());			
			for (Menu subMenu : subMenus) {
				if (subMenu.getParent_id() == topMenuId) {
					subMenu.setCode(null);		
					subMenu.setLv(null);
					subMenu.setInclude_url(null);
					subMenu.setCreated_at(null);
					subMenu.setInclude_url(null);
					if (topMenu.getFront_router() == null) {
						subMenu.setFront_router("");
					}
					children.add(subMenu);
				}
			}			
			
			parentJson.put("children", children);		
			parentLs.add(parentJson);
		}
		return parentLs;
	}

	/**
	 * 如果请求的url不在角色菜单列表则返回false
	 * 
	 */
	@Override
	public boolean menuAuth(String uri, Long roleId) {
		System.out.println("1-----------menuAuth-----------------");
		System.out.println(uri);
//		Pattern pattern = Pattern.compile(".*(\\/.+)\\/.+");
		// 通配符中也要加入转移字符 (.+?)代表要查找的内容
//		Matcher matcher = pattern.matcher(uri);
//		matcher.matches();
//		String controller = matcher.group(1);
//		System.out.println(controller);

		Example menuExample = new Example(Menu.class);
		Example.Criteria menuCriteria = menuExample.createCriteria();
		menuCriteria.andEqualTo("uri", uri).andEqualTo("isdel", 0);
		menuExample.and(menuCriteria);
		List<Menu> menuLs = menuMapper.selectByExample(menuExample);

		if (menuLs.isEmpty()) {
			return false;
		}

		// 查询角色菜单
		Example recordExample = new Example(RolePermission.class);
		// 创建查询条件
		Example.Criteria recordCriteria = recordExample.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		recordCriteria.andEqualTo("role_id", roleId).andEqualTo("disused", 0);
		recordExample.and(recordCriteria);
		List<RolePermission> roleMenus = rolePermiMapper.selectByExample(recordExample);

		ArrayList<Long> reso_ids = new ArrayList<Long>();
		for (RolePermission rolePermission : roleMenus) {
			reso_ids.add(rolePermission.getReso_id());
		}
		// 打印角色菜单
		System.out.println("------role menus------------------");
		System.out.println(reso_ids);
		if (reso_ids.isEmpty()) {
			return false;
		}
		System.out.println(menuLs.get(0).getId());
		Boolean rs = reso_ids.contains(menuLs.get(0).getId());
		return rs;
	}

	private RoleMenuParam js2RoleMenuParam(JSONObject obj) {
		Long parentUser = obj.getLong("role_id");
		Integer page = obj.getInteger("page");
		Integer listRows = obj.getInteger("listRows");
		RoleMenuParam paramBean = new RoleMenuParam(parentUser, page, listRows);
		return paramBean;
	}

	// 查询角色菜单但未分级,用于确认数据权限
	@Override
	public List<Menu> queryRoleMenus(JSONObject obj) {
		RoleMenuParam paramBean = js2RoleMenuParam(obj);
		return rolePermiMapperMy.queryRoleMenu(paramBean);
	}

	// 查询角色菜单但未分级
	@Override
	public int queryRoleMenusCount(JSONObject obj) {
		RoleMenuParam paramBean = js2RoleMenuParam(obj);
		return rolePermiMapperMy.queryRoleMenuCount(paramBean);
	}
	
}
