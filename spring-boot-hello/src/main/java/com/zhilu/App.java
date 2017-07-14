package com.zhilu;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 使用@SpringBootApplication指定这是一个spring boot的应用程序
 *
 */
@SpringBootApplication
public class App extends WebMvcConfigurerAdapter
{
	
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		//1定義converter轉消息對象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		
		//添加fastjson配置信息
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		
		//在converter中添加fastjson配置信息
		HttpMessageConverter<?> converter = fastConverter;
		
		//將converter添加到converters中
		return new HttpMessageConverters(converter);
	}
	
    public static void main( String[] args )
    {
        /*
         * 在main方法进行启动应用程序
         */
    	SpringApplication.run(App.class, args);
    }
//
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		// TODO Auto-generated method stub
//		super.configureMessageConverters(converters);
//		
//		/*
//		 * 1、先定义一个convert转换消息的对象
//		 * 2、添加fastJson的配置信息，比如：是否要格式化返回的json数据
//		 * 3、在convert中添加配置信息
//		 * 4、将convert添加到converters当中
//		 */
//		
//		// 1、先定义一个convert转换消息的对象
//		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//		
//		// 2、添加fastJson的配置信息，比如：是否要格式化返回的json数据
//		FastJsonConfig fastJsonConfig = new FastJsonConfig();
//		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//		
//		// 3、在convert中添加配置信息
//		fastConverter.setFastJsonConfig(fastJsonConfig);
//		
//		// 4、将convert添加到converters当中
//		converters.add(fastConverter);			
//	}
    
    
}
