package com.youngor.plan;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.mawujun.repository.IRepository;

import com.youngor.plan.PlanOrg;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface PlanOrgRepository extends IRepository<PlanOrg, String>{

	public List<PlanOrgdtlVO> queryPlanOrgdtlVO(Map<String, Object> params);
	public List<PlanOrgdtlVO> queryPlanOrgdtlVO_all(Map<String, Object> params);
}
