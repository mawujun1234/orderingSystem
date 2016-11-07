package com.youngor.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youngor.permission.ShiroUtils;

@Controller
public class GzsOrderInsertController {
	@Autowired
	private GzsOrderInsertService gzsOrderInsertService;
	
	@RequestMapping("/gzsorderinsert/queryColumns.do")
	@ResponseBody
	public List<Map<String,Object>> queryColumns(String sizegp) {
		return gzsOrderInsertService.queryColumns(sizegp);
	}
	
	@RequestMapping("/gzsorderinsert/queryData.do")
	@ResponseBody
	public List<Map<String,Object>> queryData(@RequestBody Map<String,Object> params) {
		return gzsOrderInsertService.queryData(params);
	}
	
	@RequestMapping("/gzsorderinsert/getSzstat.do")
	@ResponseBody
	public Map<String,Object> getSzstat(@RequestBody Map<String,Object> params){
		
		Integer szstat= gzsOrderInsertService.getSzstat( params);
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("success", true);
		result.put("szstat", szstat);
		return result;
		//return "{success:true,szstat:"+szstat+"}";
	}
	
	@RequestMapping("/gzsorderinsert/updateOrdszdtl.do")
	@ResponseBody
	public String updateOrdszdtl(String ormtno,String ordorg,String ortyno,String sampno,String bradno,String spclno,String suitno
			,String sizety,String sizeno,Integer orszqt){

		
		gzsOrderInsertService.updateOrdszdtl( ormtno, ordorg, ortyno, sampno, bradno, spclno, suitno
				, sizety, sizeno, orszqt);
		return "{success:true}";
	}
	/**
	 * 提交订单
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param ordorg
	 * @param ortyno
	 * @param bradno
	 * @return
	 */
	@RequestMapping("/gzsorderinsert/submit.do")
	@ResponseBody
	public String submit(String ormtno,String ordorg,String ortyno,String bradno){

		
		gzsOrderInsertService.submit(ormtno, ordorg, ortyno, bradno);
		return "{success:true}";
	}
}
