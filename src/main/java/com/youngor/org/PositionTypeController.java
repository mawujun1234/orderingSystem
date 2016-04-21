package com.youngor.org;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mawujun.repository.cnd.Cnd;
import com.youngor.utils.M;
import com.mawujun.utils.page.Pager;

import com.youngor.org.PositionType;
import com.youngor.org.PositionTypeService;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/positionType")
public class PositionTypeController {

	@Resource
	private PositionTypeService positionTypeService;


//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/positionType/query.do")
//	@ResponseBody
//	public Pager<PositionType> query(Pager<PositionType> pager){
//		return positionTypeService.queryPage(pager);
//	}

	@RequestMapping("/positionType/query.do")
	@ResponseBody
	public List<PositionType> queryAll() {	
		List<PositionType> positionTypees=positionTypeService.queryAll();
		return positionTypees;
	}
	

	@RequestMapping("/positionType/load.do")
	public PositionType load(String id) {
		return positionTypeService.get(id);
	}
	
	@RequestMapping("/positionType/create.do")
	//@ResponseBody
	public PositionType create(@RequestBody PositionType positionType) {
		positionTypeService.create(positionType);
		return positionType;
	}
	
	@RequestMapping("/positionType/update.do")
	//@ResponseBody
	public  PositionType update(@RequestBody PositionType positionType) {
		positionTypeService.update(positionType);
		return positionType;
	}
	
	@RequestMapping("/positionType/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		positionTypeService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/positionType/destroy.do")
	//@ResponseBody
	public PositionType destroy(@RequestBody PositionType positionType) {
		positionTypeService.delete(positionType);
		return positionType;
	}
	
	
}
