package com.youngor.permission;

public enum RoleType {
	role("角色"),rolegroup("角色组");
	
	private String name;
	
	RoleType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

}
