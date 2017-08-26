/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月22日 上午11:04:35 * 
*/
package classtest;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class DataAuth {

	public static void main(String[] args) {
		JSONObject js = new JSONObject();
		js.put("a",1);
		String a=js.getString("a");
		System.out.println(a);
		System.out.println(("1"==a));
		System.out.println((a.equals("1")));
		
//		int a = 1;
//		if (a == 2) {
//			System.out.println("if");
//		} else {
//			System.out.println("else");
//		}
//
//		String v = DataAuthUtil.NAV_ITEM_ADPTER.get("/company/query");
//		System.out.println(v);
		
		
		String b="1";
		System.out.println(b=="1");
		System.out.println(b.equals("1"));
	}

}
