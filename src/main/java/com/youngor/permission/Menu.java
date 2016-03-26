package com.youngor.permission;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.generator.model.FieldDefine;
import com.mawujun.generator.model.ShowType;

@Entity(name="t_menu")
public class Menu {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(
	        name = "uuid",
	        strategy = "org.hibernate.id.UUIDGenerator"
	    )
	@FieldDefine(title="id",hidden=true)
	@Column(length=36)
	private String id;
	
	@Column(length=30,nullable=false)
	@FieldDefine(title="菜单名称",sort=5,genQuery=true)
	private String name;
	
	@Column(length=80)
	@FieldDefine(title="地址",sort=4)
	private String url;
	
	@Column(length=15,nullable=false)
	@FieldDefine(title="菜单类型",sort=5,showType=ShowType.combobox,genQuery=true)//,hidden=true
	private MenuType menuType=MenuType.menu;
	
	@FieldDefine(title="父id",hidden=true)
	@Column(length=36)
	private String parent_id;//上级rolegroup的id
	
	@FieldDefine(title="叶子节点",sort=5,genQuery=true)
	private Boolean leaf;
	@FieldDefine(title="创建时间",sort=5,genQuery=true)
	private Date createDate;
	
	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	@Column(length=150)
	@FieldDefine(title="备注")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
