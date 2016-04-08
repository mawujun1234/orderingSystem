package com.youngor.pubcode;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.generator.model.FieldDefine;
import com.mawujun.generator.model.ShowType;

@Entity(name="ord_pubcode")
public class PubCode {
	@Id
	@FieldDefine(title="代码",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private String itno;
	
	//@Id
	@FieldDefine(title="类型代码",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=true)
	private String tyno;
	
	
	@FieldDefine(title="上级代码",sort=50,hidden=true)
	@Column(length=30,nullable=true)
	private String fitno;
	@FieldDefine(title="类型名称",sort=50)
	@Column(length=60,nullable=false)
	private String itnm;
	@FieldDefine(title="描述",sort=40,hidden=false)
	@Column(length=100,nullable=true)
	private String itms;
	@FieldDefine(title="描述",sort=40,hidden=false)
	@Column(length=100,nullable=true)
	private String itmk;
	@FieldDefine(title="排序",sort=40,hidden=false)
	private Integer itso;
	@FieldDefine(title="状态",sort=40,hidden=false,showType=ShowType.radio)
	private Boolean itst=true;//0：作废；1：有效；
	@FieldDefine(title="当季状态",sort=40,hidden=false,showType=ShowType.radio)
	private Boolean stat=true;//1：当季；0：非当季
	
	@FieldDefine(title="创建人",sort=40,hidden=false)
	@Column(length=20,nullable=true)
	private String rgsp;
	@FieldDefine(title="创建日期",sort=40)
	private Date rgdt;
	@FieldDefine(title="修改人",sort=40,hidden=false)
	@Column(length=20,nullable=true)
	private String lmsp;
	@FieldDefine(title="修改日期",sort=40)
	private Date lmdt;
	public String getItno() {
		return itno;
	}
	public void setItno(String itno) {
		this.itno = itno;
	}
	public String getTyno() {
		return tyno;
	}
	public void setTyno(String tyno) {
		this.tyno = tyno;
	}
	public String getFitno() {
		return fitno;
	}
	public void setFitno(String fitno) {
		this.fitno = fitno;
	}
	public String getItnm() {
		return itnm;
	}
	public void setItnm(String itnm) {
		this.itnm = itnm;
	}
	public String getItms() {
		return itms;
	}
	public void setItms(String itms) {
		this.itms = itms;
	}
	public String getItmk() {
		return itmk;
	}
	public void setItmk(String itmk) {
		this.itmk = itmk;
	}
	public Integer getItso() {
		return itso;
	}
	public void setItso(Integer itso) {
		this.itso = itso;
	}
	public Boolean getItst() {
		return itst;
	}
	public void setItst(Boolean itst) {
		this.itst = itst;
	}
	public Boolean getStat() {
		return stat;
	}
	public void setStat(Boolean stat) {
		this.stat = stat;
	}
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
