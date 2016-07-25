package com.youngor.report.mobile;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReportOrg {
	private String orgno;
	private String orgnm;
	private BigDecimal ormtqt;//订货数量
	private BigDecimal ormtam;//订货金额
	private BigDecimal qymtqt;//区域指标数量
	private BigDecimal qymtam;//区域指标金额
	
	/**
	 * 获取完成率
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public String getOrmtam_wancl(){
		BigDecimal ormtam=getOrmtam_wan();
		if(ormtam==null){
			return "0%";
		}
		if(qymtam!=null && qymtam.doubleValue()>0){
			return ormtam.multiply(new BigDecimal(100)).divide(qymtam, 2,RoundingMode.HALF_UP).toString()+"%";
		}
		return "";
	}
	/**
	 * 获取完成率
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public String getOrmtqt_wancl(){
		//BigDecimal ormtam=getOrmtam_wan();
		if(ormtqt==null){
			return "0%";
		}
		if(qymtqt!=null && qymtqt.doubleValue()>0){
			return ormtqt.multiply(new BigDecimal(100)).divide(qymtqt, 2,RoundingMode.HALF_UP).toString()+"%";
		}
		return "";
	}
	public BigDecimal getOrmtam_wan() {
		if(ormtam==null){
			return new BigDecimal(0);
		}
		return ormtam.divide(new BigDecimal(10000),2,RoundingMode.HALF_UP);
		//return ormtam;
	}
	public void addOrmtam(BigDecimal ormtam) {
		if(this.ormtam==null){
			this.ormtam= new BigDecimal(0);
		}
		if(ormtam==null){
			ormtam= new BigDecimal(0);
		}
		this.ormtam=this.ormtam.add(ormtam);
	}
	public void addOrmtqt(BigDecimal ormtqt) {
		if(this.ormtqt==null){
			this.ormtqt= new BigDecimal(0);
		}
		if(ormtqt==null){
			ormtqt= new BigDecimal(0);
		}
		this.ormtqt=this.ormtqt.add(ormtqt);
	}
	public void addQymtam(BigDecimal qymtam){
		if(this.qymtam==null){
			this.qymtam= new BigDecimal(0);
		}
		if(qymtam==null){
			qymtam= new BigDecimal(0);
		}
		this.qymtam=this.qymtam.add(qymtam);
	}
	public void addQymtqt(BigDecimal qymtqt){
		if(this.qymtqt==null){
			this.qymtqt= new BigDecimal(0);
		}
		if(qymtqt==null){
			qymtqt= new BigDecimal(0);
		}
		this.qymtqt=this.qymtqt.add(qymtqt);
	}
	
	
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}
	public String getOrgnm() {
		return orgnm;
	}
	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}
	public BigDecimal getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(BigDecimal ormtqt) {
		this.ormtqt = ormtqt;
	}
	public BigDecimal getOrmtam() {
		return ormtam;
	}
	public void setOrmtam(BigDecimal ormtam) {
		this.ormtam = ormtam;
	}
	public BigDecimal getQymtqt() {
		return qymtqt;
	}
	public void setQymtqt(BigDecimal qymtqt) {
		this.qymtqt = qymtqt;
	}
	public BigDecimal getQymtam() {
		return qymtam;
	}
	public void setQymtam(BigDecimal qymtam) {
		this.qymtam = qymtam;
	}
	


}
