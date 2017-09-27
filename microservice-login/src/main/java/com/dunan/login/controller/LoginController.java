package com.dunan.login.controller;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.dunan.login.bean.User;
import com.dunan.login.service.UserService;
import com.dunan.login.tools.JwtHelper;
import com.dunan.login.tools.MD5Util;

import io.jsonwebtoken.Claims;



@CrossOrigin(origins = "*",maxAge = 3600, methods = {RequestMethod.POST })
@RestController
@RequestMapping(value="/entry")
public class LoginController {
	
	//@Autowired
	private static RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private ValueOperations<String, Object> valueOperations;
	
	@Autowired
	private HashOperations<String, String, Object> hashOperations;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/dologin", method = {RequestMethod.POST })
	public Map<String, Object> index(HttpServletRequest request, HttpServletResponse response, @RequestBody Object requestBody) {
		//String username = request.getParameter("username");
		//String password = request.getParameter("password");
		//JSONObject jsonObj=JSONObject.fromObject(requestBody);
		//System.out.println(requestBody.toString());
		Map<String, Object> backjson = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(requestBody));
			//System.out.println(jsonObject);
			String username = jsonObject.getString("username");
			String password = jsonObject.getString("password");
			password = new String(decode(password));
			password = MD5Util.string2MD5(password);
			//User user = userService.findByUsername(username.trim());
			//User user = userService.findByUsernameAndDisused(username.trim(), 1);
			User user = userService.findByUsernameAndIsdel(username.trim(), 0);
			
			
			
			//System.out.println(user);
			boolean iscorrect = password.trim().equals(user.getPassword().trim());
			//System.out.println(iscorrect);
	
			if (iscorrect) {
				String name = user.getUsername();
				String userId = String.valueOf(user.getId());
				String role = String.valueOf(user.getRoleId());
				sendLog(userId, "login");
				String token = JwtHelper.createJWT(name, userId, role, "www.dunan.com", "dunan", -1, "dunanSecurity");
				try {
					String key = MD5Util.string2MD5(token);
					this.hashOperations.put("loginhash", key, token);
					String url = "http://118.31.102.18:8012/role/queryRoleMenu";
					JSONObject postData = new JSONObject();
//					postData.put("token", token);
//					System.out.println(postData);					
//					JSONObject menus = restTemplate.postForEntity(url, postData, JSONObject.class).getBody();
					
					HttpHeaders headers = new HttpHeaders();
					headers.add("token", token);
					HttpEntity<String> entity = new HttpEntity<String>(postData.toJSONString(), headers);
					String menus = restTemplate.postForObject(url, entity, String.class);
					JSONObject menuObject = JSONObject.parseObject(menus);
					//System.out.println(menuObject.getString("result"));
					
					backjson.put("menus", menuObject.getString("result"));
					backjson.put("username", user.getUsername());
					backjson.put("status", "1");
					backjson.put("token", token);
					return backjson;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					backjson.put("status", "0");
					return backjson;
				}
			} else {
				backjson.put("status", "0");
				return backjson;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			backjson.put("status", "0");
			return backjson;
		}
		
	}
	
	@RequestMapping(value="/islogin")
	public Map<String, Object> islogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Object requestBody){
		
		JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(requestBody));
		String token = jsonObject.getString("token");
		//String token = request.getParameter("token");
		Map<String, Object> backjson = new HashMap<String, Object>();
		
		try {
			String key = MD5Util.string2MD5(token);
			//Boolean hasKey = this.redisTemplate.hasKey("loginhash");
			String jwtString = String.valueOf(this.hashOperations.get("loginhash", key));
			boolean iscorrect = token.trim().equals(jwtString.trim());
			if (iscorrect) {
				Claims claims = JwtHelper.parseJWT(token, "dunanSecurity");
				String username = String.valueOf(claims.get("unique_name"));
				String userid = String.valueOf(claims.get("userid"));
				String role = String.valueOf(claims.get("role"));
				
				//System.out.println(claims);
				backjson.put("name", username);
				backjson.put("userid", userid);
				backjson.put("role", role);
				backjson.put("status", 1);
			} else {
				backjson.put("status", 0);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			backjson.put("status", 0);
		}
		return backjson;
	}
	
	@RequestMapping(value="/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response, @RequestBody Object requestBody){
		try {
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(requestBody));
			String token = jsonObject.getString("token");
			String key = MD5Util.string2MD5(token);
			String jwtString = String.valueOf(this.hashOperations.get("loginhash", key));
			boolean iscorrect = token.trim().equals(jwtString.trim());
			System.out.println(jwtString.trim());
			System.out.println(token);
			if (iscorrect) {
				Claims claims = JwtHelper.parseJWT(token, "dunanSecurity");
				String userid = String.valueOf(claims.get("userid"));
				//this.hashOperations.delete("loginhash", key);
				sendLog(userid, "exit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendLog(String userid, String operation) {
		String url = "http://118.31.102.18:8084/systemlog/add";
		JSONObject postData = new JSONObject();
		postData.put("EmployId", userid);
		postData.put("operation", operation);
		System.out.println(postData);
		HttpEntity<String> entity = new HttpEntity<String>(postData.toJSONString());
		restTemplate.postForObject(url, entity, String.class);
	}
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request){
		User user = userService.findByUsernameAndDisused("zhangsan1", 1);
		System.out.println(user.getUsername()+"====="+user.getDisused());
		try {
			String token = request.getParameter("token");
			Claims claims = JwtHelper.parseJWT(token, "dunanSecurity");
			System.out.println(claims);
			System.out.println(claims.get("unique_name"));
		} catch (Exception e) {
			System.out.println("faile");
		}
		
		return "String test";
	}
	
	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,
			60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,
			-1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
			38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
			-1, -1 };

	/**
	 * 解密
	 * @param str
	 * @return
	 */
	public static byte[] decode(String str) {
		byte[] data = str.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		int i = 0;
		int b1, b2, b3, b4;

		while (i < len) {
			do {
				b1 = base64DecodeChars[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1) {
				break;
			}

			do {
				b2 = base64DecodeChars[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1) {
				break;
			}
			buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

			do {
				b3 = data[i++];
				if (b3 == 61) {
					return buf.toByteArray();
				}
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1) {
				break;
			}
			buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

			do {
				b4 = data[i++];
				if (b4 == 61) {
					return buf.toByteArray();
				}
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1) {
				break;
			}
			buf.write((int) (((b3 & 0x03) << 6) | b4));
		}
		return buf.toByteArray();
	}
	
}
