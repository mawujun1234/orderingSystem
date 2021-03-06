package com.youngor.org;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youngor.permission.ShiroUtils;
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
			dim=Dim.SALE;
		}

		//Cnd cnd=Cnd.select().andEquals(M.OrgOrg, "root".equals(parent_id)?null:parent_id);
		List<NodeVO> orges=orgService.query(parent_id,dim);
		return orges;
	}
	@RequestMapping("/org/queryOnlyOrg.do")
	@ResponseBody
	public List<NodeVO> queryOnlyOrg(String parent_id,Dim dim) {
		if(dim==null){
			dim=Dim.SALE;
		}

		//Cnd cnd=Cnd.select().andEquals(M.OrgOrg, "root".equals(parent_id)?null:parent_id);
		List<NodeVO> orges=orgService.queryOnlyOrg(parent_id,dim);
		return orges;
	}
	@RequestMapping("/org/queryOnlyOrgByorgnm.do")
	@ResponseBody
	public List<NodeVO> queryOnlyOrgByorgnm(String orgnm,Dim dim) {
		if(dim==null){
			dim=Dim.SALE;
		}

		//Cnd cnd=Cnd.select().andEquals(M.OrgOrg, "root".equals(parent_id)?null:parent_id);
		List<NodeVO> orges=orgService.queryOnlyOrgByorgnm(orgnm,dim);
		return orges;
	}
	/**
	 * 添加了权限的组织节点过滤
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param parent_id
	 * @param dim
	 * @return
	 */
	@RequestMapping("/org/query4Combo.do")
	@ResponseBody
	public List<Org> query4Combo(String parent_no,String channo,Dim dim,Boolean showBlank) {
		if(dim==null){
			dim=Dim.SALE;
		}

		List<Org> orges=new ArrayList<Org>();//orgService.query4Combo(parent_no, channo,dim,ShiroUtils.getUserId());
		if(parent_no==null || "".equals(parent_no)){
			//orges=orgService.query4Combo(parent_no, channo,dim,ShiroUtils.getUserId());
		} else {
			orges=orgService.query4Combo(parent_no, channo,dim,ShiroUtils.getUserId());
		}
		if(showBlank!=null && showBlank==true){
			Org blank=new Org();
			blank.setOrgno("");
			blank.setOrgnm("所有");
			orges.add(0, blank);
		}
		return orges;
	}

	/**
	 * 查询某个职位可以访问的组织单元
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param parent_id
	 * @param dim
	 * @return
	 */
	@RequestMapping("/org/queryOrgAccess.do")
	@ResponseBody
	public List<NodeVO> queryOrgAccess(String parent_id,Dim dim,String position_id) {
		if(position_id==null){
			return new ArrayList<NodeVO>();
		}
		if(dim==null){
			dim=Dim.SALE;
		}

		//Cnd cnd=Cnd.select().andEquals(M.OrgOrg, "root".equals(parent_id)?null:parent_id);
		List<NodeVO> orges=orgService.queryOrgAccess(parent_id,dim,position_id);
		return orges;
	}
	
	@RequestMapping("/org/checkOrgNodes.do")
	@ResponseBody
	public void checkNodes(Dim dim,String position_id,String orgno,String orgty,Boolean checked,String parent_orgnos[]) {
//		//System.out.println(MenuType.menu);
//		//Cnd cnd=Cnd.select().andEquals(M.Menu.parent_id, "root".equals(parent_id)?null:parent_id));
//		if("root".equals(parent_id)){
//			parent_id=null;
//		}
//		List<MenuVO> menues=menuService.query_checkbox(null);
//		return menues;
		if(dim==null){
			dim=Dim.SALE;
		}
		
		orgService.checkNodes(dim, position_id, orgno, orgty, checked, parent_orgnos);
		return;
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
