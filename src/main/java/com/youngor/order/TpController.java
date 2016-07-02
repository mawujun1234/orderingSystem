package com.youngor.order;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
public class TpController {

	@Resource
	private TpService tpService;

	/**
	 * 查询某个样衣的信息
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param id
	 * @return
	 */
	@RequestMapping("/tp/zgs_tpAllQuery.do")
	@ResponseBody
	public  Pager<Map<String,Object>> zgs_tpAllQuery( Pager<Map<String,Object>> pager) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		pager= tpService.zgs_tpAllQuery(pager);
		List<Map<String,Object>> list=pager.getRoot();
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return pager;
	}
	
	@RequestMapping("/tp/zgs_updateOrmtqt_tp.do")
	@ResponseBody
	public  String zgs_updateOrmtqt_tp(String ormtno,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt) {
		tpService.zgs_updateOrmtqt_tp(ormtno, sampno, bradno, spclno, suitno, ormtqs, ormtqt);
		return "{success:true}";
	}
	@RequestMapping("/tp/zgs_restoreDZ.do")
	@ResponseBody
	public String zgs_restoreDZ(String ormtno,String sampno,String suitno) {
		
		tpService.zgs_restoreDZ( ormtno, sampno, suitno);
		return "{success:true}";
	}
	@RequestMapping("/tp/zgs_over.do")
	@ResponseBody
	public String zgs_over(String ormtno) {
		
		tpService.zgs_over( ormtno);
		return "{success:true}";
	}
	@RequestMapping("/tp/zgs_getOrstat.do")
	@ResponseBody
	public String zgs_getOrstat(String ormtno) {
		
		int orstat=tpService.zgs_getOrstat( ormtno);
		return "{success:true,orstat:"+orstat+"}";
	}
	
	@RequestMapping("/tp/queryTpYxgsColumns.do")
	@ResponseBody
	public  List<Map<String,Object>> queryTpYxgsColumns() {
		return tpService.queryTpYxgsColumns();
	}
	@RequestMapping("/tp/tpYxgsQuery.do")
	@ResponseBody
	public  List<Map<String,Object>> tpYxgsQuery(MapParams params) {
	
		List<Map<String,Object>> list=tpService.tpYxgsQuery(params.getParams());
		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return list;
	}
	@RequestMapping("/tp/tpYxgs_updateOrmtqt_tp.do")
	@ResponseBody
	public  String tpYxgs_pdateOrmtqt_tp(String ormtno,String ordorg,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt) {
		tpService.tpYxgs_updateOrmtqt_tp(ormtno, ordorg, sampno, bradno, spclno, suitno, ormtqs, ormtqt);
		return "{success:true}";
	}
	@RequestMapping("/tp/tpYxgs_getStat.do")
	@ResponseBody
	public String tpYxgs_getStat(String ormtno) {
		int stat=tpService.tpYxgs_getStat(ormtno);
		return "{success:true,stat:"+stat+"}";
	}
	@RequestMapping("/tp/tpYxgs_over.do")
	@ResponseBody
	public String tpYxgs_over(String ormtno) {
		
		tpService.tpYxgs_over( ormtno);
		return "{success:true}";
	}
	
	@RequestMapping("/tp/queryTpQyColumns.do")
	@ResponseBody
	public  List<Map<String,Object>> queryTpQyColumns(String yxgsno) {
		return tpService.queryTpQyColumns(yxgsno);
	}
	
	@RequestMapping("/tp/tpQyQuery.do")
	@ResponseBody
	public  List<Map<String,Object>> tpQyQuery(MapParams params) {
		//在查询的时候判断，是否已经超时了，如果已经超时了，就不能查询了
		
		
		List<Map<String,Object>> list= tpService.tpQyQuery(params.getParams());

		for(Map<String,Object> map:list){
			map.put("SPTYNO_NAME", PubCodeCache.getSptyno_name(map.get("SPTYNO").toString()));
			map.put("SPSENO_NAME", PubCodeCache.getSpseno_name(map.get("SPSENO").toString()));
			map.put("SUITNO_NAME", PubCodeCache.getSuitno_name(map.get("SUITNO").toString()));
		}
		return list;
	}
	
	@RequestMapping("/tp/tpQy_getStat.do")
	@ResponseBody
	public String tpQy_getStat(String ormtno,String yxgsno) {
		int stat=tpService.tpQy_getStat(ormtno,yxgsno);
		return "{success:true,stat:"+stat+"}";
	}
	@RequestMapping("/tp/tpQy_updateOrmtqt_tp.do")
	@ResponseBody
	public  String tpQy_updateOrmtqt_tp(String ormtno,String ordorg,String sampno,String bradno,String spclno,String suitno,Integer ormtqs,Integer ormtqt,Integer ormtqt1) {
		tpService.tpQy_updateOrmtqt_tp(ormtno, ordorg, sampno, bradno, spclno, suitno, ormtqs, ormtqt,ormtqt1);
		return "{success:true}";
	}
	@RequestMapping("/tp/tpQy_over.do")
	@ResponseBody
	public String tpQy_over(String ormtno,String yxgsno) {
		
		tpService.tpQy_over( ormtno,yxgsno);
		return "{success:true}";
	}
	
}
