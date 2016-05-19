package com.youngor.order;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mawujun.repository.cnd.Cnd;
import com.youngor.utils.M;
import com.mawujun.utils.page.Pager;

import com.youngor.order.Ord;
import com.youngor.order.OrdService;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ord")
public class OrdController {

	@Resource
	private OrdService ordService;


	/**
	 * 查询某个样衣的信息
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param id
	 * @return
	 */
	@RequestMapping("/ord/querySample.do")
	@ResponseBody
	public Map<String,Object> querySample(String sampnm) {
		return ordService.querySample(sampnm);
	}

//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/ord/query.do")
//	@ResponseBody
//	public Pager<Ord> query(Pager<Ord> pager){
//		return ordService.queryPage(pager);
//	}
//
//	@RequestMapping("/ord/queryAll.do")
//	@ResponseBody
//	public List<Ord> queryAll() {	
//		List<Ord> ordes=ordService.queryAll();
//		return ordes;
//	}
//	
//
//	@RequestMapping("/ord/load.do")
//	@ResponseBody
//	public Ord load(String id) {
//		return ordService.get(id);
//	}
//	
//	@RequestMapping("/ord/create.do")
//	@ResponseBody
//	public Ord create(@RequestBody Ord ord) {
//		ordService.create(ord);
//		return ord;
//	}
//	
//	@RequestMapping("/ord/update.do")
//	@ResponseBody
//	public  Ord update(@RequestBody Ord ord) {
//		ordService.update(ord);
//		return ord;
//	}
//	
//	@RequestMapping("/ord/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		ordService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/ord/destroy.do")
//	@ResponseBody
//	public Ord destroy(@RequestBody Ord ord) {
//		ordService.delete(ord);
//		return ord;
//	}
	
	
}
