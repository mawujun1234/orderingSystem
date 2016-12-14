package com.youngor.plan;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.mawujun.repository.IRepository;

import com.youngor.plan.PlanCls;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface PlanClsRepository extends IRepository<PlanCls, com.youngor.plan.PlanCls.PK>{
	public List<PlanCls> queryAll(PlanCls params);

}
