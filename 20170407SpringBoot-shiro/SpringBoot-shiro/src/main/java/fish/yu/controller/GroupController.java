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
import fish.yu.service.GroupService;

/**
 * 管理Controller
 * @author yuliang-ds1
 *
 */
@Controller
@RequestMapping(value="group")
public class GroupController  extends BaseController {
	
	@Autowired
	GroupService groupService;
	
	//跳转到Group管理页面
	@RequestMapping(value="groupPage")
	public String Group() {
		return "group/group";
	}
	
	//获取角色分页对象
	@RequestMapping(value="getGroupListWithPager")
	@ResponseBody
	public String getGroupListWithPager(FrontPage<GroupVO> page) {
		Page<GroupVO> pageList = groupService.queryAllGroups(page);
		CustomPage<GroupVO> customPage = new CustomPage<GroupVO>(pageList);
		return JSON.toJSONString(customPage);
	}
	
	
	
	//更新用户组信息
	@RequestMapping(value="/edit")
	@ResponseBody
	public String editGroup(GroupVO groupVO,String oper) {
		
		groupService.editGroupInfo(groupVO,oper);
	
		return JSON.toJSONString("Update GroupInfo Success!");
	}
	
}
