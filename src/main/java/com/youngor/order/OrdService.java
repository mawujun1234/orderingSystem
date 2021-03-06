package com.youngor.order;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.ordmt.OrdOrg;
import com.youngor.ordmt.OrdOrgService;
import com.youngor.ordmt.OrdmtScde;
import com.youngor.org.Chancl;
import com.youngor.org.Org;
import com.youngor.permission.ShiroUtils;
import com.youngor.permission.UserVO;
import com.youngor.plan.PlanOrgService;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.pubsize.PubSize;
import com.youngor.pubsize.PubSizeDtl;
import com.youngor.pubsize.PubSizeDtlRepository;
import com.youngor.pubsize.PubSizeRepository;
import com.youngor.sample.SampleCldtlVO;
import com.youngor.sample.SampleClhdRepository;
import com.youngor.sample.SampleClhdVO;
import com.youngor.sample.SampleDesign;
import com.youngor.sample.SampleDesignRepository;
import com.youngor.sample.SampleDesignStpr;
import com.youngor.sample.SamplePlan;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.M;
import com.youngor.utils.MapParams;


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
	private OrdhdRepository ordhdRepository;
	@Autowired
	private OrdszdtlRepository ordszdtlRepository;
	@Autowired
	private CompPalService compPalService;
	@Autowired
	private OrdOrgService ordOrgService;
	@Autowired
	private PlanOrgService planOrgService;
	@Autowired
	private SampleDesignRepository sampleDesignRepository;
	@Autowired
	private TpService tpService;
	@Autowired
	private  PubSizeRepository pubSizeRepository;
	@Autowired
	private PubSizeDtlRepository pubSizeDtlRepository;
	@Autowired
	private SampleClhdRepository sampleClhdRepository;
	
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public OrdRepository getRepository() {
		return ordRepository;
	}
	/**
	 * 判断某个账号是不是本期的订货单位
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public boolean isOrmtnoOrdorg(String ordorg) {
		int count=ordOrgService.queryCount(Cnd.count().andEquals(M.OrdOrg.ormtno, ContextUtils.getFirstOrdmt().getOrmtno()).andEquals(M.OrdOrg.ordorg, ordorg));
		if(count>0){
			return true;
		} else {
			return false;
		}
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
		
		ord.setOrmtno(ContextUtils.getFirstOrdmt().getOrmtno());
		ord.setOrtyno("DZ");
		ord.setOrdorg(org.getOrgno());
		ord.setChanno(org.getChanno().toString());
		//主订单编号
		ord.setMtorno(ord.getOrmtno()+"_"+ord.getOrtyno()+"_"+ord.getOrdorg());//这里边了后后面的规格平衡等地方也要变了，很多地方都要变了
		
		Ord ord_have=ordRepository.haveOrd(ord);
		if(ord_have==null){
			super.create(ord);
		} else {
			ord=ord_have;
		}
		userVO.setOrd(ord);
	}
	
	public Map<String,Object> querySample(String sampnm){
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		if(!ord.canOrd()){
			throw new BusinessException("不能进行订货!");
		}

		//1:第一次订货 ， 2:大区审批中，这个时候不能订货。3:第二次订货 。4:手机订货完成
		if(ord.getOrdCheckInfo().get("canConfirm")!=null && (Integer)ord.getOrdCheckInfo().get("canConfirm")==2){
			throw new BusinessException("审批中,不能进行订货!");
		}
		if(ord.getOrdCheckInfo().get("canConfirm")!=null && (Integer)ord.getOrdCheckInfo().get("canConfirm")==4){
			throw new BusinessException("订货已完成,不能进行订货!");
		}

		
		
		Map<String,Object> result=new HashMap<String,Object>();
		SampleVO sampleVO= ordRepository.querySample(sampnm,ContextUtils.getFirstOrdmt().getOrmtno());
		if(sampleVO==null){
			throw new BusinessException("该样衣编号不存在!");
		}
		
//		渠道类型为特许 的订货单位，输入样衣时需判断 是否可订该样衣，调用函数
//		ORDER_DL.ORDER_CAN(订货批号，特许门店代码，样衣编号代码)
//		判断，返回1 可订，返回0 不可订，提示“该样衣不在您的订货范围内”
		//特许只能订，区域订过的样衣编号
		Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		if("TX".equals(org.getChanno().toString())){
			int count=	ordRepository.order_dl__order_can(ord.getOrmtno(),org.getOrgno(),sampleVO.getSampno());
			if(count==0){
				throw new BusinessException("该样衣不可订!");
			}
//			count=	ordRepository.order_dl__order_isqy(ord.getOrmtno(),org.getOrgno(),sampleVO.getSampno());
//			if(count==0){
//				//获取对应的样衣的包装要求
//				SampleDesign sampleDesign=sampleDesignRepository.get(sampleVO.getSampno());
//				throw new BusinessException("所在区域未订货，该货号必须整箱（包装要求:"+sampleDesign.getPackqt()+"）订货！",DefaulExceptionCode.BUSSINESS_EXCEPTION);
//			}
		}
		
		
		
		
		
		sampleVO.setOrmtno(ContextUtils.getFirstOrdmt().getOrmtno());
		sampleVO.setOrdorg(org.getOrgno());
		
		sampleVO.setMtorno(ord.getMtorno());
		ord.setSampleVO(sampleVO);
		if(Chancl.TX.toString().equals(ord.getChanno())){
			sampleVO.setSpftpr(0d);
		}
		
		result.put("sampleVO", sampleVO);
		
		
		
		//获取该用户的上报方式,如果是单规+整箱和单规，显示的是单规的信息，如果是整箱上报方式，那这里显示的是包装箱
		OrdOrg ordOrg=getOrdMethod();//ord.getOrdMethod();
//		if(ordOrg==null){
//			ordOrg=ordOrgService.get(new OrdOrg.PK(ord.getOrmtno(),org.getOrgno()));
//		}
		
		//如果是包装箱上报，就显示包装箱+单规
		if(ordOrg.getSztype()==2){
			if(sampleVO.getSptyno().equals("S10")){
				//获取各个套件的价格
				setSuitStpres(sampleVO,ord.getChanno());
				
				//获取套件的标准箱
				List<SuitVO> suitVOs_PRDPK=ordRepository.querySuitVO_PRDPK(sampleVO);
				
				List<SuitVO> suitVOs=ordRepository.querySuitVO(sampleVO);
				checkSzrateZero(suitVOs);
				
				//把规格数量进行合并，合并
				for(SuitVO suitVO:suitVOs){
					for(SuitVO suitVO_prdpk:suitVOs_PRDPK){
						if(suitVO.getSuitno().equals(suitVO_prdpk.getSuitno())){
							suitVO.getSizeVOs().addAll(0,suitVO_prdpk.getSizeVOs());
						}
						
					}
				}
				
				result.put("suitVOs", suitVOs);
				
			} else {//不是套装的时候，数据获取方式
				//获取包装箱的数据
				List<SuitVO> suitVOs_T00_PRDPK=ordRepository.querySuitVO_T00_PRDPK(sampleVO);
				//放这里是因为，sql要用suitno进行关联，这样要改多个地方，玛法
				
				//获取单规的数据
				List<SuitVO> suitVOs_T00=ordRepository.querySuitVO_T00(sampleVO);
				checkSzrateZero(suitVOs_T00);
				//把规格数量进行合并，合并
				for(SuitVO suitVO:suitVOs_T00){
					for(SuitVO suitVO_prdpk:suitVOs_T00_PRDPK){
						if(suitVO.getSuitno().equals(suitVO_prdpk.getSuitno())){
							suitVO.getSizeVOs().addAll(0,suitVO_prdpk.getSizeVOs());
						}
						
					}
				}
				result.put("suitVOs", suitVOs_T00);
			}
			
		} else {
			//获取套件和套件内的规格信息，
			//只有套西的小类的时候，才需要去ORD_SUIT_DC表中获取套装种类
			if(sampleVO.getSptyno().equals("S10")){
				//获取各个套件的价格
				setSuitStpres(sampleVO,ord.getChanno());
				
				List<SuitVO> suitVOs=ordRepository.querySuitVO(sampleVO);
				result.put("suitVOs", suitVOs);
				checkSzrateZero(suitVOs);
				//sampleVO.setSuitVOs(suitVOs);
			} else {
				//获取标准条件中的规格范围，价格，具有的规格
				List<SuitVO> suitVOs=ordRepository.querySuitVO_T00(sampleVO);
				result.put("suitVOs", suitVOs);
				checkSzrateZero(suitVOs);
				//sampleVO.setSuitVOs(suitVOs);
			}
		}
		
		//如果不是特许的话，就不显示套件和规格的数据,只显示订货总数,在保存订单的时候，就不保存规格明细数据了
		//不知道等下会不会继续变化，所以先偷懒
		result.put("showSizeList", true);//默认显示规格数据
		if(!Chancl.TX.toString().equals(ord.getChanno())){
			//隐藏规格数据列表
			result.put("showSizeList", false);
			
			//同时获取搭配的图片，展示搭配数据
			List<SampleClhdVO> sampleClhdVOs=sampleClhdRepository.queryBySampno(ContextUtils.getFirstOrdmt().getOrmtno(), sampleVO.getBradno(), sampleVO.getSampno());
			result.put("sampleClhdVOs", sampleClhdVOs);
		}
		return result;
	}
	/**
	 * 当点击某一个搭配的时候，获取
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param clppno
	 * @return
	 */
	public List<SampleCldtlVO> queryMxByClppno(String clppno) {
		return sampleClhdRepository.queryMxByClppno(clppno);
	}
	/**
	 * 检查单规的规格比例，如果没有设置过，就全部设置为1，也就是说是平分
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param suitVOs
	 */
	public void checkSzrateZero(List<SuitVO> suitVOs){
		if(suitVOs==null){
			return;
		}
		for(SuitVO suitVO:suitVOs){
			suitVO.checkSzrateZero();
		}
	}
	/**
	 * 获取各个套件的价格
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampleVO
	 */
	private void setSuitStpres(SampleVO sampleVO,String channo){
		List<SampleDesignStpr> stpres=ordRepository.querySampleDesignStpr(sampleVO.getSampno());
		//如果是特许，不显示出厂价
		if("TX".equals(channo)){
			for(SampleDesignStpr sampleDesignStpr:stpres){
				sampleDesignStpr.setSpftpr(0d);
			}
		}
		sampleVO.setSuitStpres(stpres);
	}
	
	private OrdOrg getOrdMethod(){
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		OrdOrg ordOrg=ord.getOrdMethod();
		if(ordOrg==null){
			ordOrg=ordOrgService.get(new OrdOrg.PK(ord.getOrmtno(),org.getOrgno()));
		}
		return ordOrg;
	}
	/**
	 * 创建订单数据
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param suitVOs
	 */
	public void createOrddtl(SuitVO[] suitVOs) {
		if(suitVOs==null || suitVOs.length==0){
			return;
		}
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		
		//订货总数据和订单的明细数据之和是否一致，如果不一致就报错
		for(SuitVO suitVO:suitVOs){
			//如果两个地方数据不一样就报错
			//System.out.println(suitVO.geetOrmtqt_sum()!=suitVO.getOrmtqt());
			if(suitVO.getOrmtqt()!=suitVO.geetOrmtqt_sum()){
				throw new BusinessException("总订货数和明细数据不一致，不能保存!");
			}
			if(Chancl.TX.toString().equals(ord.getChanno())){
				if("T00".equals(suitVO.getSuitno()) || "T01".equals(suitVO.getSuitno())){
					Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
					//判断样衣所在区域是否订了这个货，如果没定，必须是整箱定
					int count=	ordRepository.order_dl__order_isqy(ord.getOrmtno(),org.getOrgno(),suitVO.getSampno());
					if(count==0){
						//获取对应的样衣的包装要求
						SampleDesign sampleDesign=sampleDesignRepository.get(suitVO.getSampno());
						if(sampleDesign.getPackqt()==null || sampleDesign.getPackqt()==0){
							throw new BusinessException("该样衣的<包装要求>不能为0，请联系大类管理员！");
						}
						if((suitVO.getOrmtqt()%sampleDesign.getPackqt())!=0){
							throw new BusinessException("所在区域未订货，该货号必须整箱（包装要求:"+sampleDesign.getPackqt()+"）订货！");
						}
						
					}
				}
			}
		}
		
		
		
//		   不拆套 男套西  ：标准 ，裤子     (裤子<=nvl(配置值,15%) *标准)
//		    拆套男套西   ：上衣，裤子         (上衣=<裤子<=nvl(1+配置值,115% )*上衣)
//		     女 套西：上衣，裤子，裙子      (上衣=裤子=裙子)
//		    可根据样衣的套件规格组判断； 
		SampleVO sampleVO= ord.getSampleVO();
		
		//if(suitVOs.length>1){
		if("S10".equals(sampleVO.getSptyno())){	
			int T02_ormtqt=0;//裤子的数量
			int T00_ormtqt=0;//标准的数量
			int T01_ormtqt=0;//上衣的数量
			int T04_ormtqt=0;//裙子的数量
			for(SuitVO suitVO:suitVOs){
				String suitno=suitVO.getSuitno();
//				if(suitno.lastIndexOf("_PRDPK")!=-1){
//					suitno=suitVO.getSuitno().split("_")[0];	
//				}
				if("T02".equals(suitno)){
					T02_ormtqt=suitVO.getOrmtqt();
				} else if("T00".equals(suitno)){
					T00_ormtqt=suitVO.getOrmtqt();
				}else if("T01".equals(suitno)){
					T01_ormtqt=suitVO.getOrmtqt();
				}else if("T04".equals(suitno)){
					T04_ormtqt=suitVO.getOrmtqt();
				}
			}
			//不拆套 男 套西  ：标准 ，裤子     (裤子<=nvl(配置值,15%) *标准)
			if(sampleVO.getSpltmk()==0 && "Z0".equals(sampleVO.getSexno()) && "S10".equals(sampleVO.getSptyno())){
				if(T02_ormtqt>T00_ormtqt*0.15){
					throw new BusinessException("不拆套 男套西 的裤子数量必须小于标准套的15%");
				}
			} else if(sampleVO.getSpltmk()==1 && "Z0".equals(sampleVO.getSexno()) && "S10".equals(sampleVO.getSptyno())){
//				// 拆套男套西   ：上衣，裤子         (上衣=<裤子<=nvl(1+配置值,115% )*上衣)
//				if(T02_ormtqt>T01_ormtqt*1.15){
//					throw new BusinessException("拆套 男套西 的裤子数量必须小于标准套的115%");
//				}
				
				if(!(T02_ormtqt>=T01_ormtqt && T02_ormtqt<=T01_ormtqt*1.15)){
					throw new BusinessException(" 拆套男套西 的裤子数量必须是  : 上衣<=裤子<=上衣*115%");
				}
			} 
//			else if("Z1".equals(sampleVO.getSexno()) && "S10".equals(sampleVO.getSptyno())){
//				// 女 套西：上衣，裤子，裙子      (上衣=裤子=裙子)
//				if(T01_ormtqt!=T02_ormtqt || T01_ormtqt!=T04_ormtqt || T02_ormtqt!=T04_ormtqt){
//					throw new BusinessException("女 套西:上衣=裤子=裙子");
//				}
//			}
		}

		//进行保存
		for(SuitVO suitVO:suitVOs){
			String suitno=suitVO.getSuitno();

			Orddtl orddtl=new Orddtl();
			orddtl.setMtorno(ord.getMtorno());
			orddtl.setSampno(suitVO.getSampno());
			orddtl.setSuitno(suitno);
			orddtl.setOrmtqt(suitVO.getOrmtqt());
			
			orddtl.setRgsp(ShiroUtils.getAuthenticationInfo().getId());
			orddtl.setRgdt(new Date());
			orddtl.setLmsp(ShiroUtils.getAuthenticationInfo().getId());
			orddtl.setLmdt(new Date());
			//orddtlRepository.delete(orddtl);
			orddtlRepository.createOrUpdate(orddtl);
			
			
			//如果不是特许的话，就不显示套件和规格的数据,只显示订货总数,在保存订单的时候，就不保存规格明细数据了
			//不知道等下会不会继续变化，所以先偷懒
			if(Chancl.TX.toString().equals(ord.getChanno())){
			if(suitVO.getSizeVOs()!=null && suitVO.getSizeVOs().size()!=0) {
				for(SizeVO sizeVO:suitVO.getSizeVOs()){
					Ordszdtl ordszdtl=new Ordszdtl();
					ordszdtl.setMtorno(ord.getMtorno());
					ordszdtl.setSampno(suitVO.getSampno());
					ordszdtl.setSuitno(suitno);
					ordszdtl.setSizety(sizeVO.getSizety());
					ordszdtl.setSizeno(sizeVO.getSizeno());
					ordszdtl.setOrszqt(sizeVO.getOrszqt());
					ordszdtl.setOritqt(ordszdtl.getOrszqt());//原始数量，备份现场定后数量
					ordszdtl.setOrbgqt(ordszdtl.getOrszqt());//确认数量 报表要实时查看的数量
					
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
		
	}

	/**
	 * 对某个样衣编号数据进行清零
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampno
	 * @return
	 */
	public void mobile_clearSampno(String sampno) {
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		ordRepository.clearOrddtl(ord.getMtorno(), sampno);
		ordRepository.clearOrdszdtl(ord.getMtorno(), sampno);
		
	}
	/**
	 * 查询还没有订的必定款的样衣
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public List<SampleDesign> query_none_abstat(){
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		if(org.getChanno()!=Chancl.TX){
			//订单号
			String mtorno=getMtorno(ord.getOrmtno(),ord.getOrtyno(),ord.getOrdorg());//ord.getOrmtno()+"_"+ord.getOrtyno()+"_"+ord.getOrdorg();ff
			List<SampleDesign> none_abstat=ordRepository.query_none_abstat(ord.getOrmtno(), mtorno);
//			if(none_abstat!=null && none_abstat.size()>0){
//				StringBuilder builder=new StringBuilder();
//				int i=0;
//				for(SampleDesign sampleDesign:none_abstat){
//					builder.append(",\""+sampleDesign.getSampnm1()+"\"");
//					i++;
//				}
//				
//				throw new BusinessException("["+builder.substring(1)+"]");//+none_abstat.get(0).getSampnm()
//			}
			return none_abstat;
		} else {
			return new ArrayList<SampleDesign>();
		}

	}
	/**
	 * 订单确认
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampno
	 */
	public void confirm() {
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		
		//区域 订单确认时判断 必定款的样衣是否全部已订，提示未订的必定款样衣；
		Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		confirm(org,ord);
		
//		if(org.getChanno()!=Chancl.TX){
//			//订单号
//			String mtorno=getMtorno(ord.getOrmtno(),ord.getOrtyno(),ord.getOrdorg());//ord.getOrmtno()+"_"+ord.getOrtyno()+"_"+ord.getOrdorg();ff
//			List<SampleDesign> none_abstat=ordRepository.query_none_abstat(ord.getOrmtno(), mtorno);
//			if(none_abstat!=null && none_abstat.size()>0){
////				StringBuilder builder=new StringBuilder();
////				int i=0;
////				for(SampleDesign sampleDesign:none_abstat){
////					builder.append(",\""+sampleDesign.getSampnm1()+"\"");
////					i++;
////				}
////				
////				throw new BusinessException("["+builder.substring(1)+"]");//+none_abstat.get(0).getSampnm()
//				throw new BusinessException("有必定款样衣未订");
//			}
//		}
//
//		
//		//先为订单明细表生成 审批订单号 和审批订单号版本,订单号+品牌+大类
//		ordRepository.updateMtornoMlorvn(ord.getMtorno());
////		拷贝订单明细表中的确认数量-->原始数量,同时设置确认数量为0
////		ordRepository.updateOrmtqtZeor(ord.getMtorno());
//		
//		//为订单副表 生成数据，根据订单明细表生成数据 ,
//		ordRepository.createOrd_ordhd(ord.getMtorno());
//	
////		//拷贝订单副表--》订单副表-历史
////		ordRepository.createOrd_ordhd_his(ord.getMtorno());
////		
////		//拷贝订单明细表-->订单明细表历史
////		ordRepository.createOrd_orddtl_his(ord.getMtorno());
//		
//
//		if(org.getChanno()==Chancl.TX){
//			//如果是特许，节点类型 节点类型：尾箱调整40，状态：编辑中
//			ordRepository.update_ordhd_SDTYNO(ord.getMtorno(),"40","0");
//		} else if(org.getChanno()==Chancl.ZY || org.getChanno()==Chancl.SC){
//			//如果是门店，节点类型：总公司平衡30，状态还是编辑中。
//			ordRepository.update_ordhd_SDTYNO(ord.getMtorno(),"30","0");
//		} else {
//			//如果是区域，同时修改ordhd的订单节点类型为10，还是现场订货，订单状态变成“总部审批中”
//			ordRepository.update_ordhd_SDTYNO(ord.getMtorno(),"10","2");
//		}
//		
//		//更新订单规格明细表中的“审批订单号”
//		ordRepository.update_ord_ordszdtl_MLORNO(ord.getMtorno());
//		
//		ord.getOrdCheckInfo().put("canConfirm", 2);
//		//result.put("canConfirm", canConfirm);
	}
	
	public void confirm(Org org,Ord ord){
		if(org.getChanno()!=Chancl.TX){
			//订单号
			String mtorno=getMtorno(ord.getOrmtno(),ord.getOrtyno(),ord.getOrdorg());//ord.getOrmtno()+"_"+ord.getOrtyno()+"_"+ord.getOrdorg();ff
			List<SampleDesign> none_abstat=ordRepository.query_none_abstat(ord.getOrmtno(), mtorno);
			if(none_abstat!=null && none_abstat.size()>0){
//				StringBuilder builder=new StringBuilder();
//				int i=0;
//				for(SampleDesign sampleDesign:none_abstat){
//					builder.append(",\""+sampleDesign.getSampnm1()+"\"");
//					i++;
//				}
//				
//				throw new BusinessException("["+builder.substring(1)+"]");//+none_abstat.get(0).getSampnm()
				throw new BusinessException("有必定款样衣未订");
			}
		}

		
		//先为订单明细表生成 审批订单号 和审批订单号版本,订单号+品牌+大类
		ordRepository.updateMtornoMlorvn(ord.getMtorno());
//		拷贝订单明细表中的确认数量-->原始数量,同时设置确认数量为0
//		ordRepository.updateOrmtqtZeor(ord.getMtorno());
		
		//为订单副表 生成数据，根据订单明细表生成数据 ,
		ordRepository.createOrd_ordhd(ord.getMtorno());
	
//		//拷贝订单副表--》订单副表-历史
//		ordRepository.createOrd_ordhd_his(ord.getMtorno());
//		
//		//拷贝订单明细表-->订单明细表历史
//		ordRepository.createOrd_orddtl_his(ord.getMtorno());
		

		if(org.getChanno()==Chancl.TX){
			//如果是特许，节点类型 节点类型：尾箱调整40，状态：编辑中
			ordRepository.update_ordhd_SDTYNO(ord.getMtorno(),"40","0");
		} else if(org.getChanno()==Chancl.ZY || org.getChanno()==Chancl.SC){
			//如果是门店，节点类型：总公司平衡30，状态还是编辑中。
			ordRepository.update_ordhd_SDTYNO(ord.getMtorno(),"30","0");
		} else {
			//如果是区域，同时修改ordhd的订单节点类型为10，还是现场订货，订单状态变成“总部审批中”
			ordRepository.update_ordhd_SDTYNO(ord.getMtorno(),"10","2");
		}
		
		//更新订单规格明细表中的“审批订单号”
		ordRepository.update_ord_ordszdtl_MLORNO(ord.getMtorno());
		
		ord.getOrdCheckInfo().put("canConfirm", 2);
		//result.put("canConfirm", canConfirm);
	}
	
	/**
	 * 二次订货后订单完成的时候
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	public void confirm2() {
		Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();
		//Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		//订单号
		String mtorno=getMtorno(ord.getOrmtno(),ord.getOrtyno(),ord.getOrdorg());
		//获取已有品牌大类的审批版本号
		String mlorvn=null;
		try {
				mlorvn=ordRepository.confirm2_get_mlorvn(mtorno);
		} catch(Exception e) {
			throw new BusinessException("订单版本不一致，请联系管理员更改!");
		}
		//为订单明细表生成审批订单号，和版本号
		ordRepository.confirm2_updateMtornoMlorvn(mtorno,mlorvn);
		//为订单规格明细更新审批订单号
		ordRepository.update_ord_ordszdtl_MLORNO(ord.getMtorno());
		//生成订单副表数据，同时更新订单审批号，版本号
		ordRepository.confirm2_createOrd_ordhd(mtorno, mlorvn, "20", "0");
		//订单状态改成"大区审批中“
		ordRepository.confirm2_update_orstat(mtorno, "1");
	}
	
	public boolean can_confirm_tx(String mtorno){
		int count=ordRepository.can_confirm_tx(mtorno);
		if(count>0){
			return false;
		}
		return true;
	}
	
	private void ordMgr_check_process2ANDback_stat(Integer stat,String[] mlornoes){
		StringBuilder builder=new StringBuilder();
		for(String mlorno:mlornoes){
			builder.append(",'"+mlorno+"'");
		}
		List<String> result=ordRepository.ordMgr_check_process2ANDback_stat(stat, builder.substring(1));
		if(result!=null && result.size()>0){
			builder=new StringBuilder();
			for(String str:result){
				builder.append(","+str);
			}
			if(stat==1){
				throw new BusinessException("只有'大区审批中'才能审批/退回:"+builder.substring(1));
			} else if(stat==2){
				throw new BusinessException("只有'总部审批中'才能审批/退回:"+builder.substring(1));
			} else {
				throw new BusinessException("下列订单不能审批/退货:"+builder.substring(1));
			}
		}
	}
	/**
	 * 总部审批,是按大类，品牌进行审批,审批完后进入到 总公司平衡中
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	//public void ordMgr_process2(String[] mlornoes) {
	public void ordMgr_process2(List<Map<String,String>> params) {
		if(params!=null && params.size()>0){
			String[] mlornoes=new String[params.size()];
			int i=0;
			for(Map<String,String> param:params){
				mlornoes[i]=param.get("MLORNO");
				i++;
			}
			//大区账号只能对总量状态=1，或规格状态=1的订单审批或退回
			//总部商品部只能对总量状态=2或规格状态=2的订单 审批或退回
			if(ShiroUtils.getAuthenticationInfo().hasChanno(Chancl.YXGS)) {
				ordMgr_check_process2ANDback_stat(1,mlornoes);
			} else if(ShiroUtils.getAuthenticationInfo().hasTheOrg(tpService.getSpb_orgno())){
				ordMgr_check_process2ANDback_stat(2,mlornoes);
			} else {
				throw new BusinessException("对不起，请没有权限进行操作!");
			}

			for(Map<String,String> param:params){
				String mlorno=param.get("MLORNO");
				//如果是总量状态是审批中，就修改总量的状态为编辑中
				if("1".equals(param.get("ORSTAT")) || "2".equals(param.get("ORSTAT"))){
					//如果是规格状态是审批中，就修改规格的状态为编辑中
					//把订单状态修改为“编辑中”
					ordRepository.order_dl__process(mlorno, "总量", ShiroUtils.getLoginName());
				} 
				if("1".equals(param.get("SZSTAT")) || "2".equals(param.get("SZSTAT"))){
					//如果是规格状态是审批中，就修改规格的状态为编辑中
					//把订单状态修改为“编辑中”
					ordRepository.order_dl__process(mlorno, "规格", ShiroUtils.getLoginName());
				}
			}
		}
		
//		if(mlornoes!=null && mlornoes.length>0){
//			//大区账号只能对总量状态=1，或规格状态=1的订单审批或退回
//			//总部商品部只能对总量状态=2或规格状态=2的订单 审批或退回
//			if(ShiroUtils.getAuthenticationInfo().hasChanno(Chancl.YXGS)) {
//				ordMgr_check_process2ANDback_stat(1,mlornoes);
//			} else if(ShiroUtils.getAuthenticationInfo().hasTheOrg(tpService.getSpb_orgno())){
//				ordMgr_check_process2ANDback_stat(2,mlornoes);
//			} else {
//				throw new BusinessException("对不起，请没有权限进行操作!");
//			}
//		
//			//订单状态改成“审批通过”，订单节点改成“总公司平衡”
//			for(String mlorno:mlornoes){
//				ordRepository.order_dl__process(mlorno, "总量", ShiroUtils.getLoginName());
//			}
//		}
		
	}
	/**
	 * 订单退回
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param mlornoes
	 */
	//public void ordMgr_back(String[] mlornoes) {
	public void ordMgr_back(List<Map<String,String>> params){
		if(params!=null && params.size()>0){
			String[] mlornoes=new String[params.size()];
			int i=0;
			for(Map<String,String> param:params){
				mlornoes[i]=param.get("MLORNO");
				i++;
			}
			//大区账号只能对总量状态=1，或规格状态=1的订单审批或退回
			//总部商品部只能对总量状态=2或规格状态=2的订单 审批或退回
			if(ShiroUtils.getAuthenticationInfo().hasChanno(Chancl.YXGS)) {
				ordMgr_check_process2ANDback_stat(1,mlornoes);
			} else if(ShiroUtils.getAuthenticationInfo().hasTheOrg(tpService.getSpb_orgno())){
				ordMgr_check_process2ANDback_stat(2,mlornoes);
			} else if("admin".equals(ShiroUtils.getLoginName())){
				
			} else {
				throw new BusinessException("对不起，请没有权限进行操作!");
			}
			
			//for(String mlorno:mlornoes){
			for(Map<String,String> param:params){
				String mlorno=param.get("MLORNO");
				//如果是总量状态是审批中，就修改总量的状态为编辑中
				if("1".equals(param.get("ORSTAT")) || "2".equals(param.get("ORSTAT"))){
					//如果是规格状态是审批中，就修改规格的状态为编辑中
					//把订单状态修改为“编辑中”
					ordhdRepository.update(Cnd.update().set(M.Ordhd.orstat, 0).andEquals(M.Ordhd.mlorno, mlorno));
				} 
				if("1".equals(param.get("SZSTAT")) || "2".equals(param.get("SZSTAT"))){
					//如果是规格状态是审批中，就修改规格的状态为编辑中
					//把订单状态修改为“编辑中”
					ordhdRepository.update(Cnd.update().set(M.Ordhd.szstat, 0).andEquals(M.Ordhd.mlorno, mlorno));
					//如果这个订单的上报方式是单规或者整箱，就把orszst改成0，就表示可以继续编辑，如果是整箱+单规的就不变，还是只能编辑整箱数据
					Integer aa=ordRepository.ordMgr_getSztype(mlorno);
					if(aa!=null && (aa==1 || aa==2)){
						ordszdtlRepository.update(Cnd.update().set(M.Ordszdtl.orszst, 0).andEquals(M.Ordszdtl.mlorno, mlorno));
					}
					
				} 
			}
		}
		
//		if(mlornoes!=null && mlornoes.length>0){
//			//大区账号只能对总量状态=1，或规格状态=1的订单审批或退回
//			//总部商品部只能对总量状态=2或规格状态=2的订单 审批或退回
//			if(ShiroUtils.getAuthenticationInfo().hasChanno(Chancl.YXGS)) {
//				ordMgr_check_process2ANDback_stat(1,mlornoes);
//			} else if(ShiroUtils.getAuthenticationInfo().hasTheOrg(tpService.getSpb_orgno())){
//				ordMgr_check_process2ANDback_stat(2,mlornoes);
//			} else {
//				throw new BusinessException("对不起，请没有权限进行操作!");
//			}
//			
//			for(String mlorno:mlornoes){
//				//如果是总量状态是审批中，就修改总量的状态为编辑中
//				
//				//如果是规格状态是审批中，就修改规格的状态为编辑中
//				//把订单状态修改为“编辑中”
//				ordhdRepository.update(Cnd.update().set(M.Ordhd.orstat, 0).andEquals(M.Ordhd.mlorno, mlorno));
//				
//			}
//		}
		
	}
	/**
	 * 订单作废
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param mlornoes
	 */
	public void ordMgr_isfect_no(String[] mlornoes) {
		
		
		if(mlornoes!=null && mlornoes.length>0){
			for(String mlorno:mlornoes){
				//把订单作废
				ordhdRepository.update(Cnd.update().set(M.Ordhd.isfect, 0).andEquals(M.Ordhd.mlorno, mlorno));
				
			}
		}
		
	}
	/**
	 * 订单流转
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	public void ordMgr_ordercircle(String[] mlornoes,String sdtyno) {
		if(mlornoes==null){
			return;
		}
		
		StringBuilder builder=new StringBuilder();
		for(String mlorno:mlornoes){
			builder.append(",'"+mlorno+"'");
		}
		List<String> result=ordRepository.ordMgr_check_ordercircle_stat( builder.substring(1));
		if(result!=null && result.size()>0){
			builder=new StringBuilder();
			for(String str:result){
				builder.append(","+str);
			}
	
			throw new BusinessException("下列订单不能进行单单流转:"+builder.substring(1));
			
		}
		for(String mlorno:mlornoes){
			ordRepository.order_dl__order_to(mlorno, sdtyno, ShiroUtils.getLoginName());
		}
		
	}
	
	SimpleDateFormat HHmm_format=new SimpleDateFormat("HHmm");
	SimpleDateFormat yyyyMMdd_format=new SimpleDateFormat("yyyyMMdd");
	public Map<String,Object> checked_closeing_info()  {
		//这里去获取该用户，离订货时间借宿还有多长时间，如果时间不够的话，就给出提示
		UserVO userVO=ShiroUtils.getAuthenticationInfo();
		Ord ord=userVO.getOrd();
		Org org=userVO.getFirstCurrentOrg();
		
		Map<String,Object> result=new HashMap<String,Object>();
//不是订货单位等都登陆不进来了		
//		if(org.getChanno()!=Chancl.TX  && org.getChanno()!=Chancl.QY){
//			result.put("show", false);
//			result.put("canOrd", false);//是否可以订货
//			result.put("msg", "你所在的组织单元不是订货单位，不能订货!");
//			result.put("success", true);
//			return result;
//		}
		
		ord.setOrdCheckInfo(result);
		OrdmtScde ordmtScde=ordRepository.get_ordmt_scde(ord.getOrmtno(), org.getChanno().toString());
		if(ordmtScde==null){
			result.put("show", true);
			result.put("canOrd", false);//是否可以订货
			result.put("msg", "你所在的订货单位未设置订货日程，不能订货!");
			return result;
		}
		
		
		
		//result.put("minutes", 36);
		
		
		//Date now=new Date();
		Calendar cal=Calendar.getInstance();
		//System.out.println(HHmm_format.format(ordmtScde.getMtfidt()));
		String now=yyyyMMdd_format.format(new Date());
		Date date=new Date();
		try {
			date = yyyyMMdd_format.parse(now);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//判断日期范围
		if(ordmtScde.getMtstdt().getTime()>date.getTime()){
			result.put("show", true);
			result.put("canOrd", false);//是否可以订货
			result.put("msg", "订货会还未开始，请稍候!");
		} else if(ordmtScde.getMtfidt().getTime()<date.getTime()){
			
			result.put("show", true);
			result.put("canOrd", false);//是否可以订货
			result.put("msg", "订货会已经结束，不能再订货!");
		} else {
			
			int now_hhmm=Integer.parseInt(HHmm_format.format(cal.getTime()));
			//开始时间
			int mtsttm=Integer.parseInt(ordmtScde.getMtsttm().replace(":", ""));
			//结束时间
			int mtfitm=Integer.parseInt(ordmtScde.getMtfitm().replace(":", ""));
			
			if(now_hhmm<mtsttm){
				int now_hour=cal.get(Calendar.HOUR_OF_DAY);
				int now_min=cal.get(Calendar.MINUTE);
				String mtsttmes[]=ordmtScde.getMtsttm().split(":");
				int mtsttm_hour=Integer.parseInt(mtsttmes[0]);
				int mtsttm_min=Integer.parseInt(mtsttmes[1]);
				StringBuilder builder=new StringBuilder("离今日订货会开始还有:");
				if(mtsttm_hour-now_hour>0){
					builder.append(mtsttm_hour-now_hour+"小时");
				}	
				
				builder.append(Math.abs(mtsttm_min-now_min)+"分钟，请稍候!");
				result.put("show", true);
				result.put("canOrd", false);//是否可以订货
				result.put("msg", builder.toString());
			} else if(now_hhmm>mtfitm){
				result.put("show", true);
				result.put("canOrd", false);//是否可以订货
				result.put("msg", "今日订货会已经结束，不能再订货!");
			} else {
				int now_hour=cal.get(Calendar.HOUR_OF_DAY);
				int now_min=cal.get(Calendar.MINUTE);
				String mtfitmes[]=ordmtScde.getMtfitm().split(":");
				int mtfitm_hour=Integer.parseInt(mtfitmes[0]);
				int mtfitm_min=Integer.parseInt(mtfitmes[1]);
				//如果离订货会结束，还超过1个小时就不提示
				//System.out.println((mtfitm_hour*60+mtfitm_min-now_hour*60+now_min));
				Integer min=((mtfitm_hour*60+mtfitm_min)-(now_hour*60+now_min));
				if(min>60){
					result.put("show", false);
					result.put("canOrd", true);//是否可以订货
					result.put("msg", "");
				} else {//在前面已经判断过了，今天是否可以订货，所以这里就不判断了
					result.put("show", true);
					result.put("canOrd", true);//是否可以订货
					result.put("msg", "离今日订货结束还差"+min+"分钟，注意数据保存！");
				}
				
			}
		}
		
		///判断该订单是否已经“确认”,如果已经确认，那也不能顶多
		int canConfirm=check_is_confirm(ord.getMtorno());
		result.put("canConfirm", canConfirm);

		return result;
		
	}
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param mtorno
	 * @return 1:第一次订货 ， 2:大区审批中，这个时候不能订货。3:第二次订货 。4:手机订货完成
	 */
	private int check_is_confirm(String mtorno){
		List<Map<String,Object>> list=ordRepository.check_is_confirm(mtorno);
		//表示还没有点击“订单完成”，也就是第一次订货还没有完成
		if(list ==null || list.size()==0){
			return 1;
		}
		//10:现场订货.20:区域平衡.30:总公司平衡.40:尾箱调整
		////0：编辑中；1：大区审批中；2：总部审批中；3：审批通过；4：退回 
		if(list.size()==1 ){
			Map<String,Object> map=list.get(0);
			//如果还是现场订货，并且状态是编辑中的话，就是显示“订单完成”按钮
			if("10".equals(map.get("SDTYNO")) && "0".equals(map.get("ORSTAT").toString())){
				return 1;
			}  else  if("10".equals(map.get("SDTYNO")) && "2".equals(map.get("ORSTAT").toString())){
				//进入了总部审批中，只有审批过后，才能进行区域平衡
				return 2;
			}  else if("20".equals(map.get("SDTYNO")) && "0".equals(map.get("ORSTAT").toString())){
				//进入了区域平衡
				return 3;
			} else {
				//其余情况就是，即不能确认也不能完成
				return 4;
			}
		} else {
			throw new BusinessException("订单状态不一致，某些大类未审批!");
		}
		
	}
	
	
	public MyInfoVO queryMyInfoVO() {
		UserVO userVO=ShiroUtils.getAuthenticationInfo();
		Ord ord=userVO.getOrd();
		Org org=userVO.getFirstCurrentOrg();
		MyInfoVO myInfoVO= ordRepository.queryMyInfoVO(ord.getMtorno(),org.getChanno().toString());
		if(myInfoVO==null){
			myInfoVO=new MyInfoVO();
		}
		
		myInfoVO.setOrgnm(org.getOrgnm());
		
		//如果是特许，不显示指标金额
		
		String plorno=null;//指标单号
		if("TX".equals(org.getChanno().toString())){
			//特许不显示指标数量
			
		} else {
			//然后获取该区域的的特许指标数量,默认都是获取雅戈尔品牌的指标数量，这个是暂时的
			plorno=planOrgService.getPlorno(ord.getOrmtno(), org.getOrgno(), "Y");
			MyInfoVO aaa=ordRepository.queryMyInfoVO_plan(plorno);
			if(aaa!=null){
				myInfoVO.setQymtqt(aaa.getQymtqt());
				myInfoVO.setQymtam(aaa.getQymtam());
			}
		}
		
		
		return myInfoVO;
	}
	/**
	 * 只修改区域的数据，其他节点的数据不修改
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public void process1(Map<String,Object> params) {
		//首先判断，该营销公司下的所有区域的订单的订单节点是不是i“区域平衡”，订单状态是不是“总部审批中”，如果不是，就报错错误
		List<Map<String,Object>> list=ordRepository.process1_check_stats(params);
		if(list!=null && list.size()>0){
			StringBuilder builder=new StringBuilder("");
			for(Map<String,Object> map:list){
				builder.append(","+map.get("QYNM"));
			}
			throw new BusinessException("下列区域未提交："+builder.substring(1));
		}
		//订货汇总条码打印-审批通过（订货批号,品牌,大类，操作用户）
		ordRepository.order_dl__print_ps(params.get("ormtno").toString(), params.get("bradno").toString(),
				 params.get("spclno").toString(), ShiroUtils.getLoginName());
//		String ormtno=params.get("ormtno").toString();
//		String yxgsno=params.get("yxgsno").toString();
//		//先备份数据
//		//拷贝订单副表--》订单副表-历史
//		ordRepository.createOrd_ordhd_hiss_by_yxgsno(ormtno,yxgsno);	
//		//拷贝订单明细表-->订单明细表历史
//		ordRepository.createOrd_orddtl_hiss_by_yxgsno(ormtno,yxgsno);
//		
//		
//		//然后把该营销公司下所有"区域"的订单状态改成“编辑中”
//		ordRepository.process1_update_ordhd_stats_0(ormtno, yxgsno);
//		
//		//对该营销公司下，所有的订货单位的版本号+1，包括订单副表，订单明细表
//		ordRepository.process1_update_ordhd_mlorvn(ormtno, yxgsno);
//		ordRepository.process1_update_orddtl_mlorvn(ormtno, yxgsno);
		
		
	}
	
	public List<Org> queryOrdorg(String ormtno,String qyno,String channo,String ortyno) {
		return ordRepository.queryOrdorg(ormtno, qyno, channo,ortyno,ShiroUtils.getUserId());
	}
	
	public Pager<QyVO> queryQyVO(Pager<QyVO> pager) {
		return ordRepository.queryQyVO(pager);
	}
	
	public void updateOrmtqt(String mtorno,String sampno,String suitno,Integer ormtqt) {
		ordRepository.updateOrmtqt(mtorno, sampno, suitno, ormtqt);
	}
	
	public List<QyNewFormVO> querySuitBySampnm(String ormtno,String sampnm) {
		int count=ordRepository.checkIsS10(ormtno, sampnm);
		//如果是套西的话
		if(count>0){
			List<QyNewFormVO> list= ordRepository.querySuitBySampnm_S10(ormtno, sampnm);
			return list;
		} else {
			List<QyNewFormVO> list= ordRepository.querySuitBySampnm(ormtno, sampnm);
			return list;
		}
		 

	}
	public String getMtorno(String ormtno,String ortyno,String ordorg){
		String mtorno=ormtno+"_"+ortyno+"_"+ordorg;
		return mtorno;
	}
	public void createNew(ArrayList<Orddtl> orddtles,String ordorg,String ortyno,String channo,String ormtno) {
		if(orddtles==null|| orddtles.size()==0){
			return;
		}
		String sampno=orddtles.get(0).getSampno();
		//先判断这个订货单位有没有订这样样衣编号
		int count=ordRepository.checkSampnoOrded(sampno, ordorg, ortyno, channo, ormtno);
		if(count>0){
			throw new BusinessException("该样衣编号已订！");
		}
		
		//获取对应的订单号，审批订单号，审批订单版本号,这里已经确定了品牌和大类(样衣编号)
		Ordhd ordhd=ordRepository.getOrdhd(sampno, ordorg, ortyno, channo, ormtno);
		// 如果这个样衣的这个品牌大类还没有订过，就创建订单副表的数据
		if (ordhd == null) {
			String mtorno=getMtorno( ormtno, ortyno, ordorg);
			SamplePlan sampleDesign=ordRepository.getSamplePlanBySampno(ormtno, sampno);

			ordhd=new Ordhd();
			ordhd.setMtorno(mtorno);
			ordhd.setBradno(sampleDesign.getBradno());
			ordhd.setSpclno(sampleDesign.getSpclno());
			ordhd.setMlorno(mtorno+ordhd.getBradno()+ordhd.getSpclno());
			ordhd.setMlorvn(1);
			ordhd.setSdtyno("20");
			ordhd.setOrstat(0);
			ordhd.setSzstat(0);
			ordhd.setIsfect(1);
			ordhdRepository.create(ordhd);
		} else {
			//判断状态，如果状态不对的话，就不能保存
			if(ordhd.getOrstat()!=0 && ordhd.getOrstat()!=4){
				throw new BusinessException("该订单已被提交审批，不能新建!");
			}
		
			
		}
		//创建订单明细表
		for(Orddtl orddtl:orddtles){
			orddtl.setMtorno(ordhd.getMtorno());
			orddtl.setMlorno(ordhd.getMlorno());
			orddtl.setMlorvn(ordhd.getMlorvn());
			orddtl.setOrmtqs(0);
			
			orddtlRepository.create(orddtl);
		}
		
	}
//	/**
//	 * 区域平衡 提交审批
//	 * @author mawujun qq:16064988 mawujun1234@163.com
//	 * @param qyno
//	 * @param channo
//	 * @param ordorg
//	 * @param ormtno
//	 * @param bradno
//	 * @param spclno
//	 */
//	public void updateApprove_org(String qyno,String channo,String ordorg,String ormtno,String bradno,String spclno) {
//		
//		if(ordorg!=null && !"".equals(ordorg)){
//			check_S10_rule(ormtno, ordorg, spclno, bradno);
//			//提交某个指定的订单
//			//ordRepository.updateApprove_org(ordorg, ormtno, bradno, spclno);
//		} else {
//			//提交当前区域下的所有订单
//			//获取所有的订货单位
//			List<Org> orgs=queryOrdorg( ormtno, qyno, channo, "DZ");
//			for(Org org:orgs){
//				check_S10_rule(ormtno, org.getOrgno(), spclno, bradno);
//				//ordRepository.updateApprove_org(org.getOrgno(), ormtno, bradno, spclno);
//			}
//		}
//
//	}
//	/**
//	 * 只检查套西，那么也就是只有西服大类提交的时候才会检查
//	 * @author mawujun qq:16064988 mawujun1234@163.com
//	 * @param ordorg
//	 * @param ormtno
//	 * @param spclno
//	 */
//	public void check_S10_rule(String ormtno,String ordorg,String spclno,String bradno){
//		//如果当前提交的不是西服大类，就不进行检查
//		if(!"02".equals(spclno)){
//			return;
//		}
//		String mtorno=ormtno+"_DZ_"+ordorg;
////		不拆套 男套西  ：标准 ，裤子     (裤子<=nvl(配置值,15%) *标准)
//
//		//List<String> sampnoes=new ArrayList<String>();
//		StringBuilder builder=new StringBuilder();
//
//		List<Map<String,Object>> check_S10_Z0_0=ordRepository.check_S10_Z0_0(mtorno);
//		for(Map<String,Object> map:check_S10_Z0_0){
//			BigDecimal T00_ormtqt=(BigDecimal)map.get("T00_ORMTQT");
//			BigDecimal T02_ormtqt=(BigDecimal)map.get("T02_ORMTQT");
//			if(T02_ormtqt.compareTo(T00_ormtqt.multiply(new BigDecimal(0.15)).setScale(2, RoundingMode.HALF_UP))==1){
//				//sampnoes.add(map.get("SAMPNM").toString());
//				builder.append(","+map.get("SAMPNM"));
//			}
//		}
//		if(builder.length()>0) {
//			throw new BusinessException(builder.substring(1)+"样衣<br/>的规则是： 裤子<=15%*标准");
//		}
////      拆套男套西   ：上衣，裤子         (上衣=<裤子<=nvl(1+配置值,115% )*上衣)
//		List<Map<String,Object>> check_S10_Z0_1=ordRepository.check_S10_Z0_1(mtorno);
//		for(Map<String,Object> map:check_S10_Z0_1){
//			//BigDecimal T00_ormtqt=(BigDecimal)map.get("T00_ormtqt");
//			BigDecimal T01_ormtqt=(BigDecimal)map.get("T01_ORMTQT");
//			BigDecimal T02_ormtqt=(BigDecimal)map.get("T02_ORMTQT");
//			if(T02_ormtqt.compareTo(T01_ormtqt.multiply(new BigDecimal(1.15)).setScale(2, RoundingMode.HALF_UP))==1 || T02_ormtqt.compareTo(T01_ormtqt)==-1){
//				//sampnoes.add(map.get("SAMPNM").toString());
//				builder.append(","+map.get("SAMPNM"));
//			}
//		}
//		if(builder.length()>0){
//			throw new BusinessException(builder.substring(1)+"样衣<br/>的规则是：上衣=<裤子<=115%*上衣");
//		}
//		
////		//	    女 套西：上衣，裤子，裙子      (上衣=裤子=裙子)
////		List<Map<String,Object>> check_S10_Z1=ordRepository.check_S10_Z1(mtorno);
////		for(Map<String,Object> map:check_S10_Z1){
////			//BigDecimal T00_ormtqt=(BigDecimal)map.get("T00_ormtqt");
////			BigDecimal T01_ormtqt=(BigDecimal)map.get("T01_ORMTQT");
////			BigDecimal T02_ormtqt=(BigDecimal)map.get("T02_ORMTQT");
////			BigDecimal T04_ormtqt=(BigDecimal)map.get("T04_ORMTQT");
////			if(T02_ormtqt.compareTo(T01_ormtqt.multiply(new BigDecimal(1.15)))!=0 && T02_ormtqt.compareTo(T04_ormtqt)!=0 ){
////				//sampnoes.add(map.get("SAMPNM").toString());
////				builder.append(","+map.get("SAMPNM"));
////			}
////		}
////		if(builder.length()>0){
////			throw new BusinessException(builder.substring(1)+"样衣<br/>的规则是：上衣=裤子=裙子");
////		}
//	}
	
	
	public Pager<Map<String,Object>> zgsVO_queryZgsVO(Pager<Map<String,Object>> pager) {
		return ordRepository.queryZgsVO(pager);
	}
	public List<Map<String,Object>> zgsVO_query_exportZgsVO(MapParams params) {
		return ordRepository.queryZgsVO(params.getParams());
	}
	
	
	public Map<String,Object> zgsVO_check_canedit(String ormtno,String bradno,String spclno) {
		return ordRepository.zgs_check_canedit(ormtno, bradno, spclno);
	}
	public List<Map<String,Object>> zgsVO_queryOrderState(String ormtno,String bradno,String spclno){
		return ordRepository.zgs_queryOrderState(ormtno, bradno, spclno);
	}
	
	public void zgsVO_clearNum(String[] sampnos,String ormtno) {
		if(sampnos==null || sampnos.length==0){
			return;
		}
		for(String sampno:sampnos){
			ordRepository.order_dl__comp_canc(ormtno, sampno, ShiroUtils.getLoginName());
//			//清空明细表
//			ordRepository.clearnum_orddtl(sampno);
//			////清空规格明细,放到后面的时候清零，发现明细表里的数量为0，就把规格明细表中的数据清零
//			//ordRepository.clearnum_ordszdtl(sampno);
//			//插入总公司平衡主表
//			CompPal compPay=new CompPal();
//			compPay.setOrmtno(ormtno);
//			compPay.setSampno(sampno);
//			compPay.setPaltpy("取消");
//			compPalService.create(compPay);
			
			
		}
	}
	/**
	 * 总公司平衡--合并
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param data 第一个参数源样衣编号，第二个参数是设计样衣名称
	 * @param ormtno
	 */
	public void zgsVO_meger_all(ArrayList<Map<String,Object>> data,String ormtno) {
		if(data==null || data.size()==0){
			return;
		}
		//根据目标样衣名称获取目标样衣编号
		for(Map<String,Object> map:data){
			String P_SAMPNO=(String)map.get("SAMPNO");//源样衣编号
			String PSMPNM=(String)map.get("PSMPNM");//目标样衣名称
			SampleDesign sampleDesign=sampleDesignRepository.getSampleDesignBySampnm(ormtno, PSMPNM);
			if(sampleDesign==null){
				throw new BusinessException(PSMPNM+":这个目标样衣编号没有找到!");
			}
			
			ordRepository.order_dl__comp_comb(ormtno, P_SAMPNO, sampleDesign.getSampno(), ShiroUtils.getLoginName());
		}
		
		
	}

	public List<Map<String,Object>> zgsVO_query_meger_comp(String SAMPNO) {
		return ordRepository.query_meger_comp(SAMPNO);
	}
	/**
	 * 总公司平衡--拆分
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param data
	 * @param ormtno
	 */
	public void zgsVO_meger_comp(ArrayList<Map<String,Object>> data,String ormtno) {
		
		if(data==null || data.size()==0){
			return;
		}
		// 根据目标样衣名称获取目标样衣编号
		for (Map<String, Object> map : data) {
			String P_SAMPNO = (String) map.get("SAMPNO");// 源样衣编号
			String P_YXGSNO = (String) map.get("YXGSNO");
			String PSMPNM = (String) map.get("PSMPNM");// 目标样衣名称
			SampleDesign sampleDesign = sampleDesignRepository.getSampleDesignBySampnm(ormtno, PSMPNM);
			if (sampleDesign == null) {
				throw new BusinessException(PSMPNM + ":这个目标样衣编号没有找到!");
			}

			ordRepository.order_dl__comp_comc(ormtno, P_YXGSNO, P_SAMPNO,sampleDesign.getSampno(), ShiroUtils.getLoginName());
		}
	}
	/**
	 * 总公司平衡-恢复
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampnos
	 * @param ormtno
	 */
	public void zgsVO_recover(ArrayList<Map<String,Object>> data,String ormtno) {
		if(data==null || data.size()==0){
			return;
		}
		for (Map<String, Object> map : data) {
			ordRepository.order_dl__comp_comd(ormtno, (String)map.get("PALTPY"), (String)map.get("SAMPNO"), ShiroUtils.getLoginName());
		}
	}
	/**
	 * 总公司平衡-平衡完成
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sampnos
	 * @param ormtno
	 */
	public void zgsVO_balanceOver(String ormtno,String bradno,String spclno) {
		ordRepository.order_dl__comp_pass(ormtno, bradno, spclno, ShiroUtils.getLoginName());
	}
	
	
	
	public String wxtz_check_stat(String ormtno,String bradno,String spclno) {
		List<String> list=ordRepository.wxtz_check_stat(ormtno, bradno, spclno);
		if(list==null || list.size()==0 || list.size()>1){
			//throw new BusinessException("订单的状态不对，请先检查!");
			return "不可操作";
		}
		return list.get(0);
	}
	/**
	 * 查询尾箱调整的数据
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param pager
	 * @return
	 */
	public Pager<Map<String,Object>> wxtz_queryWx(Pager<Map<String,Object>> pager) {
		return ordRepository.wxtz_queryWx(pager);
	}
	public List<Map<String,Object>> wxtz_exportWx(MapParams params) {
		return ordRepository.wxtz_queryWx(params.getParams());
	}
	/**
	 * 尾箱调整
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	public void wxtz_comp_wx(String ormtno,String bradno,String spclno){
		ordRepository.order_dl__comp_wx(ormtno, bradno, spclno, ShiroUtils.getLoginName());
	}
	public void comp_wx_qw(String ormtno,String bradno,String spclno){
		ordRepository.order_dl__comp_wx_qw(ormtno, bradno, spclno, ShiroUtils.getLoginName());
	}
	/**
	 * 尾箱调整完成
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	public void wxtz_comp_wxps(String ormtno,String bradno,String spclno){
		ordRepository.order_dl__comp_wxps(ormtno, bradno, spclno, ShiroUtils.getLoginName());
	}
	
	//==========================================================
	public List<Map<String,Object>> sizeVO_querySizeVOColumns(String sizegp,Integer sztype) {
		if(sztype==0 ){//包装箱+规格
			//生成单规后，还要生成一个单规的规格组
			List<Map<String,Object>> columns=ordRepository.sizeVO_querySizeVOColumns(sizegp, sztype);
			List<Map<String,Object>> columns_new=new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map:columns){
				if("STDSZ".equals(map.get("SIZETY"))){//如果是单规，就生成单规的规格组
					Map<String,Object> map_new = new HashMap<String,Object>();
					map_new.put("SIZETY", "STDSZPRDPK");//变成包装箱组的单规
					map_new.put("SIZENO", "STDSZPRDPK___"+map.get("SIZENO"));//通过三条下划线来分隔
					map_new.put("SIZENM", map.get("SIZENM"));
					columns_new.add(map_new);
				}
				//对原数据进行处理,保证唯一性
				map.put("SIZENO",map.get("SIZETY")+"___"+map.get("SIZENO"));
				
			}
			columns.addAll(columns_new);
			return columns;
		} else if(sztype==1){//单规上报
			List<Map<String,Object>> columns=ordRepository.sizeVO_querySizeVOColumns(sizegp, sztype);
			for(Map<String,Object> map:columns){
				//对原数据进行处理,保证唯一性
				map.put("SIZENO",map.get("SIZETY")+"___"+map.get("SIZENO"));
			}
			return columns;
		}  else if(sztype==2){//包装箱上报
			List<Map<String,Object>> columns=ordRepository.sizeVO_querySizeVOColumns(sizegp, sztype);
			List<Map<String,Object>> columns_new=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> columns_new_PRDPK=new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map:columns){
				if("STDSZ".equals(map.get("SIZETY"))){//如果是单规，就生成单规的规格组
					Map<String,Object> map_new = new HashMap<String,Object>();
					//map_new.put("SIZETY", "STDSZPRDPK");//变成包装箱组的单规
					//map_new.put("SIZENO", "STDSZPRDPK___"+map.get("SIZENO"));//通过三条下划线来分隔
					map_new.put("SIZETY", "STDSZ");//变成包装箱组的单规
					map_new.put("SIZENO", "STDSZ___"+map.get("SIZENO"));//通过三条下划线来分隔
					map_new.put("SIZENM", map.get("SIZENM"));
					columns_new.add(map_new);
				}
				if("PRDPK".equals(map.get("SIZETY"))){
					Map<String,Object> map_new = new HashMap<String,Object>();
					map_new.put("SIZETY", "PRDPK");//变成包装箱组的单规
					map_new.put("SIZENO", "PRDPK___"+map.get("SIZENO"));//通过三条下划线来分隔
					map_new.put("SIZENM", map.get("SIZENM"));
					columns_new_PRDPK.add(map_new);
				}
			}
			columns_new_PRDPK.addAll(columns_new);
			return columns_new_PRDPK;
		} else {
			return null;
		}
	}
	
	public void sizeVO_updateOrdszdtl(OrdszdtlVO ordszdtlVO) {
		if(ordszdtlVO.getValue()==null){
			return;
		}
		Ordszdtl ordszdtl=ordszdtlRepository.get(ordszdtlVO.geetPK());
		Integer orginal_orbgqt=0;
		boolean isNew=false;
		if(ordszdtl==null){
			isNew=true;
			ordszdtl=new Ordszdtl();
			
			BeanUtils.copyProperties(ordszdtlVO, ordszdtl);
			if ("PRDPK".equals(ordszdtl.getSizety())) {
				ordszdtl.setOrszst(1);
			} else {
				ordszdtl.setOrszst(0);
				
			}
			ordszdtl.setRgdt(new Date());
			ordszdtl.setRgsp(ShiroUtils.getLoginName());
		} else {
			orginal_orbgqt=ordszdtl.getOrbgqt();//修改之前的数量
		}
		// 获取包装上报方式：
		if (ordszdtlVO.getSztype() == 0) {// 如果是整箱+单规的上报方式，并且输入的是是单规的话，就只修改orszqt，否则就修改orbgqt
			//只有当单规确认后，才可以修改包装箱的数量
			//如果是标准箱，只需要设置orbgqt就可以了
			if ("PRDPK".equals(ordszdtl.getSizety())) {
				ordszdtl.setOrbgqt(ordszdtlVO.getValue());
				//同时还要修改剩余的数量
				sizeVO_checkOrdszdtl(ordszdtlVO,orginal_orbgqt);
				// ordszdtl.setOrszqt(0);
			} else {
				//如果是单规，就设置orszqt，同一行的orgbqt存放的是标准箱用完之后的剩余数量
				ordszdtl.setOrszqt(ordszdtlVO.getValue());
				//if(isNew){
					ordszdtl.setOritqt(ordszdtl.getOrszqt());//原始数量，备份现场定后数量
					ordszdtl.setOrbgqt(ordszdtl.getOrszqt());//确认数量 报表要实时查看的数量
				//}
			}

		} else if (ordszdtlVO.getSztype() == 1) {//如果是单规上报
			// 否则orszqt和orbgqt的数量就是一致的
			ordszdtl.setOrszqt(ordszdtlVO.getValue());
			ordszdtl.setOrbgqt(ordszdtlVO.getValue());
			if(isNew){
				ordszdtl.setOritqt(ordszdtl.getOrszqt());//原始数量，备份现场定后数量
			}
		} else  if (ordszdtlVO.getSztype() == 2){//整箱上报
			if ("PRDPK".equals(ordszdtl.getSizety())) {
				ordszdtl.setOrbgqt(ordszdtlVO.getValue());
				//上报方式为整箱上报的时候，录入 标准箱的时候，保存  ORD_ORDSZDTL 的 ORSZST=0 ，ORSZQT=0 2017.08.03
				ordszdtl.setOrszst(0);
				ordszdtl.setOrszqt(0);
			} else {
				
				ordszdtl.setOrszqt(ordszdtlVO.getValue());
				ordszdtl.setOrbgqt(ordszdtlVO.getValue());
			}
		}
		
		ordszdtl.setLmdt(new Date());
		ordszdtl.setLmsp(ShiroUtils.getLoginName());
		//throw new BusinessException("测试");
		ordszdtlRepository.createOrUpdate(ordszdtl);
	}
	/**
	 * 检查输入当标准箱的时候，进行判断剩余的单规数量是否足够分配给这个标准箱PRDPK时，会使用
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	public void sizeVO_checkOrdszdtl(OrdszdtlVO ordszdtlVO,Integer orginal_orbgqt){
		//获取指定样衣编号的剩余规格数量
		List<Ordszdtl> ordszdtles=ordszdtlRepository.query(Cnd.select()
				.andEquals(M.Ordszdtl.sizety, "STDSZ")
				.andEquals(M.Ordszdtl.sampno, ordszdtlVO.getSampno())
				.andEquals(M.Ordszdtl.mtorno, ordszdtlVO.getMtorno())
				.andEquals(M.Ordszdtl.suitno, ordszdtlVO.getSuitno()));
		
		//获取指定标准箱中各个规格的分配比例
		List<PubSizeDtl>  pubSizeDtles=pubSizeDtlRepository.query(Cnd.select().andEquals(M.PubSizeDtl.fszty, "PRDPK")
				.andEquals(M.PubSizeDtl.sizety, "STDSZ")
				.andEquals(M.PubSizeDtl.fszno, ordszdtlVO.getSizeno()));
		//判断是新增了还是减少了
		Integer xiangcha=ordszdtlVO.getValue()-orginal_orbgqt;
		int i=0;
		for(Ordszdtl ordszdtl:ordszdtles){
			
			for(PubSizeDtl pubSizeDtl:pubSizeDtles) {
				System.out.println(ordszdtl.getSizeno()+"==========="+pubSizeDtl.getSizeno());
				if(ordszdtl.getSizeno().equals(pubSizeDtl.getSizeno())){
					i++;
					ordszdtl.setOrbgqt(ordszdtl.getOrbgqt()-(xiangcha*pubSizeDtl.getSizeqt()));
					//判断剩余数量是否足够指定的数量的比配
					if(ordszdtl.getOrbgqt()<0){
						PubSize.PK pk=new PubSize.PK();
						pk.setSizety("STDSZ");
						pk.setSizeno(ordszdtl.getSizeno());
						PubSize pubSize=pubSizeRepository.get(pk);
						throw new BusinessException("剩余数量不足以成箱:"+pubSize.getSizenm()+"("+pubSize.getSizeno()+")");
					}
				}
			}
			
		}
		if(i!=pubSizeDtles.size()){
			throw new BusinessException("订货规格和成箱规格不一致，不能成箱!");
		}
		
		//如果都允许，就开始更新剩余规格的数量
		for(Ordszdtl ordszdtl:ordszdtles){
			//System.out.println(ordszdtl.getOrbgqt());
			ordszdtlRepository.update(ordszdtl);
		}
		
		
		
	}
	/**
	 * 规格平衡查询数据
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> sizeVO_querySizeVOData(Map<String,Object> params) {
		params.put("mtorno", getMtorno(params.get("ormtno").toString(),params.get("ortyno").toString(),params.get("ordorg").toString()));
		List<Map<String,Object>> list= ordRepository.sizeVO_querySizeVOData(params);
		List<Map<String,Object>> result= new ArrayList<Map<String,Object>>();
		
		//String temp_sampno="";
		//规格上报方式
		Integer sztype=Integer.parseInt(params.get("sztype").toString());
		//整箱+单规
		if(sztype==0){//数组组装成规格，标准箱，剩余单规三种
			Map<String,Map<String,Object>> key_map=new HashMap<String,Map<String,Object>>();
			for(Map<String,Object> listmap:list){
				Map<String,Object> map=key_map.get(listmap.get("SAMPNO").toString());
				if(map==null) {
					map=new HashMap<String,Object>();
					//map.put("ORMTNO", listmap.get(""));
					
					
					map.put("ORDORG_NAME", params.get("ordorg_name"));
					map.put("SPTYNO", listmap.get("SPTYNO"));
					map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name((String)listmap.get("SPTYNO")));
					map.put("SPSENO", listmap.get("SPSENO"));
					map.put("SPSENO_NAME", PubCodeCache.getSpseno_name((String)listmap.get("SPSENO")));
					map.put("VERSNO", listmap.get("VERSNO"));
					if(listmap.get("VERSNO")!=null){
						map.put("VERSNO_NAME", PubCodeCache.getVersno_name((String)listmap.get("VERSNO")));
					}
					
					map.put("PLSPNO", listmap.get("PLSPNO"));
					map.put("PLSPNM", listmap.get("PLSPNM"));
					map.put("SAMPNO", listmap.get("SAMPNO"));
					map.put("SAMPNM", listmap.get("SAMPNM"));
					map.put("ORMTQT", listmap.get("ORMTQT"));
					map.put("ORSZST", listmap.get("ORSZST"));
					
					//map.put("PACKQT", listmap.get("PACKQT"));
					map.put("SUITNO", listmap.get("SUITNO"));
					map.put("MTORNO", listmap.get("MTORNO"));
					map.put("MLORNO", listmap.get("MLORNO"));
					map.put("SZTYPE", sztype);
					result.add(map);
					key_map.put(listmap.get("SAMPNO").toString(), map);
					
					map.put("STDSZ___SUBTOTAL", new BigDecimal(0));
					map.put("PRDPK___SUBTOTAL", new BigDecimal(0));
					map.put("STDSZPRDPK___SUBTOTAL", new BigDecimal(0));
					map.put("ORMTQT_NOW", new BigDecimal(0));//兑现数量
				}
				//接下来是行列转换
				if("STDSZ".equals(listmap.get("SIZETY"))){//如果是单规
					map.put("STDSZPRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));//取ORBGQT（包装数量）作为单规剩余数量，就是手机端订的数量
					map.put("STDSZ___"+listmap.get("SIZENO"), listmap.get("ORSZQT"));//如果是单规，就取ORSZQT(数量)作为单规的值
					
					//添加小计,手机输入的那段单规规格数量
					if(listmap.get("ORSZQT")!=null){
						BigDecimal aaa=(BigDecimal)map.get("STDSZ___SUBTOTAL");
						BigDecimal ORSZQT=(BigDecimal)listmap.get("ORSZQT");
						if(ORSZQT==null){
							ORSZQT=new BigDecimal(0);
						}
						aaa=aaa.add(ORSZQT);
						map.put("STDSZ___SUBTOTAL",aaa);
						//兑现数量
						map.put("ORMTQT_NOW", ((BigDecimal)map.get("ORMTQT_NOW")).add(ORSZQT));
					}
					
					//添加小计，单规箱的单规
					if(listmap.get("ORBGQT")!=null){
						BigDecimal aaa=(BigDecimal)map.get("STDSZPRDPK___SUBTOTAL");
						BigDecimal ORBGQT=(BigDecimal)listmap.get("ORBGQT");
						if(ORBGQT==null){
							ORBGQT=new BigDecimal(0);
						}
						aaa=aaa.add(ORBGQT);
						map.put("STDSZPRDPK___SUBTOTAL",aaa);
						
						//兑现数量
						//map.put("ORMTQT_NOW", ((BigDecimal)map.get("ORMTQT_NOW")).add(ORBGQT));

					}
					
					
					
				} else if("PRDPK".equals(listmap.get("SIZETY"))){
					map.put("PRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));//取ORBGQT（包装数量）作为 标准箱的数量
					map.put("PRDPK___PACKQT___"+listmap.get("SIZENO"), listmap.get("PACKQT"));
					
					//添加 标准箱小计
					if(listmap.get("ORBGQT")!=null){
						BigDecimal aaa=(BigDecimal)map.get("PRDPK___SUBTOTAL");
						BigDecimal ORBGQT=(BigDecimal)listmap.get("ORBGQT");
						if(ORBGQT==null){
							ORBGQT=new BigDecimal(0);
						}
						BigDecimal packqt=(BigDecimal)listmap.get("PACKQT");
						if(packqt==null){
							packqt=new BigDecimal(1);
						}
						aaa=aaa.add(ORBGQT.multiply(packqt));
						map.put("PRDPK___SUBTOTAL",aaa);
						
						//兑现数量
						
						//map.put("ORMTQT_NOW", ((BigDecimal)map.get("ORMTQT_NOW")).add(ORBGQT.multiply(packqt)));
					}
				} 
			}
		} else if(sztype==1){//单规上报
			Map<String,Map<String,Object>> key_map=new HashMap<String,Map<String,Object>>();
			
			//BigDecimal STDSZ___SUBTOTAL=new BigDecimal(0);
			for(Map<String,Object> listmap:list){
				Map<String,Object> map=key_map.get(listmap.get("SAMPNO").toString());
				if(map==null) {
					map=new HashMap<String,Object>();
					map.put("ORDORG_NAME", params.get("ordorg_name"));
					map.put("SPTYNO", listmap.get("SPTYNO"));
					map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name((String)listmap.get("SPTYNO")));
					map.put("SPSENO", listmap.get("SPSENO"));
					map.put("SPSENO_NAME", PubCodeCache.getSpseno_name((String)listmap.get("SPSENO")));
					map.put("VERSNO", listmap.get("VERSNO"));
					map.put("VERSNO_NAME", PubCodeCache.getVersno_name((String)listmap.get("VERSNO")));
					map.put("PLSPNO", listmap.get("PLSPNO"));
					map.put("PLSPNM", listmap.get("PLSPNM"));
					map.put("SAMPNO", listmap.get("SAMPNO"));
					map.put("SAMPNM", listmap.get("SAMPNM"));
					map.put("ORMTQT", listmap.get("ORMTQT"));
					map.put("ORSZST", listmap.get("ORSZST"));
					
					map.put("PACKQT", listmap.get("PACKQT"));
					map.put("SUITNO", listmap.get("SUITNO"));
					map.put("MTORNO", listmap.get("MTORNO"));
					map.put("MLORNO", listmap.get("MLORNO"));
					map.put("SZTYPE", sztype);
					

					result.add(map);
					key_map.put(listmap.get("SAMPNO").toString(), map);
					
					map.put("STDSZ___SUBTOTAL", new BigDecimal(0));
					map.put("ORMTQT_NOW", new BigDecimal(0));//兑现数量
				}
				//接下来是行列转换
				map.put(listmap.get("SIZETY")+"___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
				//添加小计
				if(listmap.get("ORBGQT")!=null){
					BigDecimal STDSZ___SUBTOTAL=(BigDecimal)map.get("STDSZ___SUBTOTAL");
					STDSZ___SUBTOTAL=STDSZ___SUBTOTAL.add((BigDecimal)listmap.get("ORBGQT"));
					map.put("STDSZ___SUBTOTAL",STDSZ___SUBTOTAL);
					
					//兑现数量
					map.put("ORMTQT_NOW", ((BigDecimal)map.get("ORMTQT_NOW")).add((BigDecimal)listmap.get("ORBGQT")));
				}
			}
		} else if(sztype==2){//整箱上报
			Map<String,Map<String,Object>> key_map=new HashMap<String,Map<String,Object>>();
			for(Map<String,Object> listmap:list){
				Map<String,Object> map=key_map.get(listmap.get("SAMPNO").toString());
				if(map==null) {
					map=new HashMap<String,Object>();
					map.put("ORDORG_NAME", params.get("ordorg_name"));
					map.put("SPTYNO", listmap.get("SPTYNO"));
					map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name((String)listmap.get("SPTYNO")));
					map.put("SPSENO", listmap.get("SPSENO"));
					map.put("SPSENO_NAME", PubCodeCache.getSpseno_name((String)listmap.get("SPSENO")));
					map.put("VERSNO", listmap.get("VERSNO"));
					map.put("VERSNO_NAME", PubCodeCache.getVersno_name((String)listmap.get("VERSNO")));
					map.put("PLSPNO", listmap.get("PLSPNO"));
					map.put("PLSPNM", listmap.get("PLSPNM"));
					map.put("SAMPNO", listmap.get("SAMPNO"));
					map.put("SAMPNM", listmap.get("SAMPNM"));
					map.put("ORMTQT", listmap.get("ORMTQT"));
					map.put("ORSZST", listmap.get("ORSZST"));
					
					map.put("PACKQT", listmap.get("PACKQT"));
					map.put("SUITNO", listmap.get("SUITNO"));
					map.put("MTORNO", listmap.get("MTORNO"));
					map.put("MLORNO", listmap.get("MLORNO"));
					map.put("SZTYPE", sztype);
					result.add(map);
					key_map.put(listmap.get("SAMPNO").toString(), map);	
					
					map.put("STDSZ___SUBTOTAL", new BigDecimal(0));
					map.put("PRDPK___SUBTOTAL", new BigDecimal(0));
					map.put("ORMTQT_NOW", new BigDecimal(0));//兑现数量
				}
				//接下来是行列转换
				if("STDSZ".equals(listmap.get("SIZETY"))){//如果是单规
					//map.put("STDSZPRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
					map.put("STDSZ___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
					//添加小计
					if(listmap.get("ORBGQT")!=null){
						BigDecimal aaa=(BigDecimal)map.get("STDSZ___SUBTOTAL");
						aaa=aaa.add((BigDecimal)listmap.get("ORBGQT"));
						map.put("STDSZ___SUBTOTAL",aaa);
						
						//兑现数量
						map.put("ORMTQT_NOW", ((BigDecimal)map.get("ORMTQT_NOW")).add((BigDecimal)listmap.get("ORBGQT")));
					}
					
				} else if("PRDPK".equals(listmap.get("SIZETY"))){
					map.put("PRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
					map.put("PRDPK___PACKQT___"+listmap.get("SIZENO"), listmap.get("PACKQT"));
					//添加小计
					if(listmap.get("ORBGQT")!=null){
						BigDecimal aaa=(BigDecimal)map.get("PRDPK___SUBTOTAL");
						BigDecimal ORBGQT=(BigDecimal)listmap.get("ORBGQT");
						if(ORBGQT==null){
							ORBGQT=new BigDecimal(0);
						}
						aaa=aaa.add(ORBGQT);
						map.put("PRDPK___SUBTOTAL",aaa);
						
						//兑现数量
						BigDecimal packqt=(BigDecimal)listmap.get("PACKQT");
						if(packqt==null){
							packqt=new BigDecimal(1);
						}
						map.put("ORMTQT_NOW", ((BigDecimal)map.get("ORMTQT_NOW")).add(ORBGQT.multiply(packqt)));
						

					}
				}
				
			}
		}
		
		return result;
	}
	
	public Integer sizeVO_getSzstat(Map<String,Object> params) {
		Integer szstat= ordRepository.sizeVO_getSzstat(params);
		if(szstat==null){
			szstat=0;
		}
		return szstat;
	}
	
	public void sizeVO_auto_box_check_num(String ormtno,String ortyno,String ordorg,String bradno,String spclno,String suitno){
		//规格合计的总量是否  和平衡数量一致，不一致 提示出来，不允许成箱；
				String mtorno=this.getMtorno(ormtno, ortyno, ordorg);
				String mlorno=mtorno+bradno+spclno;
				List<Map<String,Object>> list=ordRepository.sizeVO_auto_box_check_num(mtorno, mlorno, suitno);
				if(list!=null && list.size()>0){
					StringBuilder builder=new StringBuilder();
					for(Map<String,Object> map:list){
						builder.append(","+map.get("SAMPNM"));
					}
					throw new BusinessException("下列样衣编号兑现数量不等于规格小计，不能提交:<br/>"+builder.substring(1));
				}
	}
	/**
	 * 规格平衡 自动成箱
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param ordtyno
	 * @param ordorg
	 * @param bradno
	 * @param spclno
	 */
	public void sizeVO_auto_box(String ormtno,String ortyno,String ordorg,String bradno,String spclno,String suitno,Integer sztype) {
		if(sztype!=0){
			throw new BusinessException("上报方式不是‘单规+整箱’，不能自动成箱!");
		}
		
		this.sizeVO_auto_box_check_num(ormtno, ortyno, ordorg, bradno, spclno, suitno);
		//System.out.println(111111);
		
		ordRepository.order_dl__auto_box(ormtno,ortyno,ordorg, bradno, spclno, ShiroUtils.getLoginName());
	}
	public void sizeVO_auto_initsize(String ormtno,String ortyno,String ordorg,String bradno,String spclno) {
		ordRepository.sizeVO_auto_initsize( ormtno, ortyno, ordorg, bradno, spclno, ShiroUtils.getLoginName());
	}
	/**
	 * 规格平衡---规格平衡-提交审批
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param ordtyno
	 * @param ordorg
	 * @param bradno
	 * @param spclno
	 */
	public void sizeVO_size_ap(String ormtno,String ortyno,String ordorg,String bradno,String spclno,String suitno) {
		//如果 总量状态 不是审批通过的时候不允许提交
		Integer arstat=ordRepository.sizeVO_size_ap_check_orstat(ormtno, ortyno, ordorg, bradno, spclno);
		if(arstat!=3){
			throw new BusinessException("总量状态 不是审批通过的时候不允许提交");
		}
		

		//包装总量 是否和 平衡数量一致，不一致的提示 出来，不允许 提交审批；
		this.sizeVO_auto_box_check_num(ormtno, ortyno, ordorg, bradno, spclno, suitno);
		
		ordRepository.order_dl__size_ap(ormtno,ortyno,ordorg, bradno, spclno, ShiroUtils.getLoginName());
	}
	
	
	
	
	public Pager<Map<String,Object>> ordMgr_queryOrdMgr(Pager<Map<String,Object>> pager) {
		Map<String,Object> params=(Map<String,Object>)pager.getParams();
		//
		params.put("user_id", ShiroUtils.getUserId());
//		//如果条件是待审
//		if("ready".equals(params.get("readyHandling"))){
//			//如果是大区，就取总量状态或规格状态为1的订单
//			if(ShiroUtils.getAuthenticationInfo().hasChanno(Chancl.YXGS)){
//				params.put("stat", "1");
//			} else if(ShiroUtils.getAuthenticationInfo().hasChanno(Chancl.GSBB)){
//				params.put("stat", "2");
//			}
//			
//		}
		
		pager= ordRepository.ordMgr_queryOrdMgr(pager);
		List<Map<String,Object>> list=pager.getRoot();//new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:list){
			map.put("ORTYNM", ContextUtils.getOrdty(map.get("ORTYNO").toString()).getOrtynm());
			map.put("CHANNO_NAME", ContextUtils.getChanno(map.get("CHANNO").toString()).getChannm());
			map.put("BRADNO_NAME", PubCodeCache.getBradno_name(map.get("BRADNO").toString()));
			map.put("SPCLNO_NAME", PubCodeCache.getSpclno_name(map.get("SPCLNO").toString()));
		}
		pager.setRoot(list);
		return pager;
	}
	
	public ReloadTotal reloadTotal(MapParams params) {
		return ordRepository.reloadTotal(params.getParams());
	}
	
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return 0:表示没有满足条件，不需要看到这个按钮。.1:表示还有区域没有 平衡完成. 2:表示可以进行“确认”了  .3:订单已经确认了
	 */
	public Integer yxgs_getOrstat() {
		Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		
		
		String ormtno=ContextUtils.getFirstOrdmt().getOrmtno();
		String yxgsno=org.getOrgno();
		//检查该营销公司下是否所有的订单都已经按过“平衡完成”，即判断该营销公司下面的所有订单的状态是否是“大区审批中”
		List<Integer> list=ordRepository.yxgs_getOrstat(ormtno, yxgsno);
		if(list!=null && list.size()>0){
//			StringBuilder builder=new StringBuilder("");
//			for(Map<String,Object> map:list){
//				builder.append(","+map.get("QYNM"));
//			}
//			throw new BusinessException("下列区域平衡未完成:"+builder.substring(1));
			//素有区域都已经是“大区审批中”的话，表示可以按确认按钮了
			if(list.size()==1 && list.get(0)==1){
				return 2;
			} else if(list.size()==1 && list.get(0)==2){
				return 3;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}
	
	/**
	 * 营销公司确认，确认该区域下所有订单都提交
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public void yxgs_confirm() {
		//UserVO userVO=ShiroUtils.getAuthenticationInfo();
		Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		if(org.getChanno()!=Chancl.YXGS){
			throw new BusinessException("你不是大区账号，没有权限进行确认!");
		}
		//Ord ord=ShiroUtils.getAuthenticationInfo().getOrd();

		//String ormtno=ord.getOrmtno();
		String ormtno=ContextUtils.getFirstOrdmt().getOrmtno();
		String yxgsno=org.getOrgno();
		//检查该营销公司下是否所有的订单都已经按过“平衡完成”，即判断该营销公司下面的所有订单的状态是否是“大区审批中”
		List<Map<String,Object>> list=ordRepository.yxgs_confirm_check_stats(ormtno, yxgsno);
		if(list!=null && list.size()>0){
			StringBuilder builder=new StringBuilder("");
			for(Map<String,Object> map:list){
				builder.append(","+map.get("QYNM"));
			}
			throw new BusinessException("下列区域平衡未完成:"+builder.substring(1));
		}
		
		//把订单状态变成“总部审批”中
		ordRepository.yxgs_confirm_update_orstat(ormtno, yxgsno);
		
	}
}
