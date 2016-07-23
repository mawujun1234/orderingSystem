package com.youngor.report.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
import com.youngor.order.OrdService;
import com.youngor.org.Chancl;
import com.youngor.org.Org;
import com.youngor.org.OrgService;
import com.youngor.permission.ShiroUtils;
import com.youngor.pubcode.PubCode;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.pubcode.PubCodeController;
import com.youngor.sample.SampleDesign;
import com.youngor.sample.SampleDesignRepository;
import com.youngor.sample.SampleDesignService;
import com.youngor.sample.SamplePhoto;
import com.youngor.sample.SamplePhotoRepository;
import com.youngor.sample.SamplePlan;
import com.youngor.sample.SamplePlanService;
import com.youngor.utils.ContextUtils;

@Controller
public class MobileReportController {
	@Autowired
	private PubCodeController pubCodeController;
	@Autowired
	private MobileReportRepository mobileReportRepository;
	@Autowired
	private OrdService ordService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private SampleDesignService sampleDesignService;
	@Autowired
	private SamplePlanService samplePlanService;
	@Autowired
	private SamplePhotoRepository samplePhotoRepository;
	@Autowired
	private SampleDesignRepository sampleDesignRepository;
	
	/**
	 * 查询未订的必定款样衣
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/mobile/report/query_none_abstat.do")
	@ResponseBody
	public List<SampleDesign> query_none_abstat(){
		List<SampleDesign> list=ordService.query_none_abstat();
		return list;
	}
	
	@RequestMapping("/mobile/report/queryBradnoCondition.do")
	@ResponseBody
	public Map<String,Object> queryBradnoCondition(String bradno){
		if(bradno==null || "".equals(bradno)){
			bradno=ContextUtils.getFirstBradno();
		}
		Map<String,Object> result=new HashMap<String,Object>();
		
		//获取品牌，并且在展示的时候，默认显示第一个品牌
		List<PubCode> bradnos=pubCodeController.query4Combo("1", null, bradno, "1", false, null);
		List<Cond> cond_bradno=new ArrayList<Cond>();
		for(PubCode pubcode:bradnos){
			Cond cond=new Cond();
			cond.setValue(pubcode.getItno());
			cond.setName(pubcode.getItnm());
//			if(bradno.equals(cond.getValue())){
//				cond.setSelected(true);
//			}
			cond_bradno.add(cond);
		}
		result.put("cond_bradno", cond_bradno);
		result.put("sel_bradno", bradno);
		
		
//		List<PubCode> spclnos=pubCodeController.query4Combo("0", null, bradno, "1", false, null);
//		List<Cond> cond_spclno=new ArrayList<Cond>();
//		for(PubCode pubcode:spclnos){
//			Cond cond=new Cond();
//			cond.setValue(pubcode.getItno());
//			cond.setName(pubcode.getItnm());
//			cond_spclno.add(cond);
//		}
//		result.put("cond_spclno", cond_spclno);
		return result;
	}
	
	@RequestMapping("/mobile/report/querySpclnoCondition.do")
	@ResponseBody
	public Map<String,List<Cond>> querySpclnoCondition(String bradno){
		if(bradno==null || "".equals(bradno)){
			bradno=ContextUtils.getFirstBradno();
		}
		Map<String,List<Cond>> result=new HashMap<String,List<Cond>>();
		
		List<PubCode> spclnos=pubCodeController.query4Combo("0", null, bradno, "1", false, null);
		List<Cond> cond_spclno=new ArrayList<Cond>();
		for(PubCode pubcode:spclnos){
			Cond cond=new Cond();
			cond.setValue(pubcode.getItno());
			cond.setName(pubcode.getItnm());
			cond_spclno.add(cond);
		}
		result.put("cond_spclno", cond_spclno);
		return result;
	}
	
	@RequestMapping("/mobile/report/querySptynoCondition.do")
	@ResponseBody
	public Map<String,List<Cond>> querySptynoCondition(String bradno,String spclno){
		if(bradno==null || "".equals(bradno)){
			bradno=ContextUtils.getFirstBradno();
		}
		Map<String,List<Cond>> result=new HashMap<String,List<Cond>>();
		
		List<PubCode> spclnos=pubCodeController.query4Combo("2", spclno, bradno, "1", false, null);
		List<Cond> cond_sptyno=new ArrayList<Cond>();
		for(PubCode pubcode:spclnos){
			Cond cond=new Cond();
			cond.setValue(pubcode.getItno());
			cond.setName(pubcode.getItnm());
			cond_sptyno.add(cond);
		}
		result.put("cond_sptyno", cond_sptyno);
		return result;
	}
	
	@RequestMapping("/mobile/report/querySpsenoCondition.do")
	@ResponseBody
	public Map<String,List<Cond>> querySpsenoCondition(String bradno,String spclno){
		if(bradno==null || "".equals(bradno)){
			bradno=ContextUtils.getFirstBradno();
		}
		Map<String,List<Cond>> result=new HashMap<String,List<Cond>>();
		
		List<PubCode> spclnos=pubCodeController.query4Combo("5", spclno, bradno, "1", false, null);
		List<Cond> cond_spseno=new ArrayList<Cond>();
		for(PubCode pubcode:spclnos){
			Cond cond=new Cond();
			cond.setValue(pubcode.getItno());
			cond.setName(pubcode.getItnm());
			cond_spseno.add(cond);
		}
		result.put("cond_spseno", cond_spseno);
		return result;
	}
	/**
	 * 获取订货单位,获取当前职位可以看到的所有的订货单位，如果营销公司进来看到什么？大区进来看到什么？
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/mobile/report/queryOrdorgCondition.do")
	@ResponseBody
	public Map<String,Object> queryOrdorgCondition(){
		
		Map<String,Object> result=new HashMap<String,Object>();
		//获取当前登录用户可以看到的订货单位
		
		
		List<Map<String,Object>> ordorges=mobileReportRepository.queryOrdorgCondition(ContextUtils.getFirstOrdmt().getOrmtno(), ShiroUtils.getUserId());
		List<Cond> cond_ordorg=new ArrayList<Cond>();
		for(Map<String,Object> map:ordorges){
			Cond cond=new Cond();
			cond.setValue(map.get("ORGNO").toString());
			cond.setName(map.get("ORGNM").toString());
//			//默认选择
//			if(cond.getValue().equals(orgno)){
//				cond.setSelected(true);
//			}
//			
			cond_ordorg.add(cond);
		}
		result.put("cond_ordorg", cond_ordorg);
		//String orgno=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
		//result.put("sel_ordorg", orgno);//默认值
		return result;
	}
	
	@RequestMapping("/mobile/report/queryReportAlreadyOd.do")
	@ResponseBody
	public Map<String,Object> queryReportAlreadyOd(String bradno,String spclno,String sptyno,String spseno,Integer start,Integer limit){
	
		Pager<AlreadyOd> pager=new Pager<AlreadyOd>();
		pager.addParam("ormtno", ContextUtils.getFirstOrdmt().getOrmtno());
		pager.addParam("bradno", bradno);
		pager.addParam("spclno", spclno);
		pager.addParam("sptyno", sptyno);
		pager.addParam("spseno", spseno);
		Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		pager.addParam("channo", org.getChanno().toString());
		pager.addParam("ordorg", org.getOrgno());
		
		pager.setStart(start);
		pager.setLimit(limit);
		pager=mobileReportRepository.queryReportAlreadyOd(pager);
		
		//封装成界面需要的两列方式
		List<AlreadyOd> list=pager.getRoot();
		List<AlreadyOd[]> result=new ArrayList<AlreadyOd[]>();
		for(int i=0;i<list.size();i++){
			
			if(i%2==0){
				//Map<String,AlreadyOd> map=new HashMap<String,AlreadyOd>();
				//map.put("sampno1", list.get(i));
				AlreadyOd samp0=list.get(i);
				samp0.setRownum(start+i+1);
				
				AlreadyOd[] tow=new AlreadyOd[2];
				tow[0]=samp0;
				if(i+1<list.size()){
					AlreadyOd samp1=list.get(i+1);
					samp1.setRownum(start+i+1+1);
					tow[1]=samp1;
				} else {
					tow=new AlreadyOd[1];
					tow[0]=samp0;
				}
				result.add(tow);
			}
		}
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("root", result);
		map.put("numPage", list.size());
		map.put("total", pager.getTotal());
		
		//获取品种数等数据
		List<Map<String,Object>> totalData_list=mobileReportRepository.queryReportAlreadyOd_totalData((Map<String,Object>)pager.getParams());
		if(totalData_list==null || totalData_list.size()==0){
			Map<String,Object> totalData=new HashMap<String,Object>();
			totalData.put("ormtqt", 0);
			totalData.put("ormtam", 0);
			totalData.put("sampnocount", 0);
			map.put("totalData", totalData);
		} else {
			Map<String,Object> totalData=new HashMap<String,Object>();
			totalData.put("ormtqt", totalData_list.get(0).get("ORMTQT"));
			totalData.put("ormtam", totalData_list.get(0).get("ORMTAM"));
			totalData.put("sampnocount", totalData_list.get(0).get("SAMPNOCOUNT"));
			map.put("totalData", totalData);
		}
		
		
		return map;
		
		
	}
	
	
	@RequestMapping("/mobile/report/queryReportSplcno.do")
	@ResponseBody
	public List<ReportSplcno> queryReportSplcno(String bradno,String ordorg){
		if(ordorg==null || "".equals(ordorg)){
			ordorg=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
		}
		Org org=orgService.get(ordorg);
		List<ReportSplcno> list=null;
		if(org.getChanno()==Chancl.TX){
			list= mobileReportRepository.queryReportSplcno_TX(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,  ShiroUtils.getUserId());
		} else if(org.getChanno()==Chancl.QY){
			list= mobileReportRepository.queryReportSplcno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,  ShiroUtils.getUserId());
		} else if(org.getChanno()==Chancl.YXGS) {
			list= mobileReportRepository.queryReportSplcno_YXGS(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,  ShiroUtils.getUserId());
		} else {
			list= mobileReportRepository.queryReportSplcno_ALL(ContextUtils.getFirstOrdmt().getOrmtno(), bradno,  ShiroUtils.getUserId());
		}
		
		//计算合计
		ReportSplcno total=new ReportSplcno();
		total.setSpclno("total");
		total.setSpclnm("合计:");
		for(ReportSplcno spclno:list){
			total.addOrmtam(spclno.getOrmtam());
			total.addQymtam(spclno.getQymtam());
		}
		list.add(total);
		return list;
		
	}
	
	@RequestMapping("/mobile/report/queryReportMoney.do")
	@ResponseBody
	public List<ReportMoney> queryReportMoney(String bradno,String spclno,String sptyno,String spseno){
		//Org org=orgService.get(ordorg);
		//String orgno=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
		Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		String orgno=org.getOrgno();
		List<ReportMoney> list=null;
		if(org.getChanno()==Chancl.TX){
			list= mobileReportRepository.queryReportMoney_TX(ContextUtils.getFirstOrdmt().getOrmtno(), orgno, bradno, 
					spclno, sptyno, spseno, ShiroUtils.getUserId());
		} else if(org.getChanno()==Chancl.QY){
			list= mobileReportRepository.queryReportMoney_QY(ContextUtils.getFirstOrdmt().getOrmtno(), orgno, bradno, 
					spclno, sptyno, spseno, ShiroUtils.getUserId());
		} else if(org.getChanno()==Chancl.YXGS) {
			list= mobileReportRepository.queryReportMoney_YXGS(ContextUtils.getFirstOrdmt().getOrmtno(), orgno, bradno, 
					spclno, sptyno, spseno, ShiroUtils.getUserId());
		} else {
			list= mobileReportRepository.queryReportMoney_ALL(ContextUtils.getFirstOrdmt().getOrmtno(), bradno, 
					spclno, sptyno, spseno, ShiroUtils.getUserId());
		}
		//计算合计
		ReportMoney total = new ReportMoney();
		total.setOrmtam_zb(new BigDecimal(1));
		total.setOrmtqt_zb(new BigDecimal(1));
		total.setSampnocount_zb(new BigDecimal(1));
		total.setSprtpr("合计:");
		for (ReportMoney aaa : list) {
			total.addOrmtam(aaa.getOrmtam());
			total.addOrmtqt(aaa.getOrmtqt());
			total.addSampnocount(aaa.getSampnocount());
		}
		list.add(total);
		return list;
	}
	
	@RequestMapping("/mobile/report/queryReportOrg.do")
	@ResponseBody
	public List<ReportOrg> queryReportOrg(String bradno,String spclno,String sptyno,String spseno){
		Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		String orgno=org.getOrgno();
		List<ReportOrg> list=null;
		if(org.getChanno()==Chancl.YXGS) {
			list= mobileReportRepository.queryReportOrg_YXGS(ContextUtils.getFirstOrdmt().getOrmtno(), orgno, bradno, spclno, sptyno, spseno);
		} else {
			list= mobileReportRepository.queryReportOrg_ALL(ContextUtils.getFirstOrdmt().getOrmtno(), bradno, spclno, sptyno, spseno);
		}
		// 计算合计
		ReportOrg total = new ReportOrg();
		total.setOrgno("total");
		total.setOrgnm("合计:");
		for (ReportOrg aaa : list) {
			total.addOrmtam(aaa.getOrmtam());
			total.addQymtam(aaa.getQymtam());
		}
		list.add(total);
		return list;
		
	}
	
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sanmpno 样衣编号
	 * @param sampnm1 出样样衣编号
	 * @return
	 */
	@RequestMapping("/mobile/report/querySampleInfo.do")
	@ResponseBody
	public List<Map<String,Object>> querysampleInfo(String sampno,String sampnm1){
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(sampno!=null && !"".equals(sampno)){
			list.add(querysampleInfoBySampno(sampno));
		}
		
		if(sampnm1!=null && !"".equals(sampnm1)){
			List<String> sampnos=sampleDesignRepository.querySampnoBySampnm1(sampnm1);
			for(String _sampno:sampnos){
				list.add(querysampleInfoBySampno(_sampno));
			}
		}
		
		return list;
		
	}
	
