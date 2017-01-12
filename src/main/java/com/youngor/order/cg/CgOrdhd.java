package com.youngor.order.cg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.utils.BaseObject;

@Entity(name="ORD_CG_ORDHD")
@IdClass(CgOrdhd.PK.class)
public class CgOrdhd extends BaseObject {
	@Id
	@FieldDefine(title="采购子批次编号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String orcgno;
	@Id
	@FieldDefine(title="采购子批次订单号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String cgorno;//orcgno+品牌+大类
	
	@FieldDefine(title="品牌",sort=50,hidden=false)
	@Column(length=30,nullable=true,updatable=false)
	private String bradno;
	@FieldDefine(title="大类",sort=50,hidden=false)
	@Column(length=30,nullable=true,updatable=false)
	private String spclno;
	@FieldDefine(title="订单状态")
	private Integer orstat;//0：编辑中；1：已确认；
	@FieldDefine(title="有效状态")
	private Integer isfect;//1：有效；0：无效
	
	public static class PK implements Serializable{
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String orcgno;
		private String cgorno;
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

	public Integer getOrstat() {
		return orstat;
	}

	public void setOrstat(Integer orstat) {
		this.orstat = orstat;
	}

	public Integer getIsfect() {
		return isfect;
	}

	public void setIsfect(Integer isfect) {
		this.isfect = isfect;
	}

}
