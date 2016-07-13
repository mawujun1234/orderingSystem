package com.youngor.pubsize;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SizeService extends AbstractService<Size, String>{

	@Autowired
	private SizeRepository sizeRepository;
	@Autowired
	private SizeDtlRepository sizeDtlRepository;
	
	@Override
	public SizeRepository getRepository() {
		return sizeRepository;
	}
	@Override
	public String create(Size size) {
		//String sizeno=size.getOrmtno()+"_"+size.getYsizeno();
		//获取现有的这次订货会，这个规格系列下面已经生成了几个规格范围
		String max_sizeno=sizeRepository.getCountByYsizeno(size.getOrmtno(), size.getYsizety(), size.getYsizeno());
		String sizeno=size.getOrmtno()+"_"+size.getYsizeno()+"_1";
		if(max_sizeno!=null && !"".equals(max_sizeno)){
			String[] aa=max_sizeno.split("_");
			Integer index=Integer.parseInt(aa[aa.length-1]);
			aa[aa.length-1]=(index+1)+"";
			sizeno=StringUtils.join(aa, "_");
		}
		
		
		size.setSizeno(sizeno);
		
		sizeRepository.create(size);
		return sizeno;
	}
	@Override
	public void delete(Size size) {
		this.getRepository().delete(size);
		//删除明细数据
		sizeDtlRepository.deleteByFszno(size.getSizety(), size.getSizeno());
		
	}
}
