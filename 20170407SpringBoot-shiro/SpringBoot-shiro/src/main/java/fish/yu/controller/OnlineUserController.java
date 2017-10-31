package fish.yu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;

import fish.yu.entity.CustomPage;
import fish.yu.entity.FrontPage;
import fish.yu.entity.UserVO;
import fish.yu.service.UserService;

/**
 * 前端控制器
 * @author yuliang-ds1
 *
 */
@Controller
@RequestMapping(value = "onlineUser")
public class OnlineUserController  extends BaseController{

	@Autowired
	UserService userService;

	// 跳转到在线用户模块管理页面
	@RequestMapping(value = "onlineUserPage")
	public String onlineUserPage() {
		return "onlineUser/onlineUser";
	}

	@RequestMapping(value = "onlineUsers")
	@ResponseBody
	public String OnlineUsers(HttpServletRequest request,FrontPage<UserVO> frontPage) {
		Page<UserVO> pageList = userService.getPagePlus(frontPage);
		List<UserVO> records = pageList.getRecords();
		if(CollectionUtils.isNotEmpty(records)){
			for (UserVO userVO : records) {
				String remoteHost = request.getRemoteHost();
				userVO.setHostName(remoteHost);
				System.out.println("remoteHost:"+remoteHost);
			}
			pageList.setRecords(records);
		}
		CustomPage<UserVO> customPage = new CustomPage<UserVO>(pageList);
		return JSON.toJSONString(customPage);
	}
	
	
	
	
}
