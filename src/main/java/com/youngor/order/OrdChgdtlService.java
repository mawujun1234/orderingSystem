package com.youngor.order;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.controller.spring.mvc.MapParams;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrdChgdtlService extends AbstractService<OrdChgdtl, com.youngor.order.OrdChgdtl.PK>{

	@Autowired
	private OrdChgdtlRepository ordChgdtlRepository;
	
	@Override
	public OrdChgdtlRepository getRepository() {
		return ordChgdtlRepository;
	}
	
	public Pager<OrdChgdtlVO> queryPager1(Pager<OrdChgdtlVO> pager){
		return ordChgdtlRepository.queryPager1(pager);
	}
	
	public List<OrdChgdtlCompVO> query4comp(MapParams params) {
		return ordChgdtlRepository.query4comp(params.getParams());
	}
	public List<OrdChgdtlQyVO> query4qy(MapParams params) {
		return ordChgdtlRepository.query4qy(params.getParams());
	}
	
	public  void updateValue(OrdChgdtl ordChgdtl_param,String filed,String value) throws IllegalAccessException, InvocationTargetException {
		OrdChgdtl ordChgdtl=ordChgdtlRepository.get(ordChgdtl_param.geetPK());
		if(ordChgdtl==null){
			ordChgdtl=new OrdChgdtl();
			BeanUtils.copyProperties(ordChgdtl, ordChgdtl_param);
			ordChgdtl.setLmdt(new Date());
			ordChgdtl.setLmsp(ShiroUtils.getUserId());
			ordChgdtl.setRgdt(new Date());
			ordChgdtl.setRgsp(ShiroUtils.getUserId());
			
			if(M.OrdChgdtl.orchqt.equals(filed)){
				ordChgdtl.setOrchqt(Integer.parseInt(value));	
			} else if(M.OrdChgdtl.ormark.equals(filed)){
				ordChgdtl.setOrmark(value);
			}
			ordChgdtlRepository.create(ordChgdtl);
		} else {
			if(M.OrdChgdtl.orchqt.equals(filed)){
				ordChgdtl.setOrchqt(Integer.parseInt(value));	
			} else if(M.OrdChgdtl.ormark.equals(filed)){
				ordChgdtl.setOrmark(value);
			}
			ordChgdtl.setLmdt(new Date());
			ordChgdtl.setLmsp(ShiroUtils.getUserId());
			ordChgdtlRepository.update(ordChgdtl);
		}
		
	}
	
	public void cancel(String ormtno,String sampno,String suitno,String[] compnos,String bradno,String spclno) {
		ordChgdtlRepository.deleteBatch(Cnd.delete().andEquals(M.OrdChgdtl.ormtno, ormtno).andEquals(M.OrdChgdtl.sampno, sampno)
				.andEquals(M.OrdChgdtl.suitno, suitno));
		for(String compno:compnos){
			List<Map<String,Object>> qylist=ordChgdtlRepository.queryOrderQy(ormtno, sampno, suitno, compno, bradno, spclno);
			for(Map<String,Object> map:qylist){
				
				OrdChgdtl ordChgdtl=new OrdChgdtl();
				ordChgdtl.setLmdt(new Date());
				ordChgdtl.setLmsp(ShiroUtils.getUserId());
				ordChgdtl.setRgdt(new Date());
				ordChgdtl.setRgsp(ShiroUtils.getUserId());
				
				ordChgdtl.setOrmtno(ormtno);
				ordChgdtl.setSampno(sampno);
				ordChgdtl.setSuitno(suitno);
				ordChgdtl.setOrdorg(map.get("QYNO").toString());
				
				ordChgdtl.setOrchqt(-Integer.parseInt(map.get("ORMTQT").toString()));
				ordChgdtlRepository.create(ordChgdtl);
				
			}
		}
		
		
	}

}
