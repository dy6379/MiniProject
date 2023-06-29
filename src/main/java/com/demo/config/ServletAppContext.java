package com.demo.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demo.beans.LoginUserBean;
import com.demo.interceptor.CheckLoginInterceptor;
import com.demo.interceptor.CheckWriterInterceptor;
import com.demo.interceptor.MenuInterceptor;
import com.demo.service.BoardService;
import com.demo.service.MenuService;

@Configuration
public class ServletAppContext implements WebMvcConfigurer {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private BoardService boardService;
	
	@Resource(name = "loginUserBean")
	private LoginUserBean loginUserBean;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		WebMvcConfigurer.super.addInterceptors(registry);
		
		MenuInterceptor menuInterceptor = new MenuInterceptor(menuService, loginUserBean);	
		InterceptorRegistration reg1 = registry.addInterceptor(menuInterceptor);
		//모든 요청주소에 대해 메뉴 인터셉터를 실행한다.
		reg1.addPathPatterns("/**"); 	
		
		CheckLoginInterceptor checkLoginInterceptor = new CheckLoginInterceptor(loginUserBean);
		InterceptorRegistration reg2 = registry.addInterceptor(checkLoginInterceptor);
		
		reg2.addPathPatterns("/user/modify", "/user/logout", "/board/*");
		reg2.excludePathPatterns("/board/main");
		
		CheckWriterInterceptor checkWriterInterceptor = new CheckWriterInterceptor(loginUserBean, boardService);
		InterceptorRegistration reg3 = registry.addInterceptor(checkWriterInterceptor);
		reg3.addPathPatterns("/board/modify", "/board/delete");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/upload/**")
			.addResourceLocations("file:///D:/Spring/mySpringWork/MiniProject/src/main/resources/static/upload/");
	}
	
}
