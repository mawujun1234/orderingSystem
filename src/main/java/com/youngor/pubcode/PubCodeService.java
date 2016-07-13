package com.youngor.pubcode;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.youngor.pubcode.PubCode;
import com.youngor.pubcode.PubCodeRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PubCodeService extends AbstractService<PubCode, String>{

	@Autowired
	private PubCodeRepository pubCodeRepository;
	
	@Override
	public PubCodeRepository getRepository() {
		return pubCodeRepository;
	}

	public List<PubCode> query(String tyno,String fitno,String bradno,String stat_stat) {
		//表示取当季的数据
		if("1".equals(tyno)){
			return pubCodeRepository.query1(tyno, fitno, bradno, stat_stat);
		}
		if(stat_stat==null){
			stat_stat="0";
		}
		//表示取所有的数据
		//stat_stat=0取所有当季和非当季的数据，stat_stat=1取当季的数据
		return pubCodeRepository.query(tyno,fitno, bradno,stat_stat);
	}
	
	public List<PubCode> queryList(String tyno,String fitno,String bradno) {
		return pubCodeRepository.queryList(tyno,fitno, bradno);
	}
	
	public  void updateStat(String bradno,String tyno,String itno,String stat) {
		pubCodeRepository.updateStat(bradno,tyno, itno, stat);
	}
}
