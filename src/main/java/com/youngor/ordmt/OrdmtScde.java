package com.youngor.ordmt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.mawujun.generator.model.FieldDefine;

@Entity(name="ord_ordmt_scde")
public class OrdmtScde {
	@FieldDefine(title="订货会批号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String ormtno;
	@FieldDefine(title="订货单位类型",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String orgty;
	
	@FieldDefine(title="开始日期",sort=50)
	@NotNull
	private Date mtstdt;
	@FieldDefine(title="结束日期",sort=50)
	@NotNull
	private Date mtfidt;
	@FieldDefine(title="开始时间",sort=50)
	@Column(length=10,nullable=false,updatable=true)
	private String mtsttm;
	@FieldDefine(title="结束时间",sort=50)
	@Column(length=10,nullable=false,updatable=true)
	private String mtfitm;
	
	@FieldDefine(title="修改人",sort=40)
	@Column(length=30,nullable=true)
	private String mtlmsp;
	@FieldDefine(title="修改日期",sort=40)
	private Date mtlmdt;
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getOrgty() {
		return orgty;
	}
	public void setOrgty(String orgty) {
		this.orgty = orgty;
	}
	public Date getMtstdt() {
		return mtstdt;
	}
	public void setMtstdt(Date mtstdt) {
		this.mtstdt = mtstdt;
	}
	public Date getMtfidt() {
		return mtfidt;
	}
	public void setMtfidt(Date mtfidt) {
		this.mtfidt = mtfidt;
	}
	public String getMtsttm() {
		return mtsttm;
	}
	public void setMtsttm(String mtsttm) {
		this.mtsttm = mtsttm;
	}
	public String getMtfitm() {
		return mtfitm;
	}
	public void setMtfitm(String mtfitm) {
		this.mtfitm = mtfitm;
	}
	public String getMtlmsp() {
		return mtlmsp;
	}
	public void setMtlmsp(String mtlmsp) {
		this.mtlmsp = mtlmsp;
	}
	public Date getMtlmdt() {
		return mtlmdt;
	}
	public void setMtlmdt(Date mtlmdt) {
		this.mtlmdt = mtlmdt;
	}

}
