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
		//List<SampleDesignStpr> sampleDesignStpres=sampleDesignStprService.querySampleDesignStpr(suitty,sampno);
		//return sampleDesignStpres;
		//如果套装种类为空，表示选的都是标准套
				List<SampleDesignStpr> sampleDesignSizegpes=null;
				if(suitty==null || "".equals(suitty)){//表示新建的没有套件的时候
					if(sampno!=null && !"".equals(sampno)){//新建样衣的时候
						sampleDesignSizegpes=sampleDesignStprService.querySampleDesignStpr_T00(sampno);
					}
					
					if(sampleDesignSizegpes==null || sampleDesignSizegpes.size()==0){
						sampleDesignSizegpes=new ArrayList<SampleDesignStpr>();
						SampleDesignStpr aa=new SampleDesignStpr();
						aa.setSampno(sampno);
						aa.setSuitno("T00");
						sampleDesignSizegpes.add(aa);
					}
				} else {
					sampleDesignSizegpes=sampleDesignStprService.querySampleDesignStpr(sampno,suitty);
					
				}
				return sampleDesignSizegpes;
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
