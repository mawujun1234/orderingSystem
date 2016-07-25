package com.youngor.report.mobile;

import java.math.BigDecimal;

public class AlreadyOd {
	private Integer rownum;
	private String sampno;
	private String sampnm;
	private String sprtpr;
	private String imgnm;
	private BigDecimal ormtqt;
	
	public String getSampnm() {
		return sampnm;
	}
	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}
	public String getSprtpr() {
		return sprtpr;
	}
	public void setSprtpr(String sprtpr) {
		this.sprtpr = sprtpr;
	}
	public String getImgnm() {
		if(this.imgnm==null || "".equals(imgnm)){
			return "../mobile/images/no_photo.jpg";
		}
		return imgnm;
	}
	public void setImgnm(String imgnm) {
		this.imgnm = imgnm;
	}
	public BigDecimal getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(BigDecimal ormtqt) {
		this.ormtqt = ormtqt;
	}
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}

}
