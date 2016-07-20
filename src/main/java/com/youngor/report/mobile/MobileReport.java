package com.youngor.report.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youngor.pubcode.PubCode;
import com.youngor.pubcode.PubCodeController;
import com.youngor.utils.ContextUtils;

@Controller
public class MobileReport {
	@Autowired
	private PubCodeController pubCodeController;
	
	@RequestMapping("/mobile/report/queryBradnoCondition.do")
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
			if(bradno.equals(cond.getValue())){
				cond.setSelected(true);
			}
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

}
