package com.youngor.ordmt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.annotation.FieldDefine;
import com.mawujun.annotation.ShowType;
import com.youngor.utils.BaseObject;

@Entity(name="ord_ordmt")
public class Ordmt extends BaseObject{
	@Id
	@FieldDefine(title="订货会批号",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=false)
	private String ormtno;
	@FieldDefine(title="订货会名称",sort=50)
	@Column(length=100,nullable=false)
	private String ormtnm;
	@FieldDefine(title="订货会简称",sort=50)
	@Column(length=100,nullable=true)
	private String ormtsn;
	@FieldDefine(title="产品年份",sort=50)
	private Integer pryear;
	@FieldDefine(title="开始日期",sort=50)
	//@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date mtstdt;
	//@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@FieldDefine(title="结束日期",sort=50)
	private Date mtfidt;
	@FieldDefine(title="状态",sort=50,showType=ShowType.radio)
	private Boolean ormtst=false;//true完成，false：进行中
	@FieldDefine(title="跟踪状态",sort=50,showType=ShowType.radio)
	private Boolean ormtfg=true;
	@FieldDefine(title="备注",sort=40)
	@Column(length=100,nullable=true)
	private String ormtmk;
	
	/**
	 * 判断当前日期是否是早于订货会的开始日期
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return true表示订货会还没有开始
	 */
	public Boolean isBeforeMtstdt(){
		if(mtstdt==null){
			return true;
		}
		if(mtstdt.getTime()>((new Date())).getTime()){
			return true;
		}
		return false;
	}
	public String getOrmtst_name(){
		if(this.getOrmtst()){
			return "完成";
		} else {
			return "进行中";
		}
	}
	
	public String getOrmtfg_name(){
		if(this.getOrmtfg()){
			return "跟踪";
		} else {
			return "未跟踪";
		}
	}
	

	
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getOrmtnm() {
		return ormtnm;
	}
	public void setOrmtnm(String ormtnm) {
		this.ormtnm = ormtnm;
	}
	public String getOrmtsn() {
		return ormtsn;
	}
	public void setOrmtsn(String ormtsn) {
		this.ormtsn = ormtsn;
	}
	public Integer getPryear() {
		return pryear;
	}
	public void setPryear(Integer pryear) {
		this.pryear = pryear;
	}
	public Date getMtstdt() {
		return mtstdt;
	}
	public void setMtstdt(Date mtstdt) {
		this.mtstdt = mtstdt;
	}
	public Date getMtfidt() {
		return mtfidt;
	}
	public void setMtfidt(Date mtfidt) {
		this.mtfidt = mtfidt;
	}
	public Boolean getOrmtst() {
		return ormtst;
	}
	public void setOrmtst(Boolean ormtst) {
		this.ormtst = ormtst;
	}
	public Boolean getOrmtfg() {
		return ormtfg;
	}
	public void setOrmtfg(Boolean ormtfg) {
		this.ormtfg = ormtfg;
	}
	public String getOrmtmk() {
		return ormtmk;
	}
	public void setOrmtmk(String ormtmk) {
		this.ormtmk = ormtmk;
	}

	
	
}
