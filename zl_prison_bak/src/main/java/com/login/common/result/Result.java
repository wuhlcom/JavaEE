/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月30日 上午11:10:40 * 
*/ 
package com.login.common.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 */
public class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public Result() {
		put("errcode", 10001);
	}
	
	public static Result error() {
		return error(10004, "未知异常，请联系管理员");
	}
	
	public static Result error(String msg) {
		return error(10004, msg);
	}
	
	public static Result error(int code, String msg) {
		Result r = new Result();
		r.put("errcode", code);
		r.put("msg", msg);
		return r;
	}
	
	public static Result error(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}

	public static Result ok(String msg) {
		Result r = new Result();
		r.put("msg", msg);
		return r;
	}
	
	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}
	
	public static Result ok() {
		return new Result();
	}
	
	public static Result ok(int code, String msg) {
		Result r = new Result();
		r.put("errcode", code);
		r.put("msg", msg);
		return r;
	}

	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}