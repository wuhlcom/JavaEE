/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月13日 下午2:20:49 * 
*/ 
package com.prison.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class DefaultView extends WebMvcConfigurerAdapter{
    public static String LOGIN_USER = "loginUser";
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	//指定默认的视图
        registry.addViewController("/").setViewName("forward:/front/register");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    } 
}