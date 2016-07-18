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
	
	private Boolean isTotal=false;//判断这行是不是小计行
	/**
	 * 获取数量合计
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Double getTotalmtqt(){
		Double mtqt=0d;
		if(super.getQymtqt()!=null){
			mtqt=mtqt+super.getQymtqt();
		}
		if(super.getTxmtqt()!=null){
			mtqt=mtqt+super.getTxmtqt();
		}
		return mtqt;
	}
	/**
	 * 获取金额合计
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Double getTotalmtam(){
		Double mtam=0d;
		if(super.getQymtam()!=null){
			mtam=mtam+super.getQymtam();
		}
		if(super.getTxmtam()!=null){
			mtam=mtam+super.getTxmtam();
		}
		return mtam;
	}
	
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
