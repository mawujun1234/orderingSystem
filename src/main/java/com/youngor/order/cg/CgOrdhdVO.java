package com.youngor.order.cg;

import com.youngor.pubcode.PubCodeCache;

public class CgOrdhdVO extends CgOrdhd {
	public String getBradno_name(){
		return PubCodeCache.getBradno_name(super.getBradno());
	}
	public String getSpclno_name(){
		return PubCodeCache.getSpclno_name(super.getSpclno());
	}
	
	public String getOrstat_name(){
		if(this.getOrstat()==1){
			return "已确认";
		} else {
			return "编辑中";
		}
	}
	public String getIsfect_name(){
		if(this.getOrstat()==1){
			return "有效";
		} else {
			return "无效";
		}
	}
}
