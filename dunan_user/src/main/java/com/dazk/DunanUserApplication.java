package com.dazk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.dazk.db.dao")
public class DunanUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(DunanUserApplication.class, args);
	}
}
