package com.youngor.pubcode;
import java.util.List;

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
//@RequestMapping("/pubPlanrt")
public class PubPlanrtController {

	@Resource
	private PubPlanrtService pubPlanrtService;



	@RequestMapping("/pubPlanrt/queryGrid.do")
	@ResponseBody
	public List<PubPlanrt> queryGrid(String bradno,String spclno,String sptyno) {	
		List<PubPlanrt> pubPlanrtes=pubPlanrtService.queryGrid(bradno,spclno,sptyno);
		return pubPlanrtes;
	}
	
	@RequestMapping("/pubPlanrt/update.do")
	@ResponseBody
	public  PubPlanrt update(@RequestBody PubPlanrt pubPlanrt) {
		pubPlanrtService.createOrUpdate(pubPlanrt);
		return pubPlanrt;
	}
	

//	@RequestMapping("/pubPlanrt/load.do")
//	public PubPlanrt load(com.youngor.pubsize.PubSize.PK id) {
//		return pubPlanrtService.get(id);
//	}
//	
//	@RequestMapping("/pubPlanrt/create.do")
//	//@ResponseBody
//	public PubPlanrt create(@RequestBody PubPlanrt pubPlanrt) {
//		pubPlanrtService.create(pubPlanrt);
//		return pubPlanrt;
//	}
//	
//	@RequestMapping("/pubPlanrt/update.do")
//	//@ResponseBody
//	public  PubPlanrt update(@RequestBody PubPlanrt pubPlanrt) {
//		pubPlanrtService.update(pubPlanrt);
//		return pubPlanrt;
//	}
//	
//	@RequestMapping("/pubPlanrt/deleteById.do")
//	//@ResponseBody
//	public com.youngor.pubsize.PubSize.PK deleteById(com.youngor.pubsize.PubSize.PK id) {
//		pubPlanrtService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/pubPlanrt/destroy.do")
//	//@ResponseBody
//	public PubPlanrt destroy(@RequestBody PubPlanrt pubPlanrt) {
//		pubPlanrtService.delete(pubPlanrt);
//		return pubPlanrt;
//	}
	
	
}
