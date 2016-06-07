package com.youngor.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.controller.shiro.IShiroUser;
import com.mawujun.generator.model.FieldDefine;

@Entity
@Table(name="t_user",schema="od")
public class User implements IShiroUser{
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(
	        name = "uuid",
	        strategy = "org.hibernate.id.UUIDGenerator"
	    )
	@FieldDefine(title="id",hidden=true)
	@Column(length=36)
	private String id;
	@FieldDefine(title="姓名",hidden=false,genQuery=true,sort=5)
	@Column(length=50,nullable=false)
	private String name;
	@FieldDefine(title="登录名",hidden=false,genQuery=true,sort=5)
	@Column(length=30,nullable=false,unique=true)
	private String loginName;
	@FieldDefine(title="密码",hidden=false,genQuery=false,sort=5)
	@Column(length=30,nullable=false)
	private String pwd;
	@FieldDefine(title="备注")
	@Column(length=150)
	private String remark;
//	@FieldDefine(title="备注")
//	@Column(length=150)
//	private Boolean isAdmin;
	
//	public boolean isAdmin(){
//		if("admin".equals(this.getLoginName())){
//			return true;
//		}
//		return false;
//	}

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
