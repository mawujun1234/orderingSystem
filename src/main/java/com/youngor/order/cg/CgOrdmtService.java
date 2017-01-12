package com.youngor.order.cg;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.order.cg.CgOrdmt;
import com.youngor.order.cg.CgOrdmtRepository;


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