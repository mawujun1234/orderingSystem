package com.youngor.sample;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.sample.SampleDesignSizegp;
import com.youngor.sample.SampleDesignSizegpRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleDesignSizegpService extends AbstractService<SampleDesignSizegp, SampleDesignSizegp.PK>{

	@Autowired
	private SampleDesignSizegpRepository sampleDesignSizegpRepository;
	
	@Override
	public SampleDesignSizegpRepository getRepository() {
		return sampleDesignSizegpRepository;
	}

	
	public List<SampleDesignSizegp> querySampleDesignSizegp(String sampno,String suitty) {
		return sampleDesignSizegpRepository.querySampleDesignSizegp(sampno, suitty);
	}
	public List<SampleDesignSizegp> querySampleDesignSizegp_T00(String sampno) {
		return sampleDesignSizegpRepository.querySampleDesignSizegp_T00(sampno);
	}
}
