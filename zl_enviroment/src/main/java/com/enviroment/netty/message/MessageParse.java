/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 下午2:40:07 * 
*/ 
package com.enviroment.netty.message;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.enviroment.service.GasService;
import com.enviroment.service.HeatService;
import com.enviroment.service.PowerService;
import com.enviroment.service.WaterService;


@Component//为实现@Resource静态注解而添加
public class MessageParse { 	
	
	public MessageParse() {
	}	

	@Resource //为实现@Resource静态注解而添加
    private PowerService powerService; 
	
	@Resource //为实现@Resource静态注解而添加
    private GasService gasService; 
	
	@Resource //为实现@Resource静态注解而添加
    private WaterService waterService; 
	
	@Resource //为实现@Resource静态注解而添加
    private HeatService heatService; 

    // 维护一个本类的静态变量
    public static MessageParse messageParse; //为实现@Resource静态注解而添加

    // 初始化的时候，将本类中的sysConfigManager赋值给静态的本类变量
    @PostConstruct //为实现@Resource静态注解而添加
    public void init() {
    	messageParse = this;
    	messageParse.powerService = this.powerService;
    	messageParse.gasService = this.gasService;
    	messageParse.waterService = this.waterService;
    	messageParse.heatService = this.heatService;
    }	
 
    
    //1 电   2 水 3 热 4 燃
    public static int classification(String message){
    	System.out.println("1--------------------classification");
    	try {    
			JSONObject jsonObj = JSONObject.parseObject(message);
		
			System.out.println(jsonObj);
			
			Integer devType=jsonObj.getInteger("dev_type");    	
			if (devType==1) {
				return messageParse.powerService.add(jsonObj);
			} else if (devType==2) {
				return messageParse.waterService.add(jsonObj);				
			} else if (devType==3) {
				return messageParse.heatService.add(jsonObj);				
			} else if (devType==4) {
				return messageParse.gasService.add(jsonObj);				
			} else {
			    return 0;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
    }
}
