/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月30日 上午9:53:01 * 
*/ 
package com.login.config.druid;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DruidDataSourceConfiguration {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource(){
		DataSource druidDataSource = new DruidDataSource();
		return druidDataSource;
	}
}
