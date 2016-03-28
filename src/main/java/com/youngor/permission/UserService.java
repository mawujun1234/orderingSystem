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
public class UserService extends AbstractService<User, String>{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	public UserRepository getRepository() {
		return userRepository;
	}
	
	public void create(User user,String role_id){
		super.create(user);
		UserRole userRole=new UserRole(user,roleRepository.get(role_id));
		userRoleRepository.create(userRole);
	}

	public void delete(User user,String role_id){
		userRoleRepository.delete(new UserRole(user,roleRepository.get(role_id)));
		
		super.delete(user);
	}
}
