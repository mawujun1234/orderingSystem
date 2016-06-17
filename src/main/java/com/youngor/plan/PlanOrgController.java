package com.youngor.plan;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageParam;
import com.mawujun.utils.page.Pager;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/planOrg")
public class PlanOrgController {

	@Resource
	private PlanOrgService planOrgService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/planOrg/queryPager.do")
	@ResponseBody
	public Pager<PlanOrg> queryPager(Pager<PlanOrg> pager){
		
		return planOrgService.queryPage(pager);
	}

	@RequestMapping("/planOrg/queryAll.do")
	@ResponseBody
	public List<PlanOrg> queryAll() {	
		List<PlanOrg> planOrges=planOrgService.queryAll();
		return planOrges;
	}
	

	@RequestMapping("/planOrg/load.do")
	public PlanOrg load(String id) {
		return planOrgService.get(id);
	}
	
	@RequestMapping("/planOrg/create.do")
	//@ResponseBody
	public PlanOrg create(@RequestBody PlanOrg planOrg) {
		planOrgService.create(planOrg);
		return planOrg;
	}
	
	@RequestMapping("/planOrg/update.do")
	//@ResponseBody
	public  PlanOrg update(@RequestBody PlanOrg planOrg) {
		planOrgService.update(planOrg);
		return planOrg;
	}
	
	@RequestMapping("/planOrg/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		planOrgService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/planOrg/destroy.do")
	//@ResponseBody
	public PlanOrg destroy(@RequestBody PlanOrg planOrg) {
		planOrgService.delete(planOrg);
		return planOrg;
	}
	
	
}
