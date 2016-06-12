package com.youngor.order;

import java.util.ArrayList;
import java.util.List;

import com.youngor.pubcode.PubCodeCache;
import com.youngor.sample.SampleDesignStpr;

public class SampleVO {

	private String sampno;//设计样衣id
	private String sampnm;//设计样衣名称
	private String plspno;//企划样衣id
	private String plspnm;//企划样衣名称
	
	private String versno;//版型
	private String spseno;//系列
	
	private Double spftpr;//出厂价
	private Double sprtpr;//零售价
	
	private String spbseno;//大系列
	//private String sprseno;//品牌系列
	
	private String splcno;//定位
	private String spbano;//上市批次
	
	private String spclno;//大类
	private String sptyno;//小类
	private String bradno;//品牌
	private String sexno;//性别  Z0：男  Z1：女
	private String suitty;//套装种类
	private Integer spltmk;//是否拆套 1：拆套；0：不拆套
	
	private Integer abstat;//必定款
	
	
	private String ormtno;//订货会编号
	private String ordorg;//订货单位
	private String mtorno;//订单号
	
	//private List<SuitVO> suitVOs;
	
	private List<SampleDesignStpr> suitStpres=new ArrayList<SampleDesignStpr>();//各个套件的价格
	public Boolean getShowSuitStpres(){
		if(suitStpres==null || suitStpres.size()==0){
			return false;
		} else {
			return true;
		}
	}
	
	
	public String getVersno_name() {
		return PubCodeCache.getVersno_name(this.getVersno());
	}
	public String getSpseno_name() {
		return PubCodeCache.getSpseno_name(this.getSpseno());
	}
	public String getSpbseno_name() {
		return PubCodeCache.getSpbseno_name(this.getSpbseno());
	}
//	public String getSprseno_name() {
//		return PubCodeCache.getSprseno_name(this.getSprseno());
//	}
	public String getSplcno_name() {
		return PubCodeCache.getSplcno_name(this.getSplcno());
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
	public String getPlspno() {
		return plspno;
	}
	public void setPlspno(String plspno) {
		this.plspno = plspno;
	}
	public String getPlspnm() {
		return plspnm;
	}
	public void setPlspnm(String plspnm) {
		this.plspnm = plspnm;
	}
	public String getVersno() {
		return versno;
	}
	public void setVersno(String versno) {
		this.versno = versno;
	}
	public String getSpseno() {
		return spseno;
	}
	public void setSpseno(String spseno) {
		this.spseno = spseno;
	}
	public Double getSpftpr() {
		return spftpr;
	}
	public void setSpftpr(Double spftpr) {
		this.spftpr = spftpr;
	}
	public Double getSprtpr() {
		return sprtpr;
	}
	public void setSprtpr(Double sprtpr) {
		this.sprtpr = sprtpr;
	}
	public String getSpbseno() {
		return spbseno;
	}
	public void setSpbseno(String spbseno) {
		this.spbseno = spbseno;
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
	public String getSptyno() {
		return sptyno;
	}
	public void setSptyno(String sptyno) {
		this.sptyno = sptyno;
	}
	public String getSexno() {
		return sexno;
	}
	public void setSexno(String sexno) {
		this.sexno = sexno;
	}
	public String getSuitty() {
		return suitty;
	}
	public void setSuitty(String suitty) {
		this.suitty = suitty;
	}
	public Integer getSpltmk() {
		return spltmk;
	}
	public void setSpltmk(Integer spltmk) {
		this.spltmk = spltmk;
	}
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}

	public String getSpclno() {
		return spclno;
	}
	public void setSpclno(String spclno) {
		this.spclno = spclno;
	}
	public String getOrdorg() {
		return ordorg;
	}
	public void setOrdorg(String ordorg) {
		this.ordorg = ordorg;
	}
	public String getBradno() {
		return bradno;
	}
	public void setBradno(String bradno) {
		this.bradno = bradno;
	}
	public String getMtorno() {
		return mtorno;
	}
	public void setMtorno(String mtorno) {
		this.mtorno = mtorno;
	}


	public List<SampleDesignStpr> getSuitStpres() {
		return suitStpres;
	}


	public void setSuitStpres(List<SampleDesignStpr> suitStpres) {
		this.suitStpres = suitStpres;
	}


	public Integer getAbstat() {
		return abstat;
	}


	public void setAbstat(Integer abstat) {
		this.abstat = abstat;
	}

}
