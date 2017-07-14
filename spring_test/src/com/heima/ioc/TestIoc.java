package com.heima.ioc;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIoc {

	@Test
	public void demo1(){
//		UserService us =new UserServiceImpl();
//		us.addUser();
		
		//1 获得容器
		String xmlPath="com/heima/ioc/beans.xml";
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext(xmlPath);
		//2获取内容
		UserService us =(UserService) applicationContext.getBean("userServiceId");
		us.addUser();
		
	}
}
