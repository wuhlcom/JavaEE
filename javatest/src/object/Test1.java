import java.util.ArrayList;
public class Test1 {
	public static void main(String[] args) {
    /*
	 int protocol = 1;
     if (protocol == 0 || protocol == 1 || protocol == 2 || protocol == 3) {
		System.out.println("aaaaaaaaa");
	 }
	 */
	 //�����ַ�תInteger
	//Integer in= Integer.parseInt("111");
	//System.out.println(in);
	//System.out.println(in.getClass());

    String str1= " abc";
	System.out.println(str1);	 
	System.out.println(str1.trim());

	String sourceStr="[\"wu005\",\"wu002\"]".trim();
	System.out.println(sourceStr);

	str1 = sourceStr.substring(0,sourceStr.length()-1);
	System.out.println(str1);	

	String newstr1 = sourceStr.substring(1,sourceStr.length()-1);
	System.out.println(str1);	

	String[] sourceStrArray = newstr1.split(",");
        for (int i = 0; i < sourceStrArray.length; i++) {
            System.out.println(sourceStrArray[i]);
        }
	}
}
