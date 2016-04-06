package com.youngor.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.generator.model.FieldDefine;
import com.mawujun.generator.model.ShowType;

@Entity
@Table(name="t_org")
public class Org {
	@Id
	@FieldDefine(title="组织代码",sort=7,hidden=false)
	@Column(length=15,nullable=false)
	private String orgno;
	@Column(length=30,nullable=false)
	@FieldDefine(title="组织名称",sort=6)
	private String orgnm;
	@Column(length=30,nullable=false)
	@FieldDefine(title="组织短名称",sort=5)
	public String orgsn;
	
	@Column(length=15,nullable=false)
	@Enumerated(EnumType.STRING)
	@FieldDefine(title="组织类型",sort=4,showType=ShowType.combobox,hidden=false)//
	private Orgty orgty;//组织类型，门店，片区，分公司，区域，营销公司
	@Column(length=15,nullable=false)
	@Enumerated(EnumType.STRING)
	@FieldDefine(title="渠道类型",sort=4,showType=ShowType.combobox,hidden=false)//
	private Chancl chancl;//渠道类型 自营，商场，特许
	@Column(length=15,nullable=false)
	@Enumerated(EnumType.STRING)
	@FieldDefine(title="状态",sort=4,showType=ShowType.combobox,hidden=false)//
	private Orgst orgst;
	
	public String getId() {
		return  getOrgno();
	}
	public String getName() {
		return getOrgnm();
	}
	
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}
	public String getOrgnm() {
		return orgnm;
	}
	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}
	public String getOrgsn() {
		return orgsn;
	}
	public void setOrgsn(String orgsn) {
		this.orgsn = orgsn;
	}
	public Orgty getOrgty() {
		return orgty;
	}
	public void setOrgty(Orgty orgty) {
		this.orgty = orgty;
	}
	public Chancl getChancl() {
		return chancl;
	}
	public void setChancl(Chancl chancl) {
		this.chancl = chancl;
	}
	public Orgst getOrgst() {
		return orgst;
	}
	public void setOrgst(Orgst orgst) {
		this.orgst = orgst;
	}


}
