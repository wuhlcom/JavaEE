/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月10日 上午11:33:25 * 
*/ 
package com.zhilu.device.util.validator;

import com.alibaba.fastjson.JSONObject;

public class DeviceValidator {
	
    public static boolean getDevIdValidate(JSONObject json){
    	String eui = json.getString("eui");
		System.out.println("eui:" + eui);
		if (RegexUtil.isNull(eui))
			return false;


		String type = json.getString("type");
		System.out.println("type:" + type);
	
		if (RegexUtil.isNull(type))
			return false;
		
		if (RegexUtil.isNotNull(type) && !RegexUtil.isDigits(type)) {
			return false;
		}
        System.out.println("参数校验PASS");
		return true;
    }
}
