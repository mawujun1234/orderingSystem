package com.youngor.sample;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;
import com.youngor.sample.SamplePlan;
import com.youngor.sample.SamplePlanRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SamplePlanService extends AbstractService<SamplePlan, String>{

	@Autowired
	private SamplePlanRepository samplePlanRepository;
	
	@Override
	public SamplePlanRepository getRepository() {
		return samplePlanRepository;
	}

}
