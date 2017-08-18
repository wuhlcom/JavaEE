/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月18日 下午2:05:09 * 
*/ 
package StringTest;
public class testString {

	public static void main(String[] args) {
		System.out.println(isCondition("and"));
	}
	
	public static boolean isCondition(String con) {
		return (con.toLowerCase()=="and"||con.toLowerCase()=="or");
	}

}
