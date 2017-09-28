package com.dazk;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@MapperScan(basePackages = "com.dazk.db.dao")
@EnableEurekaClient
public class DunanUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(DunanUserApplication.class, args);
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	   return (container -> {
	        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/static/404.html");
	        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/static/404.html");
	        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/static/404.html");

	        container.addErrorPages(error401Page, error404Page, error500Page);
	   });
	}

}
