package com.demo.beans;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class ContentBean {

	private int content_idx;		//글번호
	
	@NotBlank(message = "제목을 입력해주세요")
	private String content_subject; //제목
	
	@NotBlank(message = "내용을 입력해주세요")
	private String content_text;	//내용
	
	private MultipartFile upload_file; //업로드한 파일 객체
	private String content_file;	//파일(이미지의 이름)
	private int content_writer_idx;	//글쓴이
	private int content_board_idx;	//게시판종류(자유,유머,정치,스포츠)
	private String content_date;	//날짜
	private String content_writer_name; //글쓴이 이름 추가(조인)
	
	public MultipartFile getUpload_file() {
		return upload_file;
	}
	public void setUpload_file(MultipartFile upload_file) {
		this.upload_file = upload_file;
	}
	public int getContent_idx() {
		return content_idx;
	}
	public void setContent_idx(int content_idx) {
		this.content_idx = content_idx;
	}
	public String getContent_subject() {
		return content_subject;
	}
	public void setContent_subject(String content_subject) {
		this.content_subject = content_subject;
	}
	public String getContent_text() {
		return content_text;
	}
	public void setContent_text(String content_text) {
		this.content_text = content_text;
	}
	public String getContent_file() {
		return content_file;
	}
	public void setContent_file(String content_file) {
		this.content_file = content_file;
	}
	public int getContent_writer_idx() {
		return content_writer_idx;
	}
	public void setContent_writer_idx(int content_writer_idx) {
		this.content_writer_idx = content_writer_idx;
	}
	public int getContent_board_idx() {
		return content_board_idx;
	}
	public void setContent_board_idx(int content_board_idx) {
		this.content_board_idx = content_board_idx;
	}
	public String getContent_date() {
		return content_date;
	}
	public void setContent_date(String content_date) {
		this.content_date = content_date;
	}
	public String getContent_writer_name() {
		return content_writer_name;
	}
	public void setContent_writer_name(String content_writer_name) {
		this.content_writer_name = content_writer_name;
	}
	
	

}
