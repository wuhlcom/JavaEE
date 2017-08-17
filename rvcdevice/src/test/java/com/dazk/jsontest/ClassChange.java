package com.dazk.jsontest;

import com.alibaba.fastjson.JSON;
import com.dazk.db.model.HouseValve;
import com.dazk.db.model.HouseValveData;

/**
 * Created by Administrator on 2017/7/28.
 */
public class ClassChange
{
    public static void main(String[] args) {
        HouseValve aa = new HouseValve();
        aa.setPage(1);
        aa.setListRows(1);
        aa.setCreated_at(1l);
        aa.setIsdel(1);
        aa.setUnresolved(1);
        aa.setType(1);
        aa.setOpening(1);
        aa.setOpen(1);
        aa.setPeriod(1);
        aa.setId(1);
        aa.setVersion("1");
        aa.setRemark("1");
        aa.setPower_type(1);
        aa.setHouse_code("1");

        HouseValveData bb = new HouseValveData(aa);
        bb.setReturn_temp(1d);
        System.out.println(JSON.toJSON(bb));
    }
}
