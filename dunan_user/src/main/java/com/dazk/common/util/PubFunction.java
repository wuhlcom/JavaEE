/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月26日 上午11:02:34 * 
*/ 
package com.dazk.common.util;
public class PubFunction {
   public final static String DATA_CODE="application/json;charset=UTF-8";
   
    // 生成code
	public static String generateDevId(int strLen) {
		String base = "0123456789";
		java.util.Random random = new java.util.Random();
		StringBuffer sb = new StringBuffer();		
		for (int i = 0; i < strLen; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString().toLowerCase();
	}
}
