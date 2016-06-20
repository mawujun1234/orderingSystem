package com.youngor.plan;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;
import com.youngor.utils.MapParams;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PlanOrgService extends AbstractService<PlanOrg, String>{

	@Autowired
	private PlanOrgRepository planOrgRepository;
	
	@Override
	public PlanOrgRepository getRepository() {
		return planOrgRepository;
	}
	
	public List<PlanOrgdtlVO> queryPlanOrgdtlVO(MapParams params) {
		return planOrgRepository.queryPlanOrgdtlVO(params.getParams());
	}

}
