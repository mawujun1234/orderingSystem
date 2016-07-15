package com.youngor.plan;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;
import com.youngor.permission.ShiroUtils;
import com.youngor.plan.PlanHd;
import com.youngor.plan.PlanHdRepository;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PlanHdService extends AbstractService<PlanHd, com.youngor.plan.PlanHd.PK>{

	@Autowired
	private PlanHdRepository planHdRepository;
	
	@Override
	public PlanHdRepository getRepository() {
		return planHdRepository;
	}

	public List<PlanHdVO> queryHdGrid(String ormtno,String yxgsno,String bradno,String spclno){
		List<PlanHdVO> list= planHdRepository.queryHdGrid(ormtno, yxgsno, bradno, spclno,ShiroUtils.getUserId());
		PlanHdVO total=new PlanHdVO();
		total.setOrgnm("合计:");
		total.setIsTotal(true);
		for(PlanHdVO planHdVO:list){
			total.addPlmtam(planHdVO.getPlmtam());
			total.addPlmtqt(planHdVO.getPlmtqt());
		}
		list.add(total);
		return list;
	}
	
	public  void onPass(String ormtno,String yxgsno,String bradno,String spclno){
		planHdRepository.onPass(ormtno, yxgsno, bradno, spclno);
	}
}
