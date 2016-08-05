package com.youngor.plan;
import java.util.List;
import java.util.Map;

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
//@RequestMapping("/ordDwdt")
public class OrdDwdtController {

	@Resource
	private OrdDwdtService ordDwdtService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/ordDwdt/queryPager1.do")
	@ResponseBody
	public Pager<Map<String,Object>> queryPager1(Pager<Map<String,Object>> pager){
		
		return ordDwdtService.queryPager1(pager);
	}
	
	@RequestMapping("/ordDwdt/updateField.do")
	@ResponseBody
	public  String onMldate(@RequestBody List<Map<String,Object>> params,String ormtno,String ortyno,String count_type,String yxgsno,String qyno ) {
		ordDwdtService.updateField(params, ormtno, ortyno, count_type, yxgsno, qyno);
		return "{success:true}";
	}
//	@RequestMapping("/ordDwdt/onPldate.do")
//	@ResponseBody
//	public  String onPldate(@RequestBody List<Map<String,Object>> params) {
//		
//		return "{success:true}";
//	}
//	@RequestMapping("/ordDwdt/onPplace.do")
//	@ResponseBody
//	public  String onPplace(@RequestBody List<Map<String,Object>> params) {
//		
//		return "{success:true}";
//	}

//	@RequestMapping("/ordDwdt/queryAll.do")
//	@ResponseBody
//	public List<OrdDwdt> queryAll() {	
//		List<OrdDwdt> ordDwdtes=ordDwdtService.queryAll();
//		return ordDwdtes;
//	}
//	
//
//	@RequestMapping("/ordDwdt/load.do")
//	public OrdDwdt load(com.youngor.plan.OrdDwdt.PK id) {
//		return ordDwdtService.get(id);
//	}
//	
//	@RequestMapping("/ordDwdt/create.do")
//	@ResponseBody
//	public OrdDwdt create(@RequestBody OrdDwdt ordDwdt) {
//		ordDwdtService.create(ordDwdt);
//		return ordDwdt;
//	}
//	
//	@RequestMapping("/ordDwdt/update.do")
//	@ResponseBody
//	public  OrdDwdt update(@RequestBody OrdDwdt ordDwdt) {
//		ordDwdtService.update(ordDwdt);
//		return ordDwdt;
//	}
//	
//	@RequestMapping("/ordDwdt/deleteById.do")
//	@ResponseBody
//	public com.youngor.plan.OrdDwdt.PK deleteById(com.youngor.plan.OrdDwdt.PK id) {
//		ordDwdtService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/ordDwdt/destroy.do")
//	@ResponseBody
//	public OrdDwdt destroy(@RequestBody OrdDwdt ordDwdt) {
//		ordDwdtService.delete(ordDwdt);
//		return ordDwdt;
//	}
//	
//	
}
