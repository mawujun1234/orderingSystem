package com.youngor.order.cg;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.order.cg.CgOrddt;
import com.youngor.order.cg.CgOrddtRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CgOrddtService extends AbstractService<CgOrddt, com.youngor.order.cg.CgOrddt.PK>{

	@Autowired
	private CgOrddtRepository cgOrddtRepository;
	
	@Override
	public CgOrddtRepository getRepository() {
		return cgOrddtRepository;
	}

	public void updateBatch1( CgOrddt[] cgOrddtes) {
		for(CgOrddt cgOrddt : cgOrddtes){
			cgOrddtRepository.createOrUpdate(cgOrddt);
		}
	}
	
	

}
