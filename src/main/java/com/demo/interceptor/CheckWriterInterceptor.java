package com.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.demo.beans.ContentBean;
import com.demo.beans.LoginUserBean;
import com.demo.service.BoardService;

public class CheckWriterInterceptor implements HandlerInterceptor {
	// 로그인 정보, 글쓴이 정보(BoardService)
	private LoginUserBean loginUserBean;
	private BoardService boardService;
	
	public CheckWriterInterceptor(LoginUserBean loginUserBean, BoardService boardService) {
		this.loginUserBean = loginUserBean;
		this.boardService = boardService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//content_idx를 가져와 BoardService에 넣어 contentBean객체 가져옴
		String str1 = request.getParameter("content_idx");
		int content_idx = Integer.parseInt(str1);
		
		ContentBean currentContentBean = boardService.getContentInfo(content_idx);
		//글쓴이와 로그인유저가 다를경우 not_writer 요청
		if(currentContentBean.getContent_writer_idx() != loginUserBean.getUser_idx()) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/board/not_writer");
			return false; //기존 요청을 취소
		}
		
		return true;
	}
	
}
