package com.xcjy.web.config.spring;

import java.io.IOException;

import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

/**
 * 总的配置类
 * 
 * @author 支亚州
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.xcjy" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }),
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = { "com.xcjy.web.config.mvc.*",
				"com.xcjy.web.controller.*" }) })
@Import({ DataConfig.class, ServiceConfig.class, AuthConfig.class })
public class AppConfig {

	@Bean
	public static PropertiesFactoryBean configProperties() {
		PropertiesFactoryBean config = new PropertiesFactoryBean();
		config.setLocations(new ClassPathResource("/config.properties"));
		return config;
	}

	@Bean
	public static PreferencesPlaceholderConfigurer propertyConfigurer() {
		PreferencesPlaceholderConfigurer configurer = new PreferencesPlaceholderConfigurer();
		try {
			configurer.setProperties(configProperties().getObject());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return configurer;
	}

}
