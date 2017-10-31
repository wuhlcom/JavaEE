package fish.yu.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.List;
/**
 * 生成随机数工具类    随机生成四位随机数
 * @author yuliang-ds1
 *
 */
public class RandomStringUtil {
	
	public static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }    
	
	
	public static String generateSaltWord() {    
        String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",    
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",    
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",    
                "W", "X", "Y", "Z" };    
        List list = Arrays.asList(beforeShuffle);    
        Collections.shuffle(list);    
        StringBuilder sb = new StringBuilder();    
        for (int i = 0; i < list.size(); i++) {    
            sb.append(list.get(i));    
        }    
        String afterShuffle = sb.toString();    
        String result = afterShuffle.substring(5, 9);    
        return result;    
    } 
	
	 public static void main(String[] args) {
		
		 System.out.println(generateSaltWord());
	 }

}
