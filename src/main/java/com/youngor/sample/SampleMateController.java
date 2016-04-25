package com.youngor.sample;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.youngor.utils.M;
import com.youngor.utils.MapParams;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleMate")
public class SampleMateController {

	@Resource
	private SampleMateService sampleMateService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/sampleMate/query.do")
//	@ResponseBody
//	public Pager<SampleMate> query(Pager<SampleMate> pager){
//		return sampleMateService.queryPage(pager);
//	}

	@RequestMapping("/sampleMate/query.do")
	@ResponseBody
	public Map<String,Object> query(String sampno) {	
		List<SampleMate> sampleMatees=sampleMateService.query(Cnd.select().andEquals(M.SampleMate.sampno, sampno).asc(M.SampleMate.mateso));
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("root", sampleMatees);
		return map;
	}
	

	@RequestMapping("/sampleMate/load.do")
	public SampleMate load(SampleMate.PK id) {
		return sampleMateService.get(id);
	}
	
	@RequestMapping("/sampleMate/create.do")
	//@ResponseBody
	public SampleMate create(@RequestBody SampleMate sampleMate) {
		sampleMateService.create(sampleMate);
		return sampleMate;
	}
	
	@RequestMapping("/sampleMate/update.do")
	//@ResponseBody
	public  SampleMate update(@RequestBody SampleMate sampleMate) {
		sampleMateService.update(sampleMate);
		return sampleMate;
	}
	
	@RequestMapping("/sampleMate/deleteById.do")
	//@ResponseBody
	public SampleMate.PK deleteById(SampleMate.PK id) {
		sampleMateService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/sampleMate/destroy.do")
	//@ResponseBody
	public SampleMate destroy(@RequestBody SampleMate sampleMate) {
		sampleMateService.delete(sampleMate);
		return sampleMate;
	}
	
	@RequestMapping("/sampleMate/lock.do")
	@ResponseBody
	public void lock(String[] sampnos,MapParams mapParams) {
		if(sampnos!=null){
			sampleMateService.update(Cnd.update().set(M.SampleMate.matest, 1).andIn(M.SampleMate.sampno, sampnos));
		}
		if(mapParams.getParams()!=null){
			sampleMateService.lock(mapParams.getParams());
		}
		
	}
	@RequestMapping("/sampleMate/unlock.do")
	@ResponseBody
	public void unlock(String[] sampnos,MapParams mapParams) {
		if(sampnos!=null){
			sampleMateService.update(Cnd.update().set(M.SampleMate.matest, 0).andIn(M.SampleMate.sampno, sampnos));
		}
		if(mapParams.getParams()!=null){
			sampleMateService.unlock(mapParams.getParams());
		}
	}
}
