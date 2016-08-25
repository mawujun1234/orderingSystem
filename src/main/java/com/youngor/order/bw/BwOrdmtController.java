package com.youngor.order.bw;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.youngor.utils.M;
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
	public String approve(String[] mmornoes){
		bwOrdmtService.approve(mmornoes);
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
	public String getBwOrdhdOrstat(String ormtno,String ordorg,String bradno,String spclno){
		Integer orstat=bwOrdmtService.getBwOrdhdOrstat(ormtno,ordorg, bradno, spclno);
		return "{success:true,orstat:"+orstat+"}";
	}
	
	@RequestMapping("/bwOrdmt/getOrmmnos.do")
	@ResponseBody
	public List<BwOrdmt> getOrmmnos(String ormtno){
		return bwOrdmtService.query(Cnd.select().andEquals(M.BwOrdmt.ormtno, ormtno).desc(M.BwOrdmt.ormmno));
	}
	
	@RequestMapping("/bwOrdmt/queryBwOrdhd.do")
	@ResponseBody
	public List<BwOrdhd> queryBwOrdhd(String ormtno,String ormmno,String bradno,String spclno){
		return bwOrdmtService.queryBwOrdhd(ormtno, ormmno, bradno, spclno);
	}
	@RequestMapping("/bwOrdmt/queryBwSizeMgrList.do")
	@ResponseBody
	public List<Map<String,Object>> queryBwSizeMgrList(String mmorno) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("mmorno", mmorno);
		return bwOrdmtService.queryBwSizeMgrList(params);
	}
	
	@RequestMapping("/bwOrdmt/updateBwOrddt.do")
	@ResponseBody
	public  String updateBwOrddt(@RequestBody BwOrddt[] bwOrddt,String field) {
		List<String> sampno_none=bwOrdmtService.updateBwOrddt(bwOrddt, field);
		if(sampno_none.size()>0){
			StringBuilder builder=new StringBuilder();
			for(String str:sampno_none){
				builder.append(","+str);
			}
			//throw new BusinessException("下列样衣设置失败,其他样衣设置成功，因为这些样衣未填写数量:"+builder);
			return "{success:true,msg:'"+"下列样衣设置失败,其他样衣设置成功，因为这些样衣未填写数量:"+builder+"'}";
		} else {
			return "{success:true}";
		}
		//bwOrdmtService.update(bwOrddt);
		
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
