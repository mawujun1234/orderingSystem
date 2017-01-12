package com.youngor.order.cg;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.order.cg.CgOrddtl;
import com.youngor.order.cg.CgOrddtlRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CgOrddtlService extends AbstractService<CgOrddtl, com.youngor.order.cg.CgOrddtl.PK>{

	@Autowired
	private CgOrddtlRepository cgOrddtlRepository;
	
	@Override
	public CgOrddtlRepository getRepository() {
		return cgOrddtlRepository;
	}

}
