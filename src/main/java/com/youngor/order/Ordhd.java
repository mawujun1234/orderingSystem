package com.youngor.order;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
/**
 * 订单副表
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.order.Ordhd.PK;

@Entity(name="ord_ordhd")
@IdClass(PK.class)
public class Ordhd {
	@Id
	@Column(length=50)
	@FieldDefine(title="订单号",sort=50,hidden=false)
	private String mtorno;////ord.getOrmtno()+"_"+ord.getOrtyno()+"_"+ord.getOrdorg();
	@Id
	@Column(length=50)
	@FieldDefine(title="品牌",sort=50,hidden=false)
	private String bradno;
	@Id
	@Column(length=50)
	@FieldDefine(title="大类",sort=50,hidden=false)
	private String spclno;
	
	@Column(length=50)
	@FieldDefine(title="审批订单号",sort=50,hidden=false)
	private String mlorno;//a.mtorno||c.bradno||spclno
	@FieldDefine(title="审批订单版本号",sort=50,hidden=false)
	private Integer mlorvn;
	@Column(length=50)
	@FieldDefine(title="订单节点类型",sort=50,hidden=false)
	private String sdtyno;
	@FieldDefine(title="总量状态",sort=50,hidden=false)
	private Integer orstat;//0：编辑中；1：审批中；2：大区审批通过；3：总部审批通过；4：退回 // SELECT DEITNO,DEITNM  FROM ORD_PUB_DEFCODE WHERE DETYNO='ORDSTAT' 
	@FieldDefine(title="",sort=50,hidden=false)
	private Integer szstat;//0：编辑中；1：审批中；2：大区审批通过；3：总部审批通过；4：退回 // SELECT DEITNO,DEITNM  FROM ORD_PUB_DEFCODE WHERE DETYNO='ORDSTAT' 
	@Column(length=50)
	@FieldDefine(title="审批组织",sort=50,hidden=false)
	private String orapdp;
	@Column(length=50)
	@FieldDefine(title="审批人",sort=50,hidden=false)
	private String orapsp;
	@FieldDefine(title="审批日期",sort=50,hidden=false)
	private Date orapdt;
	@FieldDefine(title="有效状态",sort=50,hidden=false)
	private Integer isfect;//1：有效；0：无效
	
	public static class PK implements Serializable{
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String mtorno;
		private String bradno;
		private String spclno;
		public String getMtorno() {
			return mtorno;
		}
		public void setMtorno(String mtorno) {
			this.mtorno = mtorno;
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
		
	}

	public String getMtorno() {
		return mtorno;
	}

	public void setMtorno(String mtorno) {
		this.mtorno = mtorno;
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

	public String getMlorno() {
		return mlorno;
	}

	public void setMlorno(String mlorno) {
		this.mlorno = mlorno;
	}

	public Integer getMlorvn() {
		return mlorvn;
	}

	public void setMlorvn(Integer mlorvn) {
		this.mlorvn = mlorvn;
	}

	public String getSdtyno() {
		return sdtyno;
	}

	public void setSdtyno(String sdtyno) {
		this.sdtyno = sdtyno;
	}

	public Integer getOrstat() {
		return orstat;
	}

	public void setOrstat(Integer orstat) {
		this.orstat = orstat;
	}

	public Integer getSzstat() {
		return szstat;
	}

	public void setSzstat(Integer szstat) {
		this.szstat = szstat;
	}

	public String getOrapdp() {
		return orapdp;
	}

	public void setOrapdp(String orapdp) {
		this.orapdp = orapdp;
	}

	public String getOrapsp() {
		return orapsp;
	}

	public void setOrapsp(String orapsp) {
		this.orapsp = orapsp;
	}

	public Date getOrapdt() {
		return orapdt;
	}

	public void setOrapdt(Date orapdt) {
		this.orapdt = orapdt;
	}

	public Integer getIsfect() {
		return isfect;
	}

	public void setIsfect(Integer isfect) {
		this.isfect = isfect;
	}


}
