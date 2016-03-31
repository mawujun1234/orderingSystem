package com.youngor.permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class RoleService extends AbstractService<Role, String>{

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private RoleMenuRepository roleMenuRepository;
	
	@Override
	public RoleRepository getRepository() {
		return roleRepository;
	}
	
	public void checkNodes(String role_id,String ids[],Boolean checked) {
		Role role=roleRepository.load(role_id);
		for(String id:ids){
			RoleMenu roleMenu=new RoleMenu(menuRepository.load(id),role);
			if(checked){
				roleMenuRepository.create(roleMenu);
			} else {
				roleMenuRepository.delete(roleMenu);
			}
			
		}
	}

}
