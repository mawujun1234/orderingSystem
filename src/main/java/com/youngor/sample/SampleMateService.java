package com.youngor.sample;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleMateService extends AbstractService<SampleMate, SampleMate.PK>{

	@Autowired
	private SampleMateRepository sampleMateRepository;
	
	@Override
	public SampleMateRepository getRepository() {
		return sampleMateRepository;
	}

	@Override
	public SampleMate.PK create(SampleMate sampleMate) {
		Integer count=sampleMateRepository.queryCount(Cnd.count().andEquals(M.SampleMate.sampno, sampleMate.getSampno()));
		sampleMate.setMateso(count+1);
		
//		sampleMate.setRgdt(new Date());
//		sampleMate.setRgsp(ShiroUtils.getLoginName());
//		sampleMate.setLmdt(new Date());
//		sampleMate.setLmsp(ShiroUtils.getLoginName());
		super.create(sampleMate);
		return sampleMate.getPk();
	}
	
	public void lock(Map<String,Object> params) {
		sampleMateRepository.lock(params);
	}
	public void unlock(Map<String,Object> params){
		sampleMateRepository.unlock(params);
	}
	
	public List<SampleMate> queryAll(){
		return sampleMateRepository.queryAll();
	}
}
