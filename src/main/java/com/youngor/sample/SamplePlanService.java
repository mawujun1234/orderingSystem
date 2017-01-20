package com.youngor.sample;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;
import com.youngor.permission.ShiroUtils;
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
//	@Autowired
//	private SamplePlanStprRepository samplePlanStprRepository;
	@Autowired
	private SampleDesignStprRepository sampleDesignStprRepository;
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
	public  void update(SamplePlan samplePlan) {
		this.getRepository().update(samplePlan);
		
		//同时更新设计 样衣中的标准套件的 出厂价，零售价
		sampleDesignStprRepository.update_T00_stpr_by_plspno(samplePlan.getPlspno(), samplePlan.getSpftpr(), samplePlan.getSprtpr());
	}
	
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
	
	public void destroyBatch(String[] plspnos) {
		if(plspnos==null || plspnos.length==0){
			return;
		}
		for(String plspno:plspnos){
			SamplePlan samplePlan=new SamplePlan();
			samplePlan.setPlspno(plspno);
			this.delete(samplePlan);
		}
	}
	
	public void lockOrunlock(String [] plspnos,Integer plspst) {
		if(plspnos==null || plspnos.length==0){
			return;
		}
		for(String plspno:plspnos){
			SamplePlan samplePlan=samplePlanRepository.get(plspno);
			samplePlan.setPlspst(plspst);
			samplePlanRepository.update(samplePlan);
		}
		
	}
	
	public List<SamplePlanVO> queryList4Export(MapParams params){
		((Map<String,Object>)params.getParams()).put("user_id", ShiroUtils.getUserId());
		return samplePlanRepository.queryPage(params.getParams());
	}
	

}
