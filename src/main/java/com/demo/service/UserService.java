package com.demo.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.beans.LoginUserBean;
import com.demo.beans.UserBean;
import com.demo.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	// 세션에 저장된 로그인 객체를 가져오기
	@Resource(name = "loginUserBean")
	private LoginUserBean loginUserBean;

	public boolean checkuserIdExist(String user_id) {
		// id를 입력받아서 같은 아이디를 검색
		String user_name = userMapper.checkUserIdExist(user_id);
		// id가 없으면 true리턴 있으면 false리턴
		if (user_name == null) {
			return true; // 사용가능한 id
		} else {
			return false; // 사용불가능한 id
		}
	}

	// 새 유저 저장
	public void addUserInfo(UserBean joinUserBean) {
		userMapper.addUserInfo(joinUserBean);
	}

	// id, pw로 로그인
	public void getLoginUserInfo(LoginUserBean loginBean) {
		// DB에서 id, pw로 검색하여 로그인정보(idx, name)을 가져옴
		LoginUserBean tempLoginBean = userMapper.getLoginUserInfo(loginBean);

		// 로그인 정보가 있다면
		if (tempLoginBean != null) {
			// 세션의 로그인객체에 정보를 입력
			loginUserBean.setUser_idx(tempLoginBean.getUser_idx());
			loginUserBean.setUser_name(tempLoginBean.getUser_name());
			loginUserBean.setUserLogin(true); // 로그인 상태 true
		}
	}

	// 현재 로그인중인 유저의 인덱스번호로 아이디와 이름을 얻어서 modifyUserBean 객체에 저장
	public void getModifyUserInfo(UserBean modifyUserBean) {
		//유저번호로 아이디 이름을 가져오기
		UserBean temp = userMapper.getModifyUserInfo(loginUserBean.getUser_idx());
		//아이디 이름을 modifyUserBean에 저장하기
		modifyUserBean.setUser_id(temp.getUser_id());
		modifyUserBean.setUser_name(temp.getUser_name());
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
	}
	
	public void modifyUserInfo(UserBean modifyUserBean) {
		//현재 유저번호를 입력함
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
		userMapper.modifyUserInfo(modifyUserBean);
	}

}
