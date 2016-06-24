package com.youngor.plan;

import com.youngor.pubcode.PubCodeCache;

public class PlanOrgdtlVO extends PlanOrgdtl{
	private String ormtno;//订货会批号
	private String ordorg;
	private String bradno;
	private Integer plstat;
	

	private String orgnm;//订货单位
	private String spclnm;
	private String sptynm;
	private String spsenm;
	
	private Boolean isTotal=false;
	
	public String getBradnm(){
		return PubCodeCache.getBradno_name(this.getBradno());
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
	public String getOrgnm() {
		return orgnm;
	}
	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}
	public String getSpclnm() {
		return spclnm;
	}
	public void setSpclnm(String spclnm) {
		this.spclnm = spclnm;
	}
	public String getSptynm() {
		return sptynm;
	}
	public void setSptynm(String sptynm) {
		this.sptynm = sptynm;
	}
	public String getSpsenm() {
		return spsenm;
	}
	public void setSpsenm(String spsenm) {
		this.spsenm = spsenm;
	}
	public Boolean getIsTotal() {
		return isTotal;
	}
	public void setIsTotal(Boolean isTotal) {
		this.isTotal = isTotal;
	}

	


}
