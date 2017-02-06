package com.youngor.pubsize;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;
import com.mawujun.utils.bean.BeanUtils;
import com.youngor.ordmt.Ordmt;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.ContextUtils;


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
	
	public  void copy(String sizeno_old) {
		Size sizeOld=sizeRepository.get(sizeno_old);
		Size size=BeanUtils.copyOrCast(sizeOld, Size.class);
		size.setLmdt(new Date());
		size.setLmsp(ShiroUtils.getUserId());
		size.setRgdt(new Date());
		size.setRgsp(ShiroUtils.getUserId());
		Ordmt ordmt = ContextUtils.getFirstOrdmt();
		size.setOrmtno(ordmt.getOrmtno());
		size.setSizeno(null);
		
		create(size);
		
		//复制明细数据
		sizeDtlRepository.copyByFszno(sizeOld.getSizeno(), size.getSizeno(), ordmt.getOrmtno(), size.getLmsp());
		
	}
	
	public int sizeInSample(@Param("sizeno")String sizeno) {
		return sizeRepository.sizeInSample(sizeno);
	}
}
