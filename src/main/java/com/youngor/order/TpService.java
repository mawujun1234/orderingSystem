package com.youngor.order;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.utils.page.Pager;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class TpService {

	@Autowired
	private TpRepository tpRepository;

	public  Pager<Map<String,Object>> tpAllQuery( Pager<Map<String,Object>> pager) {
		return tpRepository.tpAllQuery(pager);
	}
	
	
	public  List<Map<String,Object>> queryTpYxgsColumns() {
		return tpRepository.queryTpYxgsColumns();
	}
	
	public  Pager<Map<String,Object>> tpYxgsQuery( Pager<Map<String,Object>> pager) {
		return tpRepository.tpYxgsQuery(pager);
	}
	
	public  List<Map<String,Object>> queryTpQyColumns(String yxgsno){
		return tpRepository.queryTpQyColumns(yxgsno);
	}
	
	public  Pager<Map<String,Object>> tpQyQuery( Pager<Map<String,Object>> pager) {
		return tpRepository.tpQyQuery(pager);
	}
	
}
