package com.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan(basePackages = "com.login.db.dao")
@ServletComponentScan	//扫描Servlet
@SpringBootApplication
public class PrisonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrisonApplication.class, args);
	}
}
