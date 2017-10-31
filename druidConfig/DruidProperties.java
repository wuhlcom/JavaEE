/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月26日 下午3:35:58 * 
*/ 
package com.login.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.datasource")
public class DruidProperties {
	
//	    @Value("${spring.datasource.url:#{null}}")
	    private String url;
	    
//	    @Value("${spring.datasource.username: #{null}}")
	    private String username;
	    
//	    @Value("${spring.datasource.password:#{null}}")
	    private String password;
	    
//	    @Value("${spring.datasource.driverClassName:#{null}}")
	    private String driverClassName;
//	    @Value("${spring.datasource.initialSize:#{null}}")
	    private Integer initialSize;
//	    @Value("${spring.datasource.minIdle:#{null}}")
	    private Integer minIdle;
//	    @Value("${spring.datasource.maxActive:#{null}}")
	    private Integer maxActive;
//	    @Value("${spring.datasource.maxWait:#{null}}")
	    private Integer maxWait;
//	    @Value("${spring.datasource.timeBetweenEvictionRunsMillis:#{null}}")
	    private Integer timeBetweenEvictionRunsMillis;
//	    @Value("${spring.datasource.minEvictableIdleTimeMillis:#{null}}")
	    private Integer minEvictableIdleTimeMillis;
//	    @Value("${spring.datasource.validationQuery:#{null}}")
	    private String validationQuery;
//	    @Value("${spring.datasource.testWhileIdle:#{null}}")
	    private Boolean testWhileIdle;
//	    @Value("${spring.datasource.testOnBorrow:#{null}}")
	    private Boolean testOnBorrow;
//	    @Value("${spring.datasource.testOnReturn:#{null}}")
	    private Boolean testOnReturn;
//	    @Value("${spring.datasource.poolPreparedStatements:#{null}}")
	    private Boolean poolPreparedStatements;
//	    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize:#{null}}")
	    private Integer maxPoolPreparedStatementPerConnectionSize;
//	    @Value("${spring.datasource.filters:#{null}}")
	    private String filters;
//	    @Value("{spring.datasource.connectionProperties:#{null}}")
	    private String connectionProperties;
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getDriverClassName() {
			return driverClassName;
		}
		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}
		public Integer getInitialSize() {
			return initialSize;
		}
		public void setInitialSize(Integer initialSize) {
			this.initialSize = initialSize;
		}
		public Integer getMinIdle() {
			return minIdle;
		}
		public void setMinIdle(Integer minIdle) {
			this.minIdle = minIdle;
		}
		public Integer getMaxActive() {
			return maxActive;
		}
		public void setMaxActive(Integer maxActive) {
			this.maxActive = maxActive;
		}
		public Integer getMaxWait() {
			return maxWait;
		}
		public void setMaxWait(Integer maxWait) {
			this.maxWait = maxWait;
		}
		public Integer getTimeBetweenEvictionRunsMillis() {
			return timeBetweenEvictionRunsMillis;
		}
		public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}
		public Integer getMinEvictableIdleTimeMillis() {
			return minEvictableIdleTimeMillis;
		}
		public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		}
		public String getValidationQuery() {
			return validationQuery;
		}
		public void setValidationQuery(String validationQuery) {
			this.validationQuery = validationQuery;
		}
		public Boolean getTestWhileIdle() {
			return testWhileIdle;
		}
		public void setTestWhileIdle(Boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}
		public Boolean getTestOnBorrow() {
			return testOnBorrow;
		}
		public void setTestOnBorrow(Boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
		}
		public Boolean getTestOnReturn() {
			return testOnReturn;
		}
		public void setTestOnReturn(Boolean testOnReturn) {
			this.testOnReturn = testOnReturn;
		}
		public Boolean getPoolPreparedStatements() {
			return poolPreparedStatements;
		}
		public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
			this.poolPreparedStatements = poolPreparedStatements;
		}
		public Integer getMaxPoolPreparedStatementPerConnectionSize() {
			return maxPoolPreparedStatementPerConnectionSize;
		}
		public void setMaxPoolPreparedStatementPerConnectionSize(Integer maxPoolPreparedStatementPerConnectionSize) {
			this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
		}
		public String getFilters() {
			return filters;
		}
		public void setFilters(String filters) {
			this.filters = filters;
		}
		public String getConnectionProperties() {
			return connectionProperties;
		}
		public void setConnectionProperties(String connectionProperties) {
			this.connectionProperties = connectionProperties;
		}

}
