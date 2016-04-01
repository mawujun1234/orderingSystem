package com.youngor.permission;

import java.util.List;

public class MenuVO extends Menu{
	private Boolean checked=null;
	private Boolean expanded=false;
	
	private List<MenuVO> children;
	

	
	public Boolean getLeaf(){
		if(super.getMenuType()==MenuType.element){
			return true;
		}
		return false;
	}
	
	public String getIconCls(){
		if(super.getMenuType()==MenuType.element){
			return "icon-cog";
		}
		return "icon-cogs";
	}
	



	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<MenuVO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVO> children) {
		this.children = children;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
}
