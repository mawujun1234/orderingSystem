package com.youngor.ordmt;

import java.util.List;

import com.youngor.pubcode.PubCodeCache;

public class OrdmtVO extends Ordmt {
	
	private List<OrdmtSeason> seasnos;
	
	public String getSeasnos_name(){
		if(this.seasnos!=null && this.seasnos.size()>0){
			StringBuilder builder=new StringBuilder();
			for(OrdmtSeason season :seasnos){
				builder.append(PubCodeCache.getSpsean_name(season.getSeasno()));
				builder.append(",");
			}
			return builder.substring(0, builder.length()-1);
		} else {
			return null;
		}
	}

	public List<OrdmtSeason> getSeasnos() {
		return seasnos;
	}

	public void setSeasnos(List<OrdmtSeason> seasnos) {
		this.seasnos = seasnos;
	}


	
	
	
}
