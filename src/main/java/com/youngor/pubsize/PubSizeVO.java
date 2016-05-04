package com.youngor.pubsize;

import com.youngor.pubcode.PubCodeCache;

public class PubSizeVO extends PubSize {
	public String getSzbrad_name(){
		return PubCodeCache.getBradno_name(super.getSzbrad());
	}
	public String getSzclno_name(){
		return PubCodeCache.getSpclno_name(super.getSzclno());
	}
	
	public String getSizety1_name(){
		if(this.getSizest()==1){
			return "混装";
		} else {
			return "单规装";
		}
	}
	
	public String getSizest_name(){
		if(this.getSizest()==1){
			return "有效";
		} else {
			return "作废";
		}
	}
	
	public String getSzsast_name(){
		if(this.getSzsast()==1){
			return "当季";
		} else {
			return "非当季";
		}
	}

}
