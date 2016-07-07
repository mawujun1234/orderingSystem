package com.youngor.sample;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;
import com.youngor.utils.MapParams;


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
		if(samplePlan.getPlspst()==1){
			throw new BusinessException("已经锁定,不能删除!");
		}
		samplePlanRepository.delete(samplePlan);
//		samplePlan.setPlstat(0);
//		samplePlan.setLmdt(new Date());
//		samplePlan.setLmsp(ShiroUtils.getLoginName());
//		samplePlanRepository.update(samplePlan);
	}
	
	public void lockOrunlock(String plspno,Integer plspst) {
		SamplePlan samplePlan=samplePlanRepository.get(plspno);
		samplePlan.setPlspst(plspst);
		samplePlanRepository.update(samplePlan);
	}
	
	public List<SamplePlanVO> queryList4Export(MapParams params){
		return samplePlanRepository.queryPage(params.getParams());
	}
	

}
