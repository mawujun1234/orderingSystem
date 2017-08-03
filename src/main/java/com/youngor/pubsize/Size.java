package com.youngor.pubsize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.annotation.FieldDefine;
import com.youngor.utils.BaseObject;

/**
 * 规格范围
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_size")
//@IdClass(PK.class)
public class Size extends BaseObject{
	@Id
	@FieldDefine(title="规格代码",hidden=false)
	@Column(length=36)
	private String sizeno;//ormtno_ysizeno_流水号
	@FieldDefine(title="规格名称",hidden=false)
	@Column(length=36)
	private String sizenm;
	//@Id
	@FieldDefine(title="订货会批号",hidden=false)
	@Column(length=36)
	private String ormtno;
	//@Id
	@FieldDefine(title="规格类型",hidden=false)
	@Column(length=36)
	private String sizety;

	
	@FieldDefine(title="规格系列类型",hidden=false)
	@Column(length=36)
	private String ysizety;
	@FieldDefine(title="规格系列代码",hidden=false)
	@Column(length=36)
	private String ysizeno;
	@FieldDefine(title="品牌",hidden=false)
	@Column(length=36)
	private String szbrad;
	@FieldDefine(title="大类",hidden=false)
	@Column(length=36)
	private String szclno;

	@FieldDefine(title="排序",hidden=false)
	//@Column(updatable=false)
	private Integer sizeso;

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

	public String getOrmtno() {
		return ormtno;
	}

	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}

	public String getSizety() {
		return sizety;
	}

	public void setSizety(String sizety) {
		this.sizety = sizety;
	}

	public String getYsizety() {
		return ysizety;
	}

	public void setYsizety(String ysizety) {
		this.ysizety = ysizety;
	}

	public String getYsizeno() {
		return ysizeno;
	}

	public void setYsizeno(String ysizeno) {
		this.ysizeno = ysizeno;
	}

	public String getSzbrad() {
		return szbrad;
	}

	public void setSzbrad(String szbrad) {
		this.szbrad = szbrad;
	}

	public String getSzclno() {
		return szclno;
	}

	public void setSzclno(String szclno) {
		this.szclno = szclno;
	}

	public Integer getSizeso() {
		return sizeso;
	}

	public void setSizeso(Integer sizeso) {
		this.sizeso = sizeso;
	}


	
}
