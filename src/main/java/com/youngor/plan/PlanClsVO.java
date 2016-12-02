package com.youngor.plan;

import com.youngor.pubcode.PubCodeCache;

public class PlanClsVO extends PlanCls {
	public String getBradno_name() {
		return PubCodeCache.getBradno_name(this.getBradno());
	}
	
	public String getSpclno_name() {
		return PubCodeCache.getSpclno_name(this.getSpclno());
	}
	public String getSpseno_name() {
		return PubCodeCache.getSpseno_name(this.getSpseno());
	}
	
}
