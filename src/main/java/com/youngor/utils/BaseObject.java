package com.youngor.utils;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.mawujun.generator.model.FieldDefine;
@MappedSuperclass
public class BaseObject {
	@FieldDefine(title="创建人",sort=40,hidden=true)
	@Column(length=30,nullable=true,updatable=false)
	private String rgsp;
	@FieldDefine(title="创建日期",sort=40,hidden=true)
	@Column(updatable=false)
	private Date rgdt;
	@FieldDefine(title="修改人",sort=40,hidden=true)
	@Column(length=30,nullable=true)
	private String lmsp;
	@FieldDefine(title="修改日期",sort=40,hidden=true)
	private Date lmdt;
	public String getRgsp() {
		return rgsp;
	}
	public void setRgsp(String rgsp) {
		this.rgsp = rgsp;
	}
	public Date getRgdt() {
		return rgdt;
	}
	public void setRgdt(Date rgdt) {
		this.rgdt = rgdt;
	}
	public String getLmsp() {
		return lmsp;
	}
	public void setLmsp(String lmsp) {
		this.lmsp = lmsp;
	}
	public Date getLmdt() {
		return lmdt;
	}
	public void setLmdt(Date lmdt) {
		this.lmdt = lmdt;
	}
}
