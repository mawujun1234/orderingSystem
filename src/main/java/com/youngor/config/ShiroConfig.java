package com.youngor.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.youngor.permission.ShiroAuthorizingRealm;
import com.youngor.permission.UserService;

@Configuration
//@ComponentScan(basePackages = "com.youngor", excludeFilters = @Filter(type = FilterType.ANNOTATION, value = { Controller.class }))
//@MapperScan(basePackages = "com.youngor")
//@PropertySource("classpath:config.properties")
public class ShiroConfig{
	
	@Autowired
	UserService userService;
	@Bean
	public DefaultWebSecurityManager getSecurityManager(){
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
		securityManager.setRealm(getShiroRealm());
		//securityManager.setSessionManager();
		return securityManager;
	}
	@Bean
	public Realm getShiroRealm(){
		ShiroAuthorizingRealm shiroRealm=new ShiroAuthorizingRealm();
		shiroRealm.setUserService(userService);
		//shiroRealm.setCacheManager();
		return shiroRealm;
	}
	@Bean(name = "shiroFilter")
	public Filter getShiroFilter() throws Exception{
		ShiroFilterFactoryBean shiroFilter=new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(getSecurityManager());
		shiroFilter.setLoginUrl("/login.jsp");
		shiroFilter.setSuccessUrl("/index.jsp");
		shiroFilter.setUnauthorizedUrl("/unauthorized.jsp");
		
//		<property name="filters">
//		<util:map>
//			<entry key="authc" value-ref="formAuthenticationFilter" />
//			<entry key="perms" value-ref="uRLPermissionsFilter" />
//		</util:map>
//	</property>
		
		//http://blog.csdn.net/catoop/article/details/50520958
		Map<String,String> filterChainDefinitionMap=new HashMap<String,String>();
		filterChainDefinitionMap.put("/**", "anon");//这个是临时的
//		filterChainDefinitionMap.put("/", "anon");
//		filterChainDefinitionMap.put("/login.jsp*", "anon");
//		filterChainDefinitionMap.put("/unauthorized.jsp*", "anon");
//		filterChainDefinitionMap.put("/login.do*", "anon");
//		filterChainDefinitionMap.put("/logout.do*", "anon");
//		filterChainDefinitionMap.put("/**/*.css", "anon");
//		filterChainDefinitionMap.put("/**/*.js", "anon");
//		filterChainDefinitionMap.put("/**/*.gif", "anon");
//		filterChainDefinitionMap.put("/**/*.jpg", "anon");
//		filterChainDefinitionMap.put("/**/*.png	", "anon");
//	
//		filterChainDefinitionMap.put("/**/*.jsp", "authc,perms");
//		filterChainDefinitionMap.put("/**", "authc");
		//filterChainDefinitionMap.put("", "");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return (Filter)shiroFilter.getObject();
		
	}
	
	@Bean
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor(){
		LifecycleBeanPostProcessor lifecycleBeanPostProcessor=new LifecycleBeanPostProcessor();
		return lifecycleBeanPostProcessor;
	}
	
	@Bean
    public EhCacheManager getEhCacheManager() {  
        EhCacheManager em = new EhCacheManager();  
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");  
        return em;  
    } 
}
