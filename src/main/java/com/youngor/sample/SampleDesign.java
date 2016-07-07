package com.youngor.sample;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.utils.BaseObject;

@Entity
@Table(name="ord_sample_design")
public class SampleDesign extends BaseObject{

	@Id
	@FieldDefine(title="设计样衣代码",sort=50,hidden=true)
	@Column(length=36,nullable=false,updatable=false)
	private String sampno;//plspno+sampnm
	@FieldDefine(title="订货样衣编号",sort=50)
	@Column(length=36,nullable=false,updatable=false,unique=true)
	private String sampnm;
	@FieldDefine(title="出样样衣编号",sort=50)
	@Column(length=36,nullable=false,updatable=true,unique=true)
	private String sampnm1;
	@FieldDefine(title="企划样衣编号",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=false)
	private String plspno;
	@FieldDefine(title="版型",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String versno;
	@FieldDefine(title="照片编号",sort=50,hidden=true)
	@Column(length=36,nullable=true,updatable=true)
	private String photno;
	@FieldDefine(title="工作室系列",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String stseno;
	@FieldDefine(title="设计师",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String desgno;
	@FieldDefine(title="外买样衣编号",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String buspno;
	@FieldDefine(title="生产类型",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String spmtno;
	@FieldDefine(title="客供编号",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String gustno;
	@FieldDefine(title="颜色",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String colrno;
	@FieldDefine(title="花型",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String pattno;
	@FieldDefine(title="款式",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String stylno;
	@FieldDefine(title="款式组",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String stylgp;
	@FieldDefine(title="性别",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String sexno;
	@FieldDefine(title="长短袖",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String slveno;
	@FieldDefine(title="套装种类",sort=50,hidden=false)
	@Column(length=36,nullable=true,updatable=true)
	private String suitty;
	@FieldDefine(title="规格版型说明",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String desp;
//	@FieldDefine(title="规格范围",sort=50,hidden=false)
//	@Column(length=36,nullable=false,updatable=true)
//	private String sizegp;
	
	@FieldDefine(title="包装要求",sort=50,hidden=false)
	private Integer packqt;
	@FieldDefine(title="套西是否拆套",sort=50,hidden=false)
	private Integer spltmk;//1：拆套；0：不拆套
	@FieldDefine(title="吊牌打印标志",sort=50,hidden=false)
	private Integer print;
	@FieldDefine(title="样衣状态",sort=50,hidden=true)
	private Integer sampst=1;//0：作废；1：有效
	@FieldDefine(title="锁定状态",sort=50,hidden=true)
	private Integer spstat=0;//0：未锁定，1：锁定
	@FieldDefine(title="必定款",sort=50,hidden=true)
	private Integer abstat;//0：不需要必定，1：必定
	
	@Transient
	private List<SampleDesignSizegp> sampleDesignSizegpes;
	@Transient
	private String ormtno;
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}
	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}
	public void setSampnm1(String sampnm1) {
		this.sampnm1 = sampnm1;
	}
	public void setPlspno(String plspno) {
		this.plspno = plspno;
	}
	public void setVersno(String versno) {
		if(versno==null){
			versno="";
			return;
		}
		this.versno = versno;
	}
	public void setPhotno(String photno) {
		this.photno = photno;
	}
	public void setStseno(String stseno) {
		
		this.stseno = stseno;
	}
	public void setDesgno(String desgno) {
		this.desgno = desgno;
	}
	public void setBuspno(String buspno) {
		this.buspno = buspno;
	}
	public void setSpmtno(String spmtno) {
		this.spmtno = spmtno;
	}
	public void setGustno(String gustno) {
		this.gustno = gustno;
	}
	public void setColrno(String colrno) {
		this.colrno = colrno;
	}
	public void setPattno(String pattno) {
		this.pattno = pattno;
	}
	public void setStylno(String stylno) {
		this.stylno = stylno;
	}
	public void setStylgp(String stylgp) {
		this.stylgp = stylgp;
	}
	public void setSexno(String sexno) {
		this.sexno = sexno;
	}
	public void setSlveno(String slveno) {
		this.slveno = slveno;
	}
	public void setSuitty(String suitty) {
		this.suitty = suitty;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public void setPackqt(Integer packqt) {
		this.packqt = packqt;
	}
	public void setSpltmk(Integer spltmk) {
		this.spltmk = spltmk;
	}
	public void setPrint(Integer print) {
		this.print = print;
	}
	public void setSampst(Integer sampst) {
		this.sampst = sampst;
	}
	public void setSpstat(Integer spstat) {
		this.spstat = spstat;
	}
	public void setAbstat(Integer abstat) {
		this.abstat = abstat;
	}
	public void setSampleDesignSizegpes(List<SampleDesignSizegp> sampleDesignSizegpes) {
		this.sampleDesignSizegpes = sampleDesignSizegpes;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getSampno() {
		return sampno;
	}
	public String getSampnm() {
		return sampnm;
	}
	public String getSampnm1() {
		return sampnm1;
	}
	public String getPlspno() {
		return plspno;
	}
	public String getVersno() {
		return versno;
	}
	public String getPhotno() {
		return photno;
	}
	public String getStseno() {
		return stseno;
	}
	public String getDesgno() {
		return desgno;
	}
	public String getBuspno() {
		return buspno;
	}
	public String getSpmtno() {
		return spmtno;
	}
	public String getGustno() {
		return gustno;
	}
	public String getColrno() {
		return colrno;
	}
	public String getPattno() {
		return pattno;
	}
	public String getStylno() {
		return stylno;
	}
	public String getStylgp() {
		return stylgp;
	}
	public String getSexno() {
		return sexno;
	}
	public String getSlveno() {
		return slveno;
	}
	public String getSuitty() {
		return suitty;
	}
	public String getDesp() {
		return desp;
	}
	public Integer getPackqt() {
		return packqt;
	}
	public Integer getSpltmk() {
		return spltmk;
	}
	public Integer getPrint() {
		return print;
	}
	public Integer getSampst() {
		return sampst;
	}
	public Integer getSpstat() {
		return spstat;
	}
	public Integer getAbstat() {
		return abstat;
	}
	public List<SampleDesignSizegp> getSampleDesignSizegpes() {
		return sampleDesignSizegpes;
	}
	public String getOrmtno() {
		return ormtno;
	}
	
	
}
