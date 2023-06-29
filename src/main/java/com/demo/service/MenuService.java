package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.beans.BoardInfoBean;
import com.demo.mapper.MenuMapper;

@Service
public class MenuService {

	//DB에 연결 객체
	@Autowired
	private MenuMapper menuMapper;
	
	//게시판 정보 테이블에서 순서대로 게시판 이름들을 가져오는 메서드
	public List<BoardInfoBean> getMenuList(){
		return menuMapper.getMenuList();
	}
}
