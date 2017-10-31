package fish.yu.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fish.yu.entity.User;
import fish.yu.identifyCode.Captcha;
import fish.yu.identifyCode.GifCaptcha;
import fish.yu.shiro.ShiroFilterChainService;

/**
 * 登录模块 访问控制器
 * @author yuliang-ds1
 *
 */
@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private  ShiroFilterChainService   shiroService;
	
	
	//首页访问  /index
	@RequestMapping(value="index")
	public String index(Model model,HttpServletRequest  request) {
		
		User principal = (User)SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user",principal);
		Object redirectUrl = request.getParameter("redirectUrl");
		
		System.out.println("LoginController-redirectUrl-:"+redirectUrl);
		if(redirectUrl!=null){
			model.addAttribute("redirectUrl",redirectUrl);
		}else{
			model.addAttribute("redirectUrl","");
		}
		return "index";
	}
	
	//登录  
	@RequestMapping(value="login")
	public String login() {
		return "login";
	}

	//权限测试用
	@RequestMapping(value="add")
	public String add() {
		return "add";
	}
	
	//未授权跳转的页面
	@RequestMapping(value="403")
	public String noPermissions() {
		return "403";
	}
	
	//更新权限
	@RequestMapping(value="updatePermission")
	@ResponseBody
	public String updatePermission() {
		shiroService.updatePermission();
		return "true";
	}
	
	//踢出用户
	@RequestMapping(value="kickouting")
	@ResponseBody
	public String kickouting() {
		return "kickout";
	}
	
	//被踢出后跳转的页面
	@RequestMapping(value="kickout")
	public String kickout() {
		return "kickout";
	}
	
	/**
	 * ajax登录请求
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="ajaxLogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> submitLogin(HttpServletRequest request,String username, String password,String vcode,Boolean rememberMe,Model model) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		
		if(vcode==null||vcode==""){
			resultMap.put("status", 500);
			resultMap.put("message", "验证码不能为空！");
			return resultMap;
		}
		
		Session session = SecurityUtils.getSubject().getSession();
		//转化成小写字母
		vcode = vcode.toLowerCase();
		String v = (String) session.getAttribute("_code");
		//还可以读取一次后把验证码清空，这样每次登录都必须获取验证码
		//session.removeAttribute("_come");
		System.out.println("vcode:"+vcode+"  v:"+v);
    	if(!vcode.equals(v)){
    		resultMap.put("status", 500);
			resultMap.put("message", "验证码错误！");
			return resultMap;
    	}
		
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
			SecurityUtils.getSubject().login(token);
			User principal = (User)SecurityUtils.getSubject().getPrincipal();
			model.addAttribute("user",principal);
			model.addAttribute("username", username);
			resultMap.put("status", 200);
			
			//没有权限的上一次的请求URL
			//没有认证的用户请求需要认证的链接时，shiro在跳转前会把跳转过来的页面链接保存到session的attribute中，key的值叫shiroSavedRequest，我们可以能过WebUtils类拿到。
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			System.out.println("savedRequest:"+savedRequest);
			if(savedRequest!=null&&savedRequest.getRequestURI()!=null){
				System.out.println(" LoginController:-savedRequest.getRequestURI()");
				resultMap.put("redirect", savedRequest.getRequestURI());	
			}else{
				resultMap.put("redirect", "/index");
			}
			resultMap.put("message", "登录成功");

		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 500);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value="logout",method =RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> logout(){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//退出
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 获取验证码（Gif版本）
	 * @param response
	 */
	@RequestMapping(value="getGifCode",method=RequestMethod.GET)
	public void getGifCode(HttpServletResponse response,HttpServletRequest request){
		try {
			response.setHeader("Pragma", "No-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        response.setContentType("image/gif");  
	        /**
	         * gif格式动画验证码
	         * 宽，高，位数。
	         */
	        Captcha captcha = new GifCaptcha(146,33,4);
	        //输出
	        captcha.out(response.getOutputStream());
	        HttpSession session = request.getSession(true);  
	        //存入Session
	        session.setAttribute("_code",captcha.text().toLowerCase());  
		} catch (Exception e) {
			System.err.println("获取验证码异常："+e.getMessage());
		}
}

}
