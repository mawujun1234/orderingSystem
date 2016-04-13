package com.youngor.ordmt;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youngor.permission.ShiroUtils;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ordmtScde")
public class OrdmtScdeController {

	@Resource
	private OrdmtScdeService ordmtScdeService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/ordmtScde/query.do")
//	@ResponseBody
//	public Pager<OrdmtScde> query(Pager<OrdmtScde> pager){
//		return ordmtScdeService.queryPage(pager);
//	}

	@RequestMapping("/ordmtScde/query.do")
	@ResponseBody
	public List<OrdmtScde> query() {	
		List<OrdmtScde> ordmtScdees=ordmtScdeService.queryAll();
		return ordmtScdees;
	}
	

	@RequestMapping("/ordmtScde/load.do")
	public OrdmtScde load(String id) {
		return ordmtScdeService.get(id);
	}
	
	@RequestMapping("/ordmtScde/create.do")
	//@ResponseBody
	public OrdmtScde create(@RequestBody OrdmtScde ordmtScde) {
		ordmtScde.setLmsp(ShiroUtils.getLoginName());
		ordmtScde.setLmdt(new Date());
		ordmtScdeService.create(ordmtScde);
		return ordmtScde;
	}
	
	@RequestMapping("/ordmtScde/update.do")
	//@ResponseBody
	public  OrdmtScde update(@RequestBody OrdmtScde ordmtScde) {
		ordmtScdeService.update(ordmtScde);
		return ordmtScde;
	}
	
	@RequestMapping("/ordmtScde/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		ordmtScdeService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/ordmtScde/destroy.do")
	//@ResponseBody
	public OrdmtScde destroy(@RequestBody OrdmtScde ordmtScde) {
		ordmtScdeService.delete(ordmtScde);
		return ordmtScde;
	}
	
	
}
