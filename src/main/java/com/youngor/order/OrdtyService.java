package com.youngor.order;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.order.Ordty;
import com.youngor.order.OrdtyRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrdtyService extends AbstractService<Ordty, String>{

	@Autowired
	private OrdtyRepository ordtyRepository;
	
	@Override
	public OrdtyRepository getRepository() {
		return ordtyRepository;
	}
	
	

}
