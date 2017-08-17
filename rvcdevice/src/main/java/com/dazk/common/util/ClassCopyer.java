package com.dazk.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/8/15.
 */
public class ClassCopyer {
    /*
      * 将父类所有的属性COPY到子类中。
      * 类定义中child一定要extends father；
      * 而且child和father一定为严格javabean写法，属性为deleteDate，方法为getDeleteDate
      */
    public static void fatherToChild (Object father,Object child){
        try{
            if(!(child.getClass().getSuperclass()==father.getClass())){
                throw new Exception("child不是father的子类");
            }
            Class fatherClass= father.getClass();
            Field ff[]= fatherClass.getDeclaredFields();
            for(int i=0;i<ff.length;i++){
                Field f=ff[i];//取出每一个属性，如deleteDate
                Class type=f.getType();
                Method m=fatherClass.getMethod("get"+upperHeadChar(f.getName()));//方法getDeleteDate
                Object obj=m.invoke(father);//取出属性值
                f.setAccessible(true);
                f.set(child,obj);
            }
        }catch (Exception e){

        }
    }
    /**
     * 首字母大写，in:deleteDate，out:DeleteDate
     */
    private static String upperHeadChar(String in){
        String head=in.substring(0,1);
        String out=head.toUpperCase()+in.substring(1,in.length());
        return out;
    }
}
