/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月26日 下午4:27:33 * 
*/
package com.login.config;

import org.springframework.beans.factory.annotation.Value;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan("com.login.controller")
public class Swagger2Config {

	@Value("${server.context-path}")
	private String pathMapping;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("RestfulApi")
				.genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(true).forCodeGeneration(false)
				.apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.login.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot中使用Swagger2构建RESTful APIs").description(initContextInfo())
				.termsOfServiceUrl("http://blog.didispace.com/").contact("zhilutec").version("1.0").build();
	}

	/**
	 * 设置过滤规则 这里的过滤规则支持正则匹配
	 * 
	 * @return
	 */
	private Predicate<String> doFilteringRules() {
		return or(regex("/hello.*"), regex("/vehicles.*"));
	}

	private String initContextInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("REST API 设计在细节上有很多自己独特的需要注意的技巧，并且对开发人员在构架设计能力上比传统 API 有着更高的要求。").append("<br/>")
				.append("本文通过翔实的叙述和一系列的范例，从整体结构，到局部细节，分析和解读了为了提高易用性和高效性，REST API 设计应该注意哪些问题以及如何解决这些问题。");
		return sb.toString();
	}

//	@Bean
//	public Docket swaggerSpringMvcPlugin() {
//		ApiInfo apiInfo = new ApiInfo("sample of springboot", "sample of springboot", null, null, null, null, null);
//		Docket docket = new Docket(DocumentationType.SWAGGER_2).select().paths(regex("/user/.*")).build()
//				.apiInfo(apiInfo).useDefaultResponseMessages(false);
//		return docket;
//	}

}
