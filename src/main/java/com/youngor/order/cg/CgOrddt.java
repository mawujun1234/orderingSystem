package com.youngor.order.cg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.annotation.FieldDefine;
import com.youngor.utils.BaseObject;

@Entity(name="ORD_CG_ORDDT")
@IdClass(CgOrddt.PK.class)
public class CgOrddt extends BaseObject {
	@Id
	@FieldDefine(title="采购子批次编号",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private String orcgno;
	@Id
	@FieldDefine(title="采购子批次订单号",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private String cgorno;
	@Id
	@FieldDefine(title="样衣编号",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private String sampno;
	@Id
	@FieldDefine(title="套件",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private String suitno;
	
	@FieldDefine(title="数量")
	private Integer ormtqt;
	@FieldDefine(title="计划面料交货期",sort=50,hidden=false)
	private String mldate;
	@FieldDefine(title="计划成衣交货期",sort=50,hidden=false)
	private String pldate;
	@FieldDefine(title="产地",sort=50,hidden=false)
	private String pplace;
	
	public static class PK implements Serializable {
		private String orcgno;
		private String cgorno;
		private String sampno;
		private String suitno;
		public String getOrcgno() {
			return orcgno;
		}
		public void setOrcgno(String orcgno) {
			this.orcgno = orcgno;
		}
		public String getCgorno() {
			return cgorno;
		}
		public void setCgorno(String cgorno) {
			this.cgorno = cgorno;
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

	public String getOrcgno() {
		return orcgno;
	}

	public void setOrcgno(String orcgno) {
		this.orcgno = orcgno;
	}

	public String getCgorno() {
		return cgorno;
	}

	public void setCgorno(String cgorno) {
		this.cgorno = cgorno;
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


}
