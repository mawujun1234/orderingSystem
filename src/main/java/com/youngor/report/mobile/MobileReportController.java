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

import com.youngor.order.OrdService;
import com.youngor.org.Chancl;
import com.youngor.org.Org;
import com.youngor.org.OrgService;
import com.youngor.permission.ShiroUtils;
import com.youngor.pubcode.PubCode;
import com.youngor.pubcode.PubCodeController;
import com.youngor.sample.SampleDesign;
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

}
