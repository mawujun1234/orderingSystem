package com.youngor.sample;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Pager;
import com.youngor.utils.M;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleDesign")
public class SampleDesignController {

	@Resource
	private SampleDesignService sampleDesignService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/sampleDesign/query.do")
	@ResponseBody
	public Pager<SampleDesign> query(Pager<SampleDesign> pager){
		return sampleDesignService.queryPage(pager);
	}

	@RequestMapping("/sampleDesign/queryAll.do")
	@ResponseBody
	public List<SampleDesign> queryAll() {	
		List<SampleDesign> sampleDesignes=sampleDesignService.queryAll();
		return sampleDesignes;
	}
	

	@RequestMapping("/sampleDesign/load.do")
	@ResponseBody
	public SampleDesign load(String id) {
		return sampleDesignService.get(id);
	}
	
	@RequestMapping("/sampleDesign/create.do")
	//@ResponseBody
	public SampleDesign create(@RequestBody SampleDesign sampleDesign) {
		sampleDesignService.create(sampleDesign);
		return sampleDesign;
	}
	
	@RequestMapping("/sampleDesign/update.do")
	//@ResponseBody
	public  SampleDesign update(@RequestBody SampleDesign sampleDesign) {
		sampleDesignService.update(sampleDesign);
		return sampleDesign;
	}
	
	@RequestMapping("/sampleDesign/deleteById.do")
	//@ResponseBody
	public String deleteById(String sampno) {
		sampleDesignService.deleteById(sampno);
		return sampno;
	}
	
	/**
	 * 设计师输入设计资料的时候用的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param pager
	 * @return
	 */
	@RequestMapping("/sampleDesign/queryPlanDesign.do")
	@ResponseBody
	public Pager<SamplePlanDesignVO> queryPlanDesign(Pager<SamplePlanDesignVO> pager) {
		return sampleDesignService.queryPlanDesign(pager);
	}
	
	@RequestMapping("/sampleDesign/lock.do")
	@ResponseBody
	public void lock(String[] sampnos,MapParams mapParams) {
		if(sampnos!=null){
			sampleDesignService.update(Cnd.update().set(M.SampleDesign.spstat, 1).andIn(M.SampleDesign.sampno, sampnos));
		}
		if(mapParams.getParams()!=null){
			sampleDesignService.lock(mapParams.getParams());
		}
		
	}
	@RequestMapping("/sampleDesign/unlock.do")
	@ResponseBody
	public void unlock(String[] sampnos,MapParams mapParams) {
		if(sampnos!=null){
			sampleDesignService.update(Cnd.update().set(M.SampleDesign.spstat, 0).andIn(M.SampleDesign.sampno, sampnos));
		}
		if(mapParams.getParams()!=null){
			sampleDesignService.unlock(mapParams.getParams());
		}
	}
	
//	@RequestMapping("/sampleDesign/destroy.do")
//	//@ResponseBody
//	public SampleDesign destroy(@RequestBody SampleDesign sampleDesign) {
//		sampleDesignService.delete(sampleDesign);
//		return sampleDesign;
//	}
	
	
}
