package com.youngor.permission;

import org.apache.shiro.SecurityUtils;

import com.mawujun.exception.BusinessException;

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
	/**
	 * 获取可访问品牌中的第一个品牌
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public static String getFirstBradno(){
		UserVO userVO=getAuthenticationInfo();
		if(userVO.getBrandes()==null || userVO.getBrandes().size()==0){
			throw new BusinessException("请先为用户授予可访问的品牌!");
		} else {
			return userVO.getBrandes().get(0);
		}
		
	}

}
