import java.util.ArrayList;
import java.util.Date;
public class Test1 {
	public static void main(String[] args) {
    /*
	 int protocol = 1;
     if (protocol == 0 || protocol == 1 || protocol == 2 || protocol == 3) {
		System.out.println("aaaaaaaaa");
	 }
	 */
	 //数字字符转Integer
	//Integer in= Integer.parseInt("111");
	//System.out.println(in);
	//System.out.println(in.getClass());

 /*
	String str1= " abc";
	System.out.println(str1);	 
	System.out.println(str1.trim());

    //去首尾空格
	String sourceStr="[\"wu005\",\"wu002\"]".trim();
	System.out.println(sourceStr);

	str1 = sourceStr.substring(0,sourceStr.length()-1);
	System.out.println(str1);	

	String newstr1 = sourceStr.substring(1,sourceStr.length()-1);
	System.out.println(str1);	

    //分割
	String[] sourceStrArray = newstr1.split(",");
        for (int i = 0; i < sourceStrArray.length; i++) {
            System.out.println(sourceStrArray[i]);
        }
		*/
     1 instan
	}

	
  public static void removeQuto(String str){
	  	System.out.println(str);
		String newStr=str.substring(1,str.length()-1);
		System.out.println(newStr);
	
	}


  public static void isQuto(String str){
	 boolean rs=  (str.charAt(0)=='\"');
	 System.out.println(rs);
	 boolean rs2=  (str.charAt(str.length()-1)=='\"');
	 System.out.println(rs2);	 
    }

  public static void headEndStr(String str){
	   System.out.println(str);
	   String newStr= str.substring(1);
	   System.out.println(newStr);	
	   newStr=newStr.substring(0,newStr.length()-1);
	   System.out.println(newStr);	
	    newStr=newStr.substring(newStr.length()-1);
	   System.out.println(newStr);	
  
  }

  public static void NullTest(){
     int i = (int)"";
	  System.out.println(i);	
  }
}
