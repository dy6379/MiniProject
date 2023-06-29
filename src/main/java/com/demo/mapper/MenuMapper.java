package com.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.demo.beans.BoardInfoBean;

@Mapper
public interface MenuMapper {

	//인덱스순으로  게시판이름을 가져오는 메서드
	@Select("select board_info_idx, board_info_name " +
			"from board_info_table " + 
			"order by board_info_idx")
	List<BoardInfoBean> getMenuList();

}
