package com.youngor.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;
//http://hanqunfeng.iteye.com/blog/2114967
//@Order(1)
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
