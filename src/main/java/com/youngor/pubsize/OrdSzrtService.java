package com.youngor.pubsize;
import org.hibernate.ObjectNotFoundException;
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
public class OrdSzrtService extends AbstractService<OrdSzrt, OrdSzrt.PK>{

	@Autowired
	private OrdSzrtRepository ordSzrtRepository;
	
	@Override
	public OrdSzrtRepository getRepository() {
		return ordSzrtRepository;
	}
	@Override
	public OrdSzrt.PK create(OrdSzrt ordSzrt) {
		try {
		ordSzrtRepository.deleteById(ordSzrt.geetPK());
		} catch( ObjectNotFoundException e) {
			
		}
		ordSzrtRepository.create(ordSzrt);
		return ordSzrt.geetPK();
	}
}
