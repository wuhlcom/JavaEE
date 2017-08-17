package com.dazk;

import com.dazk.global.RedisInit;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
@SpringBootApplication
@MapperScan(basePackages = "com.dazk.db.dao")
public class RvcdeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RvcdeviceApplication.class, args);
	}
}
