package com.demo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.beans.ContentBean;
import com.demo.beans.LoginUserBean;
import com.demo.beans.PageBean;
import com.demo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@Resource(name = "loginUserBean")
	private LoginUserBean loginUserBean;
	
	@GetMapping("/main")
	public String main(@RequestParam("board_info_idx") int board_info_idx, Model model,
						@RequestParam(value = "page", defaultValue = "1") int page) {
		//defaultValue 파라미터가 오지 않았을 때 1페이지로 한다
		// 파라메터로 넘어오는 board번호를 main페이지로 전달
		model.addAttribute("board_info_idx", board_info_idx);
		// 게시판 이름
		String boardInfoName = boardService.getBoardInfoName(board_info_idx);
		model.addAttribute("boardInfoName", boardInfoName);
		//표시할 게시물 리스트 (게시판 별)
		List<ContentBean> contentList = boardService.getContentList(board_info_idx, page);
		model.addAttribute("contentList", contentList);
		//게시판 번호와 현재페이지를 입력해 페이지객체를 만들기
		PageBean pageBean = boardService.getContentCnt(board_info_idx, page);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("page", page);
		
		return "board/main";
	}
	
	@GetMapping("/read")
	public String read(@RequestParam("board_info_idx") int board_info_idx, 
						@RequestParam("content_idx") int content_idx,
						@RequestParam("page") int page,
						Model model) {
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("content_idx", content_idx);
        model.addAttribute("loginUserBean", loginUserBean);
        model.addAttribute("page", page);
        
		// 글 번호로 DB에서 게시글 내용 읽어오기
		ContentBean readContentBean = boardService.getContentInfo(content_idx);
		model.addAttribute("readContentBean",readContentBean);
		return "board/read";
	}
	
	@GetMapping("/write")
	public String write(@ModelAttribute("writeContentBean") ContentBean writeContentBean,
						@RequestParam("board_info_idx") int board_info_idx) {
		//어떤 게시판인지 게시판 번호를 입력
		writeContentBean.setContent_board_idx(board_info_idx);
		//writeContentBean이 write.jsp와 연계
		return "board/write";
	}
	
	@PostMapping("/write_pro")
	public String write_pro(@Valid @ModelAttribute("writeContentBean") ContentBean writeContentBean,
							BindingResult result) {
		if(result.hasErrors()) {
			return "board/write";
		}
		//DB에 저장한다
		boardService.addContentInfo(writeContentBean);
		return "board/write_success";
	}
	
	//파라미터로 board_info_idx, content_idx 받아서 modifyContentBean 객체에 추가해서 model에 추가하기
	@GetMapping("/modify")
	public String modify(@RequestParam("board_info_idx") int board_info_idx,
  			 @RequestParam("content_idx") int content_idx, Model model,
  			 @ModelAttribute("modifyContentBean") ContentBean modifyContentBean) {
		
		modifyContentBean.setContent_board_idx(board_info_idx);
		modifyContentBean.setContent_idx(content_idx);

		boardService.getContents(modifyContentBean);
		model.addAttribute("modifyContentBean", modifyContentBean);
		
		return "board/modify";
	}
	
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyContentBean") ContentBean modifyContentBean,
						 BindingResult result) {
		
		if(result.hasErrors()) {
			return "board/modify";
		}
		//수정된 modifyContentBean 내용을 저장하는 서비스 호출
		boardService.modifyContentInfo(modifyContentBean);
		return "board/modify_success";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("board_info_idx") int board_info_idx,
						 @RequestParam("content_idx") int content_idx, Model model) {
		//게시글 삭제서비스 호출
		boardService.deleteContentInfo(content_idx);
		model.addAttribute("board_info_idx",board_info_idx);
		
		return "board/delete";
	}
	
	@GetMapping("/not_writer")
	public String not_writer() {
		return "board/not_writer";
	}
	
}
