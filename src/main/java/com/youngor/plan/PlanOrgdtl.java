package com.youngor.plan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.plan.PlanOrgdtl.PK;


@Entity(name="ord_plan_orgdtl")
@IdClass(PK.class)
public class PlanOrgdtl {
	@Id
	@Column(length=50)
	@FieldDefine(title="指标单号",sort=50,hidden=false)
	private String plorno;
	@Id
	@Column(length=50)
	@FieldDefine(title="大类",sort=50,hidden=false)
	private String spclno;
	@Id
	@Column(length=50)
	@FieldDefine(title="小类",sort=50,hidden=false)
	private String sptyno;
	@Id
	@Column(length=50)
	@FieldDefine(title="系列",sort=50,hidden=false)
	private String spseno;

	@FieldDefine(title="区域指标数量",sort=50,hidden=false)
	private Double qymtqt;
	@FieldDefine(title="区域指标金额",sort=50,hidden=false)
	private Double qymtam;
	@FieldDefine(title="特许指标数量",sort=50,hidden=false)
	private Double txmtqt;
	@FieldDefine(title="特许指标金额",sort=50,hidden=false)
	private Double txmtam;
	
	public void addQymtqt(Double qymtqt){
		if(this.qymtqt==null){
			this.qymtqt=0d;
		}
		if(qymtqt==null){
			qymtqt=0d;
		}
		this.qymtqt=this.qymtqt+qymtqt;
	}
	public void addQymtam(Double qymtam){
		if(this.qymtam==null){
			this.qymtam=0d;
		}
		if(qymtam==null){
			qymtam=0d;
		}
		this.qymtam=(new BigDecimal(this.qymtam)).add(new BigDecimal(qymtam)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public void addTxmtqt(Double txmtqt){
		if(this.txmtqt==null){
			this.txmtqt=0d;
		}
		if(txmtqt==null){
			txmtqt=0d;
		}
		this.txmtqt=this.txmtqt+txmtqt;
	}
	public void addTxmtam(Double txmtam){
		if(this.txmtam==null){
			this.txmtam=0d;
		}
		if(txmtam==null){
			txmtam=0d;
		}
		//this.txmtam=this.txmtam+txmtam;
		this.txmtam=(new BigDecimal(this.txmtam)).add(new BigDecimal(txmtam)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public static class PK implements Serializable {
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String plorno;
		private String spclno;
		private String sptyno;
		private String spseno;
		public String getPlorno() {
			return plorno;
		}
		public void setPlorno(String plorno) {
			this.plorno = plorno;
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
		public String getSpseno() {
			return spseno;
		}
		public void setSpseno(String spseno) {
			this.spseno = spseno;
		}
	}

	public String getPlorno() {
		return plorno;
	}

	public void setPlorno(String plorno) {
		this.plorno = plorno;
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

	public String getSpseno() {
		return spseno;
	}

	public void setSpseno(String spseno) {
		this.spseno = spseno;
	}

	public Double getQymtqt() {
		return qymtqt;
	}

	public void setQymtqt(Double qymtqt) {
		this.qymtqt = qymtqt;
	}

	public Double getQymtam() {
		return qymtam;
	}

	public void setQymtam(Double qymtam) {
		this.qymtam = qymtam;
	}

	public Double getTxmtqt() {
		return txmtqt;
	}

	public void setTxmtqt(Double txmtqt) {
		this.txmtqt = txmtqt;
	}

	public Double getTxmtam() {
		return txmtam;
	}

	public void setTxmtam(Double txmtam) {
		this.txmtam = txmtam;
	}

	
}
