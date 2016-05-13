package com.youngor.sample;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.utils.BaseObject;

@Entity
@Table(name="ord_sample_design")
public class SampleDesign extends BaseObject{

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(
	        name = "uuid",
	        strategy = "org.hibernate.id.UUIDGenerator"
	    )
	@FieldDefine(title="设计样衣代码",sort=50,hidden=true)
	@Column(length=36,nullable=false,updatable=false)
	private String sampno;
	@FieldDefine(title="设计样衣编号",sort=50)
	@Column(length=36,nullable=false,updatable=false,unique=true)
	private String sampnm;
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
	@Column(length=36,nullable=false,updatable=true)
	private String suitty;
	@FieldDefine(title="规格版型说明",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String desp;
	@FieldDefine(title="规格范围",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=true)
	private String sizegp;
	
	@FieldDefine(title="包装要求",sort=50,hidden=false)
	private Integer packqt;
	@FieldDefine(title="套西是否拆套",sort=50,hidden=false)
	private Integer spltmk;
	@FieldDefine(title="吊牌打印标志",sort=50,hidden=false)
	private Integer print;
	@FieldDefine(title="样衣状态",sort=50,hidden=true)
	private Integer sampst=1;//0：作废；1：有效
	@FieldDefine(title="锁定状态",sort=50,hidden=true)
	private Integer spstat=0;//0：未锁定，1：锁定
	
	@Transient
	private List<SampleDesignStpr> sampleDesignStpres;//用于前台网后台传递数据的时候，比如新建，更新
	
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
	public String getPlspno() {
		return plspno;
	}
	public void setPlspno(String plspno) {
		this.plspno = plspno;
	}
	public String getVersno() {
		return versno;
	}
	public void setVersno(String versno) {
		this.versno = versno;
	}
	public String getPhotno() {
		return photno;
	}
	public void setPhotno(String photno) {
		this.photno = photno;
	}
	public String getStseno() {
		return stseno;
	}
	public void setStseno(String stseno) {
		this.stseno = stseno;
	}
	public String getDesgno() {
		return desgno;
	}
	public void setDesgno(String desgno) {
		this.desgno = desgno;
	}
	public String getBuspno() {
		return buspno;
	}
	public void setBuspno(String buspno) {
		this.buspno = buspno;
	}
	public String getSpmtno() {
		return spmtno;
	}
	public void setSpmtno(String spmtno) {
		this.spmtno = spmtno;
	}
	public String getGustno() {
		return gustno;
	}
	public void setGustno(String gustno) {
		this.gustno = gustno;
	}
	public String getColrno() {
		return colrno;
	}
	public void setColrno(String colrno) {
		this.colrno = colrno;
	}
	public String getPattno() {
		return pattno;
	}
	public void setPattno(String pattno) {
		this.pattno = pattno;
	}
	public String getStylno() {
		return stylno;
	}
	public void setStylno(String stylno) {
		this.stylno = stylno;
	}
	public String getStylgp() {
		return stylgp;
	}
	public void setStylgp(String stylgp) {
		this.stylgp = stylgp;
	}
	public String getSexno() {
		return sexno;
	}
	public void setSexno(String sexno) {
		this.sexno = sexno;
	}
	public String getSlveno() {
		return slveno;
	}
	public void setSlveno(String slveno) {
		this.slveno = slveno;
	}
	public String getSuitty() {
		return suitty;
	}
	public void setSuitty(String suitty) {
		this.suitty = suitty;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public String getSizegp() {
		return sizegp;
	}
	public void setSizegp(String sizegp) {
		this.sizegp = sizegp;
	}
	public Integer getPackqt() {
		return packqt;
	}
	public void setPackqt(Integer packqt) {
		this.packqt = packqt;
	}
	public Integer getSpltmk() {
		return spltmk;
	}
	public void setSpltmk(Integer spltmk) {
		this.spltmk = spltmk;
	}
	public Integer getPrint() {
		return print;
	}
	public void setPrint(Integer print) {
		this.print = print;
	}
	public Integer getSampst() {
		return sampst;
	}
	public void setSampst(Integer sampst) {
		this.sampst = sampst;
	}
	public Integer getSpstat() {
		return spstat;
	}
	public void setSpstat(Integer spstat) {
		this.spstat = spstat;
	}
	public List<SampleDesignStpr> getSampleDesignStpres() {
		return sampleDesignStpres;
	}
	public void setSampleDesignStpres(List<SampleDesignStpr> sampleDesignStpres) {
		this.sampleDesignStpres = sampleDesignStpres;
	}



}