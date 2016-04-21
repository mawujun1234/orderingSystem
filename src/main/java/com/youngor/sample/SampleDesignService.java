package com.youngor.sample;
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
public class SampleDesignService extends AbstractService<SampleDesign, String>{

	@Autowired
	private SampleDesignRepository sampleDesignRepository;
	
	@Autowired
	private SampleDesignStprRepository sampleDesignStprRepository;
	
	@Override
	public SampleDesignRepository getRepository() {
		return sampleDesignRepository;
	}
	
	@Override
	public void deleteById(String sampno) {
		SampleDesign sampleDesign=sampleDesignRepository.get(sampno);
		sampleDesign.setSampst(0);
		sampleDesignRepository.update(sampleDesign);
	}
	
	@Override
	public String create(SampleDesign samplePlan) {
		String id=super.create(samplePlan);
		if(samplePlan.getSampleDesignStpres()!=null){
			for(SampleDesignStpr samplePlanStpr:samplePlan.getSampleDesignStpres()){
				samplePlanStpr.setSampno(samplePlan.getSampno());
				sampleDesignStprRepository.create(samplePlanStpr);
			}
		}
		return id;
	}
	@Override
	public  void update(SampleDesign samplePlan) {
		super.update(samplePlan);
		sampleDesignStprRepository.deleteBatch(Cnd.delete().andEquals(M.SampleDesignStpr.sampno, samplePlan.getSampno()));
		if(samplePlan.getSampleDesignStpres()!=null){
			for(SampleDesignStpr samplePlanStpr:samplePlan.getSampleDesignStpres()){
				samplePlanStpr.setSampno(samplePlan.getSampno());
				sampleDesignStprRepository.create(samplePlanStpr);
			}
		}
	}

}
