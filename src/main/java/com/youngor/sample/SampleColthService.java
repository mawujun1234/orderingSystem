package com.youngor.sample;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleColthService extends AbstractService<SampleColth, String>{

	@Autowired
	private SampleColthRepository sampleColthRepository;
	
	@Override
	public SampleColthRepository getRepository() {
		return sampleColthRepository;
	}

	
	public void lock(Map<String,Object> params) {
		sampleColthRepository.lock(params);
	}
	public void unlock(Map<String,Object> params){
		sampleColthRepository.unlock(params);
	}
}
