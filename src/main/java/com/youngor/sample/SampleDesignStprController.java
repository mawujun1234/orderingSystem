package com.youngor.sample;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleDesignStpr")
public class SampleDesignStprController {

	@Resource
	private SampleDesignStprService sampleDesignStprService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/sampleDesignStpr/query.do")
//	@ResponseBody
//	public Pager<SampleDesignStpr> query(Pager<SampleDesignStpr> pager){
//		return sampleDesignStprService.queryPage(pager);
//	}

	@RequestMapping("/sampleDesignStpr/query.do")
	@ResponseBody
	public List<SampleDesignStpr> query(String suitty,String sampno) {	
		List<SampleDesignStpr> sampleDesignStpres=sampleDesignStprService.querySampleDesignStpr(suitty,sampno);
		return sampleDesignStpres;
	}
	

//	@RequestMapping("/sampleDesignStpr/load.do")
//	@ResponseBody
//	public SampleDesignStpr load(String id) {
//		return sampleDesignStprService.get(id);
//	}
//	
//	@RequestMapping("/sampleDesignStpr/create.do")
//	//@ResponseBody
//	public SampleDesignStpr create(@RequestBody SampleDesignStpr sampleDesignStpr) {
//		sampleDesignStprService.create(sampleDesignStpr);
//		return sampleDesignStpr;
//	}
//	
//	@RequestMapping("/sampleDesignStpr/update.do")
//	//@ResponseBody
//	public  SampleDesignStpr update(@RequestBody SampleDesignStpr sampleDesignStpr) {
//		sampleDesignStprService.update(sampleDesignStpr);
//		return sampleDesignStpr;
//	}
//	
//	@RequestMapping("/sampleDesignStpr/deleteById.do")
//	//@ResponseBody
//	public String deleteById(String id) {
//		sampleDesignStprService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/sampleDesignStpr/destroy.do")
//	//@ResponseBody
//	public SampleDesignStpr destroy(@RequestBody SampleDesignStpr sampleDesignStpr) {
//		sampleDesignStprService.delete(sampleDesignStpr);
//		return sampleDesignStpr;
//	}
	
	
}
