package com.youngor.sample;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
/**
 * 搭配
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleClhd")
public class SampleClhdController {

	@Resource
	private SampleClhdService sampleClhdService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/sampleClhd/queryPager.do")
	@ResponseBody
	public Pager<SampleClhd> queryPager(Pager<SampleClhd> pager){
		
		return sampleClhdService.queryPage(pager);
	}

//	@RequestMapping("/sampleClhd/queryAll.do")
//	@ResponseBody
//	public List<SampleClhd> queryAll() {	
//		List<SampleClhd> sampleClhdes=sampleClhdService.queryAll();
//		return sampleClhdes;
//	}
//	

	@RequestMapping("/sampleClhd/load.do")
	public SampleClhd load(String id) {
		return sampleClhdService.get(id);
	}
	
	@RequestMapping("/sampleClhd/create.do")
	@ResponseBody
	public SampleClhd create(@RequestBody SampleClhd sampleClhd) {
		sampleClhdService.create(sampleClhd);
		return sampleClhd;
	}
	
	@RequestMapping("/sampleClhd/update.do")
	@ResponseBody
	public  SampleClhd update(@RequestBody SampleClhd sampleClhd) {
		sampleClhdService.update(sampleClhd);
		return sampleClhd;
	}
	
//	@RequestMapping("/sampleClhd/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		sampleClhdService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/sampleClhd/enable_disable.do")
	@ResponseBody
	public String enable_disable(String clppno) {
		sampleClhdService.enable_disable(clppno);
		return "{success:true}";
	}
	
//	@RequestMapping("/sampleClhd/querySampleClphtes.do")
//	@ResponseBody
//	public List<SampleClpht> querySampleClphtes(String clppno) {	
//		List<SampleClpht> sampleClpht=sampleClhdService.querySampleClphtes(clppno);
//		return sampleClpht;
//	}
	
	
}
