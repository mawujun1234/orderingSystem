package com.youngor.order.bw;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/bwOrdmt")
public class BwOrdmtController {

	@Resource
	private BwOrdmtService bwOrdmtService;
	
	@RequestMapping("/bwOrdmt/querySizeVOColumns.do")
	@ResponseBody
	public List<Map<String,Object>> querySizeVOColumns(String sizegp,String ormtno,String ordorg,String bradno,String spclno,String suitno ){
		
		return bwOrdmtService.querySizeVOColumns(sizegp, ormtno, ordorg, bradno, spclno, suitno);
	}
	
	@RequestMapping("/bwOrdmt/querySizeVOData.do")
	@ResponseBody
	public List<Map<String,Object>> querySizeVOData(@RequestBody Map<String,Object> params){
		return bwOrdmtService.querySizeVOData(params);
	}
	
	@RequestMapping("/bwOrdmt/updateOrszqt.do")
	@ResponseBody
	public String updateOrszqt(BwOrdszdtlVO bwOrdszdtlVO){
		bwOrdmtService.updateOrszqt(bwOrdszdtlVO);
		return "{success:true}";
	}
	
	@RequestMapping("/bwOrdmt/approve.do")
	@ResponseBody
	public String approve(String ormtno,String bradno,String spclno){
		bwOrdmtService.approve(ormtno, bradno, spclno);
		return "{success:true}";
	}
	/**
	 * 获取当前大类，小类的状态
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param ormtno
	 * @param bradno
	 * @param spclno
	 * @return
	 */
	@RequestMapping("/bwOrdmt/getBwOrdhdOrstat.do")
	@ResponseBody
	public String getBwOrdhdOrstat(String ormtno,String bradno,String spclno){
		Integer orstat=bwOrdmtService.getBwOrdhdOrstat(ormtno, bradno, spclno);
		return "{success:true,orstat:"+orstat+"}";
	}

//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/bwOrdmt/queryPager.do")
//	@ResponseBody
//	public Pager<BwOrdmt> queryPager(Pager<BwOrdmt> pager){
//		
//		return bwOrdmtService.queryPage(pager);
//	}
//
//	@RequestMapping("/bwOrdmt/queryAll.do")
//	@ResponseBody
//	public List<BwOrdmt> queryAll() {	
//		List<BwOrdmt> bwOrdmtes=bwOrdmtService.queryAll();
//		return bwOrdmtes;
//	}
//	
//
//	@RequestMapping("/bwOrdmt/load.do")
//	public BwOrdmt load(String id) {
//		return bwOrdmtService.get(id);
//	}
//	
//	@RequestMapping("/bwOrdmt/create.do")
//	@ResponseBody
//	public BwOrdmt create(@RequestBody BwOrdmt bwOrdmt) {
//		bwOrdmtService.create(bwOrdmt);
//		return bwOrdmt;
//	}
//	
//	@RequestMapping("/bwOrdmt/update.do")
//	@ResponseBody
//	public  BwOrdmt update(@RequestBody BwOrdmt bwOrdmt) {
//		bwOrdmtService.update(bwOrdmt);
//		return bwOrdmt;
//	}
//	
//	@RequestMapping("/bwOrdmt/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		bwOrdmtService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/bwOrdmt/destroy.do")
//	@ResponseBody
//	public BwOrdmt destroy(@RequestBody BwOrdmt bwOrdmt) {
//		bwOrdmtService.delete(bwOrdmt);
//		return bwOrdmt;
//	}
	
	
}