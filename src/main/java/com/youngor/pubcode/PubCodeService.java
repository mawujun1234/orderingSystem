package com.youngor.pubcode;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.pubcode.PubCode;
import com.youngor.pubcode.PubCodeRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PubCodeService extends AbstractService<PubCode, String>{

	@Autowired
	private PubCodeRepository pubCodeRepository;
	
	@Override
	public PubCodeRepository getRepository() {
		return pubCodeRepository;
	}

	public List<PubCode> query(String tyno,String fitno,String bradno,String stat) {
		return pubCodeRepository.query(tyno,fitno, bradno,stat);
	}
}
