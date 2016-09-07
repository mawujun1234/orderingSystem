package com.youngor.order;

public enum Ortyno {
	DZ("订货"),TP("统配"),BW("备忘");
	private String name;
	
	Ortyno(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
