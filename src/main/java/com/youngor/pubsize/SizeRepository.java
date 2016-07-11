package com.youngor.pubsize;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface SizeRepository extends IRepository<Size, String>{
	public int getCountByYsizeno(@Param("ormtno")String ormtno,@Param("ysizety")String ysizety,@Param("ysizeno")String ysizeno);


}
