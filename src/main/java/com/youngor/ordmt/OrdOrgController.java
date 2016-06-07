package com.youngor.ordmt;
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

import com.youngor.ordmt.OrdOrg;
import com.youngor.ordmt.OrdOrgService;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/ordOrg")
public class OrdOrgController {

	@Resource
	private OrdOrgService ordOrgService;


	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/ordOrg/queryPager.do")
	@ResponseBody
	public Pager<OrdOrg> queryPager(Pager<OrdOrg> pager){
		return ordOrgService.queryPage(pager);
	}

	@RequestMapping("/ordOrg/getOrdOrgByOrg.do")
	@ResponseBody
	public OrdOrg getOrdOrgByOrg(String ormtno,String orgno) {	
		OrdOrg ordOrg=ordOrgService.getOrdOrgByOrg(ormtno,orgno);
		if(ordOrg==null){
			return new OrdOrg();
		}
		return ordOrg;
	}
//	
//
//	@RequestMapping("/ordOrg/load.do")
//	@ResponseBody
//	public OrdOrg load(com.youngor.ordmt.OrdOrg.PK id) {
//		return ordOrgService.get(id);
//	}
	
	@RequestMapping("/ordOrg/create.do")
	@ResponseBody
	public String create(String addModel,String ordorg,Integer sztype,String role_id) {
		 ordOrgService.create(addModel, ordorg, sztype, role_id);
		 return "success";
	}
	
	@RequestMapping("/ordOrg/update.do")
	@ResponseBody
	public  OrdOrg update(@RequestBody OrdOrg ordOrg) {
		ordOrgService.update(ordOrg);
		return ordOrg;
	}
	
	@RequestMapping("/ordOrg/deleteById.do")
	@ResponseBody
	public com.youngor.ordmt.OrdOrg.PK deleteById(com.youngor.ordmt.OrdOrg.PK id) {
		ordOrgService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/ordOrg/destroy.do")
	@ResponseBody
	public OrdOrg destroy(@RequestBody OrdOrg ordOrg) {
		ordOrgService.destroy(ordOrg);
		return ordOrg;
	}
	
	
}
