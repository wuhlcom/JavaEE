package com.zhilu.aes128;
public class AlgorithmTest {

    public static void main(String[] args) {
        try {
            String hexKey = new AlgorithmUtil().getAESKey();
            System.out.println("16进制秘钥："+hexKey);
            byte[] encoded = AlgorithmUtil.getAESEncode(hexKey, "我要把你嘿嘿嘿");
            // 注意，这里的encoded是不能强转成string类型字符串的
            byte[] decoded = AlgorithmUtil.getAESDecode(hexKey, encoded);
            System.out.println(new String(decoded, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}