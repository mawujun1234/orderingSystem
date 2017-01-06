package com.youngor.sample;

import com.youngor.pubcode.PubCodeCache;

public class SamplePlanVO extends SamplePlan {
	
	

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
	public String getPlgrno_name() {
		return PubCodeCache.getPlgrno_name(this.getPlgrno());
	}

	

}
