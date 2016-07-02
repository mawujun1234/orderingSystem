package com.youngor.sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.utils.M;


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
	@Autowired
	private SamplePlanStprRepository samplePlanStprRepository;
	
	@Override
	public SamplePlanRepository getRepository() {
		return samplePlanRepository;
	}
//	@Override
//	public String create(SamplePlan samplePlan) {
//		String id=super.create(samplePlan);
//		if(samplePlan.getSamplePlanStpres()!=null){
//			for(SamplePlanStpr samplePlanStpr:samplePlan.getSamplePlanStpres()){
//				samplePlanStpr.setPlspno(samplePlan.getPlspno());
//				samplePlanStprRepository.create(samplePlanStpr);
//			}
//		}
//		return id;
//	}
//	@Override
//	public  void update(SamplePlan samplePlan) {
//		super.update(samplePlan);
//		samplePlanStprRepository.deleteBatch(Cnd.delete().andEquals(M.SamplePlanStpr.plspno, samplePlan.getPlspno()));
//		if(samplePlan.getSamplePlanStpres()!=null){
//			for(SamplePlanStpr samplePlanStpr:samplePlan.getSamplePlanStpres()){
//				samplePlanStpr.setPlspno(samplePlan.getPlspno());
//				samplePlanStprRepository.create(samplePlanStpr);
//			}
//		}
//	}
	
	@Override
	public  void delete(SamplePlan samplePlan) {
		int count=samplePlanRepository.checkPlanInDesign(samplePlan.getPlspno());
		if(count>0){
			throw new BusinessException("该企划样衣已被使用，不能删除!");
		}
		
		samplePlan=samplePlanRepository.get(samplePlan.getPlspno());
		samplePlan.setPlstat(0);
		samplePlanRepository.update(samplePlan);
	}
	
	public void lockOrunlock(String plspno,Integer plspst) {
		SamplePlan samplePlan=samplePlanRepository.get(plspno);
		samplePlan.setPlspst(plspst);
		samplePlanRepository.update(samplePlan);
	}
	

}
