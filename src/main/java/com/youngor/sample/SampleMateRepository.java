package com.youngor.sample;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.mawujun.repository.IRepository;

import com.youngor.sample.SampleMate;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface SampleMateRepository extends IRepository<SampleMate,  SampleMate.PK>{

	public void lock(Map<String,Object> params);
	public void unlock(Map<String,Object> params);
	
	public List<SampleMate> queryAll();
}
