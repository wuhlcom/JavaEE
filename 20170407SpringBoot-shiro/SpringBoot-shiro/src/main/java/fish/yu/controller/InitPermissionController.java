package fish.yu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;

import fish.yu.entity.CustomPage;
import fish.yu.entity.FrontPage;
import fish.yu.entity.MyPermissionInit;
import fish.yu.service.PermissionInitService;
import fish.yu.service.PermissionService;
import fish.yu.shiro.ShiroFilterChainService;
import fish.yu.util.StringConstant;

/**
 * 权限管理Controller
 * @author yuliang-ds1
 *
 */
@Controller
@RequestMapping(value="initPermission")
public class InitPermissionController extends BaseController {
	
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	PermissionInitService permissionInitService;
	
	
	@Autowired
	private  ShiroFilterChainService   shiroService;
	
	
	//跳转到role管理页面
	@RequestMapping(value="initPermissionPage")
	public String initPermission() {
		return "initPermission/initPermission";
	}
	
	//获取权限分页对象                               getInitPermissionListWithPager
	@RequestMapping(value="getInitPermissionListWithPager")
	@ResponseBody
	public String getInitPermissionListWithPager(FrontPage<MyPermissionInit> page) {
		
		System.out.println("getInitPermissionListWithPager");
		Page<MyPermissionInit> pageList = permissionInitService.queryAllPermissions(page);
		CustomPage<MyPermissionInit> customPage = new CustomPage<MyPermissionInit>(pageList);
		return JSON.toJSONString(customPage);
	}
	
	//跳转到role管理页面
	@RequestMapping(value="edit")
	@ResponseBody
	public String updateInitPermission(MyPermissionInit myInitPermission,String oper) {
		
		if(StringConstant.DATA_ADD.equalsIgnoreCase(oper)){
			//添加数据库数据
			System.out.println("INSERT-INITPERMISSION START: "+myInitPermission);
			myInitPermission.setId(null);
			try{
				boolean insert = permissionInitService.insert(myInitPermission);
				System.out.println("INSERT-INITPERMISSION END:"+insert);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(StringConstant.DATA_EDIT.equalsIgnoreCase(oper)){
			//更新初始化权限数据
			permissionInitService.updateById(myInitPermission);
		}else  if(StringConstant.DATA_DELETE.equalsIgnoreCase(oper)){
			//删除初始化权限数据
			permissionInitService.deleteById(myInitPermission.getId());
			
		}
			
		//更新成功之后保存数据
		shiroService.updatePermission();
		return JSON.toJSONString("Update InitPermission Success!");
	}
	
	
	
}
