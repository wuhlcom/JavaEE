package fish.yu.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import fish.yu.entity.MyPermissionInit;
import fish.yu.service.MyPermissionInitService;
import fish.yu.shiro.MyCredentialsMatcher;
import fish.yu.shiro.MyShiroRealm;
import fish.yu.shiro.filter.CustomPermissionsAuthorizationFilter;
import fish.yu.shiro.filter.CustomRoleOrAuthorizationFilter;
import fish.yu.shiro.filter.KickoutSessionControlFilter;

/**
 * @author Fish
 * @date 创建时间：2017年3月26日 下午1:16:38
 * 
 */
@Configuration
public class ShiroConfig {
	
	
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	
	@Autowired
	MyPermissionInitService permissionInitService;
	

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 *
	 * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 *
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		  System.out.println(" ShiroFilterFactoryBean-securityManager:"+securityManager);

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		
		//自定义拦截器 系列
		Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
		//限制同一帐号同时在线的个数。
		filtersMap.put("kickout", kickoutSessionControlFilter());
		//限制模块访问权限
		filtersMap.put("customperms", customPermissionsAuthorizationFilter());
		//限制角色访问权限，or关系判断,默认并关系
		filtersMap.put("customroleor", customRoleOrAuthorizationFilter());
		
		shiroFilterFactoryBean.setFilters(filtersMap);
		
		// 权限控制map.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		// 从数据库获取动态的权限
		// filterChainDefinitionMap.put("/add", "perms[权限添加]");
		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		//logout这个拦截器是shiro已经实现好了的。
		// 从数据库获取
		//userService.queryAllUsers(new FrontPage<UserVO>());
		System.out.println("开始加载数据库初始化权限.....");
		long currentTimeMillis = System.currentTimeMillis();
		List<MyPermissionInit> list = permissionInitService.selectAll();
		
		for (MyPermissionInit PermissionInit : list) {
			filterChainDefinitionMap.put(PermissionInit.getUrl(),
					PermissionInit.getPermissionInit());
		}
		long currentTimeMillis2 = System.currentTimeMillis();
		System.out.println("结束加载数据库初始化权限.....:"+(currentTimeMillis2-currentTimeMillis)+"毫秒");
		shiroFilterFactoryBean
				.setFilterChainDefinitionMap(filterChainDefinitionMap);
		System.out.println("Shiro拦截器工厂类注入成功");
		return shiroFilterFactoryBean;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(myShiroRealm());
		
		// 自定义缓存实现 使用redis
		securityManager.setCacheManager(cacheManager());
		
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		
		//注入记住我管理器;
	    securityManager.setRememberMeManager(rememberMeManager());
	    
	    
		return securityManager;
	}

	/**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 * 
	 * @return
	 */
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(myCredentialsMatcher());
		return myShiroRealm;
	}
	
	/**
	 * 自定义密码认证器
	 */
	@Bean 
	public  CredentialsMatcher  myCredentialsMatcher(){
		
		CredentialsMatcher cm=new MyCredentialsMatcher();
		
		return cm;
	}
	

	/**
	 * 配置shiro redisManager
	 * 
	 * @return
	 */
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setPort(port);
		System.out.println("RedisManager Host:"+host +"   port:"+port);
		redisManager.setExpire(1800);// 配置缓存过期时间
		// redisManager.setTimeout(timeout);
		// redisManager.setPassword(password);
		return redisManager;
	}

	/**
	 * cacheManager 缓存 redis实现
	 * 
	 * @return
	 */
	public RedisCacheManager cacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	/**
	 * RedisSessionDAO shiro sessionDao层的实现 通过redis
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}

	/**
	 * Session Manager
	 */
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO());
		//设置seesion有效时间
		sessionManager.setGlobalSessionTimeout(1800000);
		return sessionManager;
	}
	
	/**
     * cookie对象;
     * @return
     */
    public SimpleCookie rememberMeCookie(){
       //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
       SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
       //<!-- 记住我cookie生效时间30天 ,单位秒;-->
       simpleCookie.setMaxAge(2592000);
       return simpleCookie;
    }
    
    /**
     * cookie管理对象;记住我功能
     * @return
     */
    public CookieRememberMeManager rememberMeManager(){
       CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
       cookieRememberMeManager.setCookie(rememberMeCookie());
       //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
       cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
       return cookieRememberMeManager;
    }
    
    /**
     * 限制同一账号登录同时登录人数控制
     * @return
     */
    public KickoutSessionControlFilter kickoutSessionControlFilter(){
    	KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
    	//使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
    	//这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
    	//也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
    	kickoutSessionControlFilter.setCacheManager(cacheManager());
    	//用于根据会话ID，获取会话进行踢出操作的；
    	kickoutSessionControlFilter.setSessionManager(sessionManager());
    	//是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
    	kickoutSessionControlFilter.setKickoutAfter(false);
    	//同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
    	kickoutSessionControlFilter.setMaxSession(1);
    	//被踢出后重定向到的地址；
    	kickoutSessionControlFilter.setKickoutUrl("/kickout");
        return kickoutSessionControlFilter;
     }
    
    /**
     * 自定义模块权限过滤器
     * @return
     */
    public CustomPermissionsAuthorizationFilter   customPermissionsAuthorizationFilter(){
    	
    	
    	CustomPermissionsAuthorizationFilter customPermissionsAuthorizationFilter = new CustomPermissionsAuthorizationFilter();
    	
        return customPermissionsAuthorizationFilter;
    	
    	
    }
    
    
    /**
     *  自定义角色过滤器
     */
    public CustomRoleOrAuthorizationFilter   customRoleOrAuthorizationFilter(){
    	
    	
    	CustomRoleOrAuthorizationFilter customPermissionsAuthorizationFilter = new CustomRoleOrAuthorizationFilter();
    	
        return customPermissionsAuthorizationFilter;
    	
    	
    }
    
    
    
   /* @Bean  
    public MyShiroRealm myShiroRealm(){  
        MyShiroRealm myShiroRealm = new MyShiroRealm();//这里实例化一个我们自己写的MyShiroRealm类  
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());;  
        return myShiroRealm;  
    }  
      
  
    @Bean  
    public HashedCredentialsMatcher hashedCredentialsMatcher(){  
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();  
          
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;  
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));  
          
        return hashedCredentialsMatcher;  
    }  */
    //开启shiro aop注解支持  

    

    @Bean   
    public static  LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {  
    	LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();  
    	System.out.println("LifecycleBeanPostProcessor:"+lifecycleBeanPostProcessor);
        return lifecycleBeanPostProcessor;    
    }
        
    /*  @Bean
	@ConditionalOnMissingBean
	@DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }*/
	
	@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return aasa;
    }
	
    
    //注入缓存  
    /*@Bean  
    public EhCacheManager ehCacheManager(){  
        System.out.println("ShiroConfiguration.getEhCacheManager()执行");  
        EhCacheManager cacheManager=new EhCacheManager();  
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");  
        return cacheManager;  
    }*/
    
    
   
}
