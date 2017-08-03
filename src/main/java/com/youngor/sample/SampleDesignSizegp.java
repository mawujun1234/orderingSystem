package com.youngor.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.annotation.FieldDefine;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.sample.SampleDesignSizegp.PK;

@Entity
@Table(name="ord_sample_design_sizegp")
@IdClass(PK.class)
public class SampleDesignSizegp {
	@Id
	@FieldDefine(title="设计样衣代码",sort=50,hidden=true)
	@Column(length=36,nullable=false,updatable=false)
	private String sampno;
	@Id
	@FieldDefine(title="套件",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String suitno;
	
	@FieldDefine(title="规格范围",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String sizegp;

	
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

	public String getSizegp() {
		return sizegp;
	}

	public void setSizegp(String sizegp) {
		this.sizegp = sizegp;
	}
	
	


}
