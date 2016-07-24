package com.youngor.report.mobile;

import java.math.BigDecimal;

import com.youngor.pubcode.PubCodeCache;

/**
 * 第一张报表中左边的内容
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public class ReportFirst {
	private String bradno;
	private BigDecimal ormtqt;
	private BigDecimal ormtam;
	private BigDecimal sampnocount;
	
	public String getBradno_name() {
		return PubCodeCache.getBradno_name(bradno);
	}
	public String getBradno() {
		return bradno;
	}
	public void setBradno(String bradno) {
		this.bradno = bradno;
	}
	public BigDecimal getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(BigDecimal ormtqt) {
		this.ormtqt = ormtqt;
	}
	public BigDecimal getOrmtam() {
		return ormtam;
	}
	public void setOrmtam(BigDecimal ormtam) {
		this.ormtam = ormtam;
	}
	public BigDecimal getSampnocount() {
		return sampnocount;
	}
	public void setSampnocount(BigDecimal sampnocount) {
		this.sampnocount = sampnocount;
	}

}
