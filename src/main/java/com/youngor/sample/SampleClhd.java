package com.youngor.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.annotation.FieldDefine;
import com.youngor.pubcode.PubCodeCache;

/**
 * 样衣搭配主表
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity
@Table(name="ord_sample_clhd")
public class SampleClhd {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(
	        name = "uuid",
	        strategy = "org.hibernate.id.UUIDGenerator"
	    )
	@FieldDefine(title="搭配代码",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String clppno;
	@FieldDefine(title="搭配编号",sort=50,hidden=false,genQuery=true)
	@Column(length=50,nullable=true,updatable=true)
	private String clppnm;
	@FieldDefine(title="订货会批号",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String ormtno;
	@FieldDefine(title="品牌",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String bradno;
	@FieldDefine(title="上市批次",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String spbano;
	
	@FieldDefine(title="搭配说明",sort=50,hidden=false)
	@Column(length=100,nullable=true,updatable=true)
	private String clppmk;
	@FieldDefine(title="搭配状态",sort=50,hidden=false)
	private Integer clppst=1;//1:有效；0：无效
	@FieldDefine(title="吊牌打印标志",sort=50,hidden=false)
	private Integer print;
	
	public String getSpbano_name() {
		return PubCodeCache.getSpbano_name(this.getSpbano());
	}
	
	public String getClppno() {
		return clppno;
	}
	public void setClppno(String clppno) {
		this.clppno = clppno;
	}
	public String getClppnm() {
		return clppnm;
	}
	public void setClppnm(String clppnm) {
		this.clppnm = clppnm;
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
	public String getSpbano() {
		return spbano;
	}
	public void setSpbano(String spbano) {
		this.spbano = spbano;
	}
	public String getClppmk() {
		return clppmk;
	}
	public void setClppmk(String clppmk) {
		this.clppmk = clppmk;
	}
	public Integer getClppst() {
		return clppst;
	}
	public void setClppst(Integer clppst) {
		this.clppst = clppst;
	}
	public Integer getPrint() {
		return print;
	}
	public void setPrint(Integer print) {
		this.print = print;
	}

}
