package com.youngor.pubcode;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.pubcode.PubPlanrt.PK;


@Entity(name="ord_pub_planrt")
@IdClass(PK.class)
public class PubPlanrt {
	@Id
	@FieldDefine(title="品牌",hidden=false)
	@Column(length=36)
	private String bradno;
	@Id
	@FieldDefine(title="大类",hidden=false)
	@Column(length=36)
	private String spclno;
	@Id
	@FieldDefine(title="小类",hidden=false)
	@Column(length=36)
	private String sptyno;
	@FieldDefine(title="指标上报范围",hidden=false)
	private Integer planrt;//1:按小类；2：按系列
	
	@Transient
	private String spclnm;
	@Transient
	private String sptynm;
	
//	public String getBradno_name(){
//		return PubCodeCache.getBradno_name(this.getBradno());
//	}
//	public String getSpclno_name(){
//		return PubCodeCache.getSpclno_name(this.getBradno());
//	}
//	public String getSptyno_name(){
//		return PubCodeCache.getSptyno_name(this.getBradno());
//	}

	public static class PK implements Serializable {
		private String bradno;
		private String spclno;
		private String sptyno;
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
		public String getSptyno() {
			return sptyno;
		}
		public void setSptyno(String sptyno) {
			this.sptyno = sptyno;
		}
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

	public String getSptyno() {
		return sptyno;
	}

	public void setSptyno(String sptyno) {
		this.sptyno = sptyno;
	}

	public Integer getPlanrt() {
		return planrt;
	}

	public void setPlanrt(Integer planrt) {
		this.planrt = planrt;
	}

	public String getSpclnm() {
		return spclnm;
	}

	public void setSpclnm(String spclnm) {
		this.spclnm = spclnm;
	}

	public String getSptynm() {
		return sptynm;
	}

	public void setSptynm(String sptynm) {
		this.sptynm = sptynm;
	}

}
