/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年9月14日 下午1:41:18 * 
*/ 
package http;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.alibaba.fastjson.JSONObject;

public class RestTemplate {
	
	public static List<String> getCompanys(String token,String url){		
//		JSONObject jsonObj =new JSONObject();
//		System.out.println("token:"+token);
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//		jsonObj.put("token", token);	
//		
//		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
//		String result = restTemplate.postForObject(url, formEntity, String.class);
//		
//		System.out.println("Check token result="+result);		
//		JSONObject rs = JSONObject.parseObject(result);	
		return null;
	}

	
	public static void main(String [] args){
		
	
	}
}
