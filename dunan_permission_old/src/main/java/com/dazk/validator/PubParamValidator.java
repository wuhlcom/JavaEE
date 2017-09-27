package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;

import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class PubParamValidator {

	public static boolean pageVal(String page, String listRows) {
		System.out.println("page：" + page);
		if (RegexUtil.isNotNull(page) && !RegexUtil.isDigits(page)) {
			return false;
		}

		if (RegexUtil.isNotNull(page) && page.equals("0")) {
			return false;
		}

		System.out.println("listRows：" + listRows);
		if (RegexUtil.isNotNull(listRows) && !RegexUtil.isDigits(listRows)) {
			return false;
		}
		return true;
	}
}
