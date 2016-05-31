package com.youngor.sample;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.sample.SampleDesignStpr;
import com.youngor.sample.SampleDesignStprRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleDesignStprService extends AbstractService<SampleDesignStpr, String>{

	@Autowired
	private SampleDesignStprRepository sampleDesignStprRepository;
	
	@Override
	public SampleDesignStprRepository getRepository() {
		return sampleDesignStprRepository;
	}
	
//	public List<SampleDesignStpr> querySampleDesignStpr(String suitty,String sampno) {
//		return sampleDesignStprRepository.querySampleDesignStpr(suitty,sampno);
//	}
	
	public List<SampleDesignStpr> querySampleDesignStpr(String sampno,String suitty) {
		return sampleDesignStprRepository.querySampleDesignStpr(sampno, suitty);
	}
	public List<SampleDesignStpr> querySampleDesignStpr_T00(String sampno) {
		return sampleDesignStprRepository.querySampleDesignStpr_T00(sampno);
	}

}
