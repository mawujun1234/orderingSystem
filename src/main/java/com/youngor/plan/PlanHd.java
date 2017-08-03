package com.youngor.plan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.annotation.FieldDefine;
import com.youngor.plan.PlanHd.PK;
/**
 * 总部指标
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_plan_hd")
@IdClass(PK.class)
public class PlanHd {
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
	@FieldDefine(title="渠道代码",sort=50,hidden=false)
	private String orgno;//营销公司代码
	
	
	@Column(length=30)
	@FieldDefine(title="渠道类型",sort=50,hidden=false)
	private String channo;//营销公司代码  ‘YXGS’	
	@FieldDefine(title="指标数量",sort=50,hidden=false)
	private Double plmtqt;
	@FieldDefine(title="指标金额",sort=50,hidden=false)
	private Double plmtam;
	@FieldDefine(title="生效状态",sort=50,hidden=false)
	private Integer plstat;//0：编辑中；1：已确认；
	
	public void addPlmtqt(Double plmtqt){
		if(this.plmtqt==null){
			this.plmtqt=0d;
		}
		if(plmtqt==null){
			return;
		}
		this.plmtqt=this.plmtqt+plmtqt;
	}
	public void addPlmtam(Double plmtam){
		if(this.plmtam==null){
			this.plmtam=0d;
		}
		if(plmtam==null){
			return;
		}
		this.plmtam=this.plmtam+plmtam;
		this.plmtam=(new BigDecimal(this.plmtam)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	
	public static class PK implements Serializable{
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String ormtno;
		private String bradno;
		private String spclno;
		private String orgno;
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
		public String getOrgno() {
			return orgno;
		}
		public void setOrgno(String orgno) {
			this.orgno = orgno;
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
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
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
	public Integer getPlstat() {
		return plstat;
	}
	public void setPlstat(Integer plstat) {
		this.plstat = plstat;
	}
	public String getChanno() {
		return channo;
	}
	public void setChanno(String channo) {
		this.channo = channo;
	}

}
