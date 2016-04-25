package com.youngor.sample;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface SampleColthRepository extends IRepository<SampleColth, String>{
	public void lock(Map<String,Object> params);
	public void unlock(Map<String,Object> params);

}
