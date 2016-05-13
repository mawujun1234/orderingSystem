package com.youngor.pubcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PubCodeCache {

	private static Map<String,Map<String,PubCode>> cache=new HashMap<String,Map<String,PubCode>>();
	//private Map<String,Map<String,PubCode>> cache=new HashMap<String,Map<String,PubCode>>();
	
	public static void setPubCode(PubCodeType pubCodeType,List<PubCode> pubCodes) {
		Map<String,PubCode> map=new HashMap<String,PubCode>();
		for(PubCode pubCode:pubCodes){
			map.put(pubCode.getItno(), pubCode);
		}
		cache.put(pubCodeType.getTyno(), map);
	}
	/**
	 * 获取某个品牌
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param itno
	 * @return
	 */
	public static PubCode getBradno(String itno){
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
