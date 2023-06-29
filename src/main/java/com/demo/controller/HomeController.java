package com.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		//톰캣이 이 프로젝트를 실행시 실제 주소 출력
//		System.out.println(request.getServletContext().getRealPath("/"));
		return "redirect:/main";
	}
}
