package com.youngor.plan;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.pubcode.PubCodeCache;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrdDwdtService extends AbstractService<OrdDwdt, com.youngor.plan.OrdDwdt.PK>{

	@Autowired
	private OrdDwdtRepository ordDwdtRepository;
	
	@Override
	public OrdDwdtRepository getRepository() {
		return ordDwdtRepository;
	}
	
	public Pager<Map<String,Object>> queryPager1(Pager<Map<String,Object>> pager) {
		pager=ordDwdtRepository.queryPager1(pager);
		List<Map<String,Object>> list=pager.getRoot();
		for(Map<String,Object> map:list){
			map.put("SUITNM", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return pager;
	}
	
	public  void updateField(List<Map<String,Object>> sampnos,String ormtno,String ortyno,String count_type ) {
		if(sampnos==null || sampnos.size()==0){
			return;
		}
		//如果yxgsno！=0，就设置该营销公司下面所有的区域订货单位中指定的额样衣编号的交货期
		//如果qyno！=0，就设置该区域订货单位中指定的额样衣编号的交货期
		//如果不存在，就新建，否则就update
		for(Map<String,Object> map:sampnos) {
			
			String sampno=map.get("sampno").toString();
			String suitno=map.get("suitno").toString();
			String yxgsno=(String)map.get("yxgsno");
			String qyno=(String)map.get("qyno");
			
			ordDwdtRepository.indsert_dwdt(sampno, suitno, ormtno, ortyno, count_type, yxgsno, qyno, map.get("field").toString(), map.get("value").toString());
			ordDwdtRepository.update_dwdt(sampno, suitno, ormtno, ortyno, count_type, yxgsno, qyno, map.get("field").toString(), map.get("value").toString());
		}
		
	}

}
