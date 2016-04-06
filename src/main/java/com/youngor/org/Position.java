package com.youngor.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.generator.model.FieldDefine;

@Entity(name="t_position")
public class Position {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(
	        name = "uuid",
	        strategy = "org.hibernate.id.UUIDGenerator"
	    )
	@FieldDefine(title="id",hidden=true)
	@Column(length=36,nullable=false)
	private String id;
	@Column(length=30,nullable=false)
	@FieldDefine(title="职位名称",sort=6)
	private String name;
	@Column(length=30,nullable=true)
	@FieldDefine(title="备注",sort=6)
	private String remark;
	
	@FieldDefine(title="组织id",hidden=true)
	@Column(length=15,nullable=false)
	private String orgno;
	
	
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}

}
