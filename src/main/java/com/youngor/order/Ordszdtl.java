package com.youngor.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.annotation.FieldDefine;
import com.youngor.order.Ordszdtl.PK;
import com.youngor.utils.BaseObject;

/**
 * 订单明细规格表
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_ordszdtl")
@IdClass(PK.class)
public class Ordszdtl  extends BaseObject{
	@Id
	@Column(length=30)
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
	@Id
	@Column(length=30)
	@FieldDefine(title="规格类型",sort=50,hidden=false)
	private String sizety;
	@Id
	@Column(length=30)
	@FieldDefine(title="规格代码",sort=50,hidden=false)
	private String sizeno;
	
	
	@Column(length=30)
	@FieldDefine(title="审批订单号",sort=50,hidden=false)
	private String mlorno;
	
	@FieldDefine(title="规格状态",sort=50,hidden=false)
	private Integer orszst;//0：未确认；1：已确认
	
	@FieldDefine(title="原始数量",sort=50,hidden=false)
	private Integer oritqt;
	@FieldDefine(title="数量",sort=50,hidden=false)
	private Integer orszqt;
	@FieldDefine(title="包装数量",sort=50,hidden=false)
	private Integer orbgqt;//一开始都相同，只有在“整箱+单规”的上报方式的时候，并且在规格平衡之后这个数据就是自动成箱计算出来的数据,如果是其他上报方式的时候，这个数据永远都要等于数量
	
	@Column(length=100)
	@FieldDefine(title="备注",sort=50,hidden=false)
	private String ormark;
	
	public Ordszdtl.PK geetPK(){
		Ordszdtl.PK pk=new Ordszdtl.PK();
		pk.setMtorno(mtorno);
		pk.setSampno(sampno);
		pk.setSuitno(suitno);
		pk.setSizety(sizety);
		pk.setSizeno(sizeno);
		return pk;
	}
	public static class PK implements Serializable {
		private String mtorno;
		private String sampno;
		private String suitno;
		private String sizety;
		private String sizeno;
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
		public String getSizety() {
			return sizety;
		}
		public void setSizety(String sizety) {
			this.sizety = sizety;
		}
		public String getSizeno() {
			return sizeno;
		}
		public void setSizeno(String sizeno) {
			this.sizeno = sizeno;
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
	public String getSizety() {
		return sizety;
	}
	public void setSizety(String sizety) {
		this.sizety = sizety;
	}
	public String getSizeno() {
		return sizeno;
	}
	public void setSizeno(String sizeno) {
		this.sizeno = sizeno;
	}
	public Integer getOrszqt() {
		return orszqt;
	}
	public void setOrszqt(Integer orszqt) {
		this.orszqt = orszqt;
	}
	public Integer getOrszst() {
		return orszst;
	}
	public void setOrszst(Integer orszst) {
		this.orszst = orszst;
	}
	public Integer getOrbgqt() {
		return orbgqt;
	}
	public void setOrbgqt(Integer orbgqt) {
		this.orbgqt = orbgqt;
	}
	public String getOrmark() {
		return ormark;
	}
	public void setOrmark(String ormark) {
		this.ormark = ormark;
	}
	public Integer getOritqt() {
		return oritqt;
	}
	public void setOritqt(Integer oritqt) {
		this.oritqt = oritqt;
	}

}
