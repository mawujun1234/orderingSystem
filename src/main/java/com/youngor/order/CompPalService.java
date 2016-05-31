package com.youngor.order;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.order.CompPal;
import com.youngor.order.CompPalRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CompPalService extends AbstractService<CompPal, com.youngor.order.CompPal.PK>{

	@Autowired
	private CompPalRepository compPalRepository;
	
	@Override
	public CompPalRepository getRepository() {
		return compPalRepository;
	}

}
