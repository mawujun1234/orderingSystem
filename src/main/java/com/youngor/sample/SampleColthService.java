package com.youngor.sample;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.youngor.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleColthService extends AbstractService<SampleColth, String>{

	@Autowired
	private SampleColthRepository sampleColthRepository;
	@Autowired
	private SampleDesignStprRepository sampleDesignStprRepository;
	
	@Override
	public SampleColthRepository getRepository() {
		return sampleColthRepository;
	}

	
	@Override
	public String create(SampleColth sampleColth) {
		super.delete(sampleColth);
		String id=super.create(sampleColth);
		sampleDesignStprRepository.deleteBatch(Cnd.delete().andEquals(M.SampleDesignStpr.sampno, sampleColth.getSampno()));
		if(sampleColth.getSampleDesignStpres()!=null){
			for(SampleDesignStpr samplePlanStpr:sampleColth.getSampleDesignStpres()){
				samplePlanStpr.setSampno(sampleColth.getSampno());
				sampleDesignStprRepository.create(samplePlanStpr);
			}
		}
		return id;
	}
//	@Override
//	public  void update(SampleColth sampleColth) {
//		super.update(sampleColth);
//		sampleDesignStprRepository.deleteBatch(Cnd.delete().andEquals(M.SampleDesignStpr.sampno, sampleColth.getSampno()));
//		if(sampleColth.getSampleDesignStpres()!=null){
//			for(SampleDesignStpr samplePlanStpr:sampleColth.getSampleDesignStpres()){
//				samplePlanStpr.setSampno(sampleColth.getSampno());
//				sampleDesignStprRepository.create(samplePlanStpr);
//			}
//		}
//	}
	
	public  void updateSpctpr(String sampno,Double spctpr) {
		sampleColthRepository.updateSpctpr(sampno, spctpr);
	}
	
	public void lock(Map<String,Object> params) {
		sampleColthRepository.lock(params);
	}
	public void unlock(Map<String,Object> params){
		sampleColthRepository.unlock(params);
	}
	
	//@Override
	public SampleColth load(String sampno) {
		return sampleColthRepository.load(sampno);
	}
}
