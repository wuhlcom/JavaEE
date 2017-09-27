/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年9月25日 下午4:55:35 * 
*/
package com.dazk.common.util;

import java.util.Comparator;

import com.dazk.db.model.User;

public class UserCompareUtil implements Comparator<Object> {
	// 按id升序排列
	@Override
	public int compare(Object o1, Object o2) {
		User p1 = (User) o1;
		User p2 = (User) o2;
		if (p1.getId() > p2.getId()) {
			return 1;
		} else if (p1.getId() == p2.getId()) {
			return 0; // 去除重复
		} else {
			return -1;
		}
	}
}
