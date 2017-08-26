/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月22日 上午10:17:35 * 
*/ 
package com.dazk.common.util;

import java.util.HashMap;
import java.util.Map;

public class DataAuthUtil {
	public final static Map<String, String> NAV_ITEM_ADPTER = new HashMap<String, String>() {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
	    	put("/company/query", "q");
			put("/company/add", "a");
			put("/company/update", "u");
			put("/company/delete", "d");
			
			put("/hotStation/query", "q");
			put("/hotStation/add", "a");
			put("/hotStation/update", "u");
			put("/hotStation/delete", "d");

			put("/community/query", "q");
			put("/community/add", "a");
			put("/community/update", "u");
			put("/community/delete", "d");
	    }
	};
}
