package com.dazk.common.util;

public class ParamValidator {
	/**
     * 匹配符合长度的10进制数字字符串
     * @param str
     * @return
     * @author jiqinlin
     */
	public static boolean isDecNum(String str,int minLen,int maxLen){
		if(str.length() < minLen || str.length() > maxLen) return false;
		return RegexUtil.isDigits(str);
	}
	
	/**
     * 匹配符合长度的16进制数字字符串
     * @param str
     * @return
     * @author jiqinlin
     */
	public static boolean isHexNum(String str,int minLen,int maxLen){
		if(str.length() < minLen || str.length() > maxLen) return false;
		return RegexUtil.isHex(str);
	}
	
	/**
    * 匹配非负整数（正整数+0）
    *
    * @param str
    * @return
    * @author jiqinlin
    */
	public static boolean  isInteger(String str){
		return RegexUtil.isInteger(str);
	}
	
	/**
     * 过滤html代码
     *
     * @param inputString 含html标签的字符串
     * @return
     */
	public static String htmlFilter(String str){
		return RegexUtil.htmlFilter(str);
	}

	//是否为mac地址
	public static boolean isMac(String str){
		if(str != null){
			return RegexUtil.isMac(str);
		}
		return false;
	}
	
	/**
     * 字符串长度判断
     *
     * @param inputString 含html标签的字符串
     * @return
     */
	public static boolean isStrLength(String str,int minLen,int maxLen){
		if (str==null) return false;
		if(str.length() < minLen || str.length() > maxLen) return false;
		return true;		
	}
	
	/**
	 * 菜单code,目前不清楚code格式，这里只约束code长度和限制为数字
	 */
	public static boolean isCode(String code, int min, int max) {
		if (RegexUtil.isNull(code) || !ParamValidator.isStrLength(code, min, max)) {
			return false;
		}
		if (!RegexUtil.isDigits(code)) {
			return false;
		}
		return true;
	}

}
