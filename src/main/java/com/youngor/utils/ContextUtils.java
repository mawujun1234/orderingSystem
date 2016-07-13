package com.youngor.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.exception.BusinessException;
import com.youngor.order.Ordty;
import com.youngor.order.OrdtyRepository;
import com.youngor.ordmt.Ordmt;
import com.youngor.ordmt.OrdmtController;
import com.youngor.org.Channo;
import com.youngor.org.ChannoService;
import com.youngor.permission.ShiroUtils;
import com.youngor.permission.UserVO;
import com.youngor.suno.PubSuno;
import com.youngor.suno.PubSunoService;

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
			return userVO.getBrandes().get(0).getItno();
		}
		
	}
	/**
	 * 获取当前正在使用的订货会
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public static Ordmt getFirstOrdmt(){
		OrdmtController ordmtService=SpringContextHolder.getBean(OrdmtController.class);
		List<Ordmt> ordmtes=ordmtService.query4Combo();
		return ordmtes.get(0);
		
	}
	
	/**
	 * 获取当前正在使用的订货会
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	private static Map<String,Ordmt> ormtno_map=new HashMap<String,Ordmt>();
	private static Boolean ormtno_bool=false;
	public static Ordmt getOrdmt(String ormtno){
		
		if(ormtno_map==null || ormtno_map.size()==0 || ormtno_map.get(ormtno)==null){
			if(ormtno_bool){
				return null;
			}
			ormtno_bool=true;
			OrdmtController ordmtService=SpringContextHolder.getBean(OrdmtController.class);
			List<Ordmt> ordmtes=ordmtService.query4Combo();
			for(Ordmt ordmt:ordmtes){
				ormtno_map.put(ordmt.getOrmtno(), ordmt);
			}
			ormtno_bool=false;
		}
		return ormtno_map.get(ormtno);
		
	}
	
	
	/**
	 * 获取订单类型
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	private static Map<String,Ordty> ordty_map=new HashMap<String,Ordty>();
	public static Ordty getOrdty(String ortyno){
		if(ortyno==null || "".equals(ortyno)){
			return null;
		}
		if(ordty_map==null || ordty_map.size()==0 || ordty_map.get(ortyno)==null){
			OrdtyRepository ordtyRepository=SpringContextHolder.getBean(OrdtyRepository.class);
			List<Ordty> ordtyes=ordtyRepository.queryAll();
			for(Ordty ordty:ordtyes){
				ordty_map.put(ordty.getOrtyno(), ordty);
			}
		}
		
		return ordty_map.get(ortyno);
		
	}
	
	/**
	 * 获取渠道类型
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	private static Map<String,Channo> channo_map=new HashMap<String,Channo>();
	public static Channo getChanno(String channo){
		if(channo==null || "".equals(channo)){
			return null;
		}
		if(channo_map==null || channo_map.size()==0 || channo_map.get(channo)==null){
			ChannoService channoService=SpringContextHolder.getBean(ChannoService.class);
			List<Channo> ordtyes=channoService.queryAll();
			for(Channo chan:ordtyes){
				channo_map.put(chan.getChanno(), chan);
			}
		}
		
		return channo_map.get(channo);
		
	}
	/**
	 * 所有供应商
	 */
	private static Map<String,PubSuno> pubSuno_map=new HashMap<String,PubSuno>();
	private static Boolean pubSuno_bool=false;
	public static PubSuno getPubSuno(String idsuno){
		if(idsuno==null || "".equals(idsuno)){
			return null;
		}
		if(pubSuno_map==null || pubSuno_map.size()==0 || pubSuno_map.get(idsuno)==null){
			if(pubSuno_bool){
				return null;
			}
			pubSuno_bool=true;
			PubSunoService pubSunoService=SpringContextHolder.getBean(PubSunoService.class);
			List<PubSuno> pubSunoes=pubSunoService.queryAll();
			for(PubSuno pubSuno:pubSunoes){
				pubSuno_map.put(pubSuno.getIdsuno(), pubSuno);
			}
			pubSuno_bool=false;
		}
		
		return pubSuno_map.get(idsuno);
		
	}

}
