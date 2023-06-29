package com.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.demo.beans.LoginUserBean;
import com.demo.beans.UserBean;

@Mapper
public interface UserMapper {

	//같은 id가 있는지 검색하여 유저이름을 찾기
	@Select("select user_name " + "from user_table " + "where user_id = #{user_id}")
		String checkUserIdExist(String user_id);
	
	//새로운 유저 입력
	@Insert("insert into user_table (user_idx, user_name, user_id, user_pw) " +
			"values (user_seq.nextval, #{user_name}, #{user_id}, #{user_pw})")
		void addUserInfo(UserBean joinUserBean);
	
	//로그인 정보 확인
	@Select("SELECT user_idx, user_name FROM user_table "
				+ "WHERE user_id=#{user_id} AND user_pw=#{user_pw}")
		LoginUserBean getLoginUserInfo(LoginUserBean loginBean);
	
	//유저 idx번호로 유저의 아이디와 이름을 가져오기
	@Select("select user_id, user_name " + "from user_table " + "where user_idx = #{user_idx}")
		UserBean getModifyUserInfo(int user_idx);
	
	//유저테이블의 pw업데이트
	@Update("update user_table " + "set user_pw = #{user_pw} " + "where user_idx = #{user_idx}")
		void modifyUserInfo(UserBean modifyUserBean);
	
}
