package com.youngor.order.bw;

public class BwOrdszdtlVO extends BwOrdszdtl {
	private String ormmno;//订货会批号+日期
	private String ormtno;
	private String ordorg;
	
	
	
	
	//private String mmorno;
	private String bradno;
	private String spclno;
	public String getOrmmno() {
		return ormmno;
	}
	public void setOrmmno(String ormmno) {
		this.ormmno = ormmno;
	}
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
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
	public String getSpclno() {
		return spclno;
	}
	public void setSpclno(String spclno) {
		this.spclno = spclno;
	}
	
}
