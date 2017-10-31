package fish.yu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * admin 整个权限认证应用的开始入口；
 *
 */
//扫描指定包下面的mapper接口
//扫描指定包下面的mapper接口
@MapperScan("fish.yu.dao")

@SpringBootApplication
@EnableTransactionManagement 
@EnableCaching // 开启缓存功能
public class ApplicationMain extends SpringBootServletInitializer implements CommandLineRunner
{
	//程序入口
	public static void main( String[] args )
	{
		ConfigurableApplicationContext run = SpringApplication.run(ApplicationMain.class, args);
	}


	// Java EE应用服务器配置，
	// 如果要使用tomcat来加载jsp的话就必须继承SpringBootServletInitializer类并且重写其中configure方法
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationMain.class);
	}

	// springboot运行后此方法首先被调用
	// 实现CommandLineRunner抽象类中的run方法
	@Override
	public void run(String... args) throws Exception {
		System.out.println("springboot启动完成！");
	}
	
	

	
}
