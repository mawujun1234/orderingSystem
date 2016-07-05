package com.youngor.order;

public class OrdszdtlVO extends Ordszdtl {
	private Integer value;//用于在规格平衡的时候，更新数据用的
	private Boolean isSTDSZPRDPK;
	private Integer sztype;//规格上报方式
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Boolean getIsSTDSZPRDPK() {
		return isSTDSZPRDPK;
	}

	public void setIsSTDSZPRDPK(Boolean isSTDSZPRDPK) {
		this.isSTDSZPRDPK = isSTDSZPRDPK;
	}

	public Integer getSztype() {
		return sztype;
	}

	public void setSztype(Integer sztype) {
		this.sztype = sztype;
	}
}
