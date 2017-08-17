package com.dazk.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class StrUtil {
    public static boolean hasPerIn(String comple,List<String> pers){
        for(String per : pers){
            if(RegexUtil.isStartAs(comple,per)) return true;
        }
        return false;
    }
    public static List<String> filterWithPer(List<String> comples,String per){
        List<String> res = new ArrayList<String>();
        for(String str : comples){
            if(RegexUtil.isStartAs(str,per)) res.add(str);
        }
        return res;
    }
}
