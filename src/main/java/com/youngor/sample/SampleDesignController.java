package com.youngor.sample;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mawujun.repository.cnd.Cnd;
import com.youngor.utils.M;
import com.mawujun.utils.page.Pager;

import com.youngor.sample.SampleDesign;
import com.youngor.sample.SampleDesignService;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleDesign")
public class SampleDesignController {

	@Resource
	private SampleDesignService sampleDesignService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/sampleDesign/query.do")
	@ResponseBody
	public Pager<SampleDesign> query(Pager<SampleDesign> pager){
		return sampleDesignService.queryPage(pager);
	}

	@RequestMapping("/sampleDesign/queryAll.do")
	@ResponseBody
	public List<SampleDesign> queryAll() {	
		List<SampleDesign> sampleDesignes=sampleDesignService.queryAll();
		return sampleDesignes;
	}
	

	@RequestMapping("/sampleDesign/load.do")
	public SampleDesign load(String id) {
		return sampleDesignService.get(id);
	}
	
	@RequestMapping("/sampleDesign/create.do")
	//@ResponseBody
	public SampleDesign create(@RequestBody SampleDesign sampleDesign) {
		sampleDesignService.create(sampleDesign);
		return sampleDesign;
	}
	
	@RequestMapping("/sampleDesign/update.do")
	//@ResponseBody
	public  SampleDesign update(@RequestBody SampleDesign sampleDesign) {
		sampleDesignService.update(sampleDesign);
		return sampleDesign;
	}
	
	@RequestMapping("/sampleDesign/deleteById.do")
	//@ResponseBody
	public String deleteById(String sampno) {
		sampleDesignService.deleteById(sampno);
		return sampno;
	}
	
//	@RequestMapping("/sampleDesign/destroy.do")
//	//@ResponseBody
//	public SampleDesign destroy(@RequestBody SampleDesign sampleDesign) {
//		sampleDesignService.delete(sampleDesign);
//		return sampleDesign;
//	}
	
	
}