	private Map<String ,Object> querysampleInfoBySampno(String sampno){
		Map<String,Object> result=new HashMap<String,Object>();
		//获取设计样衣信息
		SampleDesign sampleDesign=sampleDesignService.get(sampno);
		List<SampleInfoField> sampleDesignFields=new ArrayList<SampleInfoField>();
		SampleInfoField sampleInfoField=new SampleInfoField("出样样衣编号",sampleDesign.getSampnm1());
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("订货样衣编号",sampleDesign.getSampnm());
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("版型",PubCodeCache.getVersno_name(sampleDesign.getVersno()));
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("工作室系列",PubCodeCache.getStseno_name(sampleDesign.getStseno()));
		sampleDesignFields.add(sampleInfoField);
		if(ContextUtils.getSjs(sampleDesign.getDesgno())!=null){
			sampleInfoField=new SampleInfoField("设计师",ContextUtils.getSjs(sampleDesign.getDesgno()).getName());
		}
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("生产类型",PubCodeCache.getSpmtno_name(sampleDesign.getSpmtno()));
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("颜色",PubCodeCache.getColrno_name(sampleDesign.getColrno()));
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("花型",PubCodeCache.getPattno_name(sampleDesign.getPattno()));
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("款式",PubCodeCache.getStylno_name(sampleDesign.getStylno()));
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("款式组",sampleDesign.getStylgp());
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("性别",PubCodeCache.getSexno_name(sampleDesign.getSexno()));
		sampleDesignFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("长短袖",PubCodeCache.getSlveno_name(sampleDesign.getSlveno()));
		sampleDesignFields.add(sampleInfoField);
		result.put("tab2", sampleDesignFields);

		//获取企划样衣信息
		SamplePlan samplePlan=samplePlanService.get(sampleDesign.getPlspno());
		List<SampleInfoField> samplePlanFields=new ArrayList<SampleInfoField>();
		sampleInfoField=new SampleInfoField("企划样衣编号",samplePlan.getPlspnm());
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("品牌",PubCodeCache.getBradno_name(samplePlan.getBradno()));
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("年份",samplePlan.getSpyear());
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("季节",PubCodeCache.getSpsean_name(samplePlan.getSpsean()));
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("大系列",PubCodeCache.getSpbseno_name(samplePlan.getSpbseno()));
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("品牌系列",PubCodeCache.getSprseno_name(samplePlan.getSprseno()));
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("大类",PubCodeCache.getSpclno_name(samplePlan.getSpclno()));
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("小类",PubCodeCache.getSptyno_name(samplePlan.getSptyno()));
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("系列",PubCodeCache.getSpseno_name(samplePlan.getSpseno()));
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("定位",PubCodeCache.getSplcno_name(samplePlan.getSplcno()));
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("上市批次",PubCodeCache.getSpbano_name(samplePlan.getSpbano()));
		samplePlanFields.add(sampleInfoField);
		sampleInfoField=new SampleInfoField("零售价",samplePlan.getSprtpr()+"");
		samplePlanFields.add(sampleInfoField);
		result.put("tab1", samplePlanFields);
		
		//设置全局的定后样衣编号
		result.put("sampnm", sampleDesign.getSampnm());
		//获取样衣图片
		List<SamplePhoto> photoes=samplePhotoRepository.queryBySampno(sampno);
		result.put("photoes", photoes);
		
		return result;
	}

}
