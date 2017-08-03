package com.youngor.order1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.annotation.FieldDefine;
import com.youngor.utils.BaseObject;

/**
 * 产品货号下单登记表
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_ord_orddtl")
@IdClass(OrdOrddtl.PK.class)
public class OrdOrddtl extends BaseObject{
	@Id
	@Column(length=30)
	@FieldDefine(title="订货会编号",sort=50,hidden=false)
	private String ormtno;
	@Id
	@FieldDefine(title="设计样衣代码",sort=50,hidden=true)
	@Column(length=36,nullable=false,updatable=false)
	private String sampno;
	@Id
	@Column(length=30)
	@FieldDefine(title="套件",sort=50,hidden=false)
	private String suitno;
	@Id
	@FieldDefine(title="下单序号",sort=50,hidden=false)
	private Integer ordseq;
	
	@FieldDefine(title="下单日期",sort=50,hidden=false)
	private String ordate;
	@FieldDefine(title="下单数量",sort=50,hidden=false)
	private Integer orodqt;
	@FieldDefine(title="备注",sort=50,hidden=false)
	private String ormark;
	
	public static class PK implements Serializable {
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String ormtno;
		private String sampno;
		private String suitno;
		private Integer ordseq;
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
		public String getOrmtno() {
			return ormtno;
		}
		public void setOrmtno(String ormtno) {
			this.ormtno = ormtno;
		}
		public Integer getOrdseq() {
			return ordseq;
		}
		public void setOrdseq(Integer ordseq) {
			this.ordseq = ordseq;
		}
	}
	
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
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
	public Integer getOrdseq() {
		return ordseq;
	}
	public void setOrdseq(Integer ordseq) {
		this.ordseq = ordseq;
	}
	public String getOrdate() {
		return ordate;
	}
	public void setOrdate(String ordate) {
		this.ordate = ordate;
	}
	public Integer getOrodqt() {
		return orodqt;
	}
	public void setOrodqt(Integer orodqt) {
		this.orodqt = orodqt;
	}
	public String getOrmark() {
		return ormark;
	}
	public void setOrmark(String ormark) {
		this.ormark = ormark;
	}

}
