package com.zhilu.device.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilu.device.util.PubMethod;

@RestController
@RequestMapping("device")
public class TestController {

	final static String TOKEN_URL = "http://119.29.68.198:9080/index.php/Users";

	@GetMapping("/hello")
	public String hello() {
		return "Zhilu Spring-boot hello";
	}

	@GetMapping("get")
	public Object get() {
		return "dev";
	}

	// 使用HttpServletRequest方法来获取uri上的参数
	@PostMapping("post")
	public Object post(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody)
			throws Exception {
		String token = request.getParameter("token");
		System.out.println(token);
		System.out.println("---------requestBody--------------");
		System.out.println(requestBody);

		System.out.println("---------json--------------");
		// 转化为json对象
		com.alibaba.fastjson.JSONObject jobj = JSON.parseObject(requestBody);
		String name = jobj.get("name").toString();
		System.out.println(name);
		String devices = jobj.get("devices").toString();
		System.out.println(devices);

		String[] sourceStrArray =PubMethod.getDevids(requestBody);

		for (int i = 0; i < sourceStrArray.length; i++) {
			System.out.println(sourceStrArray[i]);
		}

		return "post";
	}

	// 使用bean方法来获取uir上的参数
	@PostMapping("postbean")
	public Object postbean(TokenParamModel request) {
		// String token=request.getToken();
		// System.out.println("token is:"+token);
		return "postbean";
	}

	// 获取token
	// {"userid":"1879b24d-2bb7-4237-8eb1-7ef767b6455a"}
	@PostMapping("gettoken")
	public Object getToken() {
		RestTemplate restTemplate = new RestTemplate();
		String url = TOKEN_URL+"/getToken";
		MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
		requestEntity.add("userid", "1879b24d-2bb7-4237-8eb1-7ef767b6455a");
		Object token = restTemplate.postForObject(url, requestEntity, String.class);
		return token;
	}

	@GetMapping("checktoken")
	public Object checkToken() {
		RestTemplate restTemplate = new RestTemplate();
		String url = TOKEN_URL + "/check_token?token=qwertyui1500509635e6725ed31be8895c4475111";
		Object result = restTemplate.getForObject(url, String.class);
		System.out.println(result);
		com.alibaba.fastjson.JSONObject jobj = JSON.parseObject((String) result);
		String code = jobj.get("code").toString();
		System.out.println(code);
		return result;
	}
}
