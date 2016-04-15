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

import com.youngor.sample.SamplePlanStpr;
import com.youngor.sample.SamplePlanStprService;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/samplePlanStpr")
public class SamplePlanStprController {

	@Resource
	private SamplePlanStprService samplePlanStprService;

//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/samplePlanStpr/query.do")
//	@ResponseBody
//	public Pager<SamplePlanStpr> query(Pager<SamplePlanStpr> pager){
//		return samplePlanStprService.queryPage(pager);
//	}

	@RequestMapping("/samplePlanStpr/query.do")
	@ResponseBody
	public List<SamplePlanStpr> query(String plspno) {	
		List<SamplePlanStpr> samplePlanStpres=samplePlanStprService.query(Cnd.select().andEquals(M.SamplePlanStpr.plspno, plspno));
		return samplePlanStpres;
	}
	

	@RequestMapping("/samplePlanStpr/load.do")
	public SamplePlanStpr load(String id) {
		return samplePlanStprService.get(id);
	}
	
	@RequestMapping("/samplePlanStpr/create.do")
	//@ResponseBody
	public SamplePlanStpr create(@RequestBody SamplePlanStpr samplePlanStpr) {
		samplePlanStpr.setPlspno("e986494a-72f6-4882-a5f4-5f3f3cdbaf59");
		samplePlanStprService.create(samplePlanStpr);
		return samplePlanStpr;
	}
	
	@RequestMapping("/samplePlanStpr/update.do")
	//@ResponseBody
	public  SamplePlanStpr update(@RequestBody SamplePlanStpr samplePlanStpr) {
		samplePlanStprService.update(samplePlanStpr);
		return samplePlanStpr;
	}
	
	@RequestMapping("/samplePlanStpr/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		samplePlanStprService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/samplePlanStpr/destroy.do")
	//@ResponseBody
	public SamplePlanStpr destroy(@RequestBody SamplePlanStpr samplePlanStpr) {
		samplePlanStprService.delete(samplePlanStpr);
		return samplePlanStpr;
	}
	
	
}
