package com.youngor.permission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.org.OrgRepository;
import com.youngor.org.PositionOrgUser;
import com.youngor.org.PositionOrgUserRepository;
import com.youngor.org.PositionRepository;
import com.youngor.utils.M;

/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService extends AbstractService<User, String> {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private OrgRepository orgRepository;
	@Autowired
	private PositionOrgUserRepository positionOrgUserRepository;
	@Autowired
	private RoleUserRepository userRoleRepository;

	@Override
	public UserRepository getRepository() {
		return userRepository;
	}

//	public void create(User user, String role_id) {
//		super.create(user);
//		RoleUser userRole = new RoleUser(user, roleRepository.get(role_id));
//		userRoleRepository.create(userRole);
//	}
	
	public void create(User user,String position_id,String orgno) {
		super.create(user);
		PositionOrgUser positionOrgUser = new PositionOrgUser(positionRepository.load(position_id),orgRepository.load(orgno),user);
		positionOrgUserRepository.create(positionOrgUser);
	}
	public void addRole(String user_id,String role_id) {
		RoleUser userRole = new RoleUser(userRepository.load(user_id), roleRepository.load(role_id));
		userRoleRepository.create(userRole);
	}
	public void deleteByRole(String user_id,String role_id) {
		userRoleRepository.delete(new RoleUser(userRepository.load(user_id), roleRepository.load(role_id)));

		//super.delete(user);
	}

	public UserVO getByLoginName(String loginName) {
		UserVO user = userRepository.getByLoginName(loginName);
		return user;
	}

	/**
	 * 获取用户的权限
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String user_id) {
		//这里最好再添加进去用户所属的数据权限，然后放到用户里面
		List<String> list = userRepository.findPermissions(user_id);
		Set<String> set = new HashSet<String>();
		set.addAll(list);
		return set;
	}
	
	public Pager<User> queryByPosition(Pager<User> pager){
		return userRepository.queryByPosition(pager);
	}
	@Override
	public void delete(User user) {
		userRoleRepository.deleteBatch(Cnd.delete().andEquals(M.RoleUser.user.id, user.getId()));
		positionOrgUserRepository.deleteBatch(Cnd.delete().andEquals(M.PositionOrgUser.user.id, user.getId()));
		this.getRepository().delete(user);
	}
}
