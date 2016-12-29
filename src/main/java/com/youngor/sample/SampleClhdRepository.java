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
public interface SampleClhdRepository extends IRepository<SampleClhd, String>{

	//public List<SampleClpht> querySampleClphtes(@Param("clppno")String clppno);
	public List<SampleClhdVO> queryBySampno(@Param("ormtno")String ormtno,@Param("bradno")String bradno,@Param("sampno")String sampno);
	
	public List<SampleCldtlVO> queryMxByClppno(@Param("clppno")String clppno);
}
