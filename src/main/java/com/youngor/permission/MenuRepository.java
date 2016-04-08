package com.youngor.permission;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface MenuRepository extends IRepository<Menu, String>{

	public List<MenuVO> query_checkbox(@Param("parent_id")String parent_id);
	public List<MenuVO> queryByUser(@Param("parent_id")String parent_id,@Param("user_id")String user_id);
}
