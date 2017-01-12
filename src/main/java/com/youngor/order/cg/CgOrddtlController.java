package com.youngor.order.cg;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/cgOrddtl")
public class CgOrddtlController {

	@Resource
	private CgOrddtlService cgOrddtlService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/cgOrddtl/queryPager.do")
	@ResponseBody
	public Pager<CgOrddtl> queryPager(Pager<CgOrddtl> pager){
		
		return cgOrddtlService.queryPage(pager);
	}

//	@RequestMapping("/cgOrddtl/queryAll.do")
//	@ResponseBody
//	public List<CgOrddtl> queryAll() {	
//		List<CgOrddtl> cgOrddtles=cgOrddtlService.queryAll();
//		return cgOrddtles;
//	}
	

	@RequestMapping("/cgOrddtl/load.do")
	public CgOrddtl load(com.youngor.order.cg.CgOrddtl.PK id) {
		return cgOrddtlService.get(id);
	}
	
	@RequestMapping("/cgOrddtl/create.do")
	@ResponseBody
	public CgOrddtl create(@RequestBody CgOrddtl cgOrddtl) {
		cgOrddtlService.create(cgOrddtl);
		return cgOrddtl;
	}
	
	@RequestMapping("/cgOrddtl/update.do")
	@ResponseBody
	public  CgOrddtl update(@RequestBody CgOrddtl cgOrddtl) {
		cgOrddtlService.update(cgOrddtl);
		return cgOrddtl;
	}
	
	@RequestMapping("/cgOrddtl/deleteById.do")
	@ResponseBody
	public com.youngor.order.cg.CgOrddtl.PK deleteById(com.youngor.order.cg.CgOrddtl.PK id) {
		cgOrddtlService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/cgOrddtl/destroy.do")
	@ResponseBody
	public CgOrddtl destroy(@RequestBody CgOrddtl cgOrddtl) {
		cgOrddtlService.delete(cgOrddtl);
		return cgOrddtl;
	}
	
	
}
