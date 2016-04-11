package com.youngor.permission;

import org.apache.shiro.SecurityUtils;

public class ShiroUtils {
	/**
	 * 获取用户的登陆名
	 * @return
	 */
	public static String getLoginName(){
		return ((UserVO)SecurityUtils.getSubject().getPrincipal()).getLoginName();
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
	 * 获取登陆用户的id
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public static String getUserId(){
		return ShiroUtils.getAuthenticationInfo().getId();
	}
	/**
	 * 获取当前登陆的用户
	 * @return
	 */
	public static UserVO getAuthenticationInfo(){
		return (UserVO)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
	}

}
