package com.youngor.config;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.controller.spring.mvc.DateConverter;
import com.mawujun.controller.spring.mvc.exception.MappingExceptionResolver;


@Configuration
@ComponentScan(basePackages="com.youngor",
	includeFilters = @Filter(type = FilterType.ANNOTATION, value = {Controller.class}))
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	
	@Bean
	public ObjectMapper getObjectMapper(){
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
	        .indentOutput(true)
	        .dateFormat(simpleDateFormat);
	        //.modulesToInstall(new ParameterNamesModule());
		ObjectMapper mapper=builder.build();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); //允许出现没有双引号的字段名称
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true) ;//允许出现单引�?
		return mapper;
	}
	
	
	@Override
    public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new DateConverter());
    }

	/**
	 * 主要用于@ResponseBody和@RequestBody的时候，或�?�请求发过来的content-type是applicaiton/json的时�?
	 */
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(getObjectMapper()));
    }
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
		//configurer.mediaType("jsonp", MediaType.appli);
	}
	
	@Bean(name="mappingJackson2JsonView")  
	public MappingJackson2JsonView mappingJackson2JsonView(){
		MappingJackson2JsonView mappingJackson2JsonView=new MappingJackson2JsonView();
		mappingJackson2JsonView.setObjectMapper(getObjectMapper());
		return mappingJackson2JsonView;
	}
	
	@Bean
	public SpringContextHolder springContextHolder(){
		return new SpringContextHolder();
	}

	/**
	 * 视图解析�?
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		//默认使用jackson作为视图解析�?
		registry.enableContentNegotiation(mappingJackson2JsonView());
		registry.jsp("/", ".jsp");
	}
	
	@Bean(name="exceptionResolver")  
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){  
		MappingExceptionResolver simpleMappingExceptionResolver= new MappingExceptionResolver();  
        simpleMappingExceptionResolver.setDefaultErrorView("common_error"); 
        simpleMappingExceptionResolver.setDefaultStatusCode(503);
        simpleMappingExceptionResolver.setWarnLogCategory("WARN");
        //simpleMappingExceptionResolver.setExceptionAttribute("exception"); 
        
        Properties properties = new Properties();  

        String  viewname="400_error";
        properties.setProperty(HttpMessageNotReadableException.class.getName(), viewname);
        simpleMappingExceptionResolver.addStatusCode(viewname, 400);
        simpleMappingExceptionResolver.addErrorMsg(HttpMessageNotReadableException.class, "请求参数有问题，请检查输入的数据!");
        
//        viewname="404_error";
//        simpleMappingExceptionResolver.addStatusCode(viewname, 404);
//        simpleMappingExceptionResolver.addErrorMsg(viewname, "找不到指定页面"); 
        
        viewname="common_error";
        properties.setProperty(ConstraintViolationException.class.getName(), viewname);
        simpleMappingExceptionResolver.addStatusCode(viewname, 503);
        simpleMappingExceptionResolver.addErrorMsg(ConstraintViolationException.class, "违反数据库约束，某些数据可能重复了");
        
        viewname="common_error";
        properties.setProperty(IllegalArgumentException.class.getName(), viewname);
        simpleMappingExceptionResolver.addStatusCode(viewname, 503);
        simpleMappingExceptionResolver.addErrorMsg(IllegalArgumentException.class, "非法的参数，请注意!");
        
        viewname="common_error";
        properties.setProperty(Exception.class.getName(), viewname);
        simpleMappingExceptionResolver.addStatusCode(viewname, 503);
        simpleMappingExceptionResolver.addErrorMsg(Exception.class, "系统发生异常");
        
        simpleMappingExceptionResolver.setExceptionMappings(properties);  

        return simpleMappingExceptionResolver;  
    }  

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/bootstrap/**").addResourceLocations("/bootstrap/").setCachePeriod(31556926);
//        registry.addResourceHandler("/echarts-2.2.7/**").addResourceLocations("/echarts-2.2.7/").setCachePeriod(31556926);
//        registry.addResourceHandler("/jquery/**").addResourceLocations("/jquery/").setCachePeriod(31556926);
//        registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(31556926);
    }



}
