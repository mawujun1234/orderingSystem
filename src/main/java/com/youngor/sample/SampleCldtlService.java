package com.youngor.sample;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SampleCldtlService extends AbstractService<SampleCldtl, com.youngor.sample.SampleCldtl.PK>{

	@Autowired
	private SampleCldtlRepository sampleCldtlRepository;
	@Autowired
	private SampleDesignRepository sampleDesignRepository;
	
	@Override
	public SampleCldtlRepository getRepository() {
		return sampleCldtlRepository;
	}
	
	public void create(SampleCldtlVO sampleCldtlVO) {
		//先判断这个样衣编号存不存在，如果存在，再去保存
		SampleDesign sampleDesign=sampleDesignRepository.getSampleDesignBySampnm(sampleCldtlVO.getOrmtno(), sampleCldtlVO.getSampnm());
		if(sampleDesign==null){
			throw new BusinessException("该样衣编号不存在，请检查！");
		}
		SampleCldtl sampleCldtl=new SampleCldtl();
		BeanUtils.copyProperties(sampleCldtlVO, sampleCldtl);
		sampleCldtl.setSampno(sampleDesign.getSampno());
		
		sampleCldtlRepository.create(sampleCldtl);
				
	}
	
	public List<SampleCldtlVO> queryAll(String clppno) {
		return sampleCldtlRepository.queryAll(clppno);
	}

}
