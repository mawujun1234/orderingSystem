package com.youngor.plan;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.bean.BeanUtils;
import com.youngor.permission.ShiroUtils;
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
	@Autowired
	private PlanOrgdtlRepository planOrgdtlRepository;
	
	@Override
	public PlanOrgRepository getRepository() {
		return planOrgRepository;
	}
	
	public List<PlanOrgdtlVO> queryPlanOrgdtlVO(MapParams params) {
		return planOrgRepository.queryPlanOrgdtlVO(params.getParams());
	}

	public  void update(@RequestBody PlanOrgdtlVO planOrgdtlVO) {
		String plorno=planOrgdtlVO.getOrmtno()+"_"+planOrgdtlVO.getOrdorg()+"_"+planOrgdtlVO.getBradno();
		if(planOrgRepository.get(plorno)==null) {
			PlanOrg planOrg=new PlanOrg();
			planOrg.setPlorno(plorno);
			planOrg.setOrmtno(planOrgdtlVO.getOrmtno());
			planOrg.setOrdorg(planOrgdtlVO.getOrdorg());
			planOrg.setBradno(planOrgdtlVO.getBradno());
			planOrg.setPlstat(0);
			planOrgRepository.create(planOrg);
			
		}
		PlanOrgdtl planOrgdtl=BeanUtils.copyOrCast(planOrgdtlVO, PlanOrgdtl.class);
		planOrgdtl.setPlorno(plorno);
		planOrgdtlRepository.createOrUpdate(planOrgdtl);
		
		
	}
	
	public  void onPass(String ormtno, String ordorg,String bradno) {
		//如果状态是编辑中，但是不是当前组织单元 就不能提交审批
		if(!ShiroUtils.getAuthenticationInfo().inTheOrg(ordorg)){
			throw new BusinessException("你没有权限进行提交!");
		}
		
		String plorno=ormtno+"_"+ordorg+"_"+bradno;
		PlanOrg planOrg=planOrgRepository.get(plorno);
		if(planOrg.getPlstat()==3){
			return;
		}
		if(ShiroUtils.getAuthenticationInfo().inTheOrg(ordorg)  && planOrg.getPlstat()!=0){
			throw new BusinessException("已经提交过了，你没有权限进行提交!");
		}
		planOrg.setPlstat(planOrg.getPlstat()+1);
		planOrgRepository.update(planOrg);
	}
	
	public  void onBack(String ormtno, String ordorg,String bradno) {
		//如果状态是编辑中，但是不是当前组织单元 就不能提交审批
		if(ShiroUtils.getAuthenticationInfo().inTheOrg(ordorg)){
			throw new BusinessException("你没有权限进行退回!");
		}
		
		String plorno=ormtno+"_"+ordorg+"_"+bradno;
		PlanOrg planOrg=planOrgRepository.get(plorno);
		if(planOrg.getPlstat()==0){
			return;
		}
		planOrg.setPlstat(planOrg.getPlstat()-1);
		planOrgRepository.update(planOrg);
	}
}
