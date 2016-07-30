package com.youngor.sample;
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
public class SampleProdService extends AbstractService<SampleProd, com.youngor.sample.SampleProd.PK>{

	@Autowired
	private SampleProdRepository sampleProdRepository;
	
	@Override
	public SampleProdRepository getRepository() {
		return sampleProdRepository;
	}
	
	public void createOrUpdate(SampleProd sampleProd) {
		//PK pk=sampleProd.geetPK();
		sampleProdRepository.createOrUpdate(sampleProd);
	}

}
