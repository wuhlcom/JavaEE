package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;

import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class JsonParamValidator {
	public static final int house_code_len = 16;

	public static final int building_unique_code_len = 10;

	public static final int concentrator_code_len = 7;

	public static boolean valveVal(JSONObject json){
		String house_code = json.getString("house_code");
		if(!isHouseCode(house_code)){ System.out.println("house_code："+house_code); return false;}
		String type = json.getString("type");
		if(type != null && !RegexUtil.isDigits(type)) { System.out.println("type："+type); return false;}
		String period = json.getString("period");
		if(type != null && !RegexUtil.isDigits(period)) { System.out.println("period："+period); return false;}
		String comm_address = json.getString("comm_address");
		if(!isCommAddress(comm_address)) { System.out.println("comm_address："+comm_address); return false;}
		String unresolved = json.getString("unresolved");
		if(unresolved != null && !RegexUtil.isDigits(unresolved)) { System.out.println("unresolved："+unresolved); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { System.out.println("remark："+remark); return false;}
		System.out.println("验证通过");
		return true;
	}

	public static boolean houseCalorimeterVal(JSONObject json){
		String house_code = json.getString("house_code");
		if(!isHouseCode(house_code)){ System.out.println("house_code："+house_code); return false;}
		String type = json.getString("type");
		if(type != null && !RegexUtil.isDigits(type)) { System.out.println("type："+type); return false;}
		String period = json.getString("period");
		if(period != null && !RegexUtil.isDigits(period)) { System.out.println("period："+period); return false;}
		String comm_address = json.getString("comm_address");
		if(!isCommAddress(comm_address)) { System.out.println("comm_address："+comm_address); return false;}
		String pro_type = json.getString("pro_type");
		if(pro_type != null && pro_type.length() > 32)  { System.out.println("pro_type："+pro_type); return false;}
		String pipe_size = json.getString("period");
		if(pipe_size != null && !RegexUtil.isDigits(pipe_size)) { System.out.println("period："+pipe_size); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { System.out.println("remark："+remark); return false;}
		System.out.println("验证通过");
		return true;
	}

	public static boolean concentratorVal(JSONObject json){
		String building_unique_code = json.getString("building_unique_code");
		if(!isBuildingUniqueCode(building_unique_code)){ System.out.println("house_code："+building_unique_code); return false;}
		String code = json.getString("code");
		if(!isConcentratorCode(code)){ System.out.println("code："+code); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  { System.out.println("name："+name); return false;}
		String address = json.getString("address");
		if(address != null && address.length() > 128)  { System.out.println("address："+address); return false;}
		String gprsid = json.getString("gprsid");
		if(gprsid != null && gprsid.length() > 32)  { System.out.println("gprsid："+gprsid); return false;}
		String ip = json.getString("ip");
		if(ip != null && !RegexUtil.isIP(ip))  { System.out.println("ip："+ip); return false;}
		String login_time = json.getString("login_time");
		if(login_time != null && !RegexUtil.isDigits(login_time)) { System.out.println("login_time："+login_time); return false;}
		String refresh_time = json.getString("refresh_time");
		if(refresh_time != null && !RegexUtil.isDigits(refresh_time)) { System.out.println("refresh_time："+refresh_time); return false;}
		String unresolved = json.getString("unresolved");
		if(unresolved != null && !RegexUtil.isDigits(unresolved)) { System.out.println("unresolved："+unresolved); return false;}
		String sim_code = json.getString("sim_code");
		if(sim_code != null && !RegexUtil.isDigits(sim_code)) { System.out.println("sim_code："+sim_code); return false;}
		String protocol_type = json.getString("protocol_type");
		if(protocol_type != null && !RegexUtil.isDigits(protocol_type)) { System.out.println("protocol_type："+protocol_type); return false;}
		String debug_status = json.getString("debug_status");
		if(debug_status != null && !RegexUtil.isDigits(debug_status)) { System.out.println("debug_status："+debug_status); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { System.out.println("remark："+remark); return false;}
		System.out.println("验证通过");
		return true;
	}

	public static boolean gatewayVal(JSONObject json){
		String company_id = json.getString("company_id");
		if(company_id != null && !RegexUtil.isDigits(company_id)) { System.out.println("company_id："+company_id); return false;}
		String mac = json.getString("mac");
		if(!ParamValidator.isMac(mac)){ System.out.println("mac："+mac); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 128)  { System.out.println("name："+name); return false;}
		String address = json.getString("address");
		if(address != null && address.length() > 128)  { System.out.println("address："+address); return false;}
		String sim_code = json.getString("sim_code");
		if(sim_code != null && !RegexUtil.isDigits(sim_code)) { System.out.println("sim_code："+sim_code); return false;}
		String gprsid = json.getString("gprsid");
		if(gprsid != null && gprsid.length() > 32)  { System.out.println("gprsid："+gprsid); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { System.out.println("remark："+remark); return false;}
		System.out.println("验证通过");
		return true;
	}

	public static boolean buildingValveVal(JSONObject json){
		String building_unique_code = json.getString("building_unique_code");
		if(!isBuildingUniqueCode(building_unique_code)){ System.out.println("house_code："+building_unique_code); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  { System.out.println("name："+name); return false;}
		String type = json.getString("type");
		if(type != null && !RegexUtil.isDigits(type)) { System.out.println("type："+type); return false;}
		String period = json.getString("period");
		if(type != null && !RegexUtil.isDigits(period)) { System.out.println("period："+period); return false;}
		String comm_address = json.getString("comm_address");
		if(!isCommAddress(comm_address)) { System.out.println("comm_address："+comm_address); return false;}
		String pipe_size = json.getString("period");
		if(pipe_size != null && !RegexUtil.isDigits(pipe_size)) { System.out.println("period："+pipe_size); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { System.out.println("remark："+remark); return false;}
		String strategy = json.getString("strategy");
		if(strategy != null && !RegexUtil.isDigits(strategy)) { System.out.println("strategy："+strategy); return false;}
		String kp = json.getString("kp");
		if(kp != null && !RegexUtil.isFloat(kp)) { System.out.println("kp："+kp); return false;}
		String ki = json.getString("ki");
		if(ki != null && !RegexUtil.isFloat(ki)) { System.out.println("ki："+ki); return false;}
		String kd = json.getString("kd");
		if(kd != null && !RegexUtil.isFloat(kd)) { System.out.println("kd："+kd); return false;}
		String target = json.getString("target");
		if(target != null && !RegexUtil.isFloat(target)) { System.out.println("target："+target); return false;}
		String max_target = json.getString("max_target");
		if(max_target != null && !RegexUtil.isFloat(max_target)) { System.out.println("max_target："+max_target); return false;}
		String min_target = json.getString("min_target");
		if(min_target != null && !RegexUtil.isFloat(min_target)) { System.out.println("min_target："+min_target); return false;}
		System.out.println("验证通过");
		return true;
	}

	public static boolean buildingCalorimeterVal(JSONObject json){
		String building_unique_code = json.getString("building_unique_code");
		if(!isBuildingUniqueCode(building_unique_code)){ System.out.println("house_code："+building_unique_code); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  { System.out.println("name："+name); return false;}
		String type = json.getString("type");
		if(type != null && !RegexUtil.isDigits(type)) { System.out.println("type："+type); return false;}
		String period = json.getString("period");
		if(type != null && !RegexUtil.isDigits(period)) { System.out.println("period："+period); return false;}
		String comm_address = json.getString("comm_address");
		if(!isCommAddress(comm_address)) { System.out.println("comm_address："+comm_address); return false;}
		String pro_type = json.getString("pro_type");
		if(pro_type != null && pro_type.length() > 32)  { System.out.println("pro_type："+pro_type); return false;}
		String pipe_size = json.getString("period");
		if(pipe_size != null && !RegexUtil.isDigits(pipe_size)) { System.out.println("period："+pipe_size); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { System.out.println("remark："+remark); return false;}
		System.out.println("验证通过");
		return true;
	}

	//是否为住户编号
	public static boolean isHouseCode(String str){
		if(str != null){
			return ParamValidator.isDecNum(str, house_code_len, house_code_len);
		}
		return false;
	}

	//是否为楼栋唯一编号
	public static boolean isBuildingUniqueCode(String str){
		if(str != null){
			return ParamValidator.isDecNum(str, building_unique_code_len, building_unique_code_len);
		}
		return false;
	}

	//是否为采集器编号
	public static boolean isConcentratorCode(String str){
		if(str != null){
			return ParamValidator.isDecNum(str, concentrator_code_len, concentrator_code_len);
		}
		return false;
	}
	
	//是否为通信地址
	public static boolean isCommAddress(String str){
		if(str != null){
			return ParamValidator.isHexNum(str, 0, 32);
		}
		return false;
	}

}
