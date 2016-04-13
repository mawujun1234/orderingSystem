package com.youngor.sample;

import com.youngor.pubcode.PubCodeCache;

public class SamplePlanVO extends SamplePlan {
	
//	private String bradno_name;
//	private String spsean_name;
//	private String spbseno_name;
//	private String sprseno_name;
//	private String spclno_name;
//	private String sptyno_name;
//	private String spseno_name;
//	private String splcno_name;
//	private String spbano_name;
	public String getBradno_name() {
		return PubCodeCache.getBradno_name(this.getBradno());
	}
	public String getSpsean_name() {
		return PubCodeCache.getSpsean_name(this.getSpsean());
	}
	public String getSpbseno_name() {
		return PubCodeCache.getSpbseno_name(this.getSpbseno());
	}
	public String getSprseno_name() {
		return PubCodeCache.getSprseno_name(this.getSprseno());
	}
	public String getSpclno_name() {
		return PubCodeCache.getSpclno_name(this.getSpclno());
	}
	public String getSptyno_name() {
		return PubCodeCache.getSptyno_name(this.getSptyno());
	}
	public String getSpseno_name() {
		return PubCodeCache.getSpseno_name(this.getSpseno());
	}
	public String getSplcno_name() {
		return PubCodeCache.getSplcno_name(this.getSplcno());
	}
	public String getSpbano_name() {
		return PubCodeCache.getSpbano_name(this.getSpbano());
	}

	

}
