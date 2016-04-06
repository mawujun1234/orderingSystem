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

import com.youngor.org.Org;
import com.youngor.org.OrgService;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/org")
public class OrgController {

	@Resource
	private OrgService orgService;

	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/org/query.do")
	@ResponseBody
	public List<NodeVO> query(String parent_id,Dim dim) {
		if(dim==null){
			dim=Dim.sale;
		}

		//Cnd cnd=Cnd.select().andEquals(M.OrgOrg, "root".equals(parent_id)?null:parent_id);
		List<NodeVO> orges=orgService.query(parent_id,dim);
		return orges;
	}


//	@RequestMapping("/org/queryAll.do")
//	@ResponseBody
//	public List<Org> queryAll() {	
//		List<Org> orges=orgService.queryAll();
//		return orges;
//	}
	

	@RequestMapping("/org/load.do")
	public Org load(String id) {
		return orgService.get(id);
	}
	
	@RequestMapping("/org/create.do")
	//@ResponseBody
	public Org create(@RequestBody Org org,String parent_no,Dim dim) {
//		if("root".equals(parent_no)){
//			org.setParent_id(null);
//		}
//		orgService.create(org);
		return org;
	}
	
	@RequestMapping("/org/update.do")
	//@ResponseBody
	public  Org update(@RequestBody Org org) {
//		orgService.update(org);
		return org;
	}
	
	@RequestMapping("/org/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		//orgService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/org/destroy.do")
	//@ResponseBody
	public Org destroy(@RequestBody Org org) {
		//orgService.delete(org);
		return org;
	}
	
	
}
