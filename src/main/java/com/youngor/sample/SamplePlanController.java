package com.youngor.sample;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.utils.page.Pager;
import com.youngor.permission.ShiroUtils;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/samplePlan")
public class SamplePlanController {

	@Resource
	private SamplePlanService samplePlanService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/samplePlan/query.do")
	@ResponseBody
	public Pager<SamplePlan> query(Pager<SamplePlan> pager){
		
		return samplePlanService.queryPage(pager);
	}

//	@RequestMapping("/samplePlan/queryAll.do")
//	@ResponseBody
//	public List<SamplePlan> queryAll() {	
//		List<SamplePlan> samplePlanes=samplePlanService.queryAll();
//		return samplePlanes;
//	}
//	

	@RequestMapping("/samplePlan/load.do")
	public SamplePlan load(String id) {
		return samplePlanService.get(id);
	}
	
	@RequestMapping("/samplePlan/create.do")
	//@ResponseBody
	public SamplePlan create(@RequestBody SamplePlan samplePlan) {
		//samplePlan.setPlspno("222");
		//samplePlan.setOrmtno("201604");
		
		
		
		samplePlan.setPlspno(samplePlan.getOrmtno()+samplePlan.getPlspnm());
		SamplePlan aaa=samplePlanService.get(samplePlan.getPlspno());
		if(aaa!=null){
			throw new BusinessException("该样衣编号已经存在!");
		}
		samplePlan.setRgdt(new Date());
		samplePlan.setRgsp(ShiroUtils.getLoginName());
		samplePlan.setLmdt(new Date());
		samplePlan.setLmsp(ShiroUtils.getLoginName());
		samplePlan.setPlstat(1);
		samplePlanService.create(samplePlan);
		return samplePlan;
	}
	
	@RequestMapping("/samplePlan/update.do")
	//@ResponseBody
	public  SamplePlan update(@RequestBody SamplePlan samplePlan) {
		samplePlan.setLmdt(new Date());
		samplePlan.setLmsp(ShiroUtils.getLoginName());
		samplePlanService.update(samplePlan);
		return samplePlan;
	}
	
//	@RequestMapping("/samplePlan/deleteById.do")
//	//@ResponseBody
//	public String deleteById(String id) {
//		
//		samplePlanService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/samplePlan/destroy.do")
	//@ResponseBody
	public SamplePlan destroy(@RequestBody SamplePlan samplePlan) {
		samplePlanService.delete(samplePlan);
		return samplePlan;
	}
	
	
	@RequestMapping("/samplePlan/lockOrunlock.do")
	@ResponseBody
	public void lockOrunlock(String plspno,Integer plspst) {
		samplePlanService.lockOrunlock(plspno, plspst);
	}
	

	
}
