package com.youngor.ordmt;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.youngor.ordmt.OrdmtScde;
import com.youngor.ordmt.OrdmtScdeRepository;
import com.youngor.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrdmtScdeService extends AbstractService<OrdmtScde, String>{

	@Autowired
	private OrdmtScdeRepository ordmtScdeRepository;
	
	@Override
	public OrdmtScdeRepository getRepository() {
		return ordmtScdeRepository;
	}
	

}
