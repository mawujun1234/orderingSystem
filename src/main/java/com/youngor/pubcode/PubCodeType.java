package com.youngor.pubcode;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.generator.model.FieldDefine;
import com.mawujun.generator.model.ShowType;
import com.youngor.utils.BaseObject;

@Entity(name="ord_pub_code_type")
public class PubCodeType extends BaseObject{
	@Id
	@FieldDefine(title="类型代码",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private String tyno;
	@FieldDefine(title="类型名称",sort=50)
	@Column(length=30,nullable=false)
	private String tynm;
	@FieldDefine(title="上级代码",sort=50,hidden=true)
	@Column(length=30,nullable=true)
	private String ftyno;
	@FieldDefine(title="描述",sort=40,hidden=true)
	@Column(length=100,nullable=true)
	private String tyms;
	@FieldDefine(title="备注",sort=40,hidden=true)
	@Column(length=100,nullable=true)
	private String tymk;
	@FieldDefine(title="状态",sort=50,showType=ShowType.radio)
	private Boolean tyst=true;
	

	public String getTyno() {
		return tyno;
	}
	public void setTyno(String tyno) {
		this.tyno = tyno;
	}
	public String getTynm() {
		return tynm;
	}
	public void setTynm(String tynm) {
		this.tynm = tynm;
	}
	public String getFtyno() {
		return ftyno;
	}
	public void setFtyno(String ftyno) {
		this.ftyno = ftyno;
	}
	public String getTyms() {
		return tyms;
	}
	public void setTyms(String tyms) {
		this.tyms = tyms;
	}
	public String getTymk() {
		return tymk;
	}
	public void setTymk(String tymk) {
		this.tymk = tymk;
	}
	public Boolean getTyst() {
		return tyst;
	}
	public void setTyst(Boolean tyst) {
		this.tyst = tyst;
	}
	
}
