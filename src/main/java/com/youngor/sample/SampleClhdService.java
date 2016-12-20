package com.youngor.sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleClhdService extends AbstractService<SampleClhd, String>{

	@Autowired
	private SampleClhdRepository sampleClhdRepository;
	
	@Override
	public SampleClhdRepository getRepository() {
		return sampleClhdRepository;
	}

	public void enable_disable(String clppno) {
		SampleClhd sampleClhd=sampleClhdRepository.get(clppno);
		if(sampleClhd.getClppst()==0){
			sampleClhd.setClppst(1);
		} else {
			sampleClhd.setClppst(0);
		}
		
		sampleClhdRepository.update(sampleClhd);
	}
//	/**
//	 * 查询图片
//	 * @author mawujun qq:16064988 mawujun1234@163.com
//	 * @param clppno
//	 * @return
//	 */
//	public List<SampleClpht> querySampleClphtes(String clppno) {
//		return sampleClhdRepository.querySampleClphtes(clppno);
//	}
}
