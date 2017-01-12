package com.youngor.order.cg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.utils.BaseObject;


@Entity(name="ORD_CG_ORDDTL")
@IdClass(CgOrddtl.PK.class)
public class CgOrddtl extends BaseObject {
	@Id
	@FieldDefine(title="采购子批次订单号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String cgorno;
	@Id
	@FieldDefine(title="样衣编号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String sampno;
	@Id
	@FieldDefine(title="套件",sort=50,hidden=true)
	@Column(length=30,nullable=true,updatable=false)
	private String suitno;
	
	
	@FieldDefine(title="数量")
	private Integer orszqt;
	@FieldDefine(title="备注",sort=50,hidden=true)
	@Column(length=30,nullable=true,updatable=false)
	private String ormark;
	
	public static class PK implements Serializable {
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String cgorno;
		private String sampno;
		private String suitno;
		
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

	public Integer getOrszqt() {
		return orszqt;
	}

	public void setOrszqt(Integer orszqt) {
		this.orszqt = orszqt;
	}

	public String getOrmark() {
		return ormark;
	}

	public void setOrmark(String ormark) {
		this.ormark = ormark;
	}

}
