package com.youngor.order.cg;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.controller.spring.mvc.MapParams;
import com.mawujun.service.AbstractService;


import com.youngor.order.cg.CgOrdhd;
import com.youngor.order.cg.CgOrdhdRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CgOrdhdService extends AbstractService<CgOrdhd, com.youngor.order.cg.CgOrdhd.PK>{

	@Autowired
	private CgOrdhdRepository cgOrdhdRepository;
	
	@Override
	public CgOrdhdRepository getRepository() {
		return cgOrdhdRepository;
	}

	public List<CgOrdhdVO> queryAll_1(MapParams params) {
		return cgOrdhdRepository.queryAll_1(params.getParams());
	}
	
	public  void confirm(String orcgno,String cgorno) {
		CgOrdhd.PK pk=new CgOrdhd.PK();
		pk.setCgorno(cgorno);
		pk.setOrcgno(orcgno);
		CgOrdhd cgOrdhd=cgOrdhdRepository.get(pk);
		cgOrdhd.setOrstat(1);
		cgOrdhdRepository.update(cgOrdhd);
	}
}
