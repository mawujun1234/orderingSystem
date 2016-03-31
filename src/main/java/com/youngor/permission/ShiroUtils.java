package com.youngor.permission;

import org.apache.shiro.SecurityUtils;

public class ShiroUtils {
	public static String getLoginName(){
		return SecurityUtils.getSubject().getPrincipal().toString();
	}
	/**
	 * 获取用户的姓名
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public static String getUserName(){
		return ShiroUtils.getAuthenticationInfo().getName();
	}
	/**
	 * 获取用户的id
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public static String getUserId(){
		return ShiroUtils.getAuthenticationInfo().getId();
	}
	
	public static UserVO getAuthenticationInfo(){
		return (UserVO)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
	}

}
