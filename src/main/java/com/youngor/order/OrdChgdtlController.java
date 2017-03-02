package com.youngor.order;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.MapParams;
import com.mawujun.utils.page.Pager;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ordChgdtl")
public class OrdChgdtlController {

	@Resource
	private OrdChgdtlService ordChgdtlService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/ordChgdtl/queryPager.do")
	@ResponseBody
	public Pager<OrdChgdtlVO> queryPager(Pager<OrdChgdtlVO> pager){
		//pager.setParams(new OrdChgdtl());
		pager= ordChgdtlService.queryPager1(pager);
		return pager;
	}
	@RequestMapping("/ordChgdtl/query4comp.do")
	@ResponseBody
	//public List<OrdChgdtlCompVO> query4comp(String ormtno,String bradno,String spclno,String sampno) {
	public List<OrdChgdtlCompVO> query4comp(MapParams params) {
		return ordChgdtlService.query4comp(params);
	}
	@RequestMapping("/ordChgdtl/query4qy.do")
	@ResponseBody
//	public List<OrdChgdtlQyVO> query4qy(String ormtno,String bradno
//			,String spclno,String sampno,String compno) {
	public List<OrdChgdtlQyVO> query4qy(MapParams params) {
		return ordChgdtlService.query4qy(params);
	}

//	@RequestMapping("/ordChgdtl/queryAll.do")
//	@ResponseBody
//	public List<OrdChgdtl> queryAll() {	
//		List<OrdChgdtl> ordChgdtles=ordChgdtlService.queryAll();
//		return ordChgdtles;
//	}
	

//	@RequestMapping("/ordChgdtl/load.do")
//	public OrdChgdtl load(com.youngor.order.Orddtl.PK id) {
//		return ordChgdtlService.get(id);
//	}
	
//	@RequestMapping("/ordChgdtl/create.do")
//	@ResponseBody
//	public OrdChgdtl create(@RequestBody OrdChgdtl ordChgdtl) {
//		ordChgdtlService.create(ordChgdtl);
//		return ordChgdtl;
//	}
	
	@RequestMapping("/ordChgdtl/updateValue.do")
	@ResponseBody
	public  OrdChgdtl updateValue(OrdChgdtl ordChgdtl,String field,String value) throws IllegalAccessException, InvocationTargetException {
	
		ordChgdtlService.updateValue(ordChgdtl,field,value);
		return ordChgdtl;
	}
	
	@RequestMapping("/ordChgdtl/cancel.do")
	@ResponseBody
	public String cancel(String ormtno,String sampno,String suitno,String[] compnos,String bradno,String spclno) {
		ordChgdtlService.cancel(ormtno, sampno, suitno, compnos,bradno,spclno);
		return "{success:true}";
	}
	
//	@RequestMapping("/ordChgdtl/deleteById.do")
//	@ResponseBody
//	public com.youngor.order.Orddtl.PK deleteById(com.youngor.order.Orddtl.PK id) {
//		ordChgdtlService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/ordChgdtl/destroy.do")
//	@ResponseBody
//	public OrdChgdtl destroy(@RequestBody OrdChgdtl ordChgdtl) {
//		ordChgdtlService.delete(ordChgdtl);
//		return ordChgdtl;
//	}
//	
	
}
