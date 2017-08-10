/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月8日 下午9:04:59 * 
*/
package com.zhilu.device.util.validator;

import java.util.Arrays;

public class FieldLimit {
	
	public final static Integer[] DEV_TYPE = {1,2,3,4};	
	public final static Integer PRO_ID_MAX = 16;
	public final static Integer PRO_ID_MIN = 1;
	public final static Integer UUID_LEN = 16;
	
	public final static Integer LrGw_CITY_MAX = 32;
	public final static Integer LrGw_CITY_MIN = 1;
	public final static Integer LrGw_AREA_MAX = 32;
	public final static Integer LrGw_AREA_MIN = 1;	
	public final static Integer LrGw_ADDR_MAN = 32;
	public final static Integer LrGw_ADDR_MIN = 1;
	
	public static final Integer DATA_CODE_MAX = 32;
	public static final Integer[] CODE_TYPE_ARR = { 1, 2, 3 };
	public static final Integer[] SEARCH_TYPE_ARR = { 0, 1 };
	public static final Integer[] DATA_SEARCH_TYPE_ARR = { 0, 1, 2 };

	public static boolean containCkLoop(String[] arr, String targetValue) {
		for (String s : arr) {
			if (s.equals(targetValue))
				return true;
		}
		return false;
	}

	public static boolean containCkLoop(Integer[] arr, Integer targetValue) {
		for (Integer s : arr) {
			if (s.equals(targetValue))
				return true;
		}
		return false;
	}

	public static boolean containCkList(int[] devType, Integer type) {
		return Arrays.asList(devType).contains(type);
	}

	public static boolean containCkList(Integer[] arr, Integer targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
}
