package com.youngor.report.mobile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.utils.page.Pager;
import com.youngor.order.OrdService;
import com.youngor.org.Chancl;
import com.youngor.org.Dim;
import com.youngor.org.NodeVO;
import com.youngor.org.Org;
import com.youngor.org.OrgService;
import com.youngor.permission.ShiroUtils;
import com.youngor.permission.UserController;
import com.youngor.pubcode.PubCode;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.pubcode.PubCodeRepository;
import com.youngor.pubcode.PubCodeService;
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
	//@Autowired
	//private PubCodeController pubCodeController;
	@Autowired
	private PubCodeRepository pubCodeRepository;
	@Autowired
	private PubCodeService pubCodeService;
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
	@Resource
	private UserController userController;
	
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
//			if(ShiroUtils.isLogon()){
//				bradno=ContextUtils.getFirstBradno();
//			} else {
//				bradno="Y";
//			}
			bradno="Y";
		}
		Map<String,Object> result=new HashMap<String,Object>();
		
		//获取品牌，并且在展示的时候，默认显示第一个品牌
		List<PubCode> bradnos=pubCodeRepository.queryBradno4Ordmt(ContextUtils.getFirstOrdmt().getOrmtno());//pubCodeController.query4Combo("1", null, bradno, "1", false, null);
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
			//bradno=ContextUtils.getFirstBradno();
			bradno="Y";
		}
		Map<String,List<Cond>> result=new HashMap<String,List<Cond>>();
		
		//List<PubCode> spclnos=pubCodeController.query4Combo("0", null, bradno, "1", false, null);
		List<PubCode> spclnos=pubCodeService.query("0",null, bradno,"1",null);
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
			//bradno=ContextUtils.getFirstBradno();
			bradno="Y";
		}
		Map<String,List<Cond>> result=new HashMap<String,List<Cond>>();
		
		//List<PubCode> spclnos=pubCodeController.query4Combo("2", spclno, bradno, "1", false, null);
		List<PubCode> spclnos=pubCodeService.query("2",spclno, bradno,"1",null);
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
			//bradno=ContextUtils.getFirstBradno();
			bradno="Y";
		}
		Map<String,List<Cond>> result=new HashMap<String,List<Cond>>();
		
		//List<PubCode> spclnos=pubCodeController.query4Combo("5", spclno, bradno, "1", false, null);
		List<PubCode> spclnos=pubCodeService.query("5",spclno, bradno,"1",null);
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
		
		//如果用户没有登录，那就设置成，可以选择营销公司
		if(!ShiroUtils.isLogon()){
			List<NodeVO> orges=orgService.queryOnlyOrg("root", Dim.SALE);
			List<Cond> cond_ordorg=new ArrayList<Cond>();
			for(NodeVO map:orges){
				Cond cond=new Cond();
				cond.setValue(map.getOrgno());
				cond.setName(map.getName());
				cond_ordorg.add(cond);
			}
			result.put("cond_ordorg", cond_ordorg);
			return result;
		}
		
		//获取当前登录用户可以看到的订货单位
		List<Map<String,Object>> ordorges=mobileReportRepository.queryOrdorgCondition(ContextUtils.getFirstOrdmt().getOrmtno(), ShiroUtils.getUserId());
		List<Cond> cond_ordorg=new ArrayList<Cond>();
		for(Map<String,Object> map:ordorges){
			Cond cond=new Cond();
			cond.setValue(map.get("ORGNO").toString());
			cond.setName(map.get("ORGNM").toString());
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
		//Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		Org org=null;
		if(!ShiroUtils.isLogon()){
			org=new Org();
			org.setChanno(Chancl.OTH);
			org.setOrgnm("全国");
			
		} else {
			org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		}
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
//		if(ordorg==null || "".equals(ordorg)){
//			//ordorg=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
//		}
		Org org=null;
		if(!ShiroUtils.isLogon()){
			org=new Org();
			org.setChanno(Chancl.OTH);
			org.setOrgnm("全国");
			if(ordorg!=null && !"".equals(ordorg)){
				//主要是营销公司的时候，在前端01会变成1，先暂时这样，等以后改
				if(ordorg.length()==1){
					ordorg="0"+ordorg;
				}
				org=orgService.get(ordorg);
			}
			
		} else {
			
			if(ordorg==null || "".equals(ordorg)){
				org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
				ordorg=org.getOrgno();//ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
			} else {
				org=orgService.get(ordorg);
			}
		}
		//Org 
		List<ReportSplcno> list=null;
		if(org.getChanno()==Chancl.TX || org.getChanno()==Chancl.ZY){
			list= mobileReportRepository.queryReportSplcno_TX(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,org.getChanno().toString());
		} else if(org.getChanno()==Chancl.QY){
			list= mobileReportRepository.queryReportSplcno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,Chancl.QY.toString());
		} else if(org.getChanno()==Chancl.YXGS) {
			
			if("Y".equals(bradno)){
				list= mobileReportRepository.queryReportSplcno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,Chancl.YXGS.toString());
			} else {
				list= mobileReportRepository.queryReportSplcno_other_bradno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,Chancl.YXGS.toString());
			}
		} else {
			
			if("Y".equals(bradno)){
				list= mobileReportRepository.queryReportSplcno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg,bradno,null);
			} else {
				list= mobileReportRepository.queryReportSplcno_other_bradno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg,bradno,null);
			}
		}
		
		//计算合计
		ReportSplcno total=new ReportSplcno();
		total.setSpclno("total");
		total.setSpclnm("合计:");
		for(ReportSplcno spclno:list){
			total.addOrmtam(spclno.getOrmtam());
			total.addQymtam(spclno.getQymtam());
			total.addOrmtqt(spclno.getOrmtqt());
			total.addQymtqt(spclno.getQymtqt());
		}
		list.add(total);
		return list;
		
	}
	
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param bradno
	 * @param ordorg
	 * @param spclno
	 * @param tyno 2:小类  5：系列
	 * @return
	 */
	@RequestMapping("/mobile/report/queryReportSptyno.do")
	@ResponseBody
	public List<ReportSplcno> queryReportSptyno(String bradno,String ordorg,String spclno){
		if(spclno==null || "".equals(spclno)){
			throw new BusinessException("请先选择一个大类!");
		}
		spclno=spclno.substring(1);
		
		
		Org org=null;
		if(!ShiroUtils.isLogon()){
			org=new Org();
			org.setChanno(Chancl.OTH);
			org.setOrgnm("全国");
			if(ordorg!=null && !"".equals(ordorg)){
				//主要是营销公司的时候，在前端01会变成1，先暂时这样，等以后改
				if(ordorg.length()==1){
					ordorg="0"+ordorg;
				}
				org=orgService.get(ordorg);
			}
			
		} else {
			
			if(ordorg==null || "".equals(ordorg)){
				org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
				ordorg=org.getOrgno();//ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
			} else {
				org=orgService.get(ordorg);
			}
		}
		//Org 
		List<ReportSplcno> list=null;
		if(org.getChanno()==Chancl.TX || org.getChanno()==Chancl.ZY){
			list= mobileReportRepository.queryReportSptyno_TX(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,spclno,org.getChanno().toString());
		} else if(org.getChanno()==Chancl.QY){
			list= mobileReportRepository.queryReportSptyno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,spclno,Chancl.QY.toString());
		} else if(org.getChanno()==Chancl.YXGS) {
			list= mobileReportRepository.queryReportSptyno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,spclno,Chancl.YXGS.toString());
		} else {
			list= mobileReportRepository.queryReportSptyno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg,bradno,spclno,org.getChanno().toString());
		}
		
		//计算合计
		ReportSplcno total=new ReportSplcno();
		total.setSpclno("total");
		total.setSpclnm("合计:");
		for(ReportSplcno reportSplcno:list){
			total.addOrmtam(reportSplcno.getOrmtam());
			total.addQymtam(reportSplcno.getQymtam());
			total.addOrmtqt(reportSplcno.getOrmtqt());
			total.addQymtqt(reportSplcno.getQymtqt());
		}
		list.add(total);
		return list;
		
	}
	/**
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param bradno
	 * @param ordorg
	 * @param spclno
	 * @param tyno 2:小类  5：系列
	 * @return
	 */
	@RequestMapping("/mobile/report/queryReportSpseno.do")
	@ResponseBody
	public List<ReportSplcno> queryReportSpseno(String bradno,String ordorg,String spclno){
		if(spclno==null || "".equals(spclno)){
			throw new BusinessException("请先选择一个大类!");
		}
		spclno=spclno.substring(1);
		
		
		Org org=null;
		if(!ShiroUtils.isLogon()){
			org=new Org();
			org.setChanno(Chancl.OTH);
			org.setOrgnm("全国");
			if(ordorg!=null && !"".equals(ordorg)){
				//主要是营销公司的时候，在前端01会变成1，先暂时这样，等以后改
				if(ordorg.length()==1){
					ordorg="0"+ordorg;
				}
				org=orgService.get(ordorg);
			}
			
		} else {
			
			if(ordorg==null || "".equals(ordorg)){
				org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
				ordorg=org.getOrgno();//ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
			} else {
				org=orgService.get(ordorg);
			}
		}
		//Org 
		List<ReportSplcno> list=null;
		if(org.getChanno()==Chancl.TX || org.getChanno()==Chancl.ZY){
			list= mobileReportRepository.queryReportSpseno_TX(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,spclno,org.getChanno().toString());
		} else if(org.getChanno()==Chancl.QY){
			list= mobileReportRepository.queryReportSpseno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,spclno,Chancl.QY.toString());
		} else if(org.getChanno()==Chancl.YXGS) {
			list= mobileReportRepository.queryReportSpseno(ContextUtils.getFirstOrdmt().getOrmtno(), ordorg, bradno,spclno,Chancl.YXGS.toString());
		} else {
			list= mobileReportRepository.queryReportSpseno(ContextUtils.getFirstOrdmt().getOrmtno(),ordorg, bradno,spclno,null);
		}
		
		//计算合计
		ReportSplcno total=new ReportSplcno();
		total.setSpclno("total");
		total.setSpclnm("合计:");
		for(ReportSplcno reportSplcno:list){
			total.addOrmtam(reportSplcno.getOrmtam());
			total.addQymtam(reportSplcno.getQymtam());
			total.addOrmtqt(reportSplcno.getOrmtqt());
			total.addQymtqt(reportSplcno.getQymtqt());
		}
		list.add(total);
		return list;
		
	}
	
	@RequestMapping("/mobile/report/queryReportMoney.do")
	@ResponseBody
	public List<ReportMoney> queryReportMoney(String bradno,String spclno,String sptyno,String spseno){
		//Org org=orgService.get(ordorg);
		//String orgno=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
		//Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		Org org=null;
		if(!ShiroUtils.isLogon()){
			org=new Org();
			org.setChanno(Chancl.OTH);
			org.setOrgnm("全国");
			
		} else {
			org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		}
		String orgno=org.getOrgno();
		List<ReportMoney> list=null;
		if(org.getChanno()==Chancl.TX || org.getChanno()==Chancl.ZY){
			list= mobileReportRepository.queryReportMoney(ContextUtils.getFirstOrdmt().getOrmtno(), orgno, bradno, 
					spclno, sptyno, spseno,org.getChanno().toString());
		} else if(org.getChanno()==Chancl.QY){
			list= mobileReportRepository.queryReportMoney(ContextUtils.getFirstOrdmt().getOrmtno(), orgno, bradno, 
					spclno, sptyno, spseno,org.getChanno().toString());
		} else if(org.getChanno()==Chancl.YXGS) {
			list= mobileReportRepository.queryReportMoney(ContextUtils.getFirstOrdmt().getOrmtno(), orgno, bradno, 
					spclno, sptyno, spseno,org.getChanno().toString());
		} else {
			list= mobileReportRepository.queryReportMoney(ContextUtils.getFirstOrdmt().getOrmtno(), orgno, bradno, 
					spclno, sptyno, spseno,null);
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
		//Org org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		Org org=null;
		if(!ShiroUtils.isLogon()){
			org=new Org();
			org.setChanno(Chancl.OTH);
			org.setOrgnm("全国");
			
		} else {
			org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		}
		String orgno=org.getOrgno();
		List<ReportOrg> list=null;
		if(org.getChanno()==Chancl.YXGS) {
			
				list= mobileReportRepository.queryReportOrg_YXGS(ContextUtils.getFirstOrdmt().getOrmtno(), orgno, bradno, spclno, sptyno, spseno);
			
			
		} else {
			
			if("Y".equals(bradno)){
				list= mobileReportRepository.queryReportOrg_ALL(ContextUtils.getFirstOrdmt().getOrmtno(), bradno, spclno, sptyno, spseno);
			} else {
				list= mobileReportRepository.queryReportOrg_ALL_other_bradno(ContextUtils.getFirstOrdmt().getOrmtno(), bradno, spclno, sptyno, spseno);
			}
		}
		// 计算合计
		ReportOrg total = new ReportOrg();
		total.setOrgno("total");
		total.setOrgnm("合计:");
		for (ReportOrg aaa : list) {
			total.addOrmtam(aaa.getOrmtam());
			total.addQymtam(aaa.getQymtam());
			total.addOrmtqt(aaa.getOrmtqt());
			total.addQymtqt(aaa.getQymtqt());
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
	public Map<String,Object> querysampleInfo(String sampno,String sampnm1){
		
		Map<String,Object> result=new HashMap<String,Object>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(sampno!=null && !"".equals(sampno)){
			list.add(querysampleInfoBySampno(sampno,true));
			result.put("title", list.get(0).get("sampnm"));
		}
		
		if(sampnm1!=null && !"".equals(sampnm1)){
			List<String> sampnos=sampleDesignRepository.querySampnoBySampnm1(sampnm1);
			for(String _sampno:sampnos){
				list.add(querysampleInfoBySampno(_sampno,false));
			}
			result.put("title", sampnm1);
		}
		
		
		result.put("todos", list);
		
		return result;
		
	}
	
	private Map<String ,Object> querysampleInfoBySampno(String sampno,boolean bool){
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
		
		if(bool){
			//获取样衣信息的时候，同时展现区域汇总数据
			if(ShiroUtils.isLogon()){
				List<SampleInfoField> list=mobileReportRepository.queryOrmtqt_sum_by_sampno(sampno,ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno());
				result.put("tab3", list);
			} else {
				List<SampleInfoField> list=mobileReportRepository.queryOrmtqt_sum_by_sampno(sampno,null);
				result.put("tab3", list);
			}
			
		}

		return result;
	}

	@RequestMapping("/mobile/redirect_yxgshtml.do")
	public void redirect(HttpServletRequest request,HttpServletResponse response) throws IOException{
		userController.logout();
		response.sendRedirect("./yxgs.html");
	}
	@RequestMapping("/mobile/report/queryReportFirst_allBradno.do")
	@ResponseBody
	public Map<String,Object> queryReportFirst_allBradno(String from){
		Org org=null;
		if(!ShiroUtils.isLogon()){
			org=new Org();
			org.setChanno(Chancl.OTH);
			org.setOrgnm("全国");
			
		} else {
			org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
			
//			//如果不是全国的人登录了，总公司报表，那就退出，可以看到总公司报表
//			//如果是营销公司，那还是可以看到报表，并且和营销公司的账号是一样的
//			if(org.getChanno()!=Chancl.YXGS){
////				Map<String,Object> result=new HashMap<String,Object>();
////				result.put("success", false);
////				result.put("msg", "你已经登录其他账号，没有权限访问该报表!");
////				return result;
//				
//				//如果是其他用户访问总公司报表，那也就退出
//				userController.logout();
//				
//				org=new Org();
//				org.setChanno(Chancl.OTH);
//				org.setOrgnm("全国");
//			} else {
//				//如果是营销公司访问，总公司报表,那也退出，可以访问全国报表
//				//也就是说，大区账号不是通过登录访问这张报表的时候
//				if(!"index".equals(from)){
//					userController.logout();
//					
//					org=new Org();
//					org.setChanno(Chancl.OTH);
//					org.setOrgnm("全国");
//				}
//			}
			
			
		}

		
		List<ReportFirst> list=mobileReportRepository.queryReportFirst_allBradno(ContextUtils.getFirstOrdmt().getOrmtno(), 
				org.getChanno().toString(), org.getOrgno(),null);
		//if(list==null || list.size()==0){
		//	list=new ArrayList<ReportFirst>();
			//获取全国的有样衣的品牌
			//这个是临时解决方案
			List<PubCode> bradnos=pubCodeRepository.queryBradno4Ordmt(ContextUtils.getFirstOrdmt().getOrmtno());
			for(PubCode pubcode:bradnos){
				boolean exists=false;
				for(ReportFirst first_exist:list){
					if(first_exist.getBradno().equals(pubcode.getItno())){
						exists=true;
						break;
					}
				}
				if(!exists){
					ReportFirst first=new ReportFirst();
					first.setBradno(pubcode.getItno());
					list.add(first);
				}
				
			}
			
		//}
		//return list;
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("todos", list);
		result.put("orgnm", org.getOrgnm());
		return result;
	}
	@RequestMapping("/mobile/report/queryReportFirst_bradno.do")
	@ResponseBody
	public Map<String,BigDecimal> queryReportFirst_bradno(String bradno){
		Org org=null;
		if(!ShiroUtils.isLogon()){
			org=new Org();
			org.setChanno(Chancl.OTH);
		} else {
			org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		}
		
		Map<String,BigDecimal> result=new HashMap<String,BigDecimal>();
		//获取指定品牌的订货量
		List<ReportFirst> aaa=mobileReportRepository.queryReportFirst_allBradno(ContextUtils.getFirstOrdmt().getOrmtno(), 
				org.getChanno().toString(), org.getOrgno(),bradno);
		if(aaa!=null && aaa.size()>0){
			ReportFirst first=aaa.get(0);
			result.put("ormtqt", first.getOrmtqt());
			result.put("ormtam", first.getOrmtam());
		}
		
		
		
		//获取指定品牌的指标数量
		Map<String,BigDecimal> bbb=new HashMap<String,BigDecimal>();
		if("Y".equals(bradno)){
			bbb=mobileReportRepository.queryReportFirst_Y(ContextUtils.getFirstOrdmt().getOrmtno(), org.getChanno().toString(), org.getOrgno());
			result.put("qymtqt", bbb.get("QYMTQT"));
			result.put("qymtam", bbb.get("QYMTAM"));
		} else if(bradno!=null && !"".equals(bradno)){
			bbb=mobileReportRepository.queryReportFirst_other_bradno(ContextUtils.getFirstOrdmt().getOrmtno(), org.getChanno().toString(),
					org.getOrgno(),bradno);
			if(bbb!=null){
				result.put("qymtqt", bbb.get("PLMTQT"));
				result.put("qymtam", bbb.get("PLMTAM"));
			} else {
				result.put("qymtqt", new BigDecimal(0));
				result.put("qymtam", new BigDecimal(0));
			}
			
		}
		return result;
	}
	/**
	 * 在移动端 查询搭配信息
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/mobile/report/queryDapei.do")
	@ResponseBody
	public List<ReportDapei> queryDapei(String ordorg){
		List<ReportDapei> result=new ArrayList<ReportDapei>();
//		
//		for(int i=0;i<10;i++){
//			ReportDapei reportDapei=new ReportDapei();
//			reportDapei.setClppnm("商旅VP秋1左"+i);
//			reportDapei.setImgnm("./images/no_photo.jpg");
//			reportDapei.setNum(i*10+1);
//			for(int j=0;j<4;j++){
//				ReportDapeiList reportDapeiList=new ReportDapeiList();
//				reportDapeiList.setImgnm("./images/no_photo.jpg");
//				reportDapeiList.setSampnm("CLOO"+i);
//				reportDapeiList.setNum(j*10+1);
//				reportDapei.addList(reportDapeiList);
//			}
//			result.add(reportDapei);
//		}
		String ormtno=ContextUtils.getFirstOrdmt().getOrmtno();
		if(ordorg==null || "".equals(ordorg)){
			ordorg=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
		}
		 
		List<ReportDapei> list=mobileReportRepository.queryDapei(ormtno, ordorg);
		//计算并过滤掉最少套数为0的数据
		for(ReportDapei aa:list){
			aa.calMinnnum();
			if(aa.getMinnum()!=0){
				result.add(aa);
			}
		}
		//排序，套件最多的排在前面
		Collections.sort(result);
		return result;
	}
	/**
	 * 营销公司获取搭配报表的时候
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/mobile/report/queryDapei_yxgs.do")
	@ResponseBody
	public List<ReportDapei_yxgs> queryDapei_yxgs(){
		List<ReportDapei_yxgs> result=new ArrayList<ReportDapei_yxgs>();
		//获取当前营销公司下面的区域
		Org yxgs_org=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg();
		List<Map<String,Object>> qyes=mobileReportRepository.queryQY(yxgs_org.getOrgno());
		
		//ReportDapei_yxgs reportDapei_yxgs=new ReportDapei_yxgs();
		for(Map<String,Object> qy_map:qyes){
			List<ReportDapei> list=queryDapei(qy_map.get("QYNO").toString());
			for(ReportDapei reportDapei:list){
				ReportDapei_yxgs reportDapei_yxgs=null;
				for(ReportDapei_yxgs yxgs:result){
					if(yxgs.getClppnm().equals(reportDapei.getClppnm())){
						reportDapei_yxgs=yxgs;
						break;
					}
				}
				if(reportDapei_yxgs==null){
					reportDapei_yxgs=new ReportDapei_yxgs();
					reportDapei_yxgs.setClppnm(reportDapei.getClppnm());
					reportDapei_yxgs.setImgnm(reportDapei.getImgnm());
					result.add(reportDapei_yxgs);
					
				}
				reportDapei_yxgs.addSumnum(reportDapei.getMinnum());
				
				ReportDapei_qy qy=new ReportDapei_qy();
				qy.setClppnm(reportDapei.getClppnm());
				qy.setImgnm(reportDapei.getImgnm());
				qy.setMinnum(reportDapei.getMinnum());
				qy.setQynm(qy_map.get("QYNM").toString());
				qy.setQyno(qy_map.get("QYNO").toString());
				
				reportDapei_yxgs.addQyes(qy);
				
			}
			
		}
		return result;
	}
	@RequestMapping("/mobile/report/queryDapei_yxgs_list.do")
	@ResponseBody
	public List<ReportDapeiList> queryDapei_yxgs_list(String ordorg,String clppnm) {
		String ormtno=ContextUtils.getFirstOrdmt().getOrmtno();
		if(ordorg==null || "".equals(ordorg)){
			ordorg=ShiroUtils.getAuthenticationInfo().getFirstCurrentOrg().getOrgno();
		}
		
		return mobileReportRepository.queryDapei_yxgs_list(ormtno, ordorg, clppnm);
	}

}
