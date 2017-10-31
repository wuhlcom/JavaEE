package fish.yu.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;

import fish.yu.entity.CustomPage;
import fish.yu.entity.FrontPage;
import fish.yu.entity.RoleVO;
import fish.yu.entity.UserVO;
import fish.yu.service.UserService;

/**
 * 前端控制器
 * @author yuliang-ds1
 *
 */
@Controller
@RequestMapping(value = "user")
public class UserController  extends BaseController{

	@Resource(name="userService")
	UserService userService;

	
	// 跳转到用户管理模块管理页面
	@RequestMapping(value = "queryAllUserPage")
	public String queryAllUserPage() {
		return "user/queryAllUsers";
	}

	/**
	 * 查询所有用户信息
	 * @param frontPage
	 * @return
	 */
	@RequestMapping(value = "queryAllUsers")
	@ResponseBody
	public String queryAllUsers(FrontPage<UserVO> frontPage) {
	    System.out.println("Controller-queryAllUsers-userService:"+userService);
	    boolean aopProxy = AopUtils.isAopProxy(userService);
	    boolean cglibProxy = AopUtils.isCglibProxy(userService);
	    boolean jdkDynamicProxy = AopUtils.isJdkDynamicProxy(userService);
	   
	    System.out.println("aopProxy:"+aopProxy);
	    System.out.println("cglibProxy:"+cglibProxy);
	    System.out.println("jdkDynamicProxy:"+jdkDynamicProxy);
	
		Page<UserVO> pageList = userService.queryAllUsers(frontPage);
		CustomPage<UserVO> customPage = new CustomPage<UserVO>(pageList);
		return JSON.toJSONString(customPage);
	}
	
	
	//更新角色
	@RequestMapping(value="/edit")
	@ResponseBody
	public String editUserVO(UserVO userVO,String oper) {
		
		userService.editUserInfo(userVO,oper);
	
		return JSON.toJSONString("Update RoleInfo Success!");
	}
	
	
	
}
