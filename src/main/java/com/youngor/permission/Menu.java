package com.youngor.permission;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.generator.model.FieldDefine;

@Entity(name="t_menu")
public class Menu {
	@Id
	@FieldDefine(title="id",hidden=true)
	private UUID id;
	
	@Column(length=30)
	@FieldDefine(title="菜单名称")
	private String name;
	
	@Column(length=80)
	@FieldDefine(title="地址")
	private String url;
	
	@Column(length=15)
	@FieldDefine(title="菜单类型")
	private MenuType menuType=MenuType.menu;
	
	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	@Column(length=150)
	@FieldDefine(title="备注")
	private String remark;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
