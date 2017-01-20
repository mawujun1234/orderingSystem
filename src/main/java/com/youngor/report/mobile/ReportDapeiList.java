package com.youngor.report.mobile;

public class ReportDapeiList {
	private String sampnm;
	private String imgnm;
	private Integer num;//排除掉领带和鞋包的订货数量
	private Integer num_dh;//订货数量
	
	public String getSampnm() {
		return sampnm;
	}
	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}
	public String getImgnm() {
		return imgnm;
	}
	public void setImgnm(String imgnm) {
		this.imgnm = imgnm;
	}
	public Integer getNum() {
		if(num==null){
			return 0;
		}
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getNum_dh() {
		return num_dh;
	}
	public void setNum_dh(Integer num_dh) {
		this.num_dh = num_dh;
	}
}
