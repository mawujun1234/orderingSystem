package com.youngor.org;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.youngor.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/position")
public class PositionController {

	@Resource
	private PositionService positionService;

	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/position/query.do")
	@ResponseBody
	public List<Position> query(String orgno) {
		Cnd cnd=Cnd.select().andEquals(M.Position.orgno, orgno);
		List<Position> positiones=positionService.query(cnd);
		return positiones;
	}


	@RequestMapping("/position/queryAll.do")
	@ResponseBody
	public List<Position> queryAll() {	
		List<Position> positiones=positionService.queryAll();
		return positiones;
	}
	

	@RequestMapping("/position/load.do")
	public Position load(String id) {
		return positionService.get(id);
	}
	
	@RequestMapping("/position/create.do")
	//@ResponseBody
	public Position create(@RequestBody Position position) {
		
		positionService.create(position);
		return position;
	}
	
	@RequestMapping("/position/update.do")
	//@ResponseBody
	public  Position update(@RequestBody Position position) {
		positionService.update(position);
		return position;
	}
	
	@RequestMapping("/position/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		positionService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/position/destroy.do")
	//@ResponseBody
	public Position destroy(@RequestBody Position position) {
		positionService.delete(position);
		return position;
	}
	
	
}
