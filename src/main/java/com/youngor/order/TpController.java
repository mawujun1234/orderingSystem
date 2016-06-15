package com.youngor.order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
import com.youngor.ordmt.Ordmt;
import com.youngor.org.Org;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ord")
public class TpController {

	@Resource
	private TpService tpService;

	/**
	 * 查询某个样衣的信息
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param id
	 * @return
	 */
	@RequestMapping("/tp/tpAllQuery.do")
	@ResponseBody
	public  Pager<Map<String,Object>> tpAllQuery( Pager<Map<String,Object>> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= tpService.tpAllQuery(pager);
		List<Map<String,Object>> list=pager.getRoot();
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return pager;
	}
	
	@RequestMapping("/tp/queryTpYxgsColumns.do")
	@ResponseBody
	public  List<Map<String,Object>> queryTpYxgsColumns() {
		return tpService.queryTpYxgsColumns();
	}
	@RequestMapping("/tp/tpYxgsQuery.do")
	@ResponseBody
	public  Pager<Map<String,Object>> tpYxgsQuery( Pager<Map<String,Object>> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= tpService.tpAllQuery(pager);
		List<Map<String,Object>> list=pager.getRoot();
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return pager;
	}
	
	
	@RequestMapping("/tp/queryTpQyColumns.do")
	@ResponseBody
	public  List<Map<String,Object>> queryTpQyColumns(String yxgsno) {
		return tpService.queryTpQyColumns(yxgsno);
	}
	
	@RequestMapping("/tp/tpQyQuery.do")
	@ResponseBody
	public  Pager<Map<String,Object>> tpQyQuery( Pager<Map<String,Object>> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= tpService.tpQyQuery(pager);
		List<Map<String,Object>> list=pager.getRoot();
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return pager;
	}
	
	
}
