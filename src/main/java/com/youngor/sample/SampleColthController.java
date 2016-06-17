package com.youngor.sample;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.M;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleColth")
public class SampleColthController {

	@Resource
	private SampleColthService sampleColthService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/sampleColth/query.do")
//	@ResponseBody
//	public Pager<SampleColth> query(Pager<SampleColth> pager){
//		return sampleColthService.queryPage(pager);
//	}
//
	@RequestMapping("/sampleColth/query.do")
	@ResponseBody
	public List<SampleColth> query(String id) {	
		List<SampleColth> sampleColthes=sampleColthService.query(Cnd.select().andEqualsIf(M.SampleColth.sampno, id));
//		for(SampleColth sampleColth:sampleColthes){
//			sampleColth.setSpsuno_name(ContextUtils.getPubSuno(sampleColth.getSpsuno()).getIdsunm());
//			sampleColth.setPrsuno_name(ContextUtils.getPubSuno(sampleColth.getPrsuno()).getIdsunm());
//			
//		}
		return sampleColthes;
	}
	

	@RequestMapping("/sampleColth/load.do")
	@ResponseBody
	public SampleColth load(String id) {
		
		SampleColth sampleColth= sampleColthService.load(id);
		if(ContextUtils.getPubSuno(sampleColth.getSpsuno())!=null){
			sampleColth.setSpsuno_name(ContextUtils.getPubSuno(sampleColth.getSpsuno()).getIdsunm());
			sampleColth.setPrsuno_name(ContextUtils.getPubSuno(sampleColth.getPrsuno()).getIdsunm());
		}

		return sampleColth;
	}
	
	@RequestMapping("/sampleColth/create.do")
	//@ResponseBody
	public SampleColth create(@RequestBody SampleColth sampleColth) {
		sampleColthService.create(sampleColth);
		return sampleColth;
	}
	
	@RequestMapping("/sampleColth/update.do")
	//@ResponseBody
	public  SampleColth update(@RequestBody SampleColth sampleColth) {
		sampleColthService.update(sampleColth);
		return sampleColth;
	}
	
	@RequestMapping("/sampleColth/lock.do")
	@ResponseBody
	public void lock(String[] sampnos,MapParams mapParams) {
		if(sampnos!=null){
			sampleColthService.update(Cnd.update().set(M.SampleColth.spctst, 1).andIn(M.SampleColth.sampno, sampnos));
		}
		if(mapParams.getParams()!=null){
			sampleColthService.lock(mapParams.getParams());
		}
		
	}
	@RequestMapping("/sampleColth/unlock.do")
	@ResponseBody
	public void unlock(String[] sampnos,MapParams mapParams) {
		if(sampnos!=null){
			sampleColthService.update(Cnd.update().set(M.SampleColth.spctst, 0).andIn(M.SampleColth.sampno, sampnos));
		}
		if(mapParams.getParams()!=null){
			sampleColthService.unlock(mapParams.getParams());
		}
	}
	
//	@RequestMapping("/sampleColth/deleteById.do")
//	//@ResponseBody
//	public String deleteById(String id) {
//		sampleColthService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/sampleColth/destroy.do")
//	//@ResponseBody
//	public SampleColth destroy(@RequestBody SampleColth sampleColth) {
//		sampleColthService.delete(sampleColth);
//		return sampleColth;
//	}
	
	
}
