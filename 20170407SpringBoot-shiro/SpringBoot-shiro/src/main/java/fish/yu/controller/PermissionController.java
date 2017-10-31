package fish.yu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;

import fish.yu.entity.CustomPage;
import fish.yu.entity.FrontPage;
import fish.yu.entity.Permission;
import fish.yu.entity.PermissionVO;
import fish.yu.entity.RoleVO;
import fish.yu.service.PermissionInitService;
import fish.yu.service.PermissionService;

/**
 * 权限管理Controller
 * @author yuliang-ds1
 *
 */
@Controller
@RequestMapping(value="permission")
public class PermissionController  extends BaseController{
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	PermissionInitService permissionInitService;
	
	//跳转到权限管理页面
	@RequestMapping(value="permissionPage")
	public String permission() {
		return "permission/permission";
	}
	
	//获取权限分页对象
	@RequestMapping(value="getPermissionListWithPager")
	@ResponseBody
	public String getPermissionVoListWithPager(FrontPage<PermissionVO> page) {
		Page<PermissionVO> pageList = permissionService.queryAllPermissions(page);
		CustomPage<PermissionVO> customPage = new CustomPage<PermissionVO>(pageList);
		return JSON.toJSONString(customPage);
	}
	
	
	//更新角色
	@RequestMapping(value="/edit")
	@ResponseBody
	public String editPermissionInfo(Permission permission,String oper) {
		
		permissionService.editPermissionInfo(permission,oper);
	
		return JSON.toJSONString("Update PermissionInfo Success!");
	}


	
	
}
