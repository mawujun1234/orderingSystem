package com.youngor.order;

import com.youngor.pubcode.PubCodeCache;

public class OrdChgdtlVO  {
	private String spclno;
	private String sptyno;
	private String spsean;
	private String spseno;
	private String suitno;
	
	
	private String sampno;
	private String sampnm;
	private String prodnm;
	
	private Integer ormtqt;
	private Integer orchqt;
	
	public void setOrmtqt(Integer ormtqt) {
		if(ormtqt==null){
			this.ormtqt=0;
		} else {
			this.ormtqt = ormtqt;
		}
	}
	public void setOrchqt(Integer orchqt) {
		if(orchqt==null){
			this.orchqt=0;
		} else {
			this.orchqt = orchqt;
		}
	}
	public Integer getOrmtqt_orchqt(){
		return ormtqt+(orchqt==null?0:orchqt);
	}
	public String getSpclno_name() {
		return PubCodeCache.getSpclno_name(this.getSpclno());
	}
	public String getSptyno_name() {
		return PubCodeCache.getSptyno_name(this.getSptyno());
	}
	public String getSpsean_name() {
		return PubCodeCache.getSpsean_name(this.getSpsean());
	}
	public String getSpseno_name() {
		return PubCodeCache.getSpseno_name(this.getSpseno());
	}
	public String getSuitno_name() {
		return PubCodeCache.getSuitno_name(this.getSuitno());
	}
	
	
	
	public String getSpclno() {
		return spclno;
	}
	public void setSpclno(String spclno) {
		this.spclno = spclno;
	}
	public String getSptyno() {
		return sptyno;
	}
	public void setSptyno(String sptyno) {
		this.sptyno = sptyno;
	}
	public String getSpsean() {
		return spsean;
	}
	public void setSpsean(String spsean) {
		this.spsean = spsean;
	}
	public String getSpseno() {
		return spseno;
	}
	public void setSpseno(String spseno) {
		this.spseno = spseno;
	}
	public String getSuitno() {
		return suitno;
	}
	public void setSuitno(String suitno) {
		this.suitno = suitno;
	}
	public String getProdnm() {
		return prodnm;
	}
	public void setProdnm(String prodnm) {
		this.prodnm = prodnm;
	}
	public Integer getOrmtqt() {
		return ormtqt;
	}

	public Integer getOrchqt() {
		return orchqt;
	}



	public String getSampnm() {
		return sampnm;
	}


	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}
}
