package com.youngor.sample;

import com.youngor.pubcode.PubCodeCache;

public class SampleProdVO extends SampleProd{
	private String bradno;//
	private String spyear;
	private String spsean;
	private String spbseno;
	private String sprseno;
	private String spclno;
	private String sptyno;
	private String spseno;
	private String splcno;
	private String spbano;
	
	private String sampnm;
	private String versno;
	private String stseno;
	private String desgno;
	private String buspno;
	private String spmtno;
	private String gustno;
	private String colrno;
	private String pattno;
	private String stylno;
	private String stylgp;
	private String sexno;
	private String slveno;
	private String suitty;
	private String desp;
	
	
	private String sample_state;//是否订过货
	private String sizegp_name;
//	private Integer packqt;
	
	public  String getSuitno_name() {
		return PubCodeCache.getSuitno_name(this.getSuitno());
	}

	public  String getProrgd_name() {
		return PubCodeCache.getProrgd_name(this.getProrgd());
	}
	public String getPrprpt_name(){
		return PubCodeCache.getPrprpt_name(this.getPrprpt());
	}
	
	public String getPrtype_name(){
		return PubCodeCache.getPrtype_name(this.getPrtype());
	}
	public String getBradno_name() {
		return PubCodeCache.getBradno_name(this.getBradno());
	}
	public String getSpsean_name() {
		return PubCodeCache.getSpsean_name(this.getSpsean());
	}
	public String getSpbseno_name() {
		return PubCodeCache.getSpbseno_name(this.getSpbseno());
	}
	public String getSprseno_name() {
		return PubCodeCache.getSprseno_name(this.getSprseno());
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
	public String getSplcno_name() {
		return PubCodeCache.getSplcno_name(this.getSplcno());
	}
	public String getSpbano_name() {
		return PubCodeCache.getSpbano_name(this.getSpbano());
	}
	
	
	
	public String getBradno() {
		return bradno;
	}

	public void setBradno(String bradno) {
		this.bradno = bradno;
	}

	public String getSpyear() {
		return spyear;
	}

	public void setSpyear(String spyear) {
		this.spyear = spyear;
	}

	public String getSpsean() {
		return spsean;
	}

	public void setSpsean(String spsean) {
		this.spsean = spsean;
	}

	public String getSpbseno() {
		return spbseno;
	}

	public void setSpbseno(String spbseno) {
		this.spbseno = spbseno;
	}

	public String getSprseno() {
		return sprseno;
	}

	public void setSprseno(String sprseno) {
		this.sprseno = sprseno;
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

	public String getSplcno() {
		return splcno;
	}

	public void setSplcno(String splcno) {
		this.splcno = splcno;
	}

	public String getSpbano() {
		return spbano;
	}

	public void setSpbano(String spbano) {
		this.spbano = spbano;
	}

	public String getSampnm() {
		return sampnm;
	}

	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}

	public String getVersno() {
		return versno;
	}

	public void setVersno(String versno) {
		this.versno = versno;
	}

	public String getStseno() {
		return stseno;
	}

	public void setStseno(String stseno) {
		this.stseno = stseno;
	}

	public String getDesgno() {
		return desgno;
	}

	public void setDesgno(String desgno) {
		this.desgno = desgno;
	}

	public String getBuspno() {
		return buspno;
	}

	public void setBuspno(String buspno) {
		this.buspno = buspno;
	}

	public String getSpmtno() {
		return spmtno;
	}

	public void setSpmtno(String spmtno) {
		this.spmtno = spmtno;
	}

	public String getGustno() {
		return gustno;
	}

	public void setGustno(String gustno) {
		this.gustno = gustno;
	}

	public String getColrno() {
		return colrno;
	}

	public void setColrno(String colrno) {
		this.colrno = colrno;
	}

	public String getPattno() {
		return pattno;
	}

	public void setPattno(String pattno) {
		this.pattno = pattno;
	}

	public String getStylno() {
		return stylno;
	}

	public void setStylno(String stylno) {
		this.stylno = stylno;
	}

	public String getStylgp() {
		return stylgp;
	}

	public void setStylgp(String stylgp) {
		this.stylgp = stylgp;
	}

	public String getSexno() {
		return sexno;
	}

	public void setSexno(String sexno) {
		this.sexno = sexno;
	}

	public String getSlveno() {
		return slveno;
	}

	public void setSlveno(String slveno) {
		this.slveno = slveno;
	}

	public String getSuitty() {
		return suitty;
	}

	public void setSuitty(String suitty) {
		this.suitty = suitty;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public String getSample_state() {
		return sample_state;
	}

	public void setSample_state(String sample_state) {
		this.sample_state = sample_state;
	}

	public String getSizegp_name() {
		return sizegp_name;
	}

	public void setSizegp_name(String sizegp_name) {
		this.sizegp_name = sizegp_name;
	}

}
