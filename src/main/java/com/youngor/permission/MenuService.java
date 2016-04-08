package com.youngor.permission;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.youngor.permission.Menu;
import com.youngor.permission.MenuRepository;
import com.youngor.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MenuService extends AbstractService<Menu, String>{

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private RoleMenuRepository roleMenuRepository;
	
	@Override
	public MenuRepository getRepository() {
		return menuRepository;
	}
	
	public List<MenuVO> query_checkbox(String parent_id) {
		List<MenuVO> parent_list= menuRepository.query_checkbox(parent_id);
		for(MenuVO parent:parent_list){
			parent.setExpanded(true);
			List<MenuVO> children_list= menuRepository.query_checkbox(parent.getId());
			parent.setChildren(children_list);
		}
		return parent_list;
	}
	
	public List<RoleMenu> query_checked_node(String role_id) {
		return roleMenuRepository.query(Cnd.select().andEquals(M.RoleMenu.role.id, role_id));
	}
	/**
	 * 获取某个用户的菜单,只获取菜单元素，不获取界面元素
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param parent_id
	 * @return
	 */
	public List<MenuVO> queryByUser(String parent_id,String user_id) {
		List<MenuVO> parent_list= menuRepository.queryByUser(parent_id,user_id);
		for(MenuVO parent:parent_list){
			//parent.setChecked(null);
			parent.setExpanded(true);
			List<MenuVO> children_list= this.queryByUser(parent.getId(),user_id);
			parent.setChildren(children_list);
		}
		return parent_list;
	}

}
