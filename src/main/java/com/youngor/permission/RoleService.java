package com.youngor.permission;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.youngor.pubcode.PubCode;
import com.youngor.pubcode.PubCodeRepository;
import com.youngor.utils.M;


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
	
	@Autowired
	private PubCodeRepository pubCodeRepository;
	@Autowired
	private RoleBrandRepository roleBrandRepository;
	@Autowired
	private RoleClassRepository roleClassRepository;
	
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
	
	public List<String> querySelBrand(String role_id ) {
		return roleRepository.querySelBrand(role_id);
	}
	public void selBrand(String role_id,String itno) {
		Role role=roleRepository.load(role_id);
		PubCode pubCode=pubCodeRepository.load(itno);
		
		RoleBrand roleBrand=new RoleBrand(pubCode,role);
		roleBrandRepository.create(roleBrand);
	}

	public void deselBrand(String role_id,String itno) {
		Role role=roleRepository.load(role_id);
		PubCode pubCode=pubCodeRepository.load(itno);
		
		RoleBrand roleBrand=new RoleBrand(pubCode,role);
		roleBrandRepository.delete(roleBrand);
	}
	
	public List<String> querySelClass(String role_id ) {
		return roleRepository.querySelClass(role_id);
	}
	public void selClass(String role_id,String itno) {
		Role role=roleRepository.load(role_id);
		//PubCode pubCode=pubCodeRepository.load(itno);
		
		RoleClass roleClass=new RoleClass(itno,role);
		roleClassRepository.create(roleClass);
	}

	public void deselClass(String role_id,String itno) {
		Role role=roleRepository.load(role_id);
		//PubCode pubCode=pubCodeRepository.load(itno);
		
		RoleClass roleClass=new RoleClass(itno,role);
		roleClassRepository.delete(roleClass);
	}
	
	
	//-----------
	public List<PubCode> queryUserSelBrand(String user_id ) {
		return roleRepository.queryUserSelBrand(user_id);
	}
	public List<String> queryUserSelClass(String user_id ) {
		return roleRepository.queryUserSelClass(user_id);
	}

}
