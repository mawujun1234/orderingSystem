package com.youngor.permission;

import java.util.Date;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class ShiroAuthorizingRealm extends AuthorizingRealm {
	
	private UserService userService;


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		User user= (User) principals.getPrimaryPrincipal();
		String user_id =user.getId();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//authorizationInfo.setRoles(userService.findRoles(username));
		authorizationInfo.setStringPermissions(userService.findPermissions(user_id));
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken; 
	      // 通过表单接收的用户名
	      String username = token.getUsername(); 

	      
	      if( username != null && !"".equals(username) ){ 
	    	  UserVO user = userService.getByLoginName( username ); 
	         
	         if( user != null ){ 
	        	//MyAuthenticationInfo aa=new MyAuthenticationInfo(user,user.getPassword(),getName());
	        	//aa.setLoginTime(new Date());
	        	//return aa;
	        	user.setLoginDate(new Date());
	            return new SimpleAuthenticationInfo(user,user.getPwd(),getName()); 
	         } 
	      } 
	      
	      return null; 
	}
	


	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
}
