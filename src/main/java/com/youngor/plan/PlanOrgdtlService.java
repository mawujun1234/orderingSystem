package com.youngor.plan;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.plan.PlanOrgdtl;
import com.youngor.plan.PlanOrgdtlRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PlanOrgdtlService extends AbstractService<PlanOrgdtl, com.youngor.plan.PlanOrgdtl.PK>{

	@Autowired
	private PlanOrgdtlRepository planOrgdtlRepository;
	
	@Override
	public PlanOrgdtlRepository getRepository() {
		return planOrgdtlRepository;
	}

}
