package com.youngor.order1;

import com.youngor.pubcode.PubCodeCache;

public class OrdOrddtlQuery {
	private String sampno;
	private String sampnm;
	private String sampnm1;
	private String suitno;
	private String prodnm;
	private String spclno;
	private String sptyno;
	private String spbano;
	private String spseno;
	private String spftpr;
	private String sprtpr;
	private String ormtqt;
	private String lasted_ordate;
	private String plan_indate;
	private String total_orodqt;
	
	public String getSuitno_name() {
		return PubCodeCache.getSuitno_name(this.getSuitno());
	}
	public String getSpclno_name() {
		return PubCodeCache.getSpclno_name(this.getSpclno());
	}
	public String getSptyno_name() {
		return PubCodeCache.getSptyno_name(this.getSptyno());
	}
	public String getSpseno_name() {
		return PubCodeCache.getSpseno_name(this.getSpseno());
	}
	public String getSpbano_name() {
		return PubCodeCache.getSpbano_name(this.getSpbano());
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
	public String getSampnm1() {
		return sampnm1;
	}
	public void setSampnm1(String sampnm1) {
		this.sampnm1 = sampnm1;
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
	public String getSpbano() {
		return spbano;
	}
	public void setSpbano(String spbano) {
		this.spbano = spbano;
	}
	public String getSpftpr() {
		return spftpr;
	}
	public void setSpftpr(String spftpr) {
		this.spftpr = spftpr;
	}
	public String getSprtpr() {
		return sprtpr;
	}
	public void setSprtpr(String sprtpr) {
		this.sprtpr = sprtpr;
	}
	public String getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(String ormtqt) {
		this.ormtqt = ormtqt;
	}
	public String getLasted_ordate() {
		return lasted_ordate;
	}
	public void setLasted_ordate(String lasted_ordate) {
		this.lasted_ordate = lasted_ordate;
	}
	public String getPlan_indate() {
		return plan_indate;
	}
	public void setPlan_indate(String plan_indate) {
		this.plan_indate = plan_indate;
	}
	public String getTotal_orodqt() {
		return total_orodqt;
	}
	public void setTotal_orodqt(String total_orodqt) {
		this.total_orodqt = total_orodqt;
	}
	public String getSpseno() {
		return spseno;
	}
	public void setSpseno(String spseno) {
		this.spseno = spseno;
	}
}
