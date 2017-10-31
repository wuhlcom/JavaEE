/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 下午5:46:14 * 
*/ 
package com.enviroment.common.validator;

import com.alibaba.fastjson.JSONObject;
import com.enviroment.common.errcode.ResultErr;
import com.enviroment.common.errcode.ResultStatusCode;

public class GasValidator {
	public static ResultErr queryValidate(JSONObject json) {
		String dev_address = json.getString("dev_address");
		System.out.println("dev_address：" + dev_address);
//		if (RegexUtil.isNull(dev_address) || !isMenuName(dev_address))
//			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "菜单名格式输入错误");
//
//		String is_menu = json.getString("is_menu");
//		System.out.println("is_menu：" + is_menu);
//		if (RegexUtil.isNull(is_menu) || (RegexUtil.isNotNull(is_menu) && !is_menu.equals("0") && !is_menu.equals("1")
//				&& !is_menu.equals("2")))
//			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "菜单类型错误");
//
//		String parent_id = json.getString("parent_id");
//		System.out.println("parent_id：" + parent_id);
//		if (RegexUtil.isNotNull(parent_id) && !RegexUtil.isDigits(parent_id)) {
//			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "父菜单ID格式错误");
//		}

		System.out.println("验证通过");
		return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
	}
}
