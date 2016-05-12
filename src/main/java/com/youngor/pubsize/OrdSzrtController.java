package com.youngor.pubsize;
import java.util.HashMap;
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
//@RequestMapping("/ordSzrt")
public class OrdSzrtController {

	@Resource
	private OrdSzrtService ordSzrtService;
	@Resource
	private OrdSzrtRepository ordSzrtRepository;

	/**
	 * 	
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param sizegp 规格范围
	 * @return
	 */
	@RequestMapping("/ordSzrt/queryColumns.do")
	@ResponseBody
	public Map<String,List<PubSize>> queryColumns(String sizegp){
		Map<String,List<PubSize>> result=new HashMap<String,List<PubSize>>();
		result.put("szrtColumns", ordSzrtRepository.queryStdszColumns(sizegp));
		return result;
	}

	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/ordSzrt/query.do")
	@ResponseBody
	public Pager<OrdSzrt> query(Pager<OrdSzrt> pager){
		return ordSzrtService.queryPage(pager);
	}

	@RequestMapping("/ordSzrt/queryAll.do")
	@ResponseBody
	public List<OrdSzrt> queryAll() {	
		List<OrdSzrt> ordSzrtes=ordSzrtService.queryAll();
		return ordSzrtes;
	}
	

	@RequestMapping("/ordSzrt/load.do")
	@ResponseBody
	public OrdSzrt load(String id) {
		return ordSzrtService.get(id);
	}
	
	@RequestMapping("/ordSzrt/create.do")
	@ResponseBody
	public OrdSzrt create(@RequestBody OrdSzrt ordSzrt) {
		ordSzrtService.create(ordSzrt);
		return ordSzrt;
	}
	
	@RequestMapping("/ordSzrt/update.do")
	@ResponseBody
	public  OrdSzrt update(@RequestBody OrdSzrt ordSzrt) {
		ordSzrtService.update(ordSzrt);
		return ordSzrt;
	}
	
	@RequestMapping("/ordSzrt/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		ordSzrtService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/ordSzrt/destroy.do")
	@ResponseBody
	public OrdSzrt destroy(@RequestBody OrdSzrt ordSzrt) {
		ordSzrtService.delete(ordSzrt);
		return ordSzrt;
	}
	
	
}
