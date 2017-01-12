package com.youngor.sample;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 搭配样衣
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleCldtl")
public class SampleCldtlController {

	@Resource
	private SampleCldtlService sampleCldtlService;

//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/sampleCldtl/queryPager.do")
//	@ResponseBody
//	public Pager<SampleCldtl> queryPager(Pager<SampleCldtl> pager){
//		
//		return sampleCldtlService.queryPage(pager);
//	}

	@RequestMapping("/sampleCldtl/queryAll.do")
	@ResponseBody
	public List<SampleCldtlVO> queryAll(String clppno) {	
		List<SampleCldtlVO> sampleCldtles=sampleCldtlService.queryAll(clppno);
		return sampleCldtles;
	}
	

	@RequestMapping("/sampleCldtl/load.do")
	public SampleCldtl load(com.youngor.sample.SampleCldtl.PK id) {
		return sampleCldtlService.get(id);
	}
	
	@RequestMapping("/sampleCldtl/create.do")
	@ResponseBody
	public SampleCldtl create(@RequestBody SampleCldtlVO sampleCldtlVO) {
		
		sampleCldtlService.create(sampleCldtlVO);
		return sampleCldtlVO;
	}
	
	@RequestMapping("/sampleCldtl/update.do")
	@ResponseBody
	public  SampleCldtl update(@RequestBody SampleCldtl sampleCldtl) {
		sampleCldtlService.update(sampleCldtl);
		return sampleCldtl;
	}
	
	@RequestMapping("/sampleCldtl/deleteById.do")
	@ResponseBody
	public com.youngor.sample.SampleCldtl.PK deleteById(com.youngor.sample.SampleCldtl.PK id) {
		sampleCldtlService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/sampleCldtl/destroy.do")
	@ResponseBody
	public SampleCldtl destroy(@RequestBody SampleCldtl sampleCldtl) {
		sampleCldtlService.delete(sampleCldtl);
		return sampleCldtl;
	}
	
	
}
