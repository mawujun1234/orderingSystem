package com.youngor.sample;

/**
 * 用于在设计样衣资料的时候显示用的
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public class SamplePlanDesignVO extends SamplePlanVO{

	private String sampno;//设计样衣id
	private String sampnm;//设计样衣编码
	private String suitty;//套装种类
	
	private Integer spstat;//设计锁定状态
	private Integer matest;//面料信息锁定状态
	private Integer spctst;//成衣信息锁定状态
	
	
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
	public Integer getMatest() {
		return matest;
	}
	public void setMatest(Integer matest) {
		this.matest = matest;
	}
	public Integer getSpctst() {
		return spctst;
	}
	public void setSpctst(Integer spctst) {
		this.spctst = spctst;
	}
	public Integer getSpstat() {
		return spstat;
	}
	public void setSpstat(Integer spstat) {
		this.spstat = spstat;
	}
	public String getSuitty() {
		return suitty;
	}
	public void setSuitty(String suitty) {
		this.suitty = suitty;
	}
}
