package com.youngor.sample;

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
public interface SampleCldtlRepository extends IRepository<SampleCldtl, com.youngor.sample.SampleCldtl.PK>{

	public List<SampleCldtlVO> queryAll(@Param("clppno")String clppno) ;
}
