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
import com.youngor.sample.SampleDesignStpr;
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
	@Autowired
	private OrddtlRepository orddtlRepository;
	@Autowired
	private OrdszdtlRepository ordszdtlRepository;
	
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
		if(sampleVO==null){
			throw new BusinessException("该样衣编号不存在!");
		}
		sampleVO.setOrmtno(ContextUtils.getFirstOrdmt().getOrmtno());
		sampleVO.setOrdorg(ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno());
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		sampleVO.setMtorno(ord.getMtorno());
		ord.setSampleVO(sampleVO);
		
		result.put("sampleVO", sampleVO);
		//获取套件和套件内的规格信息，
		//只有套西的小类的时候，才需要去ORD_SUIT_DC表中获取套装种类
		if(sampleVO.getSptyno().equals("S10")){
			//获取各个套件的价格
			List<SampleDesignStpr> stpres=ordRepository.querySampleDesignStpr(sampleVO.getSampno());
			sampleVO.setSuitStpres(stpres);
			
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
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param suitVOs
	 */
	public void createOrddtl(SuitVO[] suitVOs) {
		if(suitVOs==null || suitVOs.length==0){
			return;
		}
		//订货总数据和订单的明细数据之和是否一致，如果不一致就报错
		for(SuitVO suitVO:suitVOs){
			//如果两个地方数据不一样就报错
			if(suitVO.getOrmtqt()!=suitVO.geetOrmtqt_sum()){
				throw new BusinessException("总订货数和明细数据不一致，不能保存!");
			}
		}
		
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		
//		   不拆套 男套西  ：标准 ，裤子     (裤子<=nvl(配置值,15%) *标准)
//		    拆套男套西   ：上衣，裤子         (上衣=<裤子<=nvl(1+配置值,115% )*上衣)
//		     女 套西：上衣，裤子，裙子      (上衣=裤子=裙子)
//		    可根据样衣的套件规格组判断； 
		if(suitVOs.length>1){
			SampleVO sampleVO= ord.getSampleVO();
			int T02_ormtqt=0;//裤子的数量
			int T00_ormtqt=0;//标准的数量
			int T01_ormtqt=0;//上衣的数量
			int T04_ormtqt=0;//裙子的数量
			for(SuitVO suitVO:suitVOs){
				if("T02".equals(suitVO.getSuitno())){
					T02_ormtqt=suitVO.getOrmtqt();
				} else if("T00".equals(suitVO.getSuitno())){
					T00_ormtqt=suitVO.getOrmtqt();
				}else if("T01".equals(suitVO.getSuitno())){
					T01_ormtqt=suitVO.getOrmtqt();
				}else if("T04".equals(suitVO.getSuitno())){
					T04_ormtqt=suitVO.getOrmtqt();
				}
			}
			//不拆套 男 套西  ：标准 ，裤子     (裤子<=nvl(配置值,15%) *标准)
			if(sampleVO.getSpltmk()==0 && "Z0".equals(sampleVO.getSexno()) && "S10".equals(sampleVO.getSptyno())){
				if(T02_ormtqt>T00_ormtqt*1.15){
					throw new BusinessException("不拆套 男套西 的裤子数量必须小于标准套的115%");
				}
			} else if(sampleVO.getSpltmk()==1 && "Z0".equals(sampleVO.getSexno()) && "S10".equals(sampleVO.getSptyno())){
				// 拆套男套西   ：上衣，裤子         (上衣=<裤子<=nvl(1+配置值,115% )*上衣)
				if(T02_ormtqt>T01_ormtqt*1.15){
					throw new BusinessException("不拆套 男套西 的裤子数量必须小于标准套的115%");
				}
				
				if(T02_ormtqt>T01_ormtqt){
					throw new BusinessException(" 拆套男套西 的裤子数量必须是  : 上衣<=裤子<=上衣*115%");
				}
			} else if("Z1".equals(sampleVO.getSexno()) && "S10".equals(sampleVO.getSptyno())){
				// 女 套西：上衣，裤子，裙子      (上衣=裤子=裙子)
				if(T01_ormtqt!=T02_ormtqt || T01_ormtqt!=T04_ormtqt || T02_ormtqt!=T04_ormtqt){
					throw new BusinessException("女 套西:上衣=裤子=裙子");
				}
			}
		}
		
		

		//进行保存
		for(SuitVO suitVO:suitVOs){
			Orddtl orddtl=new Orddtl();
			orddtl.setMtorno(ord.getMtorno());
			orddtl.setSampno(suitVO.getSampno());
			orddtl.setSuitno(suitVO.getSuitno());
			orddtl.setOrmtqt(suitVO.getOrmtqt());
			
			orddtl.setRgsp(ShiroUtils.getAuthenticationInfo().getId());
			orddtl.setRgdt(new Date());
			orddtl.setLmsp(ShiroUtils.getAuthenticationInfo().getId());
			orddtl.setLmdt(new Date());
			//orddtlRepository.delete(orddtl);
			orddtlRepository.createOrUpdate(orddtl);
			if(suitVO.getSizeVOs()!=null && suitVO.getSizeVOs().size()!=0) {
				for(SizeVO sizeVO:suitVO.getSizeVOs()){
					Ordszdtl ordszdtl=new Ordszdtl();
					ordszdtl.setMtorno(ord.getMtorno());
					ordszdtl.setSampno(suitVO.getSampno());
					ordszdtl.setSuitno(suitVO.getSuitno());
					ordszdtl.setSizety(sizeVO.getSizety());
					ordszdtl.setSizeno(sizeVO.getSizeno());
					ordszdtl.setOrszqt(sizeVO.getOrszqt());
					
					ordszdtl.setRgsp(ShiroUtils.getAuthenticationInfo().getId());
					ordszdtl.setRgdt(new Date());
					ordszdtl.setLmsp(ShiroUtils.getAuthenticationInfo().getId());
					ordszdtl.setLmdt(new Date());
					//orddtlRepository.delete(orddtl);
					ordszdtlRepository.createOrUpdate(ordszdtl);
					
				}
			}
			
			
		}
		
	}

	/**
	 * 对某个样衣编号数据进行清零
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampno
	 * @return
	 */
	public void clearSampno(String sampno) {
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		ordRepository.clearOrddtl(ord.getMtorno(), sampno);
		ordRepository.clearOrdszdtl(ord.getMtorno(), sampno);
		
	}
	/**
	 * 订单确认
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampno
	 */
	public void confirm() {
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		
		//先为订单明细表生成 审批订单号 和审批订单号版本
		
		//为订单副表 生成数据，根据订单明细表生成数据 
		
		//拷贝订单明细表中的确认数量-->原始数量,同时设置确认数量为0
		
		//拷贝订单副表--》订单副表-历史
		
		//拷贝订单明细表-->订单明细表历史
	}
}
