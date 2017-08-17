package com.dazk.common.util;

import com.dazk.db.model.Concentrator;

public class CodeUtil {
	
	public static final String LOG_OPERATION_ADD="add";//系统日志操作类型operation
	
	public static final String LOG_OPERATION_DEL="delete";
	
	public static final String LOG_OPERATION_UPDATE="update";
	
	public static final String IMPORT_DATA_TYPE_HOT="hotstation";
	
	public static final String IMPORT_DATA_TYPE_COMM="community";
	
	public static final String IMPORT_DATA_TYPE_BUILD="building";
	
	public static final String IMPORT_DATA_TYPE_HOURSE="householder";

	public static final String IMPORT_DATA_TYPE_HVALVE="houseValve";

	public static final String IMPORT_DATA_TYPE_BVALVE="buildingValve";

	public static final String IMPORT_DATA_TYPE_HCALORIMETER="houseCalorimeter";

	public static final String IMPORT_DATA_TYPE_BCALORIMETER="buildingCalorimeter";

	public static final String IMPORT_DATA_TYPE_CONCENTRATOR="concentrator";

	public static final String IMPORT_DATA_TYPE_GATEWAY="gateway";

	
	
	
	
	//001 002 003
	public static String getThreeDigitCode(Integer code){
		String codeStr="000"+code;
		return codeStr.substring(codeStr.length()-3, codeStr.length());
	}
	
	//01 02 03
	public static String getTwoDigitCode(Integer code){
		String codeStr="00"+code;
		return codeStr.substring(codeStr.length()-2, codeStr.length());
	}
	
	
	public static void main(String[] args) {
		System.out.println(getTwoDigitCode(13));
	}

}
