package com.youngor.order1;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.sample.SampleProdVO;
import com.youngor.utils.MapParams;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrdOrddtlService extends AbstractService<OrdOrddtl, com.youngor.order1.OrdOrddtl.PK>{

	@Autowired
	private OrdOrddtlRepository ordOrddtlRepository;
	
	@Override
	public OrdOrddtlRepository getRepository() {
		return ordOrddtlRepository;
	}

	public Pager<OrdOrddtlQuery> queryPage1(Pager<OrdOrddtlQuery> pager){
		return ordOrddtlRepository.queryPage1(pager);
	}
	
	public List<OrdOrddtlQuery> queryPage1(MapParams params) {
		return ordOrddtlRepository.queryPage1(params.getParams());
	}
	public List<AAAA> querymx(String ormtno,String ordate) {
		return ordOrddtlRepository.querymx(ormtno, ordate);
	}
}
