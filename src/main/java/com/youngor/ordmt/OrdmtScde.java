package com.youngor.ordmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

import com.mawujun.generator.model.FieldDefine;
import com.mawujun.generator.model.ShowType;
import com.youngor.ordmt.OrdmtScde.PK;
import com.youngor.utils.BaseObject;

@Entity(name="ord_ordmt_scde")
@IdClass(PK.class)
public class OrdmtScde extends BaseObject{
	@Id
	@FieldDefine(title="订货会批号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String ormtno;
	@Id
	@FieldDefine(title="订货单位类型",sort=50,hidden=true,showType=ShowType.combobox)
	@Column(length=30,nullable=false,updatable=false)
	private String orgty;
	
	@FieldDefine(title="开始日期",sort=50)
	@NotNull
	private Date mtstdt;
	@FieldDefine(title="结束日期",sort=50)
	@NotNull
	private Date mtfidt;
	@FieldDefine(title="开始时间",sort=50)
	@Column(length=10,nullable=false,updatable=true)
	private String mtsttm;
	@FieldDefine(title="结束时间",sort=50)
	@Column(length=10,nullable=false,updatable=true)
	private String mtfitm;
	
	@FieldDefine(title="修改人",sort=40)
	@Column(length=30,nullable=true)
	private String lmsp;
	@FieldDefine(title="修改日期",sort=40)
	private Date lmdt;
	
	public String getOrgty_name(){
		if("ZY".equals(orgty)){
			return "自营";
		} else {
			return "特许";
		}
	}
	
	public static class PK implements Serializable {
		private static final long serialVersionUID = 1L;

		private String ormtno;

        private String orgty;

		public String getOrmtno() {
			return ormtno;
		}

		public void setOrmtno(String ormtno) {
			this.ormtno = ormtno;
		}

		public String getOrgty() {
			return orgty;
		}

		public void setOrgty(String orgty) {
			this.orgty = orgty;
		}


        
    }
	
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getOrgty() {
		return orgty;
	}
	public void setOrgty(String orgty) {
		this.orgty = orgty;
	}
	public Date getMtstdt() {
		return mtstdt;
	}
	public void setMtstdt(Date mtstdt) {
		this.mtstdt = mtstdt;
	}
	public Date getMtfidt() {
		return mtfidt;
	}
	public void setMtfidt(Date mtfidt) {
		this.mtfidt = mtfidt;
	}
	public String getMtsttm() {
		return mtsttm;
	}
	public void setMtsttm(String mtsttm) {
		this.mtsttm = mtsttm;
	}
	public String getMtfitm() {
		return mtfitm;
	}
	public void setMtfitm(String mtfitm) {
		this.mtfitm = mtfitm;
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