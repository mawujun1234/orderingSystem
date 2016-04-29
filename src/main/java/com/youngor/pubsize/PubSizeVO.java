package com.youngor.pubsize;

public class PubSizeVO extends PubSize {
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
