package com.youngor.order;

public class SizeVO {
	private String sizety;//规格类型
	private String sizeno;//规格代码
	private String sizenm;//规格名称
	private Integer orszqt;//数量
	private Double szrate;//规格比例中的规格占比

	
	public SizeVO(){
		super();
	}
	
	public String getSizeno() {
		return sizeno;
	}
	public void setSizeno(String sizeno) {
		this.sizeno = sizeno;
	}
	public String getSizenm() {
		return sizenm;
	}
	public void setSizenm(String sizenm) {
		this.sizenm = sizenm;
	}
	public Integer getOrszqt() {
		return orszqt;
	}
	public void setOrszqt(Integer orszqt) {
		this.orszqt = orszqt;
	}
	public Double getSzrate() {
		return szrate;
	}
	public void setSzrate(Double szrate) {
		this.szrate = szrate;
	}

	public String getSizety() {
		return sizety;
	}

	public void setSizety(String sizety) {
		this.sizety = sizety;
	}
}
