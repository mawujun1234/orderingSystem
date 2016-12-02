package com.youngor.plan;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.plan.PlanCls;
import com.youngor.plan.PlanClsRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PlanClsService extends AbstractService<PlanCls, com.youngor.plan.PlanCls.PK>{

	@Autowired
	private PlanClsRepository planClsRepository;
	
	@Override
	public PlanClsRepository getRepository() {
		return planClsRepository;
	}
	
	public List<PlanCls> queryAll(Map<String,Object> params) {
		return planClsRepository.queryAll();
	}

}
