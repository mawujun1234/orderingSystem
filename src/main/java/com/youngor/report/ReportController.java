package com.youngor.report;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;

@Controller
public class ReportController {
	@Autowired
	private ReportRepository reportRepository;
	
	@RequestMapping("/report/queryClothPurePlan.do")
	@ResponseBody
	public  Pager<Map<String,Object>> queryClothPurePlan( Pager<Map<String,Object>> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= reportRepository.queryClothPurePlan(pager);
		List<Map<String,Object>> list=pager.getRoot();

		return pager;
	}
	
	@RequestMapping("/report/queryMatePurePlan.do")
	@ResponseBody
	public  Pager<Map<String,Object>> queryMatePurePlan( Pager<Map<String,Object>> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= reportRepository.queryMatePurePlan(pager);
		List<Map<String,Object>> list=pager.getRoot();

		return pager;
	}
	/**
	 * 双击面料计划表的的时候，弹出框
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param htitno
	 * @return
	 */
	public  List<Map<String,Object>> query_mate_podtl(String ormtno,String htitno) {
		return reportRepository.query_mate_podtl(ormtno, htitno);
	}

}
