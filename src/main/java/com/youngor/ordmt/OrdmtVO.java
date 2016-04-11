package com.youngor.ordmt;

import java.util.List;

public class OrdmtVO extends Ordmt {
	
	private List<OrdmtSeason> seasnos;
	
	public String getSeasnos_name(){
		if(this.seasnos!=null){
			StringBuilder builder=new StringBuilder();
			for(OrdmtSeason season :seasnos){
				builder.append(season.getSeasno());
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
