package com.youngor.pubcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.repository.cnd.Cnd;
import com.youngor.utils.M;

public class PubCodeCache {

	public static Map<String,Map<String,PubCode>> cache=new HashMap<String,Map<String,PubCode>>();
	//private Map<String,Map<String,PubCode>> cache=new HashMap<String,Map<String,PubCode>>();
	
	public static void setPubCode(PubCodeType pubCodeType,List<PubCode> pubCodes) {
		Map<String,PubCode> map=new HashMap<String,PubCode>();
		for(PubCode pubCode:pubCodes){
			map.put(pubCode.getItno(), pubCode);
		}
		cache.put(pubCodeType.getTyno(), map);
	}
	public static void setPubCode(String pubCodeType_id,List<PubCode> pubCodes) {
		Map<String,PubCode> map=new HashMap<String,PubCode>();
		for(PubCode pubCode:pubCodes){
			map.put(pubCode.getItno(), pubCode);
		}
		cache.put(pubCodeType_id, map);
	}
//	private static boolean isRefreshing=false;
//	public static void refreshPubCode(){
//		if(isRefreshing){
//			return;
//		}
//		isRefreshing=true;
//		PubCodeTypeService pubCodeTypeService=SpringContextHolder.getBean(PubCodeTypeService.class);
//		PubCodeService pubCodeService=SpringContextHolder.getBean(PubCodeService.class);
//		List<PubCodeType> pubCodeTypes=pubCodeTypeService.queryAll();
//		for(PubCodeType pubCodeType:pubCodeTypes){
//			List<PubCode> pubCodes=pubCodeService.query(Cnd.select().andEquals(M.PubCode.tyno, pubCodeType.getTyno()));
//			PubCodeCache.setPubCode(pubCodeType, pubCodes);
//		}
//		isRefreshing=false;
//	}
	
	private static Map<String,Boolean> isRefreshing_map=new HashMap<String,Boolean>();
	public static void refreshPubCode(String pubCodeType_id){
//		if(isRefreshing){
//			return;
//		}
//		isRefreshing=true;
//		PubCodeTypeService pubCodeTypeService=SpringContextHolder.getBean(PubCodeTypeService.class);
//		PubCodeService pubCodeService=SpringContextHolder.getBean(PubCodeService.class);
//		List<PubCodeType> pubCodeTypes=pubCodeTypeService.queryAll();
//		for(PubCodeType pubCodeType:pubCodeTypes){
//			List<PubCode> pubCodes=pubCodeService.query(Cnd.select().andEquals(M.PubCode.tyno, pubCodeType.getTyno()));
//			PubCodeCache.setPubCode(pubCodeType, pubCodes);
//		}
//		isRefreshing=false;
		
		if(isRefreshing_map.get(pubCodeType_id)!=null && isRefreshing_map.get(pubCodeType_id)){
			return;
		}
		isRefreshing_map.put(pubCodeType_id, true);
		//PubCodeTypeService pubCodeTypeService=SpringContextHolder.getBean(PubCodeTypeService.class);
		//PubCodeType pubCodeType=pubCodeTypeService.get(pubCodeType_id);
		PubCodeService pubCodeService=SpringContextHolder.getBean(PubCodeService.class);
		List<PubCode> pubCodes=pubCodeService.query(Cnd.select().andEquals(M.PubCode.tyno, pubCodeType_id));
		PubCodeCache.setPubCode(pubCodeType_id, pubCodes);
		isRefreshing_map.put(pubCodeType_id, false);
	}
	/**
	 * 获取某个品牌
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getBradno(String itno){
		if(itno!=null && cache.get("1").get(itno)==null) {
			refreshPubCode("1");
		}
		return cache.get("1").get(itno);
	}
	public static String getBradno_name(String itno){
		PubCode pubCode=getBradno(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
		
	}
	/**
	 * 获取季节
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getSpsean(String itno){
		if(itno!=null && cache.get("8").get(itno)==null) {
			refreshPubCode("8");
		}
		return cache.get("8").get(itno);
	}
	
	public static String getSpsean_name(String itno){
		PubCode pubCode=getSpsean(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	
	
	/**
	 * 获取大系列
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getSpbseno(String itno){
		if(itno!=null && cache.get("17").get(itno)==null) {
			refreshPubCode("17");
		}
		return cache.get("17").get(itno);
	}
	public static String getSpbseno_name(String itno){
		PubCode pubCode=getSpbseno(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	/**
	 * 获取品牌系列
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getSprseno(String itno){
		if(itno!=null && cache.get("10").get(itno)==null) {
			refreshPubCode("10");
		}
		return cache.get("10").get(itno);
	}
	public static String getSprseno_name(String itno){
		PubCode pubCode=getSprseno(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	/**
	 * 获取大类
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getSpclno(String itno){
		if(itno!=null && cache.get("0").get(itno)==null) {
			refreshPubCode("0");
		}
		return cache.get("0").get(itno);
	}
	
	public static String getSpclno_name(String itno){
		PubCode pubCode=getSpclno(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	/**
	 * 获取小类
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getSptyno(String itno){
		if(itno!=null && cache.get("2").get(itno)==null) {
			refreshPubCode("2");
		}
		return cache.get("2").get(itno);
	}
	
	public static String getSptyno_name(String itno){
		PubCode pubCode=getSptyno(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	/**
	 * 获取系列
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getSpseno(String itno){
		if(itno!=null && cache.get("5").get(itno)==null) {
			refreshPubCode("5");
		}
		return cache.get("5").get(itno);
	}
	public static String getSpseno_name(String itno){
		PubCode pubCode=getSpseno(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	/**
	 * 获取定位
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getSplcno(String itno){
		if(itno!=null && cache.get("18").get(itno)==null) {
			refreshPubCode("18");
		}
		return cache.get("18").get(itno);
	}
	public static String getSplcno_name(String itno){
		PubCode pubCode=getSplcno(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	/**
	 * 获取上市批次
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getSpbano(String itno){
		if(itno!=null && cache.get("23").get(itno)==null) {
			refreshPubCode("23");
		}
		return cache.get("23").get(itno);
	}
	public static String getSpbano_name(String itno){
		PubCode pubCode=getSpbano(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	
	/**
	 * 获取套件
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getSuitno(String itno){
		if(itno!=null && cache.get("3").get(itno)==null) {
			refreshPubCode("3");
		}
		return cache.get("3").get(itno);
	}
	public static String getSuitno_name(String itno){
		PubCode pubCode=getSuitno(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	/**
	 * 获取版型
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getVersno(String itno){
		if(itno!=null && cache.get("13").get(itno)==null) {
			refreshPubCode("13");
		}
		return cache.get("13").get(itno);
	}
	public static String getVersno_name(String itno){
		PubCode pubCode=getVersno(itno);
		if(pubCode==null){
			return "";
		} else {
			return pubCode.getItnm();
		}
	}
	
}
