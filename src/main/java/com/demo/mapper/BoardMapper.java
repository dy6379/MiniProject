package com.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.demo.beans.ContentBean;

@Mapper
public interface BoardMapper {

	// content_idx 값이 나오면 먼저(before) sql문을 실행 결과를 입력한다.
	@SelectKey(statement = "select content_seq.nextval from dual",
				keyProperty = "content_idx", before = true, resultType = int.class)
	//SelectKey의 content_seq.nextval을 실행해서 Insert의 #{content_idx}실행
	// DB에 게시글을 입력 (시퀀스, 제목, 내용, 파일명, 글쓴이, 게시판번호, 현재날짜)
	@Insert("INSERT INTO content_table(content_idx, content_subject, content_text, " +
			"content_file, content_writer_idx, content_board_idx, content_date) " +
			"VALUES (#{content_idx}, #{content_subject}, #{content_text}, #{content_file, jdbcType=VARCHAR}, " +
			"#{content_writer_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean); //게시글 번호도 저장됨

	// 게시판 번호에 맞는 게시판 이름
	@Select("SELECT board_info_name " + "FROM board_info_table " + "WHERE board_info_idx = #{board_info_idx}")
	String getBoardInfoName(int board_info_idx);

	// 게시글번호로 게시글 내용을 화면에 맞게 가져오기(조인)
	@Select("SELECT t2.user_name as content_writer_name, " + "to_char(t1.content_date, 'YYYY-MM-DD') as content_date,"
			+ "t1.content_subject, t1.content_text, t1.content_file, t1.content_writer_idx " 
			+ "FROM content_table t1 join user_table t2 "
			+ "ON t1.content_writer_idx = t2.user_idx " + "AND content_idx = #{ content_idx }")
	ContentBean getContentInfo(int content_idx);
	
	//게시글 수정하기
	@Update("UPDATE content_table " +
			"SET content_subject = #{content_subject}, content_text = #{content_text}, " +
			"content_file = #{content_file, jdbcType=VARCHAR} " +
			"WHERE content_idx = #{content_idx}")
	void modifyContentInfo(ContentBean modifyContentBean);
	
	@Delete("DELETE FROM content_table WHERE content_idx=#{content_idx}")
	void deleteContentInfo(int content_idx);
	
	// 게시글과 유저 테이블 조인하여 글 목록 만들기
	@Select("SELECT t1.content_idx, t1.content_subject, t2.user_name AS content_writer_name, "
			+ "to_char(t1.content_date, 'YYYY-MM-DD') AS content_date " + "FROM content_table t1 JOIN user_table t2 "
			+ "ON t1.content_writer_idx = t2.user_idx "
			+ "AND t1.content_board_idx = #{board_info_idx} ORDER BY t1.content_idx desc")
	
	//마이바티스에서 Select 검색결과에서 필요한 게시물(rowBounds)만큼 가져옴 => MainService
	List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds);

	//전체 게시글 개수
	@Select("SELECT count(*) FROM content_table WHERE content_board_idx = #{content_board_idx}")
	int getContentCnt(int content_board_idx);

//	content_idx,           시퀀스로 입력 content_seq.nextval
//	content_subject,       폼에서 적은 제목 bean에서 가져오기
//	content_text           폼에서 적은 내용 bean에서 가져오기
//	content_file,          파일을 업로드 하고 파일을 이름 가져오기 
//	                        이때 NULL 값가능 마이바티스에서 #{변수,  jdbcType=VARCHAR }
//	content_writer_idx,    로그인상태의 유저 인덱스 번호를 가져오기
//	content_board_idx,     현재 글을 쓴 게시판 인덱스 번호를 가져오기( 폼에서 hidden으로 넘어옴)
//	content_date           현재 날짜 => sysdate

}
