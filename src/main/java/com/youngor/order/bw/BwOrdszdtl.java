package com.youngor.order.bw;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.annotation.FieldDefine;
import com.youngor.order.bw.BwOrdszdtl.PK;

@Entity(name="ord_bw_ordszdtl")
@IdClass(PK.class)
public class BwOrdszdtl {
	@Id
	@Column(length=50)
	@FieldDefine(title="子批次订单号",sort=50,hidden=false)
	private String mmorno;
	@Id
	@Column(length=50)
	@FieldDefine(title="订货单位",sort=50,hidden=false)
	private String ordorg;
	@Id
	@Column(length=50)
	@FieldDefine(title="订货样衣代码",sort=50,hidden=false)
	private String sampno;
	@Id
	@Column(length=50)
	@FieldDefine(title="套件",sort=50,hidden=false)
	private String suitno;
	@Id
	@Column(length=50)
	@FieldDefine(title="规格类型",sort=50,hidden=false)
	private String sizety;
	@Id
	@Column(length=50)
	@FieldDefine(title="规格代码",sort=50,hidden=false)
	private String sizeno;
	
	@FieldDefine(title="数量",sort=50,hidden=false)
	private Integer orszqt;
	@Column(length=50)
	@FieldDefine(title="备注",sort=50,hidden=false)
	private String ormark;
	
	public static class PK implements Serializable{
		private String mmorno;
		private String ordorg;
		private String sampno;
		private String suitno;
		private String sizety;
		private String sizeno;
		public String getMmorno() {
			return mmorno;
		}
		public void setMmorno(String mmorno) {
			this.mmorno = mmorno;
		}
		public String getOrdorg() {
			return ordorg;
		}
		public void setOrdorg(String ordorg) {
			this.ordorg = ordorg;
		}
		public String getSampno() {
			return sampno;
		}
		public void setSampno(String sampno) {
			this.sampno = sampno;
		}
		public String getSuitno() {
			return suitno;
		}
		public void setSuitno(String suitno) {
			this.suitno = suitno;
		}
		public String getSizety() {
			return sizety;
		}
		public void setSizety(String sizety) {
			this.sizety = sizety;
		}
		public String getSizeno() {
			return sizeno;
		}
		public void setSizeno(String sizeno) {
			this.sizeno = sizeno;
		}
		
	}
	
	
	public String getMmorno() {
		return mmorno;
	}
	public void setMmorno(String mmorno) {
		this.mmorno = mmorno;
	}
	public String getOrdorg() {
		return ordorg;
	}
	public void setOrdorg(String ordorg) {
		this.ordorg = ordorg;
	}
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}
	public String getSuitno() {
		return suitno;
	}
	public void setSuitno(String suitno) {
		this.suitno = suitno;
	}
	public String getSizety() {
		return sizety;
	}
	public void setSizety(String sizety) {
		this.sizety = sizety;
	}
	public String getSizeno() {
		return sizeno;
	}
	public void setSizeno(String sizeno) {
		this.sizeno = sizeno;
	}
	public Integer getOrszqt() {
		return orszqt;
	}
	public void setOrszqt(Integer orszqt) {
		this.orszqt = orszqt;
	}
	public String getOrmark() {
		return ormark;
	}
	public void setOrmark(String ormark) {
		this.ormark = ormark;
	}


}
