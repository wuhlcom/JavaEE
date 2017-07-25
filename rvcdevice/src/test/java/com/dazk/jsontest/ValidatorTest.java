package com.dazk.jsontest;

import com.dazk.common.util.RegexUtil;
import com.dazk.validator.JsonParamValidator;

/**
 * Created by Administrator on 2017/7/20.
 */
public class ValidatorTest {
    public static void main(String[] args) {
        boolean bol = JsonParamValidator.isHouseCode("0010010101010101");
        System.out.println(bol);
        System.out.println(RegexUtil.isMac(""));
    }
}
