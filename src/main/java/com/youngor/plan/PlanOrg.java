package com.youngor.plan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.generator.model.FieldDefine;

@Entity(name="ord_plan_org")
public class PlanOrg {
	@Id
	@Column(length=50)
	@FieldDefine(title="指标单号",sort=50,hidden=false)
	private String plorno;
	@Column(length=36)
	@FieldDefine(title="订货批号",sort=50,hidden=false)
	private String ormtno;
	@Column(length=36)
	@FieldDefine(title="订货单位",sort=50,hidden=false)
	private String ordorg;
	@Column(length=36)
	@FieldDefine(title="品牌",sort=50,hidden=false)
	private String bradno;
	@FieldDefine(title="状态",sort=50,hidden=false)
	private Integer plstat;//"0：编辑中；1：审批中；2：大区审批通过；3：总部审批通过；4：退回"
	
	
	public String getPlorno() {
		return plorno;
	}
	public void setPlorno(String plorno) {
		this.plorno = plorno;
	}
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getOrdorg() {
		return ordorg;
	}
	public void setOrdorg(String ordorg) {
		this.ordorg = ordorg;
	}
	public String getBradno() {
		return bradno;
	}
	public void setBradno(String bradno) {
		this.bradno = bradno;
	}
	public Integer getPlstat() {
		return plstat;
	}
	public void setPlstat(Integer plstat) {
		this.plstat = plstat;
	}


}
