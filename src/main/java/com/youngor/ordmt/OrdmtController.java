package com.youngor.ordmt;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
import com.youngor.permission.ShiroUtils;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ordmt")
public class OrdmtController {

	@Resource
	private OrdmtService ordmtService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/ordmt/query.do")
	@ResponseBody
	public Pager<OrdmtVO> query(Pager<OrdmtVO> pager){
		
		pager=ordmtService.queryPageVO(pager);
		return pager;
		
	}

	@RequestMapping("/ordmt/queryAll.do")
	@ResponseBody
	public List<Ordmt> queryAll() {	
		List<Ordmt> ordmtes=ordmtService.queryAll();
		return ordmtes;
	}
	

	@RequestMapping("/ordmt/load.do")
	public Ordmt load(String id) {
		return ordmtService.get(id);
	}
	
	@RequestMapping("/ordmt/create.do")
	//@ResponseBody
	public Ordmt create(@RequestBody Ordmt ordmt,String[] seasnos) {
		ordmt.setMtrgdt(new Date());
		ordmt.setMtrgsp(ShiroUtils.getLoginName());
		ordmtService.create(ordmt,seasnos);
		return ordmt;
	}
	
	@RequestMapping("/ordmt/update.do")
	//@ResponseBody
	public  Ordmt update(@RequestBody Ordmt ordmt,String[] seasnos) {
		ordmt.setMtlmdt(new Date());
		ordmt.setMtlmsp(ShiroUtils.getLoginName());
		ordmtService.update(ordmt,seasnos);
		return ordmt;
	}
	
	@RequestMapping("/ordmt/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		ordmtService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/ordmt/destroy.do")
	//@ResponseBody
	public Ordmt destroy(@RequestBody Ordmt ordmt) {
		ordmtService.delete(ordmt);
		return ordmt;
	}
	
	
}
