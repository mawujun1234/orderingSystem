package com.youngor.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class BW2DZService {
	@Autowired
	private BW2DZRepository bW2DZRepository;
	@Autowired
	private OrdService ordService;
	@Autowired
	private OrddtlRepository  orddtlRepository;
	
	
	public void transform(List<Map<String,Object>> params) {
		
		for(Map<String,Object> param:params){
			String sampno=param.get("sampno").toString();
			String suitno=param.get("suitno").toString();
			Integer num=Integer.parseInt(param.get("num").toString());
			String dz_mtorno=ordService.getMtorno(param.get("ormtno").toString(), "DZ", param.get("ordorg").toString());
			String bw_mtorno=ordService.getMtorno(param.get("ormtno").toString(), "BW", param.get("ordorg").toString());
			//System.out.println(dz_mtorno+"----"+param.get("sampno")+"----"+param.get("suitno")+"----"+param.get("num"));
			//System.out.println(bw_mtorno+"----"+param.get("sampno")+"----"+param.get("suitno")+"----"+param.get("num"));
			
			//更新订制订单,如果订制订单中不存在这个样衣编号
			Orddtl.PK pk=new Orddtl.PK();
			pk.setMtorno(dz_mtorno);
			pk.setSampno(sampno);
			pk.setSuitno(suitno);
			Orddtl orddtl=orddtlRepository.get(pk);
			if(orddtl==null){
				if(num>0){
					throw new BusinessException("对应的订制订单没有数据，不能进行转移!");
				}
				orddtl=new Orddtl();
				orddtl.setMtorno(dz_mtorno);
				orddtl.setSampno(sampno);
				orddtl.setSuitno(suitno);
				orddtl.setOrmtqs(0);
				orddtl.setOrmtqt(Math.abs(num));	
				//获取mlorno，mlorvn
				Map<String,Object> mlorno=bW2DZRepository.get_mlorno_mlorvn(param);
				orddtl.setMlorno(mlorno.get("mlorno").toString());
				orddtl.setMlorvn(((BigDecimal)mlorno.get("mlorvn")).intValue());
				orddtlRepository.create(orddtl);
			}
			
			//更新备忘订单
			bW2DZRepository.update_DZ_ormtqt(dz_mtorno, sampno, suitno, num);
			bW2DZRepository.update_BW_ormtqt(bw_mtorno, sampno, suitno, num);
		}
		
	}
}
