package com.youngor.order;

import com.youngor.pubcode.PubCodeCache;

public class QyNewFormVO {
	private String sampno;
	private String suitno;
	//private String suitnm;
	private Integer ormtqt;
	
	public String getSuitnm(){
		return PubCodeCache.getSuitno_name(suitno);
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

	public Integer getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(Integer ormtqt) {
		this.ormtqt = ormtqt;
	}

}
