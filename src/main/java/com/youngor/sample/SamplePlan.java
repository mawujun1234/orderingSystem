package com.youngor.sample;

import java.util.Date;
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
@Table(name="ord_sample_plan")
public class SamplePlan extends BaseObject{
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(
	        name = "uuid",
	        strategy = "org.hibernate.id.UUIDGenerator"
	    )
	@FieldDefine(title="企划样衣代码",sort=50,hidden=true)
	@Column(length=36,nullable=false,updatable=false)
	private String plspno;
	@FieldDefine(title="企划样衣编号",sort=50)
	@Column(length=100,nullable=false,updatable=false)
	private String plspnm;
	
	@FieldDefine(title="订货会批号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String ormtno;
	
	@FieldDefine(title="品牌",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String bradno;//
	@FieldDefine(title="年份",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String spyear;
	@FieldDefine(title="季节",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String spsean;
	@FieldDefine(title="大系列",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String spbseno;
	@FieldDefine(title="品牌系列",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String sprseno;
	@FieldDefine(title="大类",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String spclno;
	@FieldDefine(title="小类",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String sptyno;
	@FieldDefine(title="系列",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String spseno;
	@FieldDefine(title="定位",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String splcno;
	@FieldDefine(title="上市批次",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String spbano;
	
	@FieldDefine(title="出厂价",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private Double spftpr;
	@FieldDefine(title="零售价",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private Double sprtpr;
	@FieldDefine(title="企划倍率",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private Double spplrd;
	@FieldDefine(title="企划成本价",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private Double plctpr;
	
	@FieldDefine(title="计划交货期",sort=50,hidden=false)
	private Date pldate;
	@FieldDefine(title="面料交货期",sort=50,hidden=false)
	private Date mldate;
	
	
	@FieldDefine(title="样衣状态",sort=50,hidden=true)
	private Integer plstat=1;//1：有效；0：无效
	@FieldDefine(title="锁定状态",sort=50,hidden=true)
	private Integer plspst=0;//1：锁定；0：未锁定
	
	//@Transient
	//private List<SamplePlanStpr> samplePlanStpres;//用于前台网后台传递数据的时候，比如新建，更新
	
	public String getPlspno() {
		return plspno;
	}
	public void setPlspno(String plspno) {
		this.plspno = plspno;
	}
	public String getPlspnm() {
		return plspnm;
	}
	public void setPlspnm(String plspnm) {
		this.plspnm = plspnm;
	}
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getBradno() {
		return bradno;
	}
	public void setBradno(String bradno) {
		this.bradno = bradno;
	}
	public String getSpyear() {
		return spyear;
	}
	public void setSpyear(String spyear) {
		this.spyear = spyear;
	}
	public String getSpsean() {
		return spsean;
	}
	public void setSpsean(String spsean) {
		this.spsean = spsean;
	}
	public String getSpbseno() {
		return spbseno;
	}
	public void setSpbseno(String spbseno) {
		this.spbseno = spbseno;
	}
	public String getSprseno() {
		return sprseno;
	}
	public void setSprseno(String sprseno) {
		this.sprseno = sprseno;
	}
	public String getSpclno() {
		return spclno;
	}
	public void setSpclno(String spclno) {
		this.spclno = spclno;
	}
	public String getSptyno() {
		return sptyno;
	}
	public void setSptyno(String sptyno) {
		this.sptyno = sptyno;
	}
	public String getSpseno() {
		return spseno;
	}
	public void setSpseno(String spseno) {
		this.spseno = spseno;
	}
	public String getSplcno() {
		return splcno;
	}
	public void setSplcno(String splcno) {
		this.splcno = splcno;
	}
	public String getSpbano() {
		return spbano;
	}
	public void setSpbano(String spbano) {
		this.spbano = spbano;
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
	public Double getSpplrd() {
		return spplrd;
	}
	public void setSpplrd(Double spplrd) {
		this.spplrd = spplrd;
	}
	public Double getPlctpr() {
		return plctpr;
	}
	public void setPlctpr(Double plctpr) {
		this.plctpr = plctpr;
	}
	public Date getPldate() {
		return pldate;
	}
	public void setPldate(Date pldate) {
		this.pldate = pldate;
	}
	public Date getMldate() {
		return mldate;
	}
	public void setMldate(Date mldate) {
		this.mldate = mldate;
	}
	public Integer getPlstat() {
		return plstat;
	}
	public void setPlstat(Integer plstat) {
		this.plstat = plstat;
	}
	public Integer getPlspst() {
		return plspst;
	}
	public void setPlspst(Integer plspst) {
		this.plspst = plspst;
	}

}
