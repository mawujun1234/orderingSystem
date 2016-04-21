package com.youngor.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.sample.SampleDesignStpr.PK;
import com.youngor.utils.BaseObject;
/**
 * 一件设计样衣
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity
@Table(name="ord_sample_design_stpr")
@IdClass(PK.class)
public class SampleDesignStpr extends BaseObject{
	@Id
	@FieldDefine(title="设计样衣代码",sort=50,hidden=true)
	@Column(length=36,nullable=false,updatable=false)
	private String sampno;
	@Id
	@FieldDefine(title="套件",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String suitno;
	
	@FieldDefine(title="出厂价",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private Double spftpr;
	@FieldDefine(title="零售价",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private Double sprtpr;
	@FieldDefine(title="预计成本价",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private Double plctpr;

	
	public String getSuitno_name(){
		return PubCodeCache.getSuitno_name(this.getSuitno());
	}

	public static class PK implements Serializable {
		private static final long serialVersionUID = 1L;
	
		private String sampno;
	
	    private String suitno;
	

	
		public String getSuitno() {
			return suitno;
		}
	
		public void setSuitno(String suitno) {
			this.suitno = suitno;
		}

		public String getSampno() {
			return sampno;
		}

		public void setSampno(String sampno) {
			this.sampno = sampno;
		}
	    
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


	public Double getSpftpr() {
		return spftpr;
	}


	public void setSpftpr(Double spftpr) {
		this.spftpr = spftpr;
	}


	public Double getSprtpr() {
		return sprtpr;
	}


	public void setSprtpr(Double sprtpr) {
		this.sprtpr = sprtpr;
	}


	public Double getPlctpr() {
		return plctpr;
	}


	public void setPlctpr(Double plctpr) {
		this.plctpr = plctpr;
	}
	

	
	
	
}
