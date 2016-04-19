package com.youngor.sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	@Override
	public String create(SamplePlan samplePlan) {
		String id=super.create(samplePlan);
		if(samplePlan.getSamplePlanStpres()!=null){
			for(SamplePlanStpr samplePlanStpr:samplePlan.getSamplePlanStpres()){
				samplePlanStpr.setPlspno(samplePlan.getPlspno());
				samplePlanStprRepository.create(samplePlanStpr);
			}
		}
		return id;
	}
	@Override
	public  void update(SamplePlan samplePlan) {
		super.update(samplePlan);
		samplePlanStprRepository.deleteBatch(Cnd.delete().andEquals(M.SamplePlanStpr.plspno, samplePlan.getPlspno()));
		if(samplePlan.getSamplePlanStpres()!=null){
			for(SamplePlanStpr samplePlanStpr:samplePlan.getSamplePlanStpres()){
				samplePlanStpr.setPlspno(samplePlan.getPlspno());
				samplePlanStprRepository.create(samplePlanStpr);
			}
		}
	}
	
	@Override
	public  void delete(SamplePlan samplePlan) {
		samplePlan=samplePlanRepository.get(samplePlan.getPlspno());
		samplePlan.setPlspst(0);
		samplePlanRepository.update(samplePlan);
	}
	
	public Pager<SamplePlanDesignVO> queryPlanDesign(Pager<SamplePlanDesignVO> pager) {
		return samplePlanRepository.queryPlanDesign(pager);
	}
}
