package com.youngor.sample;
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
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/samplePhoto")
public class SamplePhotoController {

	@Resource
	private SamplePhotoService samplePhotoService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/samplePhoto/query.do")
//	@ResponseBody
//	public Pager<SamplePhoto> query(Pager<SamplePhoto> pager){
//		return samplePhotoService.queryPage(pager);
//	}

	@RequestMapping("/samplePhoto/query.do")
	@ResponseBody
	public List<SamplePhoto> query() {	
		List<SamplePhoto> samplePhotoes=samplePhotoService.queryAll();
		return samplePhotoes;
	}
	

	@RequestMapping("/samplePhoto/load.do")
	public SamplePhoto load(String id) {
		return samplePhotoService.get(id);
	}
	
	@RequestMapping("/samplePhoto/create.do")
	public void create(HttpServletRequest request,HttpServletResponse response,MultipartFile imageFile,SamplePhoto samplePhoto) throws IOException {	
		//保存文件
		String contextPath=WebUtils.getRealPath(request.getServletContext(), "/");
		
		samplePhotoService.create(samplePhoto, imageFile, contextPath);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		
		response.setContentType("text/html");
		response.getWriter().write("{\"success\":true}");
		//return map;
	}
	
	@RequestMapping("/samplePhoto/update.do")
	//@ResponseBody
	public  SamplePhoto update(@RequestBody SamplePhoto samplePhoto) {
		samplePhotoService.update(samplePhoto);
		return samplePhoto;
	}
	
	@RequestMapping("/samplePhoto/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		samplePhotoService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/samplePhoto/destroy.do")
	//@ResponseBody
	public SamplePhoto destroy(@RequestBody SamplePhoto samplePhoto) {
		samplePhotoService.delete(samplePhoto);
		return samplePhoto;
	}
	
	
}
