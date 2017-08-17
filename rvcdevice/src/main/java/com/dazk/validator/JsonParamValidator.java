package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;

import com.dazk.common.ErrCode;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class JsonParamValidator {
	public static final int house_code_len = 16;

	public static final int building_unique_code_len = 10;

	public static final int concentrator_code_len = 7;

	public static final int community_code_len = 8;

	public static final int hotstation_code_len = 6;

	public static final int company_code_len = 3;

	public static boolean valveAddVal(JSONObject json,JSONObject resultObj){
		String house_code = json.getString("house_code");
		if(!isHouseCode(house_code)){ resultObj.put("msg","非法住户编号");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type == null || !RegexUtil.isDigits(type) || type.length() > 2) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String power_type = json.getString("power_type");
		if(power_type != null && ( !RegexUtil.isDigits(power_type) || power_type.length() > 2)) { resultObj.put("msg","power_type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(!isCommAddress(comm_address)) { resultObj.put("msg","comm_address需为16进制字符串");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String unresolved = json.getString("unresolved");
		if(unresolved != null && ( !RegexUtil.isDigits(unresolved) || unresolved.length() > 2)) { resultObj.put("msg","未定义状态错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String version = json.getString("version");
		if(version != null && version.length() > 32)  { resultObj.put("msg","版本号最长32");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","备注最长128");resultObj.put("errcode",ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean valveQueryVal(JSONObject json,JSONObject resultObj){
		String type = json.getString("type");
		if(type != null && ( !RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(comm_address != null && !isCommAddress(comm_address)) { resultObj.put("errcode", ErrCode.parameErr);resultObj.put("msg","通讯地址为非法值"); return false;}
		String unresolved = json.getString("unresolved");
		if(unresolved != null && ( !RegexUtil.isDigits(unresolved) || unresolved.length() > 2)) { resultObj.put("msg","未定义状态错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String open = json.getString("open");
		if(open != null && !RegexUtil.isDigits(open)) { resultObj.put("errcode", ErrCode.parameErr);resultObj.put("msg","阀门设置状态为非法值"); return false;}
		String opening = json.getString("opening");
		if(opening != null && !RegexUtil.isDigits(opening)) { resultObj.put("errcode", ErrCode.parameErr);resultObj.put("msg","阀门状态为非法值"); return false;}
		return true;
	}
	public static boolean houseHolderValveQueryVal(JSONObject json,JSONObject resultObj){
		String id_card = json.getString("id_card");
		if(id_card != null && !RegexUtil.isHex(id_card)) { resultObj.put("errcode", ErrCode.parameErr);resultObj.put("msg","id_card错误"); return false;}
		String error = json.getString("error");
		if(error != null && ( !RegexUtil.isDigits(error) || error.length() > 2)) { resultObj.put("msg","异常类型错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String err_code = json.getString("err_code");
		if(err_code != null && ( !RegexUtil.isDigits(err_code) || err_code.length() > 2)) { resultObj.put("msg","异常类型错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String mes_status = json.getString("mes_status");
		if(mes_status != null && ( !RegexUtil.isDigits(mes_status) || mes_status.length() > 2)) { resultObj.put("msg","mes_status错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String opening = json.getString("opening");
		if(opening != null && !RegexUtil.isDigits(opening)) { resultObj.put("errcode", ErrCode.parameErr);resultObj.put("msg","阀门状态为非法值"); return false;}
		return true;
	}

	public static boolean houseCalorimeterAddVal(JSONObject json,JSONObject resultObj){
		String house_code = json.getString("house_code");
		if(!isHouseCode(house_code)){ resultObj.put("msg","非法住户编号");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type == null || !RegexUtil.isDigits(type) || type.length() > 2) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(!isCommAddress(comm_address)) { resultObj.put("msg","comm_address为非法值");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String pro_type = json.getString("pro_type");
		if(pro_type == null || pro_type.length() > 32)  { resultObj.put("msg","品牌类型错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String pipe_size = json.getString("pipe_size");
		if(pipe_size != null && (!RegexUtil.isDigits(pipe_size) || pipe_size.length() > 2)) { resultObj.put("msg","管径错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","备注最长128");resultObj.put("errcode",ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean houseCalorimeterQueryVal(JSONObject json,JSONObject resultObj){
		String type = json.getString("type");
		if(type != null && ( !RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("errcode", ErrCode.parameErr);resultObj.put("msg","设备类型为非法值"); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(comm_address != null && !isCommAddress(comm_address)) { resultObj.put("errcode", ErrCode.parameErr);resultObj.put("msg","通讯地址为非法值"); return false;}
		String pro_type = json.getString("pro_type");
		if(pro_type != null && pro_type.length() > 32)  { resultObj.put("errcode", ErrCode.parameErr);resultObj.put("msg","品牌字符串最大32"); return false;}
		String pipe_size = json.getString("pipe_size");
		if(pipe_size != null && (!RegexUtil.isDigits(pipe_size) || pipe_size.length() > 2)) { resultObj.put("msg","管径错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","备注最长128");resultObj.put("errcode",ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean concentratorAddVal(JSONObject json,JSONObject resultObj){
		String building_unique_code = json.getString("building_unique_code");
		if(!isBuildingUniqueCode(building_unique_code)){ resultObj.put("msg","非法楼栋唯一编号");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String code = json.getString("code");
		if(!isConcentratorCode(code)){  resultObj.put("msg","非法采集器编号");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  {  resultObj.put("msg","名称错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String address = json.getString("address");
		if(address != null && address.length() > 128)  {  resultObj.put("msg","地址过长");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String gprsid = json.getString("gprsid");
		if(gprsid != null && gprsid.length() > 32)  {  resultObj.put("msg","gprsis错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String ip = json.getString("ip");
		if(ip != null && !RegexUtil.isIP(ip))  {  resultObj.put("msg","ip错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String login_time = json.getString("login_time");
		if(login_time != null && !RegexUtil.isDigits(login_time)) {  resultObj.put("msg","login_time错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String refresh_time = json.getString("refresh_time");
		if(refresh_time != null && !RegexUtil.isDigits(refresh_time)) {  resultObj.put("msg","参数错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String unresolved = json.getString("unresolved");
		if(unresolved != null && ( !RegexUtil.isDigits(unresolved) || unresolved.length() > 2)) {  resultObj.put("msg","unresolved非法");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String sim_code = json.getString("sim_code");
		if(sim_code != null && !RegexUtil.isPhone(sim_code)) {resultObj.put("msg","sim_code错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String protocol_type = json.getString("protocol_type");
		if(protocol_type != null && (!RegexUtil.isDigits(protocol_type) || protocol_type.length() > 2)) { resultObj.put("msg","协议错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String debug_status = json.getString("debug_status");
		if(debug_status != null && (!RegexUtil.isDigits(debug_status) || debug_status.length() > 2)) { resultObj.put("msg","调试状态错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","备注最长128");resultObj.put("errcode",ErrCode.parameErr); return false;}
		System.out.println("验证通过");
		return true;
	}

	public static boolean concentratorQueryVal(JSONObject json,JSONObject resultObj){
		String building_unique_code = json.getString("building_unique_code");
		if(building_unique_code != null && !isBuildingUniqueCode(building_unique_code)){ resultObj.put("msg","楼栋唯一编号错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String code = json.getString("code");
		if(code != null && !isConcentratorCode(code)){ resultObj.put("msg","code错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  { resultObj.put("msg","name错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String address = json.getString("address");
		if(address != null && address.length() > 128)  { resultObj.put("msg","address错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String gprsid = json.getString("gprsid");
		if(gprsid != null && gprsid.length() > 32)  { resultObj.put("msg","gprsid错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String ip = json.getString("ip");
		if(ip != null && !RegexUtil.isIP(ip))  { resultObj.put("msg","ip错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String login_time = json.getString("login_time");
		if(login_time != null && !RegexUtil.isDigits(login_time)) { resultObj.put("msg","login_time错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String refresh_time = json.getString("refresh_time");
		if(refresh_time != null && !RegexUtil.isDigits(refresh_time)) { resultObj.put("msg","refresh_time错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String unresolved = json.getString("unresolved");
		if(unresolved != null && (!RegexUtil.isDigits(unresolved) || unresolved.length() > 2)) { resultObj.put("msg","unresolved错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String sim_code = json.getString("sim_code");
		if(sim_code != null && !RegexUtil.isPhone(sim_code)) { resultObj.put("msg","sim_code错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String protocol_type = json.getString("protocol_type");
		if(protocol_type != null && (!RegexUtil.isDigits(protocol_type) || protocol_type.length() > 2)) { resultObj.put("msg","协议错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String debug_status = json.getString("debug_status");
		if(debug_status != null && (!RegexUtil.isDigits(debug_status) || debug_status.length() > 2)) { resultObj.put("msg","debug_status错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","备注最大128");resultObj.put("errcode", ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean gatewayAddVal(JSONObject json,JSONObject resultObj){
		String company_code = json.getString("company_code");
		if(company_code == null || !JsonParamValidator.isCompanyCode(company_code)) { resultObj.put("msg","company_code错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String mac = json.getString("mac");
		if(!ParamValidator.isMac(mac)){ resultObj.put("msg","mac错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 128)  { resultObj.put("msg","name错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String address = json.getString("address");
		if(address != null && address.length() > 128)  { resultObj.put("msg","address错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String sim_code = json.getString("sim_code");
		if(sim_code != null && !RegexUtil.isPhone(sim_code)) { resultObj.put("msg","sim_code错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String gprsid = json.getString("gprsid");
		if(gprsid != null && gprsid.length() > 32)  { resultObj.put("msg","gprsid错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  {  resultObj.put("msg","remark最长128");resultObj.put("errcode", ErrCode.parameErr); return false;}
		System.out.println("验证通过");
		String txpower = json.getString("txpower");
		if(txpower != null && (!RegexUtil.isDigits(txpower) || txpower.length() > 2)) { resultObj.put("msg","txpower 错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean gatewayQueryVal(JSONObject json,JSONObject resultObj){
		String company_code = json.getString("company_code");
		if(company_code != null && !JsonParamValidator.isCompanyCode(company_code)) { resultObj.put("msg","company_code错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String mac = json.getString("mac");
		if(mac != null && !ParamValidator.isMac(mac)){ resultObj.put("msg","mac错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 128)  { resultObj.put("msg","name错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String address = json.getString("address");
		if(address != null && address.length() > 128)  { resultObj.put("msg","address错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String sim_code = json.getString("sim_code");
		if(sim_code != null && !RegexUtil.isPhone(sim_code)) { resultObj.put("msg","sim_code错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String gprsid = json.getString("gprsid");
		if(gprsid != null && gprsid.length() > 32)  { resultObj.put("msg","gprsid错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","备注最大128");resultObj.put("errcode", ErrCode.parameErr);return false;}
		String txpower = json.getString("txpower");
		if(txpower != null && (!RegexUtil.isDigits(txpower) || txpower.length() > 2)) { resultObj.put("msg","txpower 错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean buildingValveAddVal(JSONObject json,JSONObject resultObj){
		String building_unique_code = json.getString("building_unique_code");
		if(!isBuildingUniqueCode(building_unique_code)){ resultObj.put("msg","building_unique_code错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  {  resultObj.put("msg","name错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type != null && (!RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(!isCommAddress(comm_address)) { resultObj.put("msg","comm_address错误");resultObj.put("errcode", ErrCode.parameErr); return false;}

		String mbus = json.getString("mbus");
		if(mbus != null && RegexUtil.isHex(mbus)) { resultObj.put("msg","mbus错误");resultObj.put("errcode", ErrCode.parameErr); return false;}

		String pipe_size = json.getString("pipe_size");
		if(pipe_size != null && (!RegexUtil.isDigits(pipe_size) || pipe_size.length() > 2)) { resultObj.put("msg","管径错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String strategy = json.getString("strategy");
		if(strategy != null && !RegexUtil.isDigits(strategy)) { resultObj.put("msg","strategy错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String kp = json.getString("kp");
		if(kp != null && !RegexUtil.isFloat(kp)) {resultObj.put("msg","kp错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String ki = json.getString("ki");
		if(ki != null && !RegexUtil.isFloat(ki)) { resultObj.put("msg","ki错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String kd = json.getString("kd");
		if(kd != null && !RegexUtil.isFloat(kd)) { resultObj.put("msg","kd错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String target = json.getString("target");
		if(target != null && !RegexUtil.isFloat(target)) { resultObj.put("msg","target错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String max_target = json.getString("max_target");
		if(max_target != null && !RegexUtil.isFloat(max_target)) { resultObj.put("msg","max_target错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String min_target = json.getString("min_target");
		if(min_target != null && (!RegexUtil.isFloat(min_target) || min_target.compareTo(max_target) > 0 )) { resultObj.put("msg","min_target 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean buildingValveQueryVal(JSONObject json,JSONObject resultObj){
		String building_unique_code = json.getString("building_unique_code");
		if(building_unique_code != null && !isBuildingUniqueCode(building_unique_code)){resultObj.put("msg","building_unique_code错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  { resultObj.put("msg","name错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type != null && (!RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(comm_address !=null && !isCommAddress(comm_address)) { resultObj.put("msg","comm_address错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String mbus = json.getString("mbus");
		if(mbus != null && RegexUtil.isHex(mbus)) { resultObj.put("msg","mbus错误");resultObj.put("errcode", ErrCode.parameErr);return false;}
		String pipe_size = json.getString("pipe_size");
		if(pipe_size != null && (!RegexUtil.isDigits(pipe_size) || pipe_size.length() > 2)) { resultObj.put("msg","管径错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String strategy = json.getString("strategy");
		if(strategy != null && !RegexUtil.isDigits(strategy)) { resultObj.put("msg","strategy错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String kp = json.getString("kp");
		if(kp != null && !RegexUtil.isFloat(kp)) {resultObj.put("msg","kp错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String ki = json.getString("ki");
		if(ki != null && !RegexUtil.isFloat(ki)) { resultObj.put("msg","ki错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String kd = json.getString("kd");
		if(kd != null && !RegexUtil.isFloat(kd)) { resultObj.put("msg","kd错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String target = json.getString("target");
		if(target != null && !RegexUtil.isFloat(target)) { resultObj.put("msg","target错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String max_target = json.getString("max_target");
		if(max_target != null && !RegexUtil.isFloat(max_target)) { resultObj.put("msg","max_target错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String min_target = json.getString("min_target");
		if(min_target != null && !RegexUtil.isFloat(min_target)) { resultObj.put("msg","min_target错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		System.out.println("验证通过");
		return true;
	}

	public static boolean buildingCalorimeterAddVal(JSONObject json,JSONObject resultObj){
		String building_unique_code = json.getString("building_unique_code");
		if(!isBuildingUniqueCode(building_unique_code)){ resultObj.put("msg","building_unique_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  { resultObj.put("msg","name 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type == null || !RegexUtil.isDigits(type) || type.length() > 2) { resultObj.put("msg","type 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(comm_address != null && !isCommAddress(comm_address)) { resultObj.put("msg","comm_address 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String pro_type = json.getString("pro_type");
		if(pro_type != null && pro_type.length() > 32)  { resultObj.put("msg","pro_type 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String pipe_size = json.getString("pipe_size");
		if(pipe_size != null && (!RegexUtil.isDigits(pipe_size) || pipe_size.length() > 2)) { resultObj.put("msg","管径错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean buildingCalorimeterQueryVal(JSONObject json,JSONObject resultObj){
		String building_unique_code = json.getString("building_unique_code");
		if(building_unique_code != null && !isBuildingUniqueCode(building_unique_code)){ resultObj.put("msg","building_unique_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  { resultObj.put("msg","name 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type != null && (!RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(comm_address != null && !isCommAddress(comm_address)) { resultObj.put("msg","comm_address 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String pro_type = json.getString("pro_type");
		if(pro_type != null && pro_type.length() > 32)  { resultObj.put("msg","pro_type 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String pipe_size = json.getString("pipe_size");
		if(pipe_size != null && (!RegexUtil.isDigits(pipe_size) || pipe_size.length() > 2)) { resultObj.put("msg","管径错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean valveEditVal(JSONObject json,JSONObject resultObj){
		String house_code = json.getString("house_code");
		if(!isHouseCode(house_code)){ resultObj.put("msg","house_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type != null && (!RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String power_type = json.getString("power_type");
		if(power_type != null && ( !RegexUtil.isDigits(power_type) || power_type.length() > 2)) { resultObj.put("msg","power_type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(comm_address != null && !isCommAddress(comm_address)) { resultObj.put("msg","comm_address 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String unresolved = json.getString("unresolved");
		if(unresolved != null && ( !RegexUtil.isDigits(unresolved) || unresolved.length() > 2)) { resultObj.put("msg","未定义状态错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String version = json.getString("version");
		if(version != null && version.length() > 32)  { resultObj.put("msg","版本号最长32");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean houseCalorimeterEditVal(JSONObject json,JSONObject resultObj){
		String house_code = json.getString("house_code");
		if(!isHouseCode(house_code)){ resultObj.put("msg","house_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type != null && (!RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(comm_address != null && !isCommAddress(comm_address)) {resultObj.put("msg","comm_address 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String pro_type = json.getString("pro_type");
		if(pro_type != null && pro_type.length() > 32)  { resultObj.put("msg","pro_type 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String pipe_size = json.getString("pipe_size");
		if(pipe_size != null && (!RegexUtil.isDigits(pipe_size) || pipe_size.length() > 2)) { resultObj.put("msg","管径错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean concentratorEditVal(JSONObject json,JSONObject resultObj){
		String building_unique_code = json.getString("building_unique_code");
		if(!isBuildingUniqueCode(building_unique_code)){ resultObj.put("msg","building_unique_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String code = json.getString("code");
		if(code != null && !isConcentratorCode(code)){ resultObj.put("msg","code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  { resultObj.put("msg","name 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String address = json.getString("address");
		if(address != null && address.length() > 128)  { resultObj.put("msg","address 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String gprsid = json.getString("gprsid");
		if(gprsid != null && gprsid.length() > 32)  { resultObj.put("msg","gprsid 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String ip = json.getString("ip");
		if(ip != null && !RegexUtil.isIP(ip))  { resultObj.put("msg","ip 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String login_time = json.getString("login_time");
		if(login_time != null && !RegexUtil.isDigits(login_time)) { resultObj.put("msg","login_time 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String refresh_time = json.getString("refresh_time");
		if(refresh_time != null && !RegexUtil.isDigits(refresh_time)) { resultObj.put("msg","refresh_time 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String unresolved = json.getString("unresolved");
		if(unresolved != null && ( !RegexUtil.isDigits(unresolved) || unresolved.length() > 2)) { resultObj.put("msg","未定义状态错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String sim_code = json.getString("sim_code");
		if(sim_code != null && !RegexUtil.isPhone(sim_code)) { resultObj.put("msg","sim_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type != null && (!RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String debug_status = json.getString("debug_status");
		if(debug_status != null && (!RegexUtil.isDigits(debug_status) || debug_status.length() > 2)) { resultObj.put("msg","debug_status错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean gatewayEditVal(JSONObject json,JSONObject resultObj){
		String id = json.getString("id");
		if(id == null){return false;}
		String company_code = json.getString("company_code");
		if(company_code != null && !JsonParamValidator.isCompanyCode(company_code)) {resultObj.put("msg","company_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String mac = json.getString("mac");
		if(mac != null && !ParamValidator.isMac(mac)){ resultObj.put("msg","mac 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 128)  {resultObj.put("msg","name 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String address = json.getString("address");
		if(address != null && address.length() > 128)  { resultObj.put("msg","address 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String sim_code = json.getString("sim_code");
		if(sim_code != null && !RegexUtil.isPhone(sim_code)) { resultObj.put("msg","sim_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String gprsid = json.getString("gprsid");
		if(gprsid != null && gprsid.length() > 32)  { resultObj.put("msg","gprsid 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String txpower = json.getString("txpower");
		if(txpower != null && (!RegexUtil.isDigits(txpower) || txpower.length() > 2)) { resultObj.put("msg","txpower 错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean buildingValveEditVal(JSONObject json,JSONObject resultObj){
		String building_unique_code = json.getString("building_unique_code");
		if(!isBuildingUniqueCode(building_unique_code)){ resultObj.put("msg","building_unique_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  {resultObj.put("msg","name 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type != null && (!RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(comm_address != null && !isCommAddress(comm_address)) { resultObj.put("msg","comm_address 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String pipe_size = json.getString("pipe_size");
		if(pipe_size != null && (!RegexUtil.isDigits(pipe_size) || pipe_size.length() > 2)) { resultObj.put("msg","管径错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String strategy = json.getString("strategy");
		if(strategy != null && !RegexUtil.isDigits(strategy)) { resultObj.put("msg","strategy 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String kp = json.getString("kp");
		if(kp != null && !RegexUtil.isFloat(kp)) { resultObj.put("msg","kp 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String ki = json.getString("ki");
		if(ki != null && !RegexUtil.isFloat(ki)) { resultObj.put("msg","ki 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String kd = json.getString("kd");
		if(kd != null && !RegexUtil.isFloat(kd)) {resultObj.put("msg","kd 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String target = json.getString("target");
		if(target != null && !RegexUtil.isFloat(target)) { resultObj.put("msg","target 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String max_target = json.getString("max_target");
		if(max_target != null && !RegexUtil.isFloat(max_target)) { resultObj.put("msg","max_target 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String min_target = json.getString("min_target");
		if(min_target != null && (!RegexUtil.isFloat(min_target) || min_target.compareTo(max_target) > 0 )) { resultObj.put("msg","min_target 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		return true;
	}

	public static boolean buildingCalorimeterEditVal(JSONObject json,JSONObject resultObj){
		String building_unique_code = json.getString("building_unique_code");
		if(!isBuildingUniqueCode(building_unique_code)){ resultObj.put("msg","building_unique_code 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String name = json.getString("name");
		if(name != null && name.length() > 32)  { resultObj.put("msg","name 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String type = json.getString("type");
		if(type != null && (!RegexUtil.isDigits(type) || type.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String period = json.getString("period");
		if(period != null && (!RegexUtil.isDigits(period) || period.length() > 2)) { resultObj.put("msg","type错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String comm_address = json.getString("comm_address");
		if(comm_address != null && !isCommAddress(comm_address)) { resultObj.put("msg","comm_address 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String pro_type = json.getString("pro_type");
		if(pro_type != null && pro_type.length() > 32)  { resultObj.put("msg","pro_type 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String pipe_size = json.getString("pipe_size");
		if(pipe_size != null && (!RegexUtil.isDigits(pipe_size) || pipe_size.length() > 2)) { resultObj.put("msg","管径错误");resultObj.put("errcode",ErrCode.parameErr); return false;}
		String remark = json.getString("remark");
		if(remark != null && remark.length() > 128)  { resultObj.put("msg","remark 错误");resultObj.put("errcode", ErrCode.parameErr);return false;}
		return true;
	}

	public static boolean stealHeatVal(JSONObject json,JSONObject resultObj){
		String wit_min = json.getString("wit_min");
		if(wit_min != null && (!RegexUtil.isPositiveFloat(wit_min) || wit_min.length() > 5)){ resultObj.put("msg","wit_min 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String wit_max = json.getString("wit_max");
		if(wit_max != null && (!RegexUtil.isPositiveFloat(wit_max) || wit_max.length() > 5)){ resultObj.put("msg","wit_max 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String wot_min = json.getString("wot_min");
		if(wot_min != null && (!RegexUtil.isPositiveFloat(wot_min) || wot_min.length() > 5)){ resultObj.put("msg","wot_min 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String wot_max = json.getString("wot_max");
		if(wot_max != null && (!RegexUtil.isPositiveFloat(wot_max) || wot_max.length() > 5)){ resultObj.put("msg","wot_max 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String condition1 = json.getString("condition1");
		if(condition1 != null && !condition1.equals("or") && !condition1.equals("and")){ resultObj.put("msg","condition1 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
		String condition2 = json.getString("condition2");
		if(condition2 != null && !condition2.equals("or") && !condition2.equals("and")){ resultObj.put("msg","condition2 错误");resultObj.put("errcode", ErrCode.parameErr); return false;}
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

	//是否为小区编号
	public static boolean isCommunityCode(String str){
		if(str != null){
			return ParamValidator.isDecNum(str, community_code_len, community_code_len);
		}
		return false;
	}

	//是否为热站编号
	public static boolean isHotstationCode(String str){
		if(str != null){
			return ParamValidator.isDecNum(str, hotstation_code_len, hotstation_code_len);
		}
		return false;
	}

	//是否为热站编号
	public static boolean isCompanyCode(String str){
		if(str != null){
			return ParamValidator.isDecNum(str, company_code_len, company_code_len);
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

	//字符串转数字
	public static Integer str2Digits(String str){
		if(RegexUtil.isDigits(str)){
			return Integer.parseInt(str);
		}else{
			return null;
		}
	}

	public static Long str2long(String str){
		if(RegexUtil.isDigits(str)){
			return Long.parseLong(str);
		}else{
			return null;
		}
	}
}
