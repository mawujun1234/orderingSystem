package com.youngor.sample;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface SampleColthRepository extends IRepository<SampleColth, String>{
	public void deleteBySampno(@Param("sampno")String sampno);
	public  void updateSpctpr(@Param("sampno")String sampno,@Param("spctpr")Double spctpr);
	
	public void lock(Map<String,Object> params);
	public void unlock(Map<String,Object> params);
	
	public SampleColth load(@Param("sampno")String sampno);

}
