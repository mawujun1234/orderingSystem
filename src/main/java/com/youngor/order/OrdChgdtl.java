package com.youngor.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.annotation.FieldDefine;
import com.youngor.order.OrdChgdtl.PK;
import com.youngor.utils.BaseObject;

@Entity(name="ord_ord_chgdtl")
@IdClass(PK.class)
public class OrdChgdtl extends BaseObject{
	@Id
	@Column(length=50)
	@FieldDefine(title="订货会编号",sort=50,hidden=true)
	private String ormtno;
	@Id
	@Column(length=50)
	@FieldDefine(title="订货单位",sort=50,hidden=true)
	private String ordorg;
	@Id
	@Column(length=50)
	@FieldDefine(title="样衣编号",sort=50,hidden=true)
	private String sampno;
	@Id
	@Column(length=50)
	@FieldDefine(title="套件码",sort=50,hidden=true)
	private String suitno;
	
	
	@FieldDefine(title="调整数量",sort=50,hidden=false)
	private Integer orchqt;
	@Column(length=100)
	@FieldDefine(title="备注",sort=50,hidden=false)
	private String ormark;
	
	public static class PK implements Serializable {
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String ormtno;
		private String ordorg;
		private String sampno;
		private String suitno;
		public String getOrmtno() {
			return ormtno;
		}
		public void setOrmtno(String ormtno) {
			this.ormtno = ormtno;
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
	
	public PK geetPK(){
		OrdChgdtl.PK pk=new OrdChgdtl.PK();
		pk.setOrmtno(ormtno);
		pk.setOrdorg(ordorg);
		pk.setSampno(sampno);
		pk.setSuitno(suitno);
		return pk;
	}
	
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
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

	public Integer getOrchqt() {
		return orchqt;
	}
	public void setOrchqt(Integer orchqt) {
		this.orchqt = orchqt;
	}
	public String getOrmark() {
		return ormark;
	}
	public void setOrmark(String ormark) {
		this.ormark = ormark;
	}
	

}
