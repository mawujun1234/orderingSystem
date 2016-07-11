package com.youngor.pubsize;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;
import com.youngor.permission.ShiroUtils;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SizeDtlService extends AbstractService<SizeDtl, com.youngor.pubsize.SizeDtl.PK>{

	@Autowired
	private SizeDtlRepository sizeDtlRepository;
	
	@Override
	public SizeDtlRepository getRepository() {
		return sizeDtlRepository;
	}

	public List<SizeDtlVO> query(String ormtno,String fszty,String fszno,String sizety) {
		return sizeDtlRepository.query(ormtno, fszty, fszno,sizety);
	}
	
	public void create(SizeDtl sizeDtl,String[] sizenos) {
		if(sizenos==null || sizenos.length==0){
			return;
		}
		for(String sizeno:sizenos){
			SizeDtl target=new SizeDtl();
			BeanUtils.copyProperties(sizeDtl, target);
			target.setSizeno(sizeno);
			target.setRgdt(new Date());
			target.setRgsp(ShiroUtils.getLoginName());
			target.setLmdt(new Date());
			target.setRgsp(ShiroUtils.getLoginName());
			sizeDtlRepository.create(target);
		}
	}
}
