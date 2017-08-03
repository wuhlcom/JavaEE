/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月2日 下午2:46:39 * 
*/ 
package com.zhilu.aes128;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import com.zhilu.aes128.HexUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESCoder {
    
    private static final Logger log=LoggerFactory.getLogger(AESCoder.class);
    
    /**
     * 加密
     * hexStr和hexKey都须为16进制表示的字符串
     * 加密后返回16进制表示的字符串*/
    public static String ecbEnc(String hexStr, String hexKey){
        String rs=null;
        try {
            byte[] inBytes = HexUtil.hexToBytes(hexStr);            
            byte[] keyBytes = HexUtil.hexToBytes(hexKey);
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(inBytes);            
            rs=HexUtil.bytesToHex(encrypted);
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("加密异常",e);
            log.error("输入参数为hexStr：{},hexKey:{}",hexStr,hexKey);
        }
        return rs;
    }
    
    /**
     * 解密
     * hexStr和hexKey都须为16进制
     * 加密后返回16进制的字符串*/
    public static String ecbDec(String hexStr,String hexKey){
        String rs=null;
        try {
            byte[] outBytes = HexUtil.hexToBytes(hexStr);
            byte[] keyBytes = HexUtil.hexToBytes(hexKey);
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "算法/模式/补码方式"
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decBytes = cipher.doFinal(outBytes);
            rs=HexUtil.bytesToHex(decBytes);
        } catch (Exception e) {
            log.error("解密异常",e);
            log.error("输入参数为hexStr：{},hexKey:{}",hexStr,hexKey);
        }
        return rs;
    }

}
