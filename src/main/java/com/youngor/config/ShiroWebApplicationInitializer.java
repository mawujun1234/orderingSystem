package com.youngor.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;
//http://hanqunfeng.iteye.com/blog/2114967
//@Order(1)
/**
 * 这个是必须加的，和web。xml中的下列代码是一样的：
 *  <filter>
    <filter-name>shiroFilter</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
    <init-param>
        <param-name>targetFilterLifecycle</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>shiroFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
 * @author mawujun
 *
 */
public class ShiroWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		DelegatingFilterProxy shiroFilter=new DelegatingFilterProxy();
		shiroFilter.setTargetFilterLifecycle(true);
		//shiroFilter.setTargetBeanName("shiroFilter");
		
		FilterRegistration.Dynamic filterRegistration=servletContext.addFilter("shiroFilter", shiroFilter);
		filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");  

	}

}
