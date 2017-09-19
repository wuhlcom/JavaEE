/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年9月14日 上午10:18:50 * 
*/ 
package reg;
public class regtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String reg = "001";
		String content = "02001204";
		//为什么isMatch返回false?
//		boolean isMatch1 = content.matches(reg);
//		System.out.println(isMatch1);
		String st1 = "dsadas";
	      System.out.println("st1:"+st1.startsWith("sa",1));
	      String st2 = "dsadas";
	      System.out.println("st1:"+st2.startsWith("sa"));
	      String st3 = "dsadas";
	      System.out.println("st2:"+st3.startsWith("ds"));
	      String st4 = "dsadas";
	      System.out.println("st4:"+st4.startsWith("ds",0));
	      String st5 = "dsadas";
	      System.out.println("st5:"+st5.startsWith("ds",1));
		
//		Pattern p = Pattern.compile(reg);
//		Matcher m = p.matcher(reg);
//		boolean b = m.matches();
//		System.out.println(b);	
		
		 
//		 boolean isMatch2 = Pattern.matches("^001", content);
//		 System.out.println(isMatch2);
		
//		 boolean isMatch3=Pattern.compile(reg).matcher(content).matches();
//		 System.out.println(isMatch3);
	}

}
