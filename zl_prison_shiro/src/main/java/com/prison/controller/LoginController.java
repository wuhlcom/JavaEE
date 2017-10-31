/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月19日 上午11:04:38 * 
*/
package com.prison.controller;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.prison.common.error.R;
import com.prison.common.util.shiro.ShiroUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Api(value = "API - Login", description = "登录接口详情")
@RestController
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

//	@Autowired
//	private Producer producer;

	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response) throws ServletException, IOException {
//		response.setHeader("Cache-Control", "no-store, no-cache");
//		response.setContentType("image/jpeg");
//
//		// 生成文字验证码
//		String text = producer.createText();
//		// 生成图片验证码
//		BufferedImage image = producer.createImage(text);
//		// 保存到shiro session
//		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
//
//		ServletOutputStream out = response.getOutputStream();
//		ImageIO.write(image, "jpg", out);
//		out.flush();
		
	}

	@ApiOperation(value = "登录接口", notes = "此接口描述xxxxxxxxxxxxx<br/>xxxxxxx<br>值得庆幸的是这儿支持html标签<hr/>", response = R.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "用户名", required = false, dataType = "string", paramType = "query", defaultValue = "admin"),
			@ApiImplicitParam(name = "password", value = "page", required = false, dataType = "Integer", paramType = "query", defaultValue = "123456"), })

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful — 请求已完成"),
			@ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"), @ApiResponse(code = 401, message = "未授权客户机访问数据"),
			@ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"), @ApiResponse(code = 500, message = "服务器不能完成请求") })
	/**
	 * 登录
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public R login(String username, String password) throws IOException {			
		try {
			Subject subject = ShiroUtils.getSubject();
			// sha256加密
			password = new Sha256Hash(password).toHex();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
		} catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			return R.error(e.getMessage());
		} catch (LockedAccountException e) {
			return R.error(e.getMessage());
		} catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}

		return R.ok();
	}

	
	
	/**
	 * 登录
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public R login(String username, String password, String captcha) throws IOException {
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if (!captcha.equalsIgnoreCase(kaptcha)) {
			return R.error("验证码不正确");
		}

		try {
			Subject subject = ShiroUtils.getSubject();
			// sha256加密
			password = new Sha256Hash(password).toHex();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
		} catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			return R.error(e.getMessage());
		} catch (LockedAccountException e) {
			return R.error(e.getMessage());
		} catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}

		return R.ok();
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}

}
