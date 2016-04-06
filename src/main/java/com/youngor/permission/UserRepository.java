package com.youngor.permission;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
import com.mawujun.utils.page.Pager;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface UserRepository extends IRepository<User, String>{
	public UserVO getByLoginName(@Param("loginName")String loginName);
	
	 public List<String> findPermissions(@Param("user_id")String user_id); 
	 
	 public Pager<User> queryByPosition(Pager<User> pager);

}
