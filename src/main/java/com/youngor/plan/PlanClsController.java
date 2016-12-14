package com.youngor.plan;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/planCls")
public class PlanClsController {

	@Resource
	private PlanClsService planClsService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/planCls/queryPager.do")
//	@ResponseBody
//	public Pager<PlanCls> queryPager(Pager<PlanCls> pager){
//		
//		return planClsService.queryPage(pager);
//	}

	@RequestMapping("/planCls/queryAll.do")
	@ResponseBody
	public List<PlanCls> queryAll(PlanCls params) {	
		List<PlanCls> planClses=planClsService.queryAll(params);
		return planClses;
	}
	
	@RequestMapping("/planCls/createOrupdate.do")
	@ResponseBody
	public  String createOrupdate(PlanCls planCls) {
		planClsService.createOrUpdate(planCls);
		return "{success:true}";
	}
//	@RequestMapping("/planCls/load.do")
//	public PlanCls load(com.youngor.plan.PlanCls.PK id) {
//		return planClsService.get(id);
//	}
//	
//	@RequestMapping("/planCls/create.do")
//	@ResponseBody
//	public PlanCls create(@RequestBody PlanCls planCls) {
//		planClsService.create(planCls);
//		return planCls;
//	}
//	
	
//	
//	@RequestMapping("/planCls/deleteById.do")
//	@ResponseBody
//	public com.youngor.plan.PlanCls.PK deleteById(com.youngor.plan.PlanCls.PK id) {
//		planClsService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/planCls/destroy.do")
//	@ResponseBody
//	public PlanCls destroy(@RequestBody PlanCls planCls) {
//		planClsService.delete(planCls);
//		return planCls;
//	}
//	
//	
}
