package com.youngor.order;

public class OrdChgdtlQyVO  {
	private String qyno;
	private String qynm;
	
	
	private String sampno;
	private String sampnm;
	
	private Integer ormtqt;
	private Integer orchqt;
	
	private String ormark;
	
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
//	public Integer getOrmtqt_orchqt(){
//		return ormtqt+(orchqt==null?0:orchqt);
//	}
	
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}
	public String getSampnm() {
		return sampnm;
	}
	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}

	public Integer getOrmtqt() {
		return ormtqt;
	}
	public Integer getOrchqt() {
		return orchqt;
	}
	public String getQyno() {
		return qyno;
	}
	public void setQyno(String qyno) {
		this.qyno = qyno;
	}
	public String getQynm() {
		return qynm;
	}
	public void setQynm(String qynm) {
		this.qynm = qynm;
	}
	public String getOrmark() {
		return ormark;
	}
	public void setOrmark(String ormark) {
		this.ormark = ormark;
	}
	
}
