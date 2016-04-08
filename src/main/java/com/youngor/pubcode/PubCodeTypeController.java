package com.youngor.pubcode;
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
//@RequestMapping("/pubCodeType")
public class PubCodeTypeController {

	@Resource
	private PubCodeTypeService pubCodeTypeService;

	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/pubCodeType/query.do")
	@ResponseBody
	public List<PubCodeType> query(String parent_id) {
		Cnd cnd=Cnd.select().andEquals(M.PubCodeType.ftyno, "root".equals(parent_id)?null:parent_id);
		List<PubCodeType> pubCodeTypees=pubCodeTypeService.query(cnd);
		return pubCodeTypees;
	}


	@RequestMapping("/pubCodeType/queryAll.do")
	@ResponseBody
	public List<PubCodeType> queryAll() {	
		List<PubCodeType> pubCodeTypees=pubCodeTypeService.queryAll();
		return pubCodeTypees;
	}
	

	@RequestMapping("/pubCodeType/load.do")
	public PubCodeType load(String id) {
		return pubCodeTypeService.get(id);
	}
	
	@RequestMapping("/pubCodeType/create.do")
	//@ResponseBody
	public PubCodeType create(@RequestBody PubCodeType pubCodeType) {
		if("root".equals(pubCodeType.getFtyno())){
			pubCodeType.setFtyno(null);
		}
		pubCodeTypeService.create(pubCodeType);
		return pubCodeType;
	}
	
	@RequestMapping("/pubCodeType/update.do")
	//@ResponseBody
	public  PubCodeType update(@RequestBody PubCodeType pubCodeType) {
		pubCodeTypeService.update(pubCodeType);
		return pubCodeType;
	}
	
	@RequestMapping("/pubCodeType/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		pubCodeTypeService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/pubCodeType/destroy.do")
	//@ResponseBody
	public PubCodeType destroy(@RequestBody PubCodeType pubCodeType) {
		pubCodeTypeService.delete(pubCodeType);
		return pubCodeType;
	}
	
	
}
