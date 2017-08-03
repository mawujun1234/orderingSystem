package com.youngor.suno;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.annotation.FieldDefine;

@Entity(name="ord_pub_suno")
public class PubSuno {
	@Id
	@FieldDefine(title="供应商代码",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String idsuno;
	@FieldDefine(title="供应商名称",sort=50,hidden=true)
	@Column(length=150,nullable=false,updatable=false)
	private String idsunm;
	@FieldDefine(title="供应商简称",sort=50,hidden=true)
	@Column(length=130,nullable=false,updatable=false)
	private String idalsu;
	@FieldDefine(title="群组代码",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String iisucl;
	@FieldDefine(title="群组名称",sort=50,hidden=true)
	@Column(length=60,nullable=false,updatable=false)
	private String iiclnm;
	@FieldDefine(title="订单类型",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String iiorty;
	@FieldDefine(title="订单类型名称",sort=50,hidden=true)
	@Column(length=60,nullable=false,updatable=false)
	private String iiornm;
	@FieldDefine(title="币种代码",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String iicucd;
	@FieldDefine(title="币种",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String iicdnm;
	
	@FieldDefine(title="状态",sort=40,hidden=true)
	private Integer idstat;

	public String getIdsuno() {
		return idsuno;
	}

	public void setIdsuno(String idsuno) {
		this.idsuno = idsuno;
	}

	public String getIdsunm() {
		return idsunm;
	}

	public void setIdsunm(String idsunm) {
		this.idsunm = idsunm;
	}

	public String getIdalsu() {
		return idalsu;
	}

	public void setIdalsu(String idalsu) {
		this.idalsu = idalsu;
	}

	public String getIisucl() {
		return iisucl;
	}

	public void setIisucl(String iisucl) {
		this.iisucl = iisucl;
	}

	public String getIiclnm() {
		return iiclnm;
	}

	public void setIiclnm(String iiclnm) {
		this.iiclnm = iiclnm;
	}

	public String getIiorty() {
		return iiorty;
	}

	public void setIiorty(String iiorty) {
		this.iiorty = iiorty;
	}

	public String getIiornm() {
		return iiornm;
	}

	public void setIiornm(String iiornm) {
		this.iiornm = iiornm;
	}

	public String getIicucd() {
		return iicucd;
	}

	public void setIicucd(String iicucd) {
		this.iicucd = iicucd;
	}

	public String getIicdnm() {
		return iicdnm;
	}

	public void setIicdnm(String iicdnm) {
		this.iicdnm = iicdnm;
	}

	public Integer getIdstat() {
		return idstat;
	}

	public void setIdstat(Integer idstat) {
		this.idstat = idstat;
	}

}
