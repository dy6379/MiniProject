package com.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.demo.beans.LoginUserBean;

public class CheckLoginInterceptor implements HandlerInterceptor {

	private LoginUserBean loginUserBean;
	//인터셉터 객체 생성시 로그인정보 객체 필요함
	public CheckLoginInterceptor(LoginUserBean loginUserBean) {
		this.loginUserBean = loginUserBean;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 요청주소(컨트롤러 메서드)보다 먼저 실행됨 리턴이 true일때 컨트롤러 실행
		
		if(loginUserBean.isUserLogin() == false) {
			String contexPath = request.getContextPath();
			response.sendRedirect(contexPath+"/user/not_login");
			return false; //요청주소 실행안됨
		}
		
		return true; //요청주소로 실행됨
	}
	
}
