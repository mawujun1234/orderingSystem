package com.youngor.order;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;
import com.youngor.org.Org;
import com.youngor.permission.ShiroUtils;
import com.youngor.permission.UserVO;
import com.youngor.utils.ContextUtils;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrdService extends AbstractService<Ord, String>{

	@Autowired
	private OrdRepository ordRepository;
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public OrdRepository getRepository() {
		return ordRepository;
	}
	
	public void create(){
		//先判断主订单是否已经有了，这个组织的单元
		
		UserVO userVO=ShiroUtils.getAuthenticationInfo();
		if(userVO.getCurrentOrges()==null){
			throw new BusinessException("该用户未加入组织单元，不能进行订货!");
		}
		if(userVO.getCurrentOrges().size()>1){
			throw new BusinessException("该用户属于多个组织单元，不能进行订货!");
		}
		Org org=userVO.getCurrentOrges().get(0);
		
		
		
		Ord ord=new Ord();
		ord.setMtorno(format.format(new Date()));
		ord.setOrmtno(ContextUtils.getFirstOrdmt().getOrmtno());
		ord.setOrtyno("DZ");
		ord.setOrdorg(org.getOrgno());
		ord.setChanno(org.getChancl().toString());
		
		Ord ord_have=ordRepository.haveOrd(ord);
		if(ord_have==null){
			super.create(ord);
		} else {
			ord=ord_have;
		}
		userVO.setOrd(ord);
	}
	
	public Map<String,Object> querySample(String sampnm){
		Map<String,Object> result=new HashMap<String,Object>();
		SampleVO sampleVO= ordRepository.querySample(sampnm,ContextUtils.getFirstOrdmt().getOrmtno());
		sampleVO.setOrmtno(ContextUtils.getFirstOrdmt().getOrmtno());
		sampleVO.setOrdorg(ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno());
		
		
		result.put("sampleVO", sampleVO);
		//获取套件和套件内的规格信息，
		//只有套西的小类的时候，才需要去ORD_SUIT_DC表中获取套装种类
		if(sampleVO.getSptyno().equals("S10")){
			List<SuitVO> suitVOs=ordRepository.querySuitVO(sampleVO);
			result.put("suitVOs", suitVOs);
			//sampleVO.setSuitVOs(suitVOs);
		} else {
			//获取标准条件中的规格范围，价格，具有的规格
			List<SuitVO> suitVOs=ordRepository.querySuitVO_T00(sampleVO);
			result.put("suitVOs", suitVOs);
			//sampleVO.setSuitVOs(suitVOs);
		}
		
		
		return result;
	}

}
