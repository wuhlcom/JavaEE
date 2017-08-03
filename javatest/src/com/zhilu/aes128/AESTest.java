/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月2日 下午2:48:07 * 
*/ 
package com.zhilu.aes128;

import org.junit.Test;

public class AESTest {
    
    private static String key="2b7e151628aed2a6abf7158809cf4f3c";
    
    @Test
    public void test_all(){
    	System.out.println("11111111111111111111111111111");
        String enOri="000000000000000WAZX-B55SY6-S6DT5";
        String enHex=HexUtil.strToHex(enOri);
        System.out.println("enHex为："+enHex);
        String enRS=AESCoder.ecbEnc(enHex,key);     
        System.out.println("加密结果为："+enRS);
        
//        String deHex="7312560ccb30ad9b445ee94b426c8a2bdf75d11ded50f053568ec08bf3f9be04";
        String deHex="7312560ccb30ad9b445ee94b426c8a2bdf75d11ded50";
        String deRS=AESCoder.ecbDec(deHex,key);
        String deOri=HexUtil.hexToStr(deRS);
        System.out.println("解密结果为："+deOri);
    }
    
    
    @Test
    public void test_enc(){
        String enStr ="6bc1bee22e409f96e93d7e117393172a";        
        String enRS=AESCoder.ecbEnc(enStr,key);        
        System.out.println(enRS);
    }
    
    @Test
    public void test_dec(){
        String deStr ="3ad77bb40d7a3660a89ecaf32466ef97";        
        String enRS=AESCoder.ecbDec(deStr,key);        
        System.out.println(enRS);

    }
    
    
    
    /**
     * 和后台联调时使用
     * 该函数用于讲16进制数组转成String
     * 如密钥key为
     * uint8_t key[] = 
     *         {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 
     *          0xab, 0xf7, 0x15, 0x88, 0x09, 0xcf, 0x4f, 0x3c}
     * 则格式化之后为"2b7e151628aed2a6abf7158809cf4f3c"
     * */
    public static String convertStr(String hexStr) {
        String[] kStrs = hexStr.split(",");
        String[] keyStrs = new String[kStrs.length];
        for (int i = 0; i < kStrs.length; i++) {
            String str = kStrs[i].trim().substring(2);
            keyStrs[i] = str;
        }
        StringBuffer sb = new StringBuffer();
        for (String str : keyStrs) {
            sb.append(str);
        }
        return sb.toString().toUpperCase();
    }

}