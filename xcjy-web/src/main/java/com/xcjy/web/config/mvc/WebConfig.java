package com.xcjy.web.config.mvc;

import java.util.Properties;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * spring mvc 的配置，只扫描controller类
 * 
 * @author 支亚州
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.xcjy.web.controller", useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
public class WebConfig extends WebMvcConfigurerAdapter {

	// ****************** spring mvc 多视图支持 begin ********************//
	/**
	 * velocity视图解析器 order=0
	 * 
	 * @return
	 */
	@Bean
	public VelocityViewResolver velocityViewResolver() {

		/*
		 * VelocityViewResolver velocityViewResolver = new
		 * VelocityViewResolver(); velocityViewResolver.setSuffix(".vm");
		 * velocityViewResolver.setContentType("text/html; charset=UTF-8");
		 * velocityViewResolver.setExposeSessionAttributes(true);
		 * velocityViewResolver.setExposeRequestAttributes(true);
		 * velocityViewResolver.setRequestContextAttribute("rc"); // toolbox的配置
		 * velocityViewResolver.setToolboxConfigLocation(
		 * "classpath*:velocityToolBox.xml");
		 * velocityViewResolver.setViewClass(VelocityToolboxView.class);
		 * velocityViewResolver.setOrder(0);
		 */

		// 参考 http://blog.csdn.net/mingtianhaiyouwo/article/details/50395346
		VelocityLayoutViewResolver velocityViewResolver = new VelocityLayoutViewResolver();
		velocityViewResolver.setCache(true);
		velocityViewResolver.setPrefix("/");
		velocityViewResolver.setSuffix(".vm");
		velocityViewResolver.setContentType("text/html; charset=UTF-8");
		velocityViewResolver.setAllowSessionOverride(true);
		velocityViewResolver.setAllowRequestOverride(true);
		velocityViewResolver.setExposeSessionAttributes(true);
		velocityViewResolver.setExposeRequestAttributes(true);
		velocityViewResolver.setRequestContextAttribute("rc");
		velocityViewResolver.setLayoutUrl("layout/layout.vm");
		// toolbox的配置
		velocityViewResolver.setToolboxConfigLocation("/WEB-INF/velocityToolBox.xml");
		velocityViewResolver.setViewClass(VelocityLayoutView.class);
		velocityViewResolver.setOrder(0);
		return velocityViewResolver;
	}

	/**
	 * velocity配置对象
	 * 
	 * @return
	 */
	@Bean
	public VelocityConfigurer velocityConfigurer() {
		VelocityConfigurer configurer = new VelocityConfigurer();
		Properties velocityProperties = new Properties();
		velocityProperties.setProperty("input.encoding", "UTF-8");
		velocityProperties.setProperty("output.encoding", "UTF-8");
		configurer.setVelocityProperties(velocityProperties);
		configurer.setResourceLoaderPath("/WEB-INF/templates");
		return configurer;
	}

	/**
	 * jsp视图解析器 order=1
	 * 
	 * @return
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setViewClass(JstlView.class);
		internalResourceViewResolver.setPrefix("/WEB-INF/pages");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setOrder(1);
		return internalResourceViewResolver;
	}

	/**
	 * beanName视图解析器 order=2
	 * 
	 * @return
	 */
	@Bean
	public BeanNameViewResolver beanNameViewResolver() {
		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(2);
		return beanNameViewResolver;
	}

	// ****************** spring mvc 多视图支持 end ********************//

	/**
	 * 静态文件路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
	}

	/**
	 * view controllers
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 设置主页
		registry.addViewController("/index").setViewName("index");
		registry.addRedirectViewController("/", "/index");
		// 设置错误页面
		registry.addViewController("/404").setViewName("404");
		registry.addViewController("/500").setViewName("500");
		registry.addViewController("/403").setViewName("403");
	}

	/**
	 * 文件上传解析器（使用 Commons FileUpload）
	 * 
	 * @return
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		// 文件大小限制在10M以内
		multipartResolver.setMaxUploadSize(10485760L);
		multipartResolver.setMaxInMemorySize(4096);
		return multipartResolver;
	}

	// 开启Shiro注解的Spring配置方式的beans。

	@Autowired
	private SecurityManager securityManager;

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
		simpleMappingExceptionResolver.setDefaultErrorView("500");

		Properties exceptionMappings = new Properties();
		exceptionMappings.setProperty("org.apache.shiro.authz.UnauthorizedException", "403");
		exceptionMappings.setProperty("org.apache.shiro.authz.UnauthenticatedException", "403");
		simpleMappingExceptionResolver.setExceptionMappings(exceptionMappings);

		Properties statusCodes = new Properties();
		statusCodes.setProperty("403", "403");
		statusCodes.setProperty("404", "404");
		statusCodes.setProperty("500", "500");
		simpleMappingExceptionResolver.setStatusCodes(statusCodes);

		return simpleMappingExceptionResolver;
	}
}
