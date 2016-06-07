package com.youngor.ordmt;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.service.AbstractService;
import com.youngor.org.AccessRule;
import com.youngor.org.Dim;
import com.youngor.org.Org;
import com.youngor.org.OrgRepository;
import com.youngor.org.Position;
import com.youngor.org.PositionOrgUser;
import com.youngor.org.PositionOrgUserRepository;
import com.youngor.org.PositionRepository;
import com.youngor.org.PositionService;
import com.youngor.permission.RoleRepository;
import com.youngor.permission.RoleUser;
import com.youngor.permission.RoleUserRepository;
import com.youngor.permission.User;
import com.youngor.permission.UserService;
import com.youngor.utils.ContextUtils;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrdOrgService extends AbstractService<OrdOrg, com.youngor.ordmt.OrdOrg.PK>{

	@Autowired
	private OrdOrgRepository ordOrgRepository;
	@Autowired
	private OrgRepository orgRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private PositionService positionService;
	@Autowired
	private PositionOrgUserRepository positionOrgUserRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleUserRepository roleUserRepository;
	@Override
	public OrdOrgRepository getRepository() {
		return ordOrgRepository;
	}

	/**
	 * 把所有用户都放在订货员这个职位里面，同时放到某个角色里面，比如区域就需要登录到系统进行访问
	 * 和角色挂钩的目的是让区域可以登录系统进行访问
	 * 
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param addModel
	 * @param ordorg
	 * @param sztype
	 * @param role_id
	 */
	public void create(String addModel,String ordorg,Integer sztype,String role_id)  {
		String ormtno=ContextUtils.getFirstOrdmt().getOrmtno();
		if("single".equals(addModel)){
			Org org=orgRepository.get(ordorg);
			OrdOrg ordOrg=new OrdOrg();
			ordOrg.setChanno(org.getChanno().toString());
			ordOrg.setOrdorg(ordorg);
			ordOrg.setOrmtno(ormtno);
			ordOrg.setSztype(sztype);
			ordOrg.setPrint(0);
			ordOrgRepository.create(ordOrg);
			//同时生成用户名和默认密码，账号是区域代码，密码是0
			User user=userService.getByLoginName(ordorg.toLowerCase());
			if(user==null){
				user=new User();
				user.setLoginName(ordorg.toLowerCase());
				user.setPwd("0");
				user.setName(org.getName());
				userService.create(user);
			}
			//同时挂到这个组织的单元所在的“订货员”职位上，如果没有职员职位，就创建一个
			List<Position> positiones=positionRepository.queryPositionByName(ordorg, "订货员");
			Position position=null;
			if(positiones==null || positiones.size()==0){
				position=new Position();
				position.setName("订货员");
				position.setOrgno(ordorg);
				//定义该职位可以访问的组织单元
				position.setAccessRule(AccessRule.this_org);
				
				position.setPositionType_id(null);
				positionRepository.create(position);
				positionService.this_org(position, Dim.SALE);
				
			} else {
				position=positiones.get(0);
			}
			//建立用户，组织单元，职位的挂钩
			PositionOrgUser positionOrgUser=new PositionOrgUser();
			positionOrgUser.setOrg(org);
			positionOrgUser.setPosition(position);
			positionOrgUser.setUser(user);
			positionOrgUserRepository.create(positionOrgUser);
			//用户和角色进行挂钩
			RoleUser roleUser=new RoleUser();
			roleUser.setRole(roleRepository.load(role_id));
			roleUser.setUser(user);
			roleUserRepository.create(roleUser);
			
			
		} else if("allQY".equals(addModel)){
			
		} else if("allTX".equals(addModel)){
			
		}

	}
	
	public OrdOrg getOrdOrgByOrg(String ormtno,String orgno) {
		return ordOrgRepository.getOrdOrgByOrg(ormtno, orgno);
	}
	
	public void destroy(OrdOrg ordOrg) {
		super.delete(ordOrg);
		
		//删除这个用户
		String loginName=ordOrg.getOrdorg().toLowerCase();
		User user=userService.getByLoginName(loginName);
		
		
		positionRepository.delete_t_position_org_userByUser(user.getId());
		roleRepository.delete_t_role_userByUser(user.getId());
		
		userService.deleteUserByLoginName(loginName);
		
	}
}
