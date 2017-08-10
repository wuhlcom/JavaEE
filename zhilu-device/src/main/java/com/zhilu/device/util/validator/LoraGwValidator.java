/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月9日 下午1:43:49 * 
*/
package com.zhilu.device.util.validator;

import com.alibaba.fastjson.JSONObject;

public class LoraGwValidator {
	// "type":"xxx",
	// "city":"sz",
	// "area":"xx",
	// "addr":"xx",
	// "channel_plan_id":14,
	// "secret":"ff23232f"
	// ,"power":14,
	// "status":0,
	public static boolean lrGwValidator(JSONObject json) {
		String type = json.getString("type");
		System.out.println("type:"+type);
		if (RegexUtil.isNull(type))
			return false;
		
		String city = json.getString("city");
		System.out.println("city:"+city);
		
		if (RegexUtil.isNotNull(city)&& !RegexUtil.stringCheck(city)){
			return false;
		}
		
		String area = json.getString("area");
		System.out.println("area:"+area);
		
		if (RegexUtil.isNotNull(area)&& !RegexUtil.stringCheck(area)){
			return false;
		}		
		
		String addr = json.getString("addr");
		System.out.println("addr:"+addr);
		
		if (RegexUtil.isNotNull(addr)&& !RegexUtil.stringCheck(addr)){
			return false;
		}
		
		String channel_plan_id = json.getString("channel_plan_id");
		System.out.println("channel_plan_id:"+channel_plan_id);
		
		if (RegexUtil.isNotNull(addr)&& !RegexUtil.isDigits(channel_plan_id)){
			return false;
		}
		
		
		String secret = json.getString("secret");
		System.out.println("secret:"+secret);
		
		if (RegexUtil.isNotNull(secret)&& !RegexUtil.stringCheck(secret)){
			return false;
		}
		
		
		String power = json.getString("power");
		System.out.println("power:"+power);
		
		if (RegexUtil.isNotNull(power)&& !RegexUtil.isDigits(power)){
			return false;
		}
		
		String status = json.getString("status");
		System.out.println("status:"+status);
		
		if (RegexUtil.isNotNull(status)&& !RegexUtil.isDigits(status)){
			return false;
		}
	    System.out.println("参数校验PASS");
		return true;
	}

}
