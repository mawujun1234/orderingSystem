package com.youngor.order.cg;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.youngor.pubcode.PubCodeCache;

public class CgOrddtlVO extends CgOrddtl {
	private String sampnm;
	private String sampnm1;
	private String orstat;
	private Integer ormtqt;
	private Integer orszqt_now;
	private Integer orszqt_already;
	
	private String mldate;
	private String pldate;
	private String pplace;
	
	public String getSuitno_name(){
		return PubCodeCache.getSuitno_name(this.getSuitno());
	}
	
	public Integer getOrszqt_residue(){
		if(ormtqt==null || ormtqt==0){
			ormtqt=0;
		}
		if(orszqt_already==null){
			orszqt_already=0;
		}
		return ormtqt-orszqt_already;
	}
	public String getOrszqt_zhanb(){
		if(ormtqt==null || ormtqt==0){
			return "0";
		}
		if(orszqt_already==null) {
			//orszqt_already=0;
			return "0";
		}
		return (new BigDecimal(orszqt_already)).divide(new BigDecimal(ormtqt),RoundingMode.HALF_UP).multiply(new BigDecimal(100)).toString();
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
	public String getOrstat() {
		return orstat;
	}
	public void setOrstat(String orstat) {
		this.orstat = orstat;
	}
	public Integer getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(Integer ormtqt) {
		this.ormtqt = ormtqt;
	}
	public Integer getOrszqt_now() {
		return orszqt_now;
	}
	public void setOrszqt_now(Integer orszqt_now) {
		this.orszqt_now = orszqt_now;
	}
	public Integer getOrszqt_already() {
		return orszqt_already;
	}
	public void setOrszqt_already(Integer orszqt_already) {
		this.orszqt_already = orszqt_already;
	}

	public String getMldate() {
		return mldate;
	}

	public void setMldate(String mldate) {
		this.mldate = mldate;
	}

	public String getPldate() {
		return pldate;
	}

	public void setPldate(String pldate) {
		this.pldate = pldate;
	}

	public String getPplace() {
		return pplace;
	}

	public void setPplace(String pplace) {
		this.pplace = pplace;
	}

}
