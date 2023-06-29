package com.demo.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.demo.beans.BoardInfoBean;
import com.demo.beans.LoginUserBean;
import com.demo.service.MenuService;

public class MenuInterceptor implements HandlerInterceptor {

	//DB연결 서비스 객체
	private MenuService menuService;
	//로그인 정보 객체
	private LoginUserBean loginUserBean;
	
	public MenuInterceptor(MenuService menuService, LoginUserBean loginUserBean) {
		this.menuService = menuService;
		this.loginUserBean = loginUserBean;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//모든 요청에 대해 먼저 게시판 이름들을 순서대로 가져와 request에 menuList로 저장
		List<BoardInfoBean> menuList = menuService.getMenuList();
		request.setAttribute("menuList", menuList);
		//현재 로그인 정보객체를 전달
		request.setAttribute("loginUserBean", loginUserBean);
		
		return true;
	}
	
}
