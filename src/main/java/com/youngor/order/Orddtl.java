package com.youngor.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.order.Orddtl.PK;
import com.youngor.utils.BaseObject;
/**
 * 订单明细报
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_orddtl")
@IdClass(PK.class)
public class Orddtl extends BaseObject{
	@Id
	@Column(length=50)
	@FieldDefine(title="订单号",sort=50,hidden=false)
	private String mtorno;
	@Id
	@Column(length=30)
	@FieldDefine(title="设计样衣代码",sort=50,hidden=false)
	private String sampno;
	@Id
	@Column(length=30)
	@FieldDefine(title="套件",sort=50,hidden=false)
	private String suitno;
	
	@Column(length=30)
	@FieldDefine(title="审批订单号",sort=50,hidden=false)
	private String mlorno;
	@FieldDefine(title="审批订单版本号",sort=50,hidden=false)
	private Integer mlorvn;
	
	@FieldDefine(title="原始数量",sort=50,hidden=false)
	private Integer ormtqs;
	@FieldDefine(title="确认数量",sort=50,hidden=false)
	private Integer ormtqt;
	@FieldDefine(title="数量1",sort=50,hidden=false)
	private Integer ormtqt1;
	
	@Column(length=100)
	@FieldDefine(title="备注",sort=50,hidden=false)
	private String ormark;
	
	public static class PK implements Serializable {
		private String mtorno;
		private String sampno;
		private String suitno;
		public String getSampno() {
			return sampno;
		}
		public void setSampno(String sampno) {
			this.sampno = sampno;
		}
		public String getMtorno() {
			return mtorno;
		}
		public void setMtorno(String mtorno) {
			this.mtorno = mtorno;
		}
		public String getSuitno() {
			return suitno;
		}
		public void setSuitno(String suitno) {
			this.suitno = suitno;
		}
	}

	public String getMtorno() {
		return mtorno;
	}

	public void setMtorno(String mtorno) {
		this.mtorno = mtorno;
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

	public Integer getOrmtqs() {
		return ormtqs;
	}

	public void setOrmtqs(Integer ormtqs) {
		this.ormtqs = ormtqs;
	}

	public Integer getOrmtqt() {
		return ormtqt;
	}

	public void setOrmtqt(Integer ormtqt) {
		this.ormtqt = ormtqt;
	}

	public Integer getOrmtqt1() {
		return ormtqt1;
	}

	public void setOrmtqt1(Integer ormtqt1) {
		this.ormtqt1 = ormtqt1;
	}

	public String getOrmark() {
		return ormark;
	}

	public void setOrmark(String ormark) {
		this.ormark = ormark;
	}


}
