package com.youngor.plan;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.plan.OrdDwdt.PK;



/**
 * 交货期维护
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_ord_dwdt")
@IdClass(PK.class)
public class OrdDwdt {
	@Id
	@Column(length=30)
	@FieldDefine(title="订货批号",sort=50,hidden=false)
	private String ormtno;
	@Id
	@Column(length=30)
	@FieldDefine(title="订货类型",sort=50,hidden=false)
	private String ortyno;
	@Id
	@Column(length=30)
	@FieldDefine(title="订货单位",sort=50,hidden=false)
	private String ordorg;	
	@Id
	@Column(length=30)
	@FieldDefine(title="设计样衣代码",sort=50,hidden=false)
	private String sampno;
	@Id
	@Column(length=30)
	@FieldDefine(title="套件",sort=50,hidden=false)
	private String suitno;
	

	@FieldDefine(title="订货数量",sort=50,hidden=false)
	private Integer ormtqt;
	@FieldDefine(title="计划面料交货期",sort=50,hidden=false)
	private Date mldate;
	@FieldDefine(title="计划成衣交货期",sort=50,hidden=false)
	private Date pldate;
	
	
	public static class PK implements Serializable{
		private String ormtno;
		private String ortyno;
		private String ordorg;
		private String sampno;
		private String suitno;
		public String getOrmtno() {
			return ormtno;
		}
		public void setOrmtno(String ormtno) {
			this.ormtno = ormtno;
		}
		public String getOrtyno() {
			return ortyno;
		}
		public void setOrtyno(String ortyno) {
			this.ortyno = ortyno;
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
		
	}
	
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getOrtyno() {
		return ortyno;
	}
	public void setOrtyno(String ortyno) {
		this.ortyno = ortyno;
	}
	public String getOrdorg() {
		return ordorg;
	}
	public void setOrdorg(String ordorg) {
		this.ordorg = ordorg;
	}
	public Integer getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(Integer ormtqt) {
		this.ormtqt = ormtqt;
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
	public Date getMldate() {
		return mldate;
	}
	public void setMldate(Date mldate) {
		this.mldate = mldate;
	}
	public Date getPldate() {
		return pldate;
	}
	public void setPldate(Date pldate) {
		this.pldate = pldate;
	}



}
