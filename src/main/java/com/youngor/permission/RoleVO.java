package com.youngor.permission;

import com.mawujun.generator.model.FieldDefine;

public class RoleVO extends Role {
	
	@FieldDefine(title="角色组名称",hidden=false)
	private String parent_name;

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

}
