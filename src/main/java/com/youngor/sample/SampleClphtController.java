package com.youngor.sample;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;
/**
 * 搭配图片
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sampleClpht")
public class SampleClphtController {

	@Resource
	private SampleClphtService sampleClphtService;

//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/sampleClpht/queryPager.do")
//	@ResponseBody
//	public Pager<SampleClpht> queryPager(Pager<SampleClpht> pager){
//		
//		return sampleClphtService.queryPage(pager);
//	}

	@RequestMapping("/sampleClpht/queryAll.do")
	@ResponseBody
	public List<SampleClpht> queryAll(String clppno) {	
		List<SampleClpht> sampleClphtes=sampleClphtService.queryAll(clppno);
		return sampleClphtes;
	}
	

	@RequestMapping("/sampleClpht/load.do")
	public SampleClpht load(String id) {
		return sampleClphtService.get(id);
	}
	
	@RequestMapping("/sampleClpht/create.do")
	@ResponseBody
	public Map<String,Object> create(HttpServletRequest request,HttpServletResponse response,MultipartFile imageFile,SampleClpht sampleClpht) throws IOException {
		//sampleClphtService.create(sampleClpht);
		//return sampleClpht;
		String contextPath=WebUtils.getRealPath(request.getServletContext(), "/");
		
		Integer photno=sampleClphtService.create(sampleClpht, imageFile, contextPath);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("photno", photno);
		
		//response.setContentType("text/html");
		//response.getWriter().write("{\"success\":true}");
		return map;
	}
	
	@RequestMapping("/sampleClpht/update.do")
	@ResponseBody
	public  SampleClpht update(@RequestBody SampleClpht sampleClpht) {
		sampleClphtService.update(sampleClpht);
		return sampleClpht;
	}
	
	@RequestMapping("/sampleClpht/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		sampleClphtService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/sampleClpht/destroy.do")
	@ResponseBody
	public SampleClpht destroy(@RequestBody SampleClpht sampleClpht) {
		sampleClphtService.delete(sampleClpht);
		return sampleClpht;
	}
	
	
}
