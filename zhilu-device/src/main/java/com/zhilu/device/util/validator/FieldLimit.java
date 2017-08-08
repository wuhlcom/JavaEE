/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月8日 下午9:04:59 * 
*/ 
package com.zhilu.device.util.validator;

import java.util.Arrays;

public class FieldLimit {	
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
