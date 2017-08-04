package com.youngor.order.cg;
import java.util.List;

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
//@RequestMapping("/cgOrddt")
public class CgOrddtController {

	@Resource
	private CgOrddtService cgOrddtService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/cgOrddt/queryPager.do")
//	@ResponseBody
//	public Pager<CgOrddt> queryPager(Pager<CgOrddt> pager){
//		
//		return cgOrddtService.queryPage(pager);
//	}

	@RequestMapping("/cgOrddt/queryAll.do")
	@ResponseBody
	public List<CgOrddt> queryAll() {	
		List<CgOrddt> cgOrddtes=cgOrddtService.queryAll();
		return cgOrddtes;
	}
	

	@RequestMapping("/cgOrddt/load.do")
	public CgOrddt load(com.youngor.order.cg.CgOrddt.PK id) {
		return cgOrddtService.get(id);
	}
	
	@RequestMapping("/cgOrddt/create.do")
	@ResponseBody
	public CgOrddt create(@RequestBody CgOrddt cgOrddt) {
		cgOrddtService.create(cgOrddt);
		return cgOrddt;
	}
	
	@RequestMapping("/cgOrddt/update.do")
	@ResponseBody
	public  CgOrddt update(@RequestBody CgOrddt cgOrddt) {
		cgOrddtService.update(cgOrddt);
		return cgOrddt;
	}
	
//	@RequestMapping("/cgOrddt/deleteById.do")
//	@ResponseBody
//	public com.youngor.order.cg.CgOrddt.PK deleteById(com.youngor.order.cg.CgOrddt.PK id) {
//		cgOrddtService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/cgOrddt/destroy.do")
//	@ResponseBody
//	public CgOrddt destroy(@RequestBody CgOrddt cgOrddt) {
//		cgOrddtService.delete(cgOrddt);
//		return cgOrddt;
//	}
	
	@RequestMapping("/cgOrddt/updateBatch1.do")
	@ResponseBody
	public  String updateBatch1(@RequestBody CgOrddt[] cgOrddt) {
		cgOrddtService.updateBatch1(cgOrddt);
		return "{success:true}";
	}
}
