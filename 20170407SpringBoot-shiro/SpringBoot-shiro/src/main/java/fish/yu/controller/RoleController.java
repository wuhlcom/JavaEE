package fish.yu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;

import fish.yu.entity.CustomPage;
import fish.yu.entity.FrontPage;
import fish.yu.entity.GroupVO;
import fish.yu.entity.MyPermissionInit;
import fish.yu.entity.RoleVO;
import fish.yu.service.RoleService;
import fish.yu.util.StringConstant;

/**
 * 角色管理Controller
 * @author yuliang-ds1
 *
 */
@Controller
@RequestMapping(value="role")
public class RoleController  extends BaseController{
	
	@Autowired
	RoleService roleService;
	
	//跳转到role管理页面
	@RequestMapping(value="rolePage")
	public String role() {
		return "role/role";
	}
	
	//获取角色分页对象
	@RequestMapping(value="getRoleListWithPager")
	@ResponseBody
	public String getRoleListWithPager(FrontPage<RoleVO> page) {
		Page<RoleVO> pageList = roleService.queryAllRoles(page);
		CustomPage<RoleVO> customPage = new CustomPage<RoleVO>(pageList);
		return JSON.toJSONString(customPage);
	}
	
	
	
	//更新角色
	@RequestMapping(value="/edit")
	@ResponseBody
	public String editRoleVO(RoleVO roleVO,String oper) {
		
		roleService.editRoleInfo(roleVO,oper);
	
		return JSON.toJSONString("Update RoleInfo Success!");
	}
}
