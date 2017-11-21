package com.topicme.web.security.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.topicme.web.security.config.LoginSecurityConfig;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.topicme.web.security.config","com.topicme.web.controller" })
@Import(value = { LoginSecurityConfig.class })
public class LoginApplicationConfig {

	@PostConstruct
	public void init(){
		System.out.println("LoginApplicationConfig");
	}
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
}
