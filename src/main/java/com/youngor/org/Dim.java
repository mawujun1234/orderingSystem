package com.youngor.org;

/**
 * 
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public enum Dim {
	sale("销售渠道"),drp("物资渠道");
	
	private String name;
	
	Dim(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
}
