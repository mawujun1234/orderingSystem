package com.youngor.order;

public class OrdChgdtlCompVO  {
	private String compno;
	private String compnm;
	
	
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
	public String getCompno() {
		return compno;
	}
	public void setCompno(String compno) {
		this.compno = compno;
	}
	public String getCompnm() {
		return compnm;
	}
	public void setCompnm(String compnm) {
		this.compnm = compnm;
	}
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
	
}
