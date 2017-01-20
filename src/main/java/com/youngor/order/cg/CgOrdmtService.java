package com.youngor.order.cg;
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
public class CgOrdmtService extends AbstractService<CgOrdmt, com.youngor.order.cg.CgOrdmt.PK>{

	@Autowired
	private CgOrdmtRepository cgOrdmtRepository;
	
	@Override
	public CgOrdmtRepository getRepository() {
		return cgOrdmtRepository;
	}

}
