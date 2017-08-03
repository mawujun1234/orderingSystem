package com.youngor.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.annotation.FieldDefine;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.sample.SamplePlanStpr.PK;
import com.youngor.utils.BaseObject;

@Entity
@Table(name="ord_sample_plan_stpr")
@IdClass(PK.class)
public class SamplePlanStpr extends BaseObject{
	@Id
	@FieldDefine(title="企划样衣代码",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String plspno;
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
	@FieldDefine(title="企划倍率",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private Double spplrd;
	@FieldDefine(title="企划成本价",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private Double plctpr;
	
	public String getSuitno_name(){
		return PubCodeCache.getSuitno_name(this.getSuitno());
	}
	
	public static class PK implements Serializable {
		private static final long serialVersionUID = 1L;

		private String plspno;

        private String suitno;

		public String getPlspno() {
			return plspno;
		}

		public void setPlspno(String plspno) {
			this.plspno = plspno;
		}

		public String getSuitno() {
			return suitno;
		}

		public void setSuitno(String suitno) {
			this.suitno = suitno;
		}
        
	}
	
	public String getPlspno() {
		return plspno;
	}
	public void setPlspno(String plspno) {
		this.plspno = plspno;
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
	public Double getSpplrd() {
		return spplrd;
	}
	public void setSpplrd(Double spplrd) {
		this.spplrd = spplrd;
	}
	public Double getPlctpr() {
		return plctpr;
	}
	public void setPlctpr(Double plctpr) {
		this.plctpr = plctpr;
	}


}
