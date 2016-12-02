package com.youngor.plan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;

/**
 * 商品起订量表
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_plan_cls")
@IdClass(PlanCls.PK.class)
public class PlanCls {
	@Id
	@Column(length=30)
	@FieldDefine(title="订货批号",sort=50,hidden=false)
	private String ormtno;
	@Id
	@Column(length=30)
	@FieldDefine(title="品牌",sort=50,hidden=false)
	private String bradno;
	@Id
	@Column(length=30)
	@FieldDefine(title="大类",sort=50,hidden=false)
	private String spclno;
	@Id
	@Column(length=30)
	@FieldDefine(title="系列",sort=50,hidden=false)
	private String spseno;
	@FieldDefine(title="起订量",sort=50,hidden=false)
	private Integer ordqty;

	public static class PK implements Serializable{
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String ormtno;
		private String bradno;
		private String spclno;
		private String spseno;
		public String getOrmtno() {
			return ormtno;
		}
		public void setOrmtno(String ormtno) {
			this.ormtno = ormtno;
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
		public String getSpseno() {
			return spseno;
		}
		public void setSpseno(String spseno) {
			this.spseno = spseno;
		}
		
		
	}

	public String getOrmtno() {
		return ormtno;
	}

	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
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

	public String getSpseno() {
		return spseno;
	}

	public void setSpseno(String spseno) {
		this.spseno = spseno;
	}

	public Integer getOrdqty() {
		return ordqty;
	}

	public void setOrdqty(Integer ordqty) {
		this.ordqty = ordqty;
	}
}
