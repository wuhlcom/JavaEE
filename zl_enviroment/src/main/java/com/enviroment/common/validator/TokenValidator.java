/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月7日 上午10:34:54 * 
*/
package com.enviroment.common.validator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.enviroment.common.utils.GlobalParamsUtil;

public class TokenValidator {

	public static JSONObject getRsToken(String token) {
		JSONObject jsonObj =new JSONObject();
		System.out.println("token:"+token);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		jsonObj.put("token", token);	
		
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
		String result = restTemplate.postForObject(GlobalParamsUtil.TOKEN_URL, formEntity, String.class);
		
		System.out.println("Check token result="+result);		
		JSONObject rs = JSONObject.parseObject(result);	
		return rs;
	}


	public static boolean checkRsToken(JSONObject json) {
		Integer status = json.getInteger("status");
		if (status == 1) {
			return true;
		} else if (status == 0) {
			return false;
		} else {
			return false;
		}
	}

	public static boolean checkToken(String token) {
		JSONObject json = getRsToken(token);
		if (json == null)
			return false;
		boolean rs = checkRsToken(json);
		return rs;
	}
}
