package com.youngor.permission;

public enum MenuType {
	menu("菜单"),element("界面元素");
	
	private String name;
	
	MenuType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}


}
