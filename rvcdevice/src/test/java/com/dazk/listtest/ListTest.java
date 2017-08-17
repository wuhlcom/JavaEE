package com.dazk.listtest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> codes = new ArrayList<String>();
        codes.add("1");
        codes.add("2");
        codes.add("3");
        codes.remove(0);
        System.out.println(codes.get(2));
    }
}
