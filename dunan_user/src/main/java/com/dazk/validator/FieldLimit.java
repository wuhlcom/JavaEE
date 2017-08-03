/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午4:24:38 * 
*/
package com.dazk.validator;

import java.util.Arrays;

public class FieldLimit {
	// Menu restriction
	public static final int MENU_NAME_MIN = 1;
	public static final int MENU_NAME_MAX = 16;
	public static final int URI_LEN_MIN = 2;
	public static final int URI_LEN_MAX = 45;
	public static final int MENU_CODE_MIN = 1;
	public static final int MENU_CODE_MAX = 20;
	public static final int MENU_ID_MIN = 1;
	public static final int MENU_ID_MAX = 11;

	//role
	public static final int ROLE_NAME_MIN = 1;
	public static final int ROLE_NAME_MAX = 20;
	public static final int ROLE_REMARKS_MAX = 45;
	public static final int ROLE_ID_MIN = 1;
	public static final int ROLE_ID_MAX = 11;
	public static final int ROLE_CODE_MIN = 1;
	public static final int ROLE_CODE_MAX = 20;

	//user
	public static final int LOGIN_NAME_MIN = 6;
	public static final int LOGIN_NAME_MAX = 32;

	public static final int USER_ID_MIN = 1;
	public static final int USER_ID_MAX = 11;
	
	public static final int USER_NAME_MIN = 1;
	public static final int USER_NAME_MAX = 64;
	public static final int USER_PW_MIN = 1;
	public static final int USER_PW_MAX = 32;
	
	public static final int COMPANY_MIN = 1;
	public static final int COMPANY_MAX = 64;
	
	public static final int POSITION_MIN = 1;
	public static final int POSITION_MAX = 32;	
	
	public static final int ADDRESS_MIN = 1;
	public static final int ADDRESS_MAX = 128;
	
	public static final int REMARK_MIN = 1;
	public static final int REMARK_MAX = 255;

	// data permission
	public static final int DATA_ID_MIN = 1;
	public static final int DATA_ID_MAX = 11;
	public static final int DATA_CODE_MIN = 1;
	public static final int DATA_CODE_MAX = 32;
	public static final Integer[] CODE_TYPE_ARR = { 1, 2, 3 };
	public static final Integer[] SEARCH_TYPE_ARR= { 0,1 };
	public static final Integer[] DATA_SEARCH_TYPE_ARR= { 0,1,2 };
	
	public static boolean containCkLoop(String[] arr, String targetValue) {
	    for(String s: arr){
	        if(s.equals(targetValue))
	            return true;
	    }
	    return false;
	}
	
	public static boolean containCkLoop(Integer[] arr, Integer targetValue) {
	    for(Integer s: arr){
	        if(s.equals(targetValue))
	            return true;
	    }
	    return false;
	}
	
	
	public static boolean containCkList(String[] codeTypeArr, String codeType) {
	    return Arrays.asList(codeTypeArr).contains(codeType);
	}
	
	public static boolean containCkList(Integer[] arr, Integer targetValue) {
	    return Arrays.asList(arr).contains(targetValue);
	}
}
