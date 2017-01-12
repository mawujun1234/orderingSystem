package com.youngor.order.cg;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.MapParams;
import com.youngor.permission.ShiroUtils;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/cgOrdhd")
public class CgOrdhdController {

	@Resource
	private CgOrdhdService cgOrdhdService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/cgOrdhd/queryPager.do")
//	@ResponseBody
//	public Pager<CgOrdhd> queryPager(Pager<CgOrdhd> pager){
//		
//		return cgOrdhdService.queryPage(pager);
//	}

	@RequestMapping("/cgOrdhd/queryAll.do")
	@ResponseBody
	public List<CgOrdhdVO> queryAll(MapParams params) {	
		List<CgOrdhdVO> cgOrdhdes=cgOrdhdService.queryAll_1(params);
		return cgOrdhdes;
	}
	

	@RequestMapping("/cgOrdhd/load.do")
	public CgOrdhd load(com.youngor.order.cg.CgOrdhd.PK id) {
		return cgOrdhdService.get(id);
	}
	
	@RequestMapping("/cgOrdhd/create.do")
	@ResponseBody
	public CgOrdhd create(@RequestBody CgOrdhd cgOrdhd) {
		cgOrdhd.setRgdt(new Date());
		cgOrdhd.setRgsp(ShiroUtils.getUserId());
		cgOrdhd.setLmdt(new Date());
		cgOrdhd.setLmsp(ShiroUtils.getUserId());
		
		cgOrdhd.setCgorno(cgOrdhd.getOrcgno()+"_"+cgOrdhd.getBradno()+"_"+cgOrdhd.getSpclno());
		cgOrdhdService.create(cgOrdhd);
		return cgOrdhd;
	}
	
	@RequestMapping("/cgOrdhd/update.do")
	@ResponseBody
	public  CgOrdhd update(@RequestBody CgOrdhd cgOrdhd) {
		cgOrdhd.setLmdt(new Date());
		cgOrdhd.setLmsp(ShiroUtils.getUserId());
		cgOrdhdService.update(cgOrdhd);
		return cgOrdhd;
	}
	@RequestMapping("/cgOrdhd/confirm.do")
	@ResponseBody
	public  String confirm(String orcgno,String cgorno) {

		cgOrdhdService.confirm(orcgno,cgorno);
		return "{success:true}";
	}
	
//	@RequestMapping("/cgOrdhd/deleteById.do")
//	@ResponseBody
//	public com.youngor.order.cg.CgOrdhd.PK deleteById(com.youngor.order.cg.CgOrdhd.PK id) {
//		cgOrdhdService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/cgOrdhd/destroy.do")
	@ResponseBody
	public CgOrdhd destroy(@RequestBody CgOrdhd cgOrdhd) {
		cgOrdhdService.delete(cgOrdhd);
		return cgOrdhd;
	}
	
	
}
