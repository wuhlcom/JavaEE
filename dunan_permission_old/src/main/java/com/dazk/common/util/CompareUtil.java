/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年9月14日 下午4:36:53 * 
*/
package com.dazk.common.util;

import java.util.Comparator;
import com.dazk.db.model.Menu;

public class CompareUtil implements Comparator<Object> {	
	//按id升序排列
	@Override
	public int compare(Object o1, Object o2) {
		Menu p1 = (Menu) o1;
		Menu p2 = (Menu) o2;	
		if (p1.getId() > p2.getId()){
			return 1;
		}else if(p1.getId() == p2.getId()){
			return 0; //去除重复
		}else{
			return -1;
		}
	}
}
