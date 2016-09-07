package com.youngor.report;



import com.mawujun.utils.NumberFormat;
import com.youngor.order.Ortyno;
import com.youngor.pubcode.PubCodeCache;

public class OrderNumTotal {
	private String yxgsnm;
	private String qynm;
	private String orgnm;
	private String bradno;
	private String spclno;
	private String sptyno;
	private String spseno;
	private String colrno;
	private String spsean;
	private String spbano;
	private String versno;
	private Ortyno ortyno;
	
	private String prodnm;
	private String sampnm;
	private String suitno;
	
	private Double ormtqt;
	private Double spftpr;
	private Double spftpr_jine;
	private Double sprtpr;
	private Double sprtpr_jine;
	
	public Double getSprtpr_jine_wan() {
		if(sprtpr_jine==null){
			return 0d;
		}
		Double temp=sprtpr_jine/10000;
		return Double.parseDouble(NumberFormat.formatNoComma(temp));
	}
	public Double getSpftpr_jine_wan() {
		if(spftpr_jine==null){
			return 0d;
		}
		Double temp=spftpr_jine/10000;
		return Double.parseDouble(NumberFormat.formatNoComma(temp));
	}
	public String getBradno_name() {
		return PubCodeCache.getBradno_name(this.getBradno());
	}
	public String getSpsean_name() {
		return PubCodeCache.getSpsean_name(this.getSpsean());
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
	public String getColrno_name() {
		return PubCodeCache.getColrno_name(this.getColrno());
	}
	public String getVersno_name() {
		return PubCodeCache.getVersno_name(this.getVersno());
	}
	public String getSuitno_name() {
		return PubCodeCache.getSuitno_name(this.getSuitno());
	}
	public String getOrtyno_name() {
		if(ortyno==null){
			return null;
		}
		return ortyno.getName();
	}

	
	public String getYxgsnm() {
		return yxgsnm;
	}
	public void setYxgsnm(String yxgsnm) {
		this.yxgsnm = yxgsnm;
	}
	public String getQynm() {
		return qynm;
	}
	public void setQynm(String qynm) {
		this.qynm = qynm;
	}
	public String getOrgnm() {
		return orgnm;
	}
	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}
	public String getBradno() {
		return bradno;
	}
	public void setBradno(String bradno) {
		this.bradno = bradno;
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
	public String getSpseno() {
		return spseno;
	}
	public void setSpseno(String spseno) {
		this.spseno = spseno;
	}
	public String getColrno() {
		return colrno;
	}
	public void setColrno(String colrno) {
		this.colrno = colrno;
	}
	public String getSpsean() {
		return spsean;
	}
	public void setSpsean(String spsean) {
		this.spsean = spsean;
	}
	public String getSpbano() {
		return spbano;
	}
	public void setSpbano(String spbano) {
		this.spbano = spbano;
	}
	public String getVersno() {
		return versno;
	}
	public void setVersno(String versno) {
		this.versno = versno;
	}
	public String getProdnm() {
		return prodnm;
	}
	public void setProdnm(String prodnm) {
		this.prodnm = prodnm;
	}
	public String getSampnm() {
		return sampnm;
	}
	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}
	public String getSuitno() {
		return suitno;
	}
	public void setSuitno(String suitno) {
		this.suitno = suitno;
	}
	public Double getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(Double ormtqt) {
		this.ormtqt = ormtqt;
	}
	public Double getSpftpr() {
		return spftpr;
	}
	public void setSpftpr(Double spftpr) {
		this.spftpr = spftpr;
	}
	public Double getSpftpr_jine() {
		return spftpr_jine;
	}
	public void setSpftpr_jine(Double spftpr_jine) {
		this.spftpr_jine = spftpr_jine;
	}
	public Double getSprtpr() {
		return sprtpr;
	}
	public void setSprtpr(Double sprtpr) {
		this.sprtpr = sprtpr;
	}
	public Double getSprtpr_jine() {
		return sprtpr_jine;
	}
	public void setSprtpr_jine(Double sprtpr_jine) {
		this.sprtpr_jine = sprtpr_jine;
	}


}
