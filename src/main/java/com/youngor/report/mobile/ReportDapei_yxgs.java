package com.youngor.report.mobile;

import java.util.ArrayList;
import java.util.List;

public class ReportDapei_yxgs {
	private String clppnm;//搭配名称
	private String imgnm;//搭配图片
	private Integer sumnum;//搭配数量-汇总
	
	private List<ReportDapei_qy> qyes;
	
	public void addQyes(ReportDapei_qy qy) {
		if(this.qyes==null){
			this.qyes=new ArrayList<ReportDapei_qy>();
		}
		this.qyes.add(qy);
	}
	public void addSumnum(Integer minnum){
		if(this.sumnum==null){
			this.sumnum=0;
		}
		this.sumnum=this.sumnum+minnum;
	}
	
	public String getClppnm() {
		return clppnm;
	}
	public void setClppnm(String clppnm) {
		this.clppnm = clppnm;
	}
	public String getImgnm() {
		return imgnm;
	}
	public void setImgnm(String imgnm) {
		this.imgnm = imgnm;
	}
	public Integer getSumnum() {
		return sumnum;
	}
	public void setSumnum(Integer sumnum) {
		this.sumnum = sumnum;
	}
	public List<ReportDapei_qy> getQyes() {
		return qyes;
	}
	public void setQyes(List<ReportDapei_qy> qyes) {
		this.qyes = qyes;
	}
}
