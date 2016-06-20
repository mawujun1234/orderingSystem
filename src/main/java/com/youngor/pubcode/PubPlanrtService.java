package com.youngor.pubcode;
import java.util.List;

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
public class PubPlanrtService extends AbstractService<PubPlanrt, com.youngor.pubsize.PubSize.PK>{

	@Autowired
	private PubPlanrtRepository pubPlanrtRepository;
	
	@Override
	public PubPlanrtRepository getRepository() {
		return pubPlanrtRepository;
	}
	
	public List<PubPlanrt> queryGrid(String bradno,String spclno,String sptyno) {
		return pubPlanrtRepository.queryGrid(bradno,spclno,sptyno);
	}

}
