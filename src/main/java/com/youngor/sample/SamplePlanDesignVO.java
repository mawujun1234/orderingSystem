package com.youngor.sample;

/**
 * 用于在设计样衣资料的时候显示用的
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public class SamplePlanDesignVO extends SamplePlanVO{

	private String photno;//
	private String sampno;//设计样衣id
	private String sampnm;//设计样衣编码
	private String sampnm1;//出样样衣编号
	private String suitty;//套装种类
	private Integer abstat;//必定款
	
	private Integer spstat;//设计锁定状态
	private Integer matest;//面料信息锁定状态
	private Integer spctst;//成衣信息锁定状态
	
	private String spordt;//最晚下单日期
	private String spindt;
	
	
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
	public String getPhotno() {
		return photno;
	}
	public void setPhotno(String photno) {
		this.photno = photno;
	}
	public Integer getAbstat() {
		return abstat;
	}
	public void setAbstat(Integer abstat) {
		this.abstat = abstat;
	}
	public String getSampnm1() {
		return sampnm1;
	}
	public void setSampnm1(String sampnm1) {
		this.sampnm1 = sampnm1;
	}
	public String getSpordt() {
		return spordt;
	}
	public void setSpordt(String spordt) {
		this.spordt = spordt;
	}
	public String getSpindt() {
		return spindt;
	}
	public void setSpindt(String spindt) {
		this.spindt = spindt;
	}
}
