package com.youngor.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.generator.model.FieldDefine;
import com.mawujun.generator.model.ShowType;

/**
 * 只设计成两级，第一级是rolegroup，第二级才是角色
 * @author mawujun
 *
 */
@Entity(name="t_role")
public class Role {
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
	@FieldDefine(title="角色名称")
	private String name;
	
	@Column(length=15,nullable=false)
	@Enumerated(EnumType.STRING)
	@FieldDefine(title="角色类型",sort=5,showType=ShowType.combobox)//
	private RoleType roleType=RoleType.rolegroup;
	
//	@FieldDefine(title="是否叶子")
//	private Boolean leaf=true;
	
	@Column(length=150)
	@FieldDefine(title="备注")
	private String remark;
	
	@Column(length=36)
	@FieldDefine(title="角色组id",hidden=true)
	private String parent_id;//上级rolegroup的id
	
	public Boolean getLeaf() {
		if(roleType==RoleType.role){
			return true;
		} else {
			return false;
		}
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}


}
