/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月10日 上午11:11:53 * 
*/
package com.zhilu.device.util.validator;

import com.alibaba.fastjson.JSONObject;

public class LoraDevValidator {
	// "deveui":"11-22-33-44-55-66",
	// "devaddr":"22-33-44-55",
	// "nwkskey":"22222222",
	// "appskey":"appskey",
	// "appkey":"appkey",
	// "app_id":123213,
	// "type":"c",
	// "city":"sz",
	// "area":"ns",
	// "addr":"dx",
	// "protocol":"",
	// "rxwindow":"23",
	// "rxloffset":1,
	// "rxdelay":0,
	// "rx2dr":0,
	// "rx2frequency":471.3000,
	// "clazz":0,
	// "ism":"471.3000"
	public static boolean lrDevValidator(JSONObject json) {
		String deveui = json.getString("deveui");
		System.out.println("deveui:" + deveui);
		if (RegexUtil.isNull(deveui))
			return false;

		String devaddr = json.getString("devaddr");
		System.out.println("devaddr:" + devaddr);
		// if (RegexUtil.isNull(devaddr))
		// return false;

		String nwkskey = json.getString("nwkskey");
		System.out.println("nwkskey:" + nwkskey);
		// if (RegexUtil.isNull(nwkskey))
		// return false;

		String appskey = json.getString("appskey");
		System.out.println("appskey:" + appskey);
		// if (RegexUtil.isNull(nwkskey))
		// return false;

		String appkey = json.getString("appkey");
		System.out.println("appkey:" + appkey);
		// if (RegexUtil.isNull(appkey))
		// return false;

		String app_id = json.getString("app_id");
		System.out.println("app_id:" + app_id);
		if (RegexUtil.isNull(app_id))
			return false;
		if (RegexUtil.isNotNull(app_id) && !RegexUtil.isDigits(app_id)) {
			return false;
		}

		String type = json.getString("type");
		System.out.println("type:" + type);
		// if (RegexUtil.isNull(type))
		// return false;

		String city = json.getString("city");
		System.out.println("city:" + city);

		if (RegexUtil.isNotNull(city) && !RegexUtil.stringCheck(city)) {
			return false;
		}

		String area = json.getString("area");
		System.out.println("area:" + area);

		if (RegexUtil.isNotNull(area) && !RegexUtil.stringCheck(area)) {
			return false;
		}

		String addr = json.getString("addr");
		System.out.println("addr:" + addr);

		if (RegexUtil.isNotNull(addr) && !RegexUtil.stringCheck(addr)) {
			return false;
		}

		String protocol = json.getString("protocol");
		System.out.println("protocol:" + protocol);

		if (RegexUtil.isNotNull(protocol) && !RegexUtil.stringCheck(protocol)) {
			return false;
		}

		String rxwindow = json.getString("rxwindow");
		System.out.println("rxwindow:" + rxwindow);

		if (RegexUtil.isNotNull(rxwindow) && !RegexUtil.isDigits(rxwindow)) {
			return false;
		}

		String rxloffset = json.getString("rxloffset");
		System.out.println("rxloffset:" + rxloffset);

		if (RegexUtil.isNotNull(rxloffset) && !RegexUtil.isDigits(rxloffset)) {
			return false;
		}

		String rxdelay = json.getString("rxdelay");
		System.out.println("rxdelay:" + rxdelay);

		if (RegexUtil.isNotNull(rxdelay) && !RegexUtil.isDigits(rxdelay)) {
			return false;
		}

		String rx2dr = json.getString("rx2dr");
		System.out.println("rx2dr:" + rx2dr);

		if (RegexUtil.isNotNull(rx2dr) && !RegexUtil.isDigits(rx2dr)) {
			return false;
		}

		String rx2frequency = json.getString("rx2frequency");
		System.out.println("rx2frequency:" + rx2frequency);

		if (RegexUtil.isNotNull(rx2frequency) && !RegexUtil.isDigits(rx2frequency)) {
			return false;
		}

		String clazz = json.getString("clazz");
		System.out.println("clazz:" + clazz);

		if (RegexUtil.isNotNull(clazz) && !RegexUtil.stringCheck(clazz)) {
			return false;
		}

		String ism = json.getString("ism");
		System.out.println("ism:" + ism);

		if (RegexUtil.isNotNull(ism) && !RegexUtil.stringCheck(ism)) {
			return false;
		}
	    System.out.println("参数校验PASS");
		return true;
	}

}
