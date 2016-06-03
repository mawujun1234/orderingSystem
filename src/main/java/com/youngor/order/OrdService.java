package com.youngor.order;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.mawujun.utils.page.Pager;
import com.youngor.ordmt.OrdmtScde;
import com.youngor.org.Org;
import com.youngor.permission.ShiroUtils;
import com.youngor.permission.UserVO;
import com.youngor.pubcode.PubCodeCache;
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
	private OrdhdRepository ordhdRepository;
	@Autowired
	private OrdszdtlRepository ordszdtlRepository;
	@Autowired
	private CompPalService compPalService;
	
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
		
		Map<String,Object> result=new HashMap<String,Object>();
		SampleVO sampleVO= ordRepository.querySample(sampnm,ContextUtils.getFirstOrdmt().getOrmtno());
		if(sampleVO==null){
			throw new BusinessException("该样衣编号不存在!");
		}
		sampleVO.setOrmtno(ContextUtils.getFirstOrdmt().getOrmtno());
		sampleVO.setOrdorg(ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno());
		
		sampleVO.setMtorno(ord.getMtorno());
		ord.setSampleVO(sampleVO);
		if("TX".equals(ord.getChanno())){
			sampleVO.setSpftpr(0d);
		}
		
		result.put("sampleVO", sampleVO);
		//获取套件和套件内的规格信息，
		//只有套西的小类的时候，才需要去ORD_SUIT_DC表中获取套装种类
		if(sampleVO.getSptyno().equals("S10")){
			//获取各个套件的价格
			List<SampleDesignStpr> stpres=ordRepository.querySampleDesignStpr(sampleVO.getSampno());
			//如果是特许，不显示出厂价
			if("TX".equals(ord.getChanno())){
				for(SampleDesignStpr sampleDesignStpr:stpres){
					sampleDesignStpr.setSpftpr(0d);
				}
			}
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
	 * 创建订单数据
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
				if(T02_ormtqt>T00_ormtqt*0.15){
					throw new BusinessException("不拆套 男套西 的裤子数量必须小于标准套的15%");
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
					ordszdtl.setOritqt(ordszdtl.getOrszqt());//原始数量，备份现场定后数量
					ordszdtl.setOrbgqt(ordszdtl.getOrbgqt());//确认数量 报表要实时查看的数量
					
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
		
		//先为订单明细表生成 审批订单号 和审批订单号版本,订单号+品牌+大类
		ordRepository.updateMtornoMlorvn(ord.getMtorno());
//		拷贝订单明细表中的确认数量-->原始数量,同时设置确认数量为0
//		ordRepository.updateOrmtqtZeor(ord.getMtorno());
		
		//为订单副表 生成数据，根据订单明细表生成数据 
		ordRepository.createOrd_ordhd(ord.getMtorno());
	
		//拷贝订单副表--》订单副表-历史
		ordRepository.createOrd_ordhd_his(ord.getMtorno());
		
		//拷贝订单明细表-->订单明细表历史
		ordRepository.createOrd_orddtl_his(ord.getMtorno());
		
		//修改ordhd的订单节点类型为20，即区域平衡
		ordRepository.update_ordhd_SDTYNO(ord.getMtorno());
		
		//更新订单规格明细表中的“审批订单号”
		ordRepository.update_ord_ordszdtl_MLORNO(ord.getMtorno());
	}
	
	SimpleDateFormat HHmm_format=new SimpleDateFormat("HHmm");
	public Map<String,Object> checked_closeing_info() {
		//这里去获取该用户，离订货时间借宿还有多长时间，如果时间不够的话，就给出提示
		UserVO userVO=ShiroUtils.getAuthenticationInfo();
		Ord ord=userVO.getOrd();
		Org org=userVO.getFirstCurrentOrg();
		Map<String,Object> result=new HashMap<String,Object>();
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
		//判断日期范围
		if(ordmtScde.getMtstdt().getTime()>cal.getTimeInMillis()){
			result.put("show", true);
			result.put("canOrd", false);//是否可以订货
			result.put("msg", "订货会还未开始，请稍候!");
		} else if(ordmtScde.getMtfidt().getTime()<cal.getTimeInMillis()){
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
		
		///判断该订单是否已经“确认”，
		boolean canConfirm=check_is_confirm(ord.getMtorno());
		result.put("canConfirm", canConfirm);
		return result;
		
	}
	
	private boolean check_is_confirm(String mtorno){
		List<String> list=ordRepository.check_is_confirm(mtorno);
		if(list ==null || list.size()==0){
			return true;
		}
		if(list.size()==1 ){
			if("10".equals(list.get(0))){
				return true;
			}
		}
		return false;
	}
	
	public MyInfoVO queryMyInfoVO() {
		UserVO userVO=ShiroUtils.getAuthenticationInfo();
		Ord ord=userVO.getOrd();
		MyInfoVO myInfoVO= ordRepository.queryMyInfoVO(ord.getMtorno());
		Org org=userVO.getFirstCurrentOrg();
		myInfoVO.setOrgnm(org.getOrgnm());
		return myInfoVO;
	}
	
	public List<Org> queryOrdorg(String ormtno,String qyno,String channo,String ortyno) {
		return ordRepository.queryOrdorg(ormtno, qyno, channo,ortyno);
	}
	
	public Pager<QyVO> queryQyVO(Pager<QyVO> pager) {
		return ordRepository.queryQyVO(pager);
	}
	
	public void updateOrmtqt(String mtorno,String sampno,String suitno,Integer ormtqt) {
		ordRepository.updateOrmtqt(mtorno, sampno, suitno, ormtqt);
	}
	
	public List<QyNewFormVO> querySuitBySampnm(String ormtno,String sampnm) {
		return ordRepository.querySuitBySampnm(ormtno, sampnm);
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
		//判断状态，如果状态不对的话，就不能保存
		if(ordhd.getOrstat()!=0 && ordhd.getOrstat()!=4){
			throw new BusinessException("该订单已被提交审批，不能新建!");
		}
//		// 如果这个样衣的这个品牌大类还没有订过，就创建订单副表的数据
//		if (ordhd == null) {
//
//		}	
		//创建订单明细表
		for(Orddtl orddtl:orddtles){
			orddtl.setMtorno(ordhd.getMtorno());
			orddtl.setMlorno(ordhd.getMlorno());
			orddtl.setMlorvn(ordhd.getMlorvn());
			orddtl.setOrmtqs(0);
			
			orddtlRepository.create(orddtl);
		}
	}
	
	public void updateApprove_org(String qyno,String channo,String ordorg,String ormtno,String bradno,String spclno) {
		
		if(ordorg!=null && !"".equals(ordorg)){
			//提交某个指定的订单
			ordRepository.updateApprove_org(ordorg, ormtno, bradno, spclno);
		} else {
			//提交当前区域下的所有订单
			//获取所有的订货单位
			List<Org> orgs=queryOrdorg( ormtno, qyno, channo, "DZ");
			for(Org org:orgs){
				ordRepository.updateApprove_org(org.getOrgno(), ormtno, bradno, spclno);
			}
		}
		
	}
	
	public Pager<Map<String,Object>> queryZgsVO(Pager<Map<String,Object>> pager) {
		return ordRepository.queryZgsVO(pager);
	}
	
	public void clearNum(String[] sampnos,String ormtno) {
		if(sampnos==null || sampnos.length==0){
			return;
		}
		for(String sampno:sampnos){
			//清空明细表
			ordRepository.clearnum_orddtl(sampno);
			////清空规格明细,放到后面的时候清零，发现明细表里的数量为0，就把规格明细表中的数据清零
			//ordRepository.clearnum_ordszdtl(sampno);
			//插入总公司平衡主表
			CompPal compPay=new CompPal();
			compPay.setOrmtno(ormtno);
			compPay.setSampno(sampno);
			compPay.setPaltpy("取消");
			compPalService.create(compPay);
			
		}
	}
	
	public void meger_all(ArrayList<Map<String,Object>> data) {
		
	}
	public List<Map<String,Object>> query_meger_comp(String SAMPNO) {
		return ordRepository.query_meger_comp(SAMPNO);
	}
	public void meger_comp(ArrayList<Map<String,Object>> data) {
		
	}
	public void recover(String[] sampnos,String ormtno) {
		
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
					map_new.put("SIZETY", "STDSZPRDPK");//变成包装箱组的单规
					map_new.put("SIZENO", "STDSZPRDPK___"+map.get("SIZENO"));//通过三条下划线来分隔
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
	/**
	 * 规格平衡查询数据
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> sizeVO_querySizeVOData(Map<String,Object> params) {
		List<Map<String,Object>> list= ordRepository.sizeVO_querySizeVOData(params);
		List<Map<String,Object>> result= new ArrayList<Map<String,Object>>();
		
		//String temp_sampno="";
		
		Integer sztype=Integer.parseInt(params.get("sztype").toString());
		if(sztype==0){//数组组装成规格，标准箱，剩余单规三种
			Map<String,Map<String,Object>> key_map=new HashMap<String,Map<String,Object>>();
			for(Map<String,Object> listmap:list){
				Map<String,Object> map=key_map.get(listmap.get("SAMPNO").toString());
				if(map==null) {
					map=new HashMap<String,Object>();
					map.put("ORDORG_NAME", params.get("ordorg_name"));
					map.put("SPTYNO", listmap.get("SPTYNO"));
					map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(listmap.get("SPTYNO").toString()));
					map.put("SPSENO", listmap.get("SPSENO"));
					map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(listmap.get("SPSENO").toString()));
					map.put("VERSNO", listmap.get("VERSNO"));
					map.put("VERSNO_NAME", PubCodeCache.getVersno_name(listmap.get("VERSNO").toString()));
					map.put("PLSPNO", listmap.get("PLSPNO"));
					map.put("PLSPNM", listmap.get("PLSPNM"));
					map.put("SAMPNO", listmap.get("SAMPNO"));
					map.put("SAMPNM", listmap.get("SAMPNM"));
					map.put("ORMTQT", listmap.get("ORMTQT"));
					result.add(map);
					key_map.put(listmap.get("SAMPNO").toString(), map);
				}
				//接下来是行列转换
				if("STDSZ".equals(listmap.get("SIZETY"))){//如果是单规
					map.put("STDSZPRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));//取ORBGQT（确认数量）作为单规剩余数量
					map.put("STDSZ___"+listmap.get("SIZENO"), listmap.get("ORSZQT"));//如果是单规，就取ORSZQT(数量)作为单规的值
				} else if("PRDPK".equals(listmap.get("SIZETY"))){
					map.put("PRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
				}
			}
		} else if(sztype==1){//单规上报
			Map<String,Map<String,Object>> key_map=new HashMap<String,Map<String,Object>>();
			for(Map<String,Object> listmap:list){
				Map<String,Object> map=key_map.get(listmap.get("SAMPNO").toString());
				if(map==null) {
					map=new HashMap<String,Object>();
					map.put("ORDORG_NAME", params.get("ordorg_name"));
					map.put("SPTYNO", listmap.get("SPTYNO"));
					map.put("SPSENO", listmap.get("SPSENO"));
					map.put("VERSNO", listmap.get("VERSNO"));
					map.put("PLSPNO", listmap.get("PLSPNO"));
					map.put("PLSPNM", listmap.get("PLSPNM"));
					map.put("SAMPNO", listmap.get("SAMPNO"));
					map.put("SAMPNM", listmap.get("SAMPNM"));
					map.put("ORMTQT", listmap.get("ORMTQT"));
					result.add(map);
					key_map.put(listmap.get("SAMPNO").toString(), map);
				}
				//接下来是行列转换
				map.put(map.get("SIZETY")+"___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
			}
		} else if(sztype==2){
			Map<String,Map<String,Object>> key_map=new HashMap<String,Map<String,Object>>();
			for(Map<String,Object> listmap:list){
				Map<String,Object> map=key_map.get(listmap.get("SAMPNO").toString());
				if(map==null) {
					map=new HashMap<String,Object>();
					map.put("ORDORG_NAME", params.get("ordorg_name"));
					map.put("SPTYNO", listmap.get("SPTYNO"));
					map.put("SPSENO", listmap.get("SPSENO"));
					map.put("VERSNO", listmap.get("VERSNO"));
					map.put("PLSPNO", listmap.get("PLSPNO"));
					map.put("PLSPNM", listmap.get("PLSPNM"));
					map.put("SAMPNO", listmap.get("SAMPNO"));
					map.put("SAMPNM", listmap.get("SAMPNM"));
					map.put("ORMTQT", listmap.get("ORMTQT"));
					result.add(map);
					key_map.put(listmap.get("SAMPNO").toString(), map);
				}
				//接下来是行列转换
				if("STDSZ".equals(listmap.get("SIZETY"))){//如果是单规
					map.put("STDSZPRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));//取ORBGQT（确认数量）作为单规剩余数量
				} else if("PRDPK".equals(listmap.get("SIZETY"))){
					map.put("PRDPK___"+listmap.get("SIZENO"), listmap.get("ORBGQT"));
				}
			}
		}
		
		return result;
	}
	
	
	public Pager<Map<String,Object>> ordMgr_queryOrdMgr(Pager<Map<String,Object>> pager) {
		//
		
		pager= ordRepository.ordMgr_queryOrdMgr(pager);
		List<Map<String,Object>> list=pager.getRoot();//new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:list){
			map.put("CHANNO_NAME", ContextUtils.getChanno(map.get("CHANNO").toString()).getChannm());
			map.put("BRADNO_NAME", PubCodeCache.getBradno_name(map.get("BRADNO").toString()));
			map.put("SPCLNO_NAME", PubCodeCache.getSpclno_name(map.get("SPCLNO").toString()));
		}
		pager.setRoot(list);
		return pager;
	}
}
