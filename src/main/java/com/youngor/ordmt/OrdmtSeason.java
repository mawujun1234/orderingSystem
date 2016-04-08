package com.youngor.ordmt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.mawujun.generator.model.FieldDefine;

@Entity(name="ord_ordmt_season")
public class OrdmtSeason {
	@FieldDefine(title="订货会批号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String ormtno;
	@FieldDefine(title="产品季节代码",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String seasno;
	
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
	public String getSeasno() {
		return seasno;
	}
	public void setSeasno(String seasno) {
		this.seasno = seasno;
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
