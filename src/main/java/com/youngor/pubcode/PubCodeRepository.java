package com.youngor.pubcode;

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
public interface PubCodeRepository extends IRepository<PubCode, String>{

	public List<PubCode> query(@Param("tyno")String tyno,@Param("fitno")String fitno,@Param("bradno")String bradno);
}
