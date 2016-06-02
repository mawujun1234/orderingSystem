package com.youngor.sample;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.generator.model.FieldDefine;



@Entity
@Table(name="ord_sample_colth")
public class SampleColth {
	@Id
	@FieldDefine(title="设计样衣代码",sort=50,hidden=true)
	@Column(length=36,nullable=false,updatable=false)
	private String sampno;
	@FieldDefine(title="纱厂",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String spcotn;
	@FieldDefine(title="开发供应商代码",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String spsuno;
	@FieldDefine(title="货号采购供应商代码",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String prsuno;
	@FieldDefine(title="含税工缴",sort=50,hidden=false)
	@Column(nullable=false,updatable=true)
	private Double sptapa;
	@FieldDefine(title="含税辅料",sort=50,hidden=false)
	@Column(nullable=false,updatable=true)
	private Double spacry;
	@FieldDefine(title="服饰配料",sort=50,hidden=false)
	@Column(nullable=false,updatable=true)
	private Double spclbd;
	@FieldDefine(title="新成衣价",sort=50,hidden=false)
	@Column(nullable=false,updatable=true)
	private Double spnwpr;
	@FieldDefine(title="成衣数量",sort=50,hidden=false)
	@Column(nullable=false,updatable=true)
	private Integer contqt;
	@FieldDefine(title="成衣金额",sort=50,hidden=false)
	@Column(nullable=false,updatable=true)
	private Double contam;
	@FieldDefine(title="成衣核价克重",sort=50,hidden=false)
	@Column(nullable=false,updatable=true)
	private Double contpr;
	@FieldDefine(title="合同交期",sort=50,hidden=false)
	private Date ctdwdt;
	@FieldDefine(title="包装辅料费",sort=50,hidden=false)
	@Column(nullable=true,updatable=true)
	private Integer acsyam;
	@FieldDefine(title="预计成本价",sort=50,hidden=false)
	@Column(nullable=true,updatable=true)
	private Double spctpr;
	@FieldDefine(title="备注",sort=50,hidden=false)
	@Column(length=100,nullable=true,updatable=true)
	private String sprmk;
	@FieldDefine(title="锁定状态",sort=50,hidden=false)
	private Integer spctst=0;//：1：锁定；0：未锁定
	
	@Transient
	private Double spftpr;//出厂价
	@Transient
	private Double sprtpr;//零售价
	//spftpr_spctpr//出厂价/预计成本价
	//sprtpr_spftpr//零售价/出厂价
	//sprtpr_spctpr//零售价/预计成本价
	/**
	 * 出厂价/预计成本价
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Double getSpftpr_spctpr(){
		if(spftpr==null || spctpr==null || spctpr==0){
			return 0d;
		} else {
			return spftpr/spctpr;
		}
	}
	/**
	 * 零售价/出厂价
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Double getSprtpr_spftpr(){
		if(sprtpr==null || spftpr==null || spftpr==0){
			return 0d;
		} else {
			return sprtpr/spftpr;
		}
	}
	/**
	 * 零售价/预计成本价
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Double getSprtpr_spctpr(){
		if(sprtpr==null || spctpr==null || spctpr==0){
			return 0d;
		} else {
			return sprtpr/spctpr;
		}
	}
	
	@Transient
	private List<SampleDesignStpr> sampleDesignStpres;//用于前台网后台传递数据的时候，比如新建，更新
	
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}
	public String getSpcotn() {
		return spcotn;
	}
	public void setSpcotn(String spcotn) {
		this.spcotn = spcotn;
	}
	public String getSpsuno() {
		return spsuno;
	}
	public void setSpsuno(String spsuno) {
		this.spsuno = spsuno;
	}
	public String getPrsuno() {
		return prsuno;
	}
	public void setPrsuno(String prsuno) {
		this.prsuno = prsuno;
	}
	public Double getSptapa() {
		return sptapa;
	}
	public void setSptapa(Double sptapa) {
		this.sptapa = sptapa;
	}
	public Double getSpacry() {
		return spacry;
	}
	public void setSpacry(Double spacry) {
		this.spacry = spacry;
	}
	public Double getSpclbd() {
		return spclbd;
	}
	public void setSpclbd(Double spclbd) {
		this.spclbd = spclbd;
	}
	public Double getSpnwpr() {
		return spnwpr;
	}
	public void setSpnwpr(Double spnwpr) {
		this.spnwpr = spnwpr;
	}
	public Integer getContqt() {
		return contqt;
	}
	public void setContqt(Integer contqt) {
		this.contqt = contqt;
	}
	public Double getContam() {
		return contam;
	}
	public void setContam(Double contam) {
		this.contam = contam;
	}
	public Double getContpr() {
		return contpr;
	}
	public void setContpr(Double contpr) {
		this.contpr = contpr;
	}
	public Date getCtdwdt() {
		return ctdwdt;
	}
	public void setCtdwdt(Date ctdwdt) {
		this.ctdwdt = ctdwdt;
	}
	public Integer getAcsyam() {
		return acsyam;
	}
	public void setAcsyam(Integer acsyam) {
		this.acsyam = acsyam;
	}
	public Double getSpctpr() {
		return spctpr;
	}
	public void setSpctpr(Double spctpr) {
		this.spctpr = spctpr;
	}
	public String getSprmk() {
		return sprmk;
	}
	public void setSprmk(String sprmk) {
		this.sprmk = sprmk;
	}
	public Integer getSpctst() {
		return spctst;
	}
	public void setSpctst(Integer spctst) {
		this.spctst = spctst;
	}
	public List<SampleDesignStpr> getSampleDesignStpres() {
		return sampleDesignStpres;
	}
	public void setSampleDesignStpres(List<SampleDesignStpr> sampleDesignStpres) {
		this.sampleDesignStpres = sampleDesignStpres;
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


}
