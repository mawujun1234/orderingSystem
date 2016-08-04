package com.youngor.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Pager;

/**
 * 备忘转定制的功能
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Controller
public class BW2DZController {
	@Autowired
	private BW2DZRepository bW2DZRepository;
	@Autowired
	private BW2DZService bW2DZService;
	
	@RequestMapping("/bw2dz/queryPager.do")
	@ResponseBody
	public Pager<Map<String,Object>> queryPager(Pager<Map<String,Object>> pager){
		
		return bW2DZRepository.queryPager(pager);
	}
	@RequestMapping("/bw2dz/transform.do")
	@ResponseBody
	public String transform(@RequestBody List<Map<String,Object>> params){
		bW2DZService.transform(params);
		return "{success:true}";
	}

}
