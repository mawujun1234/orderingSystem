package com.youngor.permission;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.exception.BusinessException;

/**
 * http://jinnianshilongnian.iteye.com/blog/2022468
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;


	/**
	 * 通过显示的调用SecurityUtils.getSubject().hasRole("XXX") ;来初始用户权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		User user= (User) principals.getPrimaryPrincipal();
		String user_id =user.getId();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		if(userService==null){
  		  this.setUserService(SpringContextHolder.getBean(UserService.class));
  	  	}
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
	    	  if(userService==null){
	    		  this.setUserService(SpringContextHolder.getBean(UserService.class));
	    	  }
	    	  UserVO user = userService.getByLoginName( username ); 
	    	  if(user == null) {  
	              throw new BusinessException("该账号不存在!");//没找到帐号  
	          }  
//	          if(Boolean.TRUE.equals(user.getLocked())) {  
//	              throw new LockedAccountException(); //帐号锁定  
//	          }  
	         
	         if( user != null ){ 
	        	//MyAuthenticationInfo aa=new MyAuthenticationInfo(user,user.getPassword(),getName());
	        	//aa.setLoginTime(new Date());
	        	//return aa;
	        	user.setLoginDate(new Date());
	        	
	        	//doGetAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
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
