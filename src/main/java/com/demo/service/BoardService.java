package com.demo.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.beans.ContentBean;
import com.demo.beans.LoginUserBean;
import com.demo.beans.PageBean;
import com.demo.mapper.BoardMapper;

@Service
@PropertySource("/static/properties/option.properties")
public class BoardService {

	@Value("${path.upload}")
	private String path_upload;

	@Value("${page.listcnt}")
	private int page_listcnt; // 10개

	@Value("${page.pagenationcnt}")
	private int page_paginationcnt; // 10개

	@Autowired
	private BoardMapper boardMapper;

	@Resource(name = "loginUserBean")
	private LoginUserBean loginUserBean;

	// 서버로 업로드 된 파일을 업로드 폴더에 저장하고 파일의 이름을 리턴하는 메소드
	private String saveUploadFile(MultipartFile upload_file) {

		// 현재 시간(밀리세컨드)을 이용해서 파일의 이름이 중복되지 않게 설정
		String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();

		try {
			upload_file.transferTo(new File(path_upload + "/" + file_name));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file_name;
	}

	public void addContentInfo(ContentBean writeContentBean) {
		// write.jsp에서 입력한 파일
		MultipartFile upload_file = writeContentBean.getUpload_file();
		// 업로드 파일이 있을경우
		if (upload_file.getSize() > 0) {
			String file_name = saveUploadFile(upload_file); // 지정폴더에 저장하고 파일이름 리턴
			writeContentBean.setContent_file(file_name); // 파일이름 저장
		}
		// 글쓴이 정보를 입력
		writeContentBean.setContent_writer_idx(loginUserBean.getUser_idx());
		// DB에 저장
//		for(int i=0; i<100; i++) {
////			글 내용을 한번에 100개 넣기
//			boardMapper.addContentInfo(writeContentBean);
//		}
		boardMapper.addContentInfo(writeContentBean);
		
	}

	// 게시판 이름 가져오기
	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}

	// 현재 페이지와 게시판번호를 입력받아서 페이지객체를 리턴
	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		// 전체 게시글 개수
		int content_cnt = boardMapper.getContentCnt(content_board_idx);

		PageBean pageBean = new PageBean(content_cnt, currentPage, page_listcnt, page_paginationcnt);

		return pageBean;
	}

	// 페이지에 해당하는 글들을 가져온다
	public List<ContentBean> getContentList(int board_info_idx, int page) {
		// 시작인덱스 = ( 페이지번호 - 1 ) * 10
		int start = (page - 1) * page_listcnt;
		// 마이바티스의 RowBounds 클래스를 사용해 가져올 글시작 번호 , 가져올 갯수로 설정
		RowBounds rowBounds = new RowBounds(start, page_listcnt);
		// 매퍼에서 처리하도록 rowBounds 객체를 매개변수로 추가
		return boardMapper.getContentList(board_info_idx, rowBounds);
	}

	// 글 게시글 읽기
	public ContentBean getContentInfo(int content_idx) {
		return boardMapper.getContentInfo(content_idx);
	}

	// 글 인덱스 번호로 검색해서 글 정보를 modifyContentBean 입력한다.
	public void getContents(ContentBean modifyContentBean) {

		ContentBean temp = boardMapper.getContentInfo(modifyContentBean.getContent_idx());

		modifyContentBean.setContent_writer_name(temp.getContent_writer_name());
		modifyContentBean.setContent_date(temp.getContent_date());
		modifyContentBean.setContent_subject(temp.getContent_subject());
		modifyContentBean.setContent_text(temp.getContent_text());
		modifyContentBean.setContent_file(temp.getContent_file());
	}

	// 수정하기
	public void modifyContentInfo(ContentBean modifyContentBean) {
		// 업로드한 파일이 있으면 업로드 폴더에 저장하고 그 이름을 modifyContentBean에 저장
		MultipartFile upload_file = modifyContentBean.getUpload_file();

		if (upload_file.getSize() > 0) {
			String file_name = saveUploadFile(upload_file);
			modifyContentBean.setContent_file(file_name);
		}
		// DB에 저장하기(파일은 이름만 저장)
		boardMapper.modifyContentInfo(modifyContentBean);
	}

	// 삭제하기
	public void deleteContentInfo(int content_idx) {
		boardMapper.deleteContentInfo(content_idx);
	}

}
