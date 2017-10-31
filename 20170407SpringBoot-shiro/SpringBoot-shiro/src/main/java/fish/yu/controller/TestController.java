package fish.yu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;

import fish.yu.entity.BeautifulPictures;
import fish.yu.service.BeautifulPicturesService;


/**
 * 前端控制器
 * @author yuliang-ds1
 *
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
	
	@Autowired
	BeautifulPicturesService beautifulPicturesService;
	
	@RequestMapping("/test1")  //参数：current 要获取当前页数  ；size  获取的条数
	public String view(Model model,Page<BeautifulPictures> page) {
		Page<BeautifulPictures> pageList= beautifulPicturesService.selectPage(page);
		model.addAttribute("user",JSON.toJSONString(pageList.getRecords()));
		return "index";
    }
}
