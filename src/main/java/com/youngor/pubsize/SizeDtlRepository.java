package com.youngor.pubsize;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import com.mawujun.repository.IRepository;

import com.youngor.pubsize.SizeDtl;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface SizeDtlRepository extends IRepository<SizeDtl, com.youngor.pubsize.SizeDtl.PK>{

	public List<SizeDtlVO> query(@Param("ormtno")String ormtno,@Param("fszty")String fszty,@Param("fszno")String fszno,@Param("sizety")String sizety);
	
	public void deleteByFszno(@Param("fszty")String fszty,@Param("fszno")String fszno);
	
	public void copyByFszno(@Param("fszno_old")String fszno_old,@Param("fszno_new")String fszno_new,@Param("ormtno")String ormtno,@Param("user_id")String user_id);
}
