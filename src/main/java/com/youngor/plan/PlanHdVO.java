package com.youngor.plan;

import com.youngor.pubcode.PubCodeCache;

public class PlanHdVO extends PlanHd {
	private String orgnm;
	
	private Boolean isTotal=false;
	
	public String getBradno_name(){
		return PubCodeCache.getBradno_name(super.getBradno());
	}
	public String getSpclno_name(){
		return PubCodeCache.getSpclno_name(super.getSpclno());
	}

	public String getOrgnm() {
		return orgnm;
	}

	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}
	public Boolean getIsTotal() {
		return isTotal;
	}
	public void setIsTotal(Boolean isTotal) {
		this.isTotal = isTotal;
	}
}
