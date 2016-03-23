package com.youngor.permission;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.mawujun.generator.model.FieldDefine;

/**
 * 只设计成两级，第一级是rolegroup，第二级才是角色
 * @author mawujun
 *
 */
@Entity(name="t_role")
public class Role {
	@Id
	@FieldDefine(title="id",hidden=true)
	private UUID id;
	
	@Column(length=30,nullable=false)
	@FieldDefine(title="角色名称")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	@NotNull
	@FieldDefine(title="角色类型",hidden=true)
	private RoleType roleType;
	
	@Column(length=150)
	@FieldDefine(title="备注")
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@FieldDefine(title="角色组id",hidden=true)
	private UUID parent_id;//上级rolegroup的id

	public UUID getParent_id() {
		return parent_id;
	}

	public void setParent_id(UUID parent_id) {
		this.parent_id = parent_id;
	}

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

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	
	

}
