package com.youngor.order;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ord")
public class BwController {

	@Resource
	private BwService bwService;


	
	@RequestMapping("/bw/queryQyColumns.do")
	@ResponseBody
	public  List<Map<String,Object>> queryQyColumns(String yxgsno) {
		return bwService.queryQyColumns(yxgsno);
	}
	
	@RequestMapping("/bw/queryQyData.do")
	@ResponseBody
	public  List<Map<String,Object>> queryQyData(MapParams params) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		List<Map<String,Object>> list= bwService.queryQyData(params.getParams());

		for(Map<String,Object> map:list){
			map.put("SPCLNO_NAME", PubCodeCache.getSpclno_name(map.get("SPCLNO").toString()));
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return list;
	}
	
	@RequestMapping("/bw/qy_getStat.do")
	@ResponseBody
	public String qy_getStat(String ormtno,String yxgsno) {
		int stat=bwService.qy_getStat(ormtno,yxgsno);
		return "{success:true,stat:"+stat+"}";
	}
	@RequestMapping("/bw/qy_updateOrmtqt.do")
	@ResponseBody
	public  String qy_updateOrmtqt(String ormtno,String ordorg,String sampno,String bradno,String spclno,String suitno,Integer ormtqt) {
		bwService.qy_updateOrmtqt(ormtno, ordorg, sampno, bradno, spclno, suitno, ormtqt);
		return "{success:true}";
	}
	@RequestMapping("/bw/qy_approve.do")
	@ResponseBody
	public String qy_approve(String ormtno,String yxgsno,String bradno,String spclno) {
		
		bwService.qy_approve(ormtno, yxgsno, bradno, spclno);
		return "{success:true}";
	}
	@RequestMapping("/bw/qy_over.do")
	@ResponseBody
	public String qy_over( String ormtno,String yxgsno,String bradno,String spclno) {
		
		bwService.qy_over(ormtno, yxgsno, bradno, spclno);
		return "{success:true}";
	}
	
	
}
