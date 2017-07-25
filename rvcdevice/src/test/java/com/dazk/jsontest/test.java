package com.dazk.jsontest;

import com.alibaba.fastjson.JSON;
import com.dazk.db.model.*;

/**
 * Created by Administrator on 2017/7/20.
 */
public class test {
    public static void main(String[] args){
        HouseValve obj1 = new HouseValve();
        obj1.setHouse_code("0010010101010101");
        obj1.setComm_address("1234567890");
        obj1.setCreated_at(System.currentTimeMillis()/1000);
        obj1.setId(1);
        obj1.setIsdel(0);
        obj1.setOpen(1);
        obj1.setPeriod(10);
        obj1.setOpening(0);
        obj1.setRemark("test");
        obj1.setType(1);
        obj1.setUnresolved(1);
        System.out.println("HouseValve="+JSON.toJSONString(obj1));

        HouseCalorimeter obj2 = new HouseCalorimeter();
        obj2.setHouse_code("0010010101010101");
        obj2.setComm_address("1234567890");
        obj2.setCreated_at(System.currentTimeMillis()/1000);
        obj2.setId(1);
        obj2.setIsdel(0);
        obj2.setPro_type("德邦");
        obj2.setPeriod(10);
        obj2.setPipe_size(10);
        obj2.setRemark("test");
        obj2.setType(1);
        obj2.setUnresolved(1);
        System.out.println("HouseCalorimeter="+JSON.toJSONString(obj2));

        Concentrator obj3 = new Concentrator();
        obj3.setBuilding_unique_code("0010010101");
        obj3.setAddress("东南大是大非");
        obj3.setCreated_at(System.currentTimeMillis()/1000);
        obj3.setId(1);
        obj3.setIsdel(0);
        obj3.setCode("1701221");
        obj3.setDebug_status(0);
        obj3.setGprsid("21548");
        obj3.setRemark("test");
        obj3.setIp("1.1.1.1");
        obj3.setRefresh_time(System.currentTimeMillis()/1000);
        obj3.setProtocol_type(1);
        obj3.setSim_code("54521");
        obj3.setUnresolved(1);
        System.out.println("Concentrator="+JSON.toJSONString(obj3));

        Gateway obj4 = new Gateway();
        obj4.setCompany_id(1);
        obj4.setMac("30-52-CB-04-D3-F8");
        obj4.setAddress("深圳知路");
        obj4.setCreated_at(System.currentTimeMillis()/1000);
        obj4.setId(1);
        obj4.setIsdel(0);
        obj4.setName("1号基站");
        obj4.setGprsid("21548");
        obj4.setRemark("test");
        obj4.setIp("1.1.1.1");
        obj4.setSim_code("54521");
        System.out.println("Gateway="+JSON.toJSONString(obj4));

        BuildingValve obj5 = new BuildingValve();
        obj5.setBuilding_unique_code("0010010101");
        obj5.setName("1号楼栋调节阀");
        obj5.setType(1);
        obj5.setId(1);
        obj5.setIsdel(0);
        obj5.setPeriod(10);
        obj5.setComm_address("23542cd");
        obj5.setPipe_size(20);
        obj5.setRemark("test");
        obj5.setStrategy(1);
        obj5.setKi(22.6);
        obj5.setKp(1.22);
        obj5.setKd(52.3);
        obj5.setTarget(22.6);
        obj5.setMax_target(1.22);
        obj5.setMin_target(52.3);
        obj5.setUnresolved(1);
        System.out.println("BuildingValve="+JSON.toJSONString(obj5));

        BuildingCalorimeter obj6 = new BuildingCalorimeter();
        obj6.setBuilding_unique_code("0010010101");
        obj6.setName("1号楼栋热表");
        obj6.setType(1);
        obj6.setId(1);
        obj6.setIsdel(0);
        obj6.setPeriod(25);
        obj6.setComm_address("125486545");
        obj6.setPro_type("德邦");
        obj6.setRemark("test");
        obj6.setPipe_size(25);
        obj6.setUnresolved(1);
        System.out.println("BuildingCalorimeter="+JSON.toJSONString(obj6));
    }
}
