package com.youngor.sample;
import java.util.ArrayList;
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
//@RequestMapping("/sampleDesignSizegp")
public class SampleDesignSizegpController {

	@Resource
	private SampleDesignSizegpService sampleDesignSizegpService;



	@RequestMapping("/sampleDesignSizegp/queryAll.do")
	@ResponseBody
	public List<SampleDesignSizegp> queryAll(String sampno,String suitty) {	
		//如果套装种类为空，表示选的都是标准套
		List<SampleDesignSizegp> sampleDesignSizegpes=null;
		if(suitty==null || "".equals(suitty)){//表示新建的没有套件的时候
			if(sampno!=null && !"".equals(sampno)){//新建样衣的时候
				sampleDesignSizegpes=sampleDesignSizegpService.querySampleDesignSizegp_T00(sampno);
			}
			
			if(sampleDesignSizegpes==null || sampleDesignSizegpes.size()==0){
				sampleDesignSizegpes=new ArrayList<SampleDesignSizegp>();
				SampleDesignSizegp aa=new SampleDesignSizegp();
				aa.setSampno(sampno);
				aa.setSuitno("T00");
				sampleDesignSizegpes.add(aa);
			}
		} else {
			sampleDesignSizegpes=sampleDesignSizegpService.querySampleDesignSizegp(sampno,suitty);
			
		}
		return sampleDesignSizegpes;
		
	}
//	
//
//	@RequestMapping("/sampleDesignSizegp/load.do")
//	@ResponseBody
//	public SampleDesignSizegp load(String id) {
//		return sampleDesignSizegpService.get(id);
//	}
//	
//	@RequestMapping("/sampleDesignSizegp/create.do")
//	@ResponseBody
//	public SampleDesignSizegp create(@RequestBody SampleDesignSizegp sampleDesignSizegp) {
//		sampleDesignSizegpService.create(sampleDesignSizegp);
//		return sampleDesignSizegp;
//	}
//	
//	@RequestMapping("/sampleDesignSizegp/update.do")
//	@ResponseBody
//	public  SampleDesignSizegp update(@RequestBody SampleDesignSizegp sampleDesignSizegp) {
//		sampleDesignSizegpService.update(sampleDesignSizegp);
//		return sampleDesignSizegp;
//	}
//	
//	@RequestMapping("/sampleDesignSizegp/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		sampleDesignSizegpService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/sampleDesignSizegp/destroy.do")
//	@ResponseBody
//	public SampleDesignSizegp destroy(@RequestBody SampleDesignSizegp sampleDesignSizegp) {
//		sampleDesignSizegpService.delete(sampleDesignSizegp);
//		return sampleDesignSizegp;
//	}
//	
	
}
