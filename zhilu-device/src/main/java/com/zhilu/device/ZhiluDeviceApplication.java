package com.zhilu.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@SpringBootApplication
public class ZhiluDeviceApplication {
	// 添加此配置后可以使用JSONField注解
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1定義converter轉消息對象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

		// 添加fastjson配置信息
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);

		// 在converter中添加fastjson配置信息
		HttpMessageConverter<?> converter = fastConverter;

		// 將converter添加到converters中
		return new HttpMessageConverters(converter);
	}

	public static void main(String[] args) {
		SpringApplication.run(ZhiluDeviceApplication.class, args);
	}
}
