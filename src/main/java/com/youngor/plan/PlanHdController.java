package com.youngor.plan;
import java.util.List;

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
//@RequestMapping("/planHd")
public class PlanHdController {

	@Resource
	private PlanHdService planHdService;




	@RequestMapping("/planHd/queryHdGrid.do")
	@ResponseBody
	public List<PlanHdVO> queryHdGrid(String ormtno,String yxgsno,String bradno,String spclno) {	
		List<PlanHdVO> planHdes=planHdService.queryHdGrid(ormtno, yxgsno, bradno, spclno);
		return planHdes;
	}
	

	@RequestMapping("/planHd/update.do")
	@ResponseBody
	public  PlanHd update(@RequestBody PlanHd planHd) {
		planHdService.createOrUpdate(planHd);
		return planHd;
	}
	
	@RequestMapping("/planHd/onPass.do")
	@ResponseBody
	public  String onPass(String ormtno,String yxgsno,String bradno,String spclno) {
//		//如果状态是编辑中，但是不是当前组织单元 就不能提交审批
//		if(!ShiroUtils.getAuthenticationInfo().inTheOrg(ordorg)){
//			throw new BusinessException("你没有权限进行提交!");
//		}

		planHdService.onPass(ormtno, yxgsno, bradno, spclno);
		return "success";
	}
	
//	@RequestMapping("/planHd/load.do")
//	public PlanHd load(com.youngor.plan.PlanHd.PK id) {
//		return planHdService.get(id);
//	}
//	
//	@RequestMapping("/planHd/create.do")
//	@ResponseBody
//	public PlanHd create(@RequestBody PlanHd planHd) {
//		planHdService.create(planHd);
//		return planHd;
//	}
//	
//	@RequestMapping("/planHd/update.do")
//	@ResponseBody
//	public  PlanHd update(@RequestBody PlanHd planHd) {
//		planHdService.update(planHd);
//		return planHd;
//	}
//	
//	@RequestMapping("/planHd/deleteById.do")
//	@ResponseBody
//	public com.youngor.plan.PlanHd.PK deleteById(com.youngor.plan.PlanHd.PK id) {
//		planHdService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/planHd/destroy.do")
//	@ResponseBody
//	public PlanHd destroy(@RequestBody PlanHd planHd) {
//		planHdService.delete(planHd);
//		return planHd;
//	}
	
	
}
