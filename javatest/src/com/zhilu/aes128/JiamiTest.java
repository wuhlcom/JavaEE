/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月2日 下午4:25:51 * 
*/ 
package com.zhilu.aes128;
import com.zhilu.aes128.JiaMi;
public class JiamiTest {
	public static void main(String[]args) {
		String content = "test";  
		String password = "12345678";  
		//加密  
		System.out.println("加密前：" + content);  
		byte[] encryptResult = JiaMi.encrypt(content, password);  
		//解密  
		byte[] decryptResult = JiaMi.decrypt(encryptResult,password);  
		System.out.println("解密后：" + new String(decryptResult)); 
	}
}
