package com.youngor.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mawujun.controller.spring.SpringContextHolder;
import com.youngor.permission.ShiroAuthorizingRealm;
import com.youngor.permission.ShiroKickoutSessionControlFilter;
import com.youngor.permission.UserService;

@Configuration
//@ComponentScan(basePackages = "com.youngor", excludeFilters = @Filter(type = FilterType.ANNOTATION, value = { Controller.class }))
//@MapperScan(basePackages = "com.youngor")
//@PropertySource("classpath:config.properties")
public class ShiroConfig{
	
//	@Autowired
//	UserService userService;
//	@Inject
//	RepositoryConfig repositoryConfig;
	
	@Bean
	public DefaultWebSecurityManager securityManager(){
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm());
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}
	@Bean
	public Realm shiroRealm(){
		ShiroAuthorizingRealm shiroRealm=new ShiroAuthorizingRealm();

		shiroRealm.setUserService(SpringContextHolder.getBean(UserService.class));
		//shiroRealm.setCacheManager();
		return shiroRealm;
	}
	@Bean//(name = "shiroFilter")
	public Filter shiroFilter() throws Exception{
		ShiroFilterFactoryBean shiroFilter=new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager());
		shiroFilter.setLoginUrl("/main/login.jsp");
		shiroFilter.setSuccessUrl("/main/index.jsp");
		shiroFilter.setUnauthorizedUrl("/main/unauthorized.jsp");
		
		
//		Map<String,Filter> filters=new HashMap<String,Filter>();
//		filters.put("authc", new ShiroFormAjaxAuthenticationFilter()());
//		filters.put("perms", new ShiroURLPermissionsFilter()());	
		//filters.put("kickout", shiroKickoutSessionControlFilter);
		//shiroFilter.setFilters(filters);
		ShiroKickoutSessionControlFilter shiroKickoutSessionControlFilter=new ShiroKickoutSessionControlFilter();
		shiroKickoutSessionControlFilter.setKickoutUrl("/main/login.jsp");
		shiroKickoutSessionControlFilter.setMaxSession(1);
		shiroKickoutSessionControlFilter.setKickoutAfter(false);
		shiroKickoutSessionControlFilter.setCacheManager(ehcacheManager());
		//还要添加到下面的路径中，哪些地址进行拦截
		//filterChainDefinitionMap.put("/**/*.jsp", "authc,perms,kickout");//表示就在这里进行登录
		shiroFilter.getFilters().put("kickout", shiroKickoutSessionControlFilter);
		
		
		
		//http://blog.csdn.net/catoop/article/details/50520958
		Map<String,String> filterChainDefinitionMap=new HashMap<String,String>();
		//filterChainDefinitionMap.put("/**", "anon");//这个是临时的
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/main/login.jsp*", "anon");
		filterChainDefinitionMap.put("/main/unauthorized.jsp*", "anon");
		filterChainDefinitionMap.put("/user/login.do*", "anon");
		filterChainDefinitionMap.put("/user/logout.do*", "anon");
		filterChainDefinitionMap.put("/**/*.css", "anon");
		filterChainDefinitionMap.put("/**/*.js", "anon");
		filterChainDefinitionMap.put("/**/*.gif", "anon");
		filterChainDefinitionMap.put("/**/*.jpg", "anon");
		filterChainDefinitionMap.put("/**/*.png	", "anon");
	
		filterChainDefinitionMap.put("/**/*.jsp", "authc,perms,kickout");
		filterChainDefinitionMap.put("/**", "authc");
		//filterChainDefinitionMap.put("", "");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return (Filter)shiroFilter.getObject();
		
	}
	
//	@Bean
//	public ShiroFormAjaxAuthenticationFilter shiroFormAjaxAuthenticationFilter(){
//		ShiroFormAjaxAuthenticationFilter formAjaxAuthenticationFilter=new ShiroFormAjaxAuthenticationFilter();
//		return formAjaxAuthenticationFilter;
//	}
//	
//	@Bean
//	public ShiroURLPermissionsFilter shiroURLPermissionsFilter(){
//		ShiroURLPermissionsFilter shiroURLPermissionsFilter=new ShiroURLPermissionsFilter();
//		return shiroURLPermissionsFilter;
//	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		LifecycleBeanPostProcessor lifecycleBeanPostProcessor=new LifecycleBeanPostProcessor();
		return lifecycleBeanPostProcessor;
	}
	
	@Bean
    public EhCacheManager ehcacheManager() {  
        EhCacheManager em = new EhCacheManager();  
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");  
        return em;  
    } 
	
	//回话管理部分
	/**
	 * 会话Cookie模板 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	//@Bean
	public SimpleCookie sessionIdCookie(){
		SimpleCookie simpleCookie=new SimpleCookie("sid");
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(6000000);
		return simpleCookie;
	}
	//@Bean
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		JavaUuidSessionIdGenerator sessionIdGenerator=new JavaUuidSessionIdGenerator();
		return sessionIdGenerator;
	}
	//@Bean
	public EnterpriseCacheSessionDAO sessionDAO() {
		EnterpriseCacheSessionDAO sessionDAO=new EnterpriseCacheSessionDAO();
		sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		sessionDAO.setSessionIdGenerator(sessionIdGenerator());
		return sessionDAO;
	}	
	//@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(1800000);//
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		//sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
		sessionManager.setSessionDAO(sessionDAO());
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		//监听会话的开始，结束的监听器，实现SessionListenerAdapter方类
		//sessionManager.setSessionListeners();
		return sessionManager;
	}
	/**
	 * 会话验证调度器
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	//@Bean
	public ExecutorServiceSessionValidationScheduler sessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler sessionValidationScheduler=new ExecutorServiceSessionValidationScheduler();
		sessionValidationScheduler.setInterval(120000);
		sessionValidationScheduler.setSessionManager( sessionManager());
		return sessionValidationScheduler;
	}
}
