package com.youngor.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.annotation.FieldDefine;

/**
 * 职位类型
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="t_position_type")
public class PositionType {
	@Id
	@FieldDefine(title="id",hidden=false)
	@Column(length=36,nullable=false)
	private String id;
	@Column(length=30,nullable=false)
	@FieldDefine(title="职位名称",sort=6)
	private String name;
	@Column(length=300,nullable=true)
	@FieldDefine(title="备注",sort=6)
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
