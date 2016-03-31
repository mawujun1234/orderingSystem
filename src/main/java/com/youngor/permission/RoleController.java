package com.youngor.permission;
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

import com.youngor.permission.Role;
import com.youngor.permission.RoleService;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/role")
public class RoleController {

	@Resource
	private RoleService roleService;

	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/role/query.do")
	@ResponseBody
	public List<Role> query(String parent_id) {
		Cnd cnd=Cnd.select().andEquals(M.Role.parent_id, "root".equals(parent_id)?null:parent_id);
		List<Role> rolees=roleService.query(cnd);
		return rolees;
	}


	@RequestMapping("/role/queryAll.do")
	@ResponseBody
	public List<Role> queryAll() {	
		List<Role> rolees=roleService.queryAll();
		return rolees;
	}
	

	@RequestMapping("/role/load.do")
	public Role load(String id) {
		return roleService.get(id);
	}
	
	@RequestMapping("/role/create.do")
	//@ResponseBody
	public Role create(@RequestBody Role role) {
		if("root".equals(role.getParent_id())){
			role.setParent_id(null);
		}
		roleService.create(role);
		return role;
	}
	
	@RequestMapping("/role/update.do")
	//@ResponseBody
	public  Role update(@RequestBody Role role) {
		roleService.update(role);
		return role;
	}
	
	@RequestMapping("/role/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		roleService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/role/destroy.do")
	//@ResponseBody
	public Role destroy(@RequestBody Role role) {
		roleService.delete(role);
		return role;
	}
	
	
	/**
	 * 角色选择菜单的时候
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param parent_id
	 * @return
	 */
	@RequestMapping("/role/checkMenuNodes.do")
	@ResponseBody
	public void checkNodes(String role_id,String ids[],Boolean checked) {
//		//System.out.println(MenuType.menu);
//		//Cnd cnd=Cnd.select().andEquals(M.Menu.parent_id, "root".equals(parent_id)?null:parent_id));
//		if("root".equals(parent_id)){
//			parent_id=null;
//		}
//		List<MenuVO> menues=menuService.query_checkbox(null);
//		return menues;
		roleService.checkNodes(role_id, ids, checked);
		return;
	}
	
}
