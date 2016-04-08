package com.youngor.pubcode;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.pubcode.PubCodeType;
import com.youngor.pubcode.PubCodeTypeRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PubCodeTypeService extends AbstractService<PubCodeType, String>{

	@Autowired
	private PubCodeTypeRepository pubCodeTypeRepository;
	
	@Override
	public PubCodeTypeRepository getRepository() {
		return pubCodeTypeRepository;
	}

}
