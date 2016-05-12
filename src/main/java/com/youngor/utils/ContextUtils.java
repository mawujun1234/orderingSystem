package com.youngor.utils;

import java.util.List;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.youngor.ordmt.Ordmt;
import com.youngor.ordmt.OrdmtService;
import com.youngor.permission.ShiroUtils;
import com.youngor.permission.UserVO;

public class ContextUtils {
	/**
	 * 获取可访问品牌中的第一个品牌
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public static String getFirstBradno(){
		UserVO userVO=ShiroUtils.getAuthenticationInfo();
		if(userVO.getBrandes()==null || userVO.getBrandes().size()==0){
			throw new BusinessException("请先为用户授予可访问的品牌!");
		} else {
			return userVO.getBrandes().get(0);
		}
		
	}
	/**
	 * 获取当前正在使用的订货会
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public static Ordmt getFirstOrdmt(){
		OrdmtService ordmtService=SpringContextHolder.getBean(OrdmtService.class);
		List<Ordmt> ordmtes=ordmtService.query(Cnd.select().asc(M.Ordmt.ormtno));
		return ordmtes.get(0);
		
	}
}
