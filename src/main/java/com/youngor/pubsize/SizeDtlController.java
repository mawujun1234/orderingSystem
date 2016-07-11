package com.youngor.pubsize;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sizeDtl")
public class SizeDtlController {

	@Resource
	private SizeDtlService sizeDtlService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/sizeDtl/queryPager.do")
//	@ResponseBody
//	public Pager<SizeDtl> queryPager(Pager<SizeDtl> pager){
//		
//		return sizeDtlService.queryPage(pager);
//	}

	@RequestMapping("/sizeDtl/query.do")
	@ResponseBody
	public List<SizeDtlVO> query(String ormtno,String fszty,String fszno,String sizety) {	
		List<SizeDtlVO> sizeDtles=sizeDtlService.query(ormtno, fszty, fszno,sizety);
		return sizeDtles;
	}
	

//	@RequestMapping("/sizeDtl/load.do")
//	public SizeDtl load(com.youngor.pubsize.SizeDtl.PK id) {
//		return sizeDtlService.get(id);
//	}
//	
	@RequestMapping("/sizeDtl/create.do")
	@ResponseBody
	public SizeDtl create(SizeDtl sizeDtl,String[] sizenos) {
		sizeDtlService.create(sizeDtl,sizenos);
		return sizeDtl;
	}
	@RequestMapping("/sizeDtl/deleteById.do")
	@ResponseBody
	public com.youngor.pubsize.SizeDtl.PK deleteById(com.youngor.pubsize.SizeDtl.PK id) {
		sizeDtlService.deleteById(id);
		return id;
	}
//	
//	@RequestMapping("/sizeDtl/update.do")
//	@ResponseBody
//	public  SizeDtl update(@RequestBody SizeDtl sizeDtl) {
//		sizeDtlService.update(sizeDtl);
//		return sizeDtl;
//	}
//	
//	@RequestMapping("/sizeDtl/deleteById.do")
//	@ResponseBody
//	public com.youngor.pubsize.SizeDtl.PK deleteById(com.youngor.pubsize.SizeDtl.PK id) {
//		sizeDtlService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/sizeDtl/destroy.do")
//	@ResponseBody
//	public SizeDtl destroy(@RequestBody SizeDtl sizeDtl) {
//		sizeDtlService.delete(sizeDtl);
//		return sizeDtl;
//	}
	
	
}
