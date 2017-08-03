package com.youngor.order.bw;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import com.mawujun.annotation.FieldDefine;
import com.youngor.order.bw.BwOrddt.PK;
import com.youngor.utils.BaseObject;

@Entity(name="ord_bw_orddt")
@IdClass(PK.class)
public class BwOrddt extends BaseObject{
	@Id
	@Column(length=50)
	@FieldDefine(title="子批次订单",sort=50,hidden=false)
	private String ormmno;
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
	
	
	@FieldDefine(title="数量",sort=50,hidden=false)
	private Integer ormtqt;
	@Column(length=50)
	@FieldDefine(title="计划面料交货期",sort=50,hidden=false)
	private String mldate;
	@Column(length=50)
	@FieldDefine(title="计划成衣交货期",sort=50,hidden=false)
	private String pldate;
	@Column(length=50)
	@FieldDefine(title="产地",sort=50,hidden=false)
	private String pplace;
	
	
	@Transient
	private String sampnm;
	
	public PK geetPK(){
		PK pk=new PK();
		pk.setOrmmno(ormmno);
		pk.setMmorno(mmorno);
		pk.setOrdorg(ordorg);
		pk.setSampno(sampno);
		pk.setSuitno(suitno);
		return pk;
	}
	
	public static class PK implements Serializable {
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String ormmno;
		private String mmorno;
		private String ordorg;
		private String sampno;
		private String suitno;

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
		public String getOrmmno() {
			return ormmno;
		}
		public void setOrmmno(String ormmno) {
			this.ormmno = ormmno;
		}
		
		
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

	public Integer getOrmtqt() {
		return ormtqt;
	}

	public void setOrmtqt(Integer ormtqt) {
		this.ormtqt = ormtqt;
	}

	public String getMldate() {
		return mldate;
	}

	public void setMldate(String mldate) {
		this.mldate = mldate;
	}

	public String getPldate() {
		return pldate;
	}

	public void setPldate(String pldate) {
		this.pldate = pldate;
	}

	public String getPplace() {
		return pplace;
	}

	public void setPplace(String pplace) {
		this.pplace = pplace;
	}

	public String getSampnm() {
		return sampnm;
	}

	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}
	
	
}
