package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.UserService;

@RestController
public class RestApiController {

	@Autowired
	private UserService userService;

	@GetMapping("/user/check/{user_id}")
	// PathVariable로 주소에 id라는 변수를 받아 저장
	public String checkUserIdExist(@PathVariable String user_id) {
		// 유저 아이디가 없으면 true 있으면 false
		boolean check = userService.checkuserIdExist(user_id);
		//rest컨트롤러는 데이터를 뷰없이 리턴한다
		return check + ""; //boolean + 문자열 = 문자열
	}
}
