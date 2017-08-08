/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 下午2:45:53 * 
*/
package com.dazk.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dazk.db.dao.MenuMapper;
import com.dazk.db.dao.RoleMapper;
import com.dazk.db.dao.RolePermissionMapper;
import com.dazk.db.model.Menu;
import com.dazk.db.model.Role;
import com.dazk.db.model.RolePermission;
import com.dazk.service.RolePermissionService;
import com.dazk.service.RoleService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class RolePermissionImpl implements RolePermissionService {

	@Autowired
	private RolePermissionMapper rolePermiMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Resource
	private RoleService roleService;

	@Override
	public int addRolePermi(JSONObject obj) {
		RolePermission record = new RolePermission();
		Long role_id = obj.getLong("role_id");

		// 找不到角色就不添加
		Role roleRecord = new Role();
		roleRecord.setId(role_id);
		roleRecord.setIsdel(0);
		int exist1 = roleMapper.selectCount(roleRecord);
		if (exist1 > 0) {
			return -2;
		}

		// 找不到菜单不添加
		Long reso_id = obj.getLong("reso_id");
		Menu menuRecord = new Menu();
		menuRecord.setId(reso_id);
		menuRecord.setIsdel(0);
		int exist2 = menuMapper.selectCount(menuRecord);
		if (exist2 > 0) {
			return -2;
		}

		record.setRole_id(role_id);
		record.setReso_id(reso_id);
		record.setDisused(0);
		int exist = rolePermiMapper.selectCount(record);
		if (exist > 0) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);
		return rolePermiMapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int delRoleMenu(JSONObject obj) {
		RolePermission record = new RolePermission();
		Long role_id = obj.getLong("role_id");
		String reso_ids = obj.getString("reso_ids");
		if (reso_ids == null || reso_ids.trim() == "") {
			return this.delRolePermi(role_id);
		} else {
			String[] menuIds = reso_ids.split(",");
			for (String menuId : menuIds) {
				Long meId = Long.parseLong(menuId);
				record.setRole_id(role_id);
				record.setReso_id(meId);
				record.setDisused(1);
				try {
					// 创建example
					Example example = new Example(RolePermission.class);
					// 创建查询条件
					Example.Criteria criteria = example.createCriteria();
					// 设置查询条件 多个andEqualTo串联表示 and条件查询
					criteria.andEqualTo("role_id", role_id).andEqualTo("reso_id", meId).andEqualTo("disused", 0);
					return rolePermiMapper.updateByExampleSelective(record, example);
				} catch (Exception e) {
					return -1;
				}
			}
		}
		return -1;
	}

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

	public int delRolePermi(Long role_id) {
		RolePermission record = new RolePermission();
		record.setRole_id(role_id);
		record.setDisused(1);
		try {
			// 创建example
			Example example = new Example(RolePermission.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("role_id", role_id).andEqualTo("disused", 0);
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

		try {
			// 创建example
			Example example = new Example(RolePermission.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("role_id", role_id).andEqualTo("disused", 0);
			return rolePermiMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}
	}

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
			recordCriteria.andEqualTo("isdel", 0);
			example.and(recordCriteria);
		} else if (type == 1) {
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			recordCriteria.andEqualTo("role_id", search).andEqualTo("isdel", 0);
			example.and(recordCriteria);
		}
		return rolePermiMapper.selectByExample(example);
	}

	@Override
	public int queryRolePermiCount(JSONObject obj) {
		String role_id = obj.getString("role_id");
		RolePermission record = new RolePermission();
		record = JSON.parseObject(obj.toJSONString(), RolePermission.class);

		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}

		Example example = new Example(RolePermission.class);
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		Example.Criteria recordCriteria = example.createCriteria();
		recordCriteria.andEqualTo("role_id", role_id).andEqualTo("disused", 0);
		example.and(recordCriteria);
		return rolePermiMapper.selectCountByExample(example);
	}

	@Override
	@Transactional()
	public int addRoleMenu(JSONObject obj) {
		String roleName = obj.getString("name");

		int rs = roleService.addRole(obj);
		if (rs == -2) {
			return -2; // 角色添加失败
		}
		// 获取角id
		Example example = new Example(Role.class);
		// 创建查询条件
		Example.Criteria roleCriteria = example.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		roleCriteria.andEqualTo("name", roleName).andEqualTo("isdel", 0);
		example.and(roleCriteria);
		List<Role> role = roleMapper.selectByExample(example);

		Long role_id = role.get(0).getId();
		String menuIds = obj.getString("menus");

		// 分割menu id成数组
		String[] menuIdArray = menuIds.split(",");
		Menu menuRecord = new Menu();
		int rsInsert = 0;
		for (String menuId : menuIdArray) {
			Long meId = Long.valueOf(menuId);
			menuRecord.setId(meId);
			menuRecord.setIsdel(0);
			int exist2 = menuMapper.selectCount(menuRecord);
			if (exist2 == 0) {
				// 找不到菜单id将会回滚角色的创建,使用异常来实现事务功能
				throw new NullPointerException("菜单不存在为空");
				// return -2; // 找不到菜单id不添加
			}

			RolePermission record = new RolePermission();
			record.setRole_id(role_id);
			record.setReso_id(meId);
			record.setDisused(0);

			int exist = rolePermiMapper.selectCount(record);
			if (exist > 0) {
				continue; // 角色菜单已添加
			}
			rsInsert = rolePermiMapper.insertSelective(record);
			if (rsInsert == 0) {
				throw new NullPointerException("插入菜单数据过程有错误");
			}
		}
		return rsInsert;
	}

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

		// 先将角色原有菜单权限删除再重新添加菜单权限
		int del = this.delRolePermi(roleId);
		String menuIds = obj.getString("menus");
		// 分割menu id成数组
		String[] menuIdArray = menuIds.split(",");

		// 再重新添加菜单权限
		Menu menuRecord = new Menu();
		for (String menuId : menuIdArray) {
			Long meId = Long.valueOf(menuId);
			menuRecord.setId(meId);
			menuRecord.setIsdel(0);
			int exist2 = menuMapper.selectCount(menuRecord);
			if (exist2 == 0) {
				// 找不到菜单id将会回滚角色的创建,使用异常来实现事务功能
				throw new NullPointerException("菜单不存在为空");
				// return -2; // 找不到菜单id不添加
			}

			RolePermission record = new RolePermission();
			record.setRole_id(roleId);
			record.setReso_id(meId);
			record.setDisused(0);

			exist = rolePermiMapper.selectCount(record);
			if (exist > 0) {
				continue; // 角色菜单已添加
			}
			rsInsert = rolePermiMapper.insertSelective(record);
			if (rsInsert == 0) {
				throw new NullPointerException("插入菜单数据过程有错误");
			}
		}
		return rsInsert;
	}

	@Override
	public List queryRoleMenu(JSONObject obj) {
		Long role_id = obj.getLong("role_id");
		List parentLs = new ArrayList();
		JSONObject parentJson = new JSONObject();
		JSONObject subJson = new JSONObject();

		// 找不到角色
		Role roleRecord = new Role();
		roleRecord.setId(role_id);
		roleRecord.setIsdel(0);
		int exist1 = roleMapper.selectCount(roleRecord);
		if (exist1 > 1) {
			return null;
		}

		if (exist1 == 0) {
			return null;
		}

		// 查询角色菜单
		Example recordExample = new Example(RolePermission.class);
		// 创建查询条件
		Example.Criteria recordCriteria = recordExample.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		recordCriteria.andEqualTo("role_id", role_id).andEqualTo("disused", 0);
		recordExample.and(recordCriteria);
		List<RolePermission> roleMenus = rolePermiMapper.selectByExample(recordExample);

		for (RolePermission roleMenu : roleMenus) {

			Long menuId = roleMenu.getReso_id();

			Menu menuRecord = new Menu();
			menuRecord.setId(menuId);
			menuRecord.setIsdel(0);
			int exist = roleMapper.selectCount(roleRecord);
			if (exist == 0) {
				// 菜单不存在
				continue;
			} else {
				// 菜单存在
				Example menuExample = new Example(Menu.class);
				Example.Criteria menuCriteria = menuExample.createCriteria();
				menuCriteria.andEqualTo("id", menuId).andEqualTo("isdel", 0);
				menuExample.and(menuCriteria);
				List<Menu> menus = menuMapper.selectByExample(menuExample);

				for (Menu menu : menus) {
					// 重新初始化防止出$ref循环重复引用
					parentJson = new JSONObject();
					Long pid = menu.getParent_id();
					// 父ID为0的为顶级菜单
					if (pid == 0) {
						Long meId = menu.getId();
						parentJson.put("id", menu.getId());
						parentJson.put("name", menu.getName());
						parentJson.put("uri", menu.getUri());
						if (menu.getFront_router() != null) {
							parentJson.put("front_router", menu.getFront_router());
						}
						parentJson.put("parent_id", pid);
						parentJson.put("is_menu", menu.getIs_menu());

						menuExample.clear();

						// 取出父id为0的菜单的子菜单
						Example.Criteria menuCriteria2 = menuExample.createCriteria();
						menuCriteria2.andEqualTo("parent_id", meId).andEqualTo("isdel", 0);
						menuExample.and(menuCriteria2);
						List<Menu> subMenus = menuMapper.selectByExample(menuExample);

						// 解析子菜单
						if (!subMenus.isEmpty()) {
							List subLs = new ArrayList();
							for (Menu subMenu : subMenus) {
								subJson = new JSONObject();
								subJson.put("id", subMenu.getId());
								subJson.put("name", subMenu.getName());
								System.out.println(subMenu.getName());
								subJson.put("uri", subMenu.getUri());
								if (menu.getFront_router() != null) {
									subJson.put("front_router", subMenu.getFront_router());
								}
								subJson.put("parent_id", subMenu.getParent_id());
								subJson.put("is_menu", subMenu.getIs_menu());
								subLs.add(subJson);
							}
							// JSON.toJSONString(subLs,
							// SerializerFeature.DisableCircularReferenceDetect);
							// 插入子菜单信息
							parentJson.put("children", subLs);

						}
						// 父菜单id为0的直接保存到list
						parentLs.add(parentJson);
					}
				}
			}
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
		Pattern pattern = Pattern.compile(".*(\\/.+)\\/.+");
		// 通配符中也要加入转移字符 (.+?)代表要查找的内容
		Matcher matcher = pattern.matcher(uri);
		matcher.matches();
		String controller = matcher.group(1);
		System.out.println(controller);

		Example menuExample = new Example(Menu.class);
		Example.Criteria menuCriteria = menuExample.createCriteria();
		menuCriteria.andEqualTo("uri", controller).andEqualTo("isdel", 0);
		menuExample.and(menuCriteria);
		List<Menu> menuLs = menuMapper.selectByExample(menuExample);
		
		System.out.println(menuLs);
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
        //打印角色菜单
		System.out.println(reso_ids);
		if (reso_ids.isEmpty()) {
			return false;
		}
	
		Boolean rs = reso_ids.contains(menuLs.get(0).getId());	
		return rs;
	}

}
