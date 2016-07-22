package com.youngor.report.mobile;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReportMoney {
	private String sprtpr;
	private BigDecimal ormtqt;
	private BigDecimal ormtqt_zb;
	private BigDecimal ormtam;
	private BigDecimal ormtam_zb;
	private BigDecimal sampnocount;
	private BigDecimal sampnocount_zb;
	
	public void addOrmtam(BigDecimal ormtam){
		if(this.ormtam==null){
			this.ormtam= new BigDecimal(0);
		}
		if(ormtam==null){
			ormtam= new BigDecimal(0);
		}
		this.ormtam=this.ormtam.add(ormtam);
	}
	public void addOrmtqt(BigDecimal ormtqt){
		if(this.ormtqt==null){
			this.ormtqt= new BigDecimal(0);
		}
		if(ormtqt==null){
			ormtqt= new BigDecimal(0);
		}
		this.ormtqt=this.ormtqt.add(ormtqt);
	}
	public void addSampnocount(BigDecimal sampnocount){
		if(this.sampnocount==null){
			this.sampnocount= new BigDecimal(0);
		}
		if(sampnocount==null){
			sampnocount= new BigDecimal(0);
		}
		this.sampnocount=this.sampnocount.add(sampnocount);
	}
	
	public BigDecimal getOrmtam_wan() {
		if(this.ormtam==null){
			return new BigDecimal(0);
		}
		return ormtam.divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP);
	}
	public String getSprtpr() {
		return sprtpr;
	}
	public BigDecimal getOrmtqt() {
		return ormtqt;
	}
	public BigDecimal getOrmtqt_zb() {
		if(ormtqt_zb==null){
			return new BigDecimal(0);
		}
		return ormtqt_zb.setScale(2, RoundingMode.HALF_UP);
	}
	public BigDecimal getOrmtam() {
		return ormtam;
	}
	public BigDecimal getOrmtam_zb() {
		if(ormtam_zb==null){
			return new BigDecimal(0);
		}
		return ormtam_zb.setScale(2, RoundingMode.HALF_UP);
	}
	public BigDecimal getSampnocount() {
		return sampnocount;
	}
	public BigDecimal getSampnocount_zb() {
		if(sampnocount_zb==null){
			return new BigDecimal(0);
		}
		return sampnocount_zb.setScale(2, RoundingMode.HALF_UP);
	}
	public void setSprtpr(String sprtpr) {
		this.sprtpr = sprtpr;
	}
	public void setOrmtqt(BigDecimal ormtqt) {
		this.ormtqt = ormtqt;
	}
	public void setOrmtqt_zb(BigDecimal ormtqt_zb) {
		this.ormtqt_zb = ormtqt_zb;
	}
	public void setOrmtam(BigDecimal ormtam) {
		this.ormtam = ormtam;
	}
	public void setOrmtam_zb(BigDecimal ormtam_zb) {
		this.ormtam_zb = ormtam_zb;
	}
	public void setSampnocount(BigDecimal sampnocount) {
		this.sampnocount = sampnocount;
	}
	public void setSampnocount_zb(BigDecimal sampnocount_zb) {
		this.sampnocount_zb = sampnocount_zb;
	}
	
	

}
