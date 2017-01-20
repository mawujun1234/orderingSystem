package com.youngor.order.cg;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.DateUtils;
import com.youngor.permission.ShiroUtils;
import com.youngor.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/cgOrdmt")
public class CgOrdmtController {

	@Resource
	private CgOrdmtService cgOrdmtService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/cgOrdmt/queryPager.do")
//	@ResponseBody
//	public Pager<CgOrdmt> queryPager(Pager<CgOrdmt> pager){
//		
//		return cgOrdmtService.queryPage(pager);
//	}

	@RequestMapping("/cgOrdmt/queryAll.do")
	@ResponseBody
	public List<CgOrdmt> queryAll(String ormtno) {	
		List<CgOrdmt> cgOrdmtes=cgOrdmtService.query(Cnd.select().andEquals(M.CgOrdmt.ormtno, ormtno));
		return cgOrdmtes;
	}
	

	@RequestMapping("/cgOrdmt/load.do")
	public CgOrdmt load(com.youngor.order.cg.CgOrdmt.PK id) {
		return cgOrdmtService.get(id);
	}
	
	@RequestMapping("/cgOrdmt/create.do")
	@ResponseBody
	public CgOrdmt create(@RequestBody CgOrdmt cgOrdmt) {
		
		cgOrdmt.setOrcgno(DateUtils.format4Id());
		
		cgOrdmt.setRgdt(new Date());
		cgOrdmt.setRgsp(ShiroUtils.getUserId());
		cgOrdmt.setLmdt(new Date());
		cgOrdmt.setLmsp(ShiroUtils.getUserId());
		cgOrdmtService.create(cgOrdmt);
		return cgOrdmt;
	}
	
//	@RequestMapping("/cgOrdmt/update.do")
//	@ResponseBody
//	public  CgOrdmt update(@RequestBody CgOrdmt cgOrdmt) {
//		cgOrdmt.setLmdt(new Date());
//		cgOrdmt.setLmsp(ShiroUtils.getUserId());
//		cgOrdmtService.update(cgOrdmt);
//		return cgOrdmt;
//	}
//	
//	@RequestMapping("/cgOrdmt/deleteById.do")
//	@ResponseBody
//	public com.youngor.order.cg.CgOrdmt.PK deleteById(com.youngor.order.cg.CgOrdmt.PK id) {
//		cgOrdmtService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/cgOrdmt/destroy.do")
	@ResponseBody
	public CgOrdmt destroy(@RequestBody CgOrdmt cgOrdmt) {
		cgOrdmtService.delete(cgOrdmt);
		return cgOrdmt;
	}
	
	
}
