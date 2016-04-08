package com.youngor.permission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//@RequestMapping("/menu")
public class MenuController {

	@Resource
	private MenuService menuService;

	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/menu/query.do")
	@ResponseBody
	public List<Menu> query(String parent_id,MenuType menuType) {
		//System.out.println(MenuType.menu);
		Cnd cnd=Cnd.select().andEquals(M.Menu.parent_id, "root".equals(parent_id)?null:parent_id)
				.andEquals(M.Menu.menuType, menuType==null?MenuType.menu:menuType);
		List<Menu> menues=menuService.query(cnd);
		return menues;
	}
	
	
	@RequestMapping("/menu/query_checkbox.do")
	@ResponseBody
	public List<MenuVO> query_checkbox(String parent_id) {
		//System.out.println(MenuType.menu);
		//Cnd cnd=Cnd.select().andEquals(M.Menu.parent_id, "root".equals(parent_id)?null:parent_id));
		if("root".equals(parent_id)){
			parent_id=null;
		}
		List<MenuVO> menues=menuService.query_checkbox(null);
		return menues;
	}
	
	@RequestMapping("/menu/query_checked_node.do")
	@ResponseBody
	public Map<String,Boolean> query_checked_node(String role_id) {
		//List<String> result=new ArrayList<String>();
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		List<RoleMenu> list=menuService.query_checked_node(role_id);
		for(RoleMenu roleMenu:list){
			//result.add(roleMenu.getMenu().getId());
			result.put(roleMenu.getMenu().getId(), true);
		}
		return result;
	}

	/**
	 * 用户登录的时候获取可访问的菜单
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	@RequestMapping("/menu/queryByUser.do")
	@ResponseBody
	public List<MenuVO> queryByUser() {	
		List<MenuVO> menues=menuService.queryByUser(null,ShiroUtils.getUserId());
		return menues;
	}

	@RequestMapping("/menu/queryAll.do")
	@ResponseBody
	public List<Menu> queryAll() {	
		List<Menu> menues=menuService.queryAll();
		return menues;
	}
	

	@RequestMapping("/menu/load.do")
	public Menu load(String id) {
		return menuService.get(id);
	}
	
	@RequestMapping("/menu/create.do")
	//@ResponseBody
	public Menu create(@RequestBody Menu menu) {
		if("root".equals(menu.getParent_id())){
			menu.setParent_id(null);
		}
		menuService.create(menu);
		return menu;
	}
	
	@RequestMapping("/menu/update.do")
	//@ResponseBody
	public  Menu update(@RequestBody Menu menu) {
		menuService.update(menu);
		return menu;
	}
	
	@RequestMapping("/menu/deleteById.do")
	//@ResponseBody
	public String deleteById(String id) {
		menuService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/menu/destroy.do")
	//@ResponseBody
	public Menu destroy(@RequestBody Menu menu) {
		menuService.delete(menu);
		return menu;
	}
	
	
}
