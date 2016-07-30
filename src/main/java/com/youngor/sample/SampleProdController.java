package com.youngor.sample;
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
//@RequestMapping("/sampleProd")
public class SampleProdController {

	@Resource
	private SampleProdService sampleProdService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/sampleProd/queryPager.do")
	@ResponseBody
	public Pager<SampleProd> queryPager(Pager<SampleProd> pager){
		
		return sampleProdService.queryPage(pager);
	}

//	@RequestMapping("/sampleProd/queryAll.do")
//	@ResponseBody
//	public List<SampleProd> queryAll() {	
//		List<SampleProd> sampleProdes=sampleProdService.queryAll();
//		return sampleProdes;
//	}
	

	@RequestMapping("/sampleProd/load.do")
	public SampleProd load(com.youngor.sample.SampleProd.PK id) {
		return sampleProdService.get(id);
	}
	
	@RequestMapping("/sampleProd/createOrUpdate.do")
	@ResponseBody
	public SampleProd createOrUpdate(@RequestBody SampleProd sampleProd) {
		sampleProdService.createOrUpdate(sampleProd);
		return sampleProd;
	}
	
//	@RequestMapping("/sampleProd/create.do")
//	@ResponseBody
//	public SampleProd create(@RequestBody SampleProd sampleProd) {
//		sampleProdService.create(sampleProd);
//		return sampleProd;
//	}
//	
//	@RequestMapping("/sampleProd/update.do")
//	@ResponseBody
//	public  SampleProd update(@RequestBody SampleProd sampleProd) {
//		sampleProdService.update(sampleProd);
//		return sampleProd;
//	}
//	
//	@RequestMapping("/sampleProd/deleteById.do")
//	@ResponseBody
//	public com.youngor.sample.SampleProd.PK deleteById(com.youngor.sample.SampleProd.PK id) {
//		sampleProdService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/sampleProd/destroy.do")
//	@ResponseBody
//	public SampleProd destroy(@RequestBody SampleProd sampleProd) {
//		sampleProdService.delete(sampleProd);
//		return sampleProd;
//	}
	
	
}
