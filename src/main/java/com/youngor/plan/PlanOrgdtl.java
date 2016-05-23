package com.youngor.plan;

import java.io.Serializable;

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
	@FieldDefine(title="商品系列",sort=50,hidden=false)
	private String spseno1;
	@Id
	@Column(length=50)
	@FieldDefine(title="指标分类",sort=50,hidden=false)
	private String pltyno;//QY(区域)/TX（特许）
	@FieldDefine(title="指标数量",sort=50,hidden=false)
	private Double plmtqt;
	@FieldDefine(title="指标金额",sort=50,hidden=false)
	private Double plmtam;
	
	public static class PK implements Serializable {
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String plorno;
		private String spclno;
		private String spseno1;
		private String pltyno;
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
		public String getSpseno1() {
			return spseno1;
		}
		public void setSpseno1(String spseno1) {
			this.spseno1 = spseno1;
		}
		public String getPltyno() {
			return pltyno;
		}
		public void setPltyno(String pltyno) {
			this.pltyno = pltyno;
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

	public String getSpseno1() {
		return spseno1;
	}

	public void setSpseno1(String spseno1) {
		this.spseno1 = spseno1;
	}

	public String getPltyno() {
		return pltyno;
	}

	public void setPltyno(String pltyno) {
		this.pltyno = pltyno;
	}

	public Double getPlmtqt() {
		return plmtqt;
	}

	public void setPlmtqt(Double plmtqt) {
		this.plmtqt = plmtqt;
	}

	public Double getPlmtam() {
		return plmtam;
	}

	public void setPlmtam(Double plmtam) {
		this.plmtam = plmtam;
	}


}
