package com.youngor.order.bw;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.pubcode.PubCodeCache;



@Entity(name="ord_bw_ordhd")
//@IdClass(PK.class)
public class BwOrdhd {
	@Id
	@Column(length=50)
	@FieldDefine(title="子批次订单号",sort=50,hidden=false)
	private String mmorno;//订货会子批次+品牌+大类
	
	//@Id
	@Column(length=30)
	@FieldDefine(title="订货会子批次",sort=50,hidden=false)
	private String ormmno;
	//@Id
	@Column(length=50)
	@FieldDefine(title="品牌",sort=50,hidden=false)
	private String bradno;
	//@Id
	@Column(length=50)
	@FieldDefine(title="大类",sort=50,hidden=false)
	private String spclno;

//	@Column(length=50)
//	@FieldDefine(title="计划面料交货期",sort=50,hidden=false)
//	private String mldate;
//	@Column(length=50)
//	@FieldDefine(title="计划成衣交货期",sort=50,hidden=false)
//	private String pldate;
//	@Column(length=50)
//	@FieldDefine(title="产地",sort=50,hidden=false)
//	private String pplace;
	@FieldDefine(title="订单状态",sort=50,hidden=false)
	private Integer orstat;//0：编辑中；1：已确认；
	@FieldDefine(title="有效状态",sort=50,hidden=false)
	private Integer isfect;//1：有效；0：无效
	
	public String getBradno_name() {
		return PubCodeCache.getBradno_name(this.getBradno());
	}
	public String getSpclno_name() {
		return PubCodeCache.getSpclno_name(this.getSpclno());
	}
	
	public String getOrmmno() {
		return ormmno;
	}
	public void setOrmmno(String ormmno) {
		this.ormmno = ormmno;
	}
	public String getMmorno() {
		return mmorno;
	}
	public void setMmorno(String mmorno) {
		this.mmorno = mmorno;
	}
	public String getBradno() {
		return bradno;
	}
	public void setBradno(String bradno) {
		this.bradno = bradno;
	}
	public String getSpclno() {
		return spclno;
	}
	public void setSpclno(String spclno) {
		this.spclno = spclno;
	}
	
	public Integer getOrstat() {
		return orstat;
	}
	public void setOrstat(Integer orstat) {
		this.orstat = orstat;
	}
	public Integer getIsfect() {
		return isfect;
	}
	public void setIsfect(Integer isfect) {
		this.isfect = isfect;
	}

	

}
