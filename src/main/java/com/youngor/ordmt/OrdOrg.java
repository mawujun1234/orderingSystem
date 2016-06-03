package com.youngor.ordmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.ordmt.OrdOrg.PK;

/**
 * 订货单位
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ORD_ORG")
@IdClass(PK.class)
public class OrdOrg {
	@Id
	@FieldDefine(title="订货会编号")
	@Column(length=36)
	private String ormtno;
	@Id
	@FieldDefine(title="订货单位")
	@Column(length=36)
	private String ordorg;//订货单位
	
	@FieldDefine(title="订货会类型")
	@Column(length=36)
	private String channo;//订货单位类型
	@FieldDefine(title="上报方式")
	private Integer sztype;//0：规格+包装上报；1：规格上报；2：包装上报；
	@FieldDefine(title="打印状态")
	private Integer print=0;//0：未打印；1：已打印
	
	@Transient
	private String orgnm;
	@Transient
	private String channm;
	
	public static class PK implements Serializable{
		private String ormtno;
		private String ordorg;
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

	public String getChanno() {
		return channo;
	}

	public void setChanno(String channo) {
		this.channo = channo;
	}

	public Integer getSztype() {
		return sztype;
	}

	public void setSztype(Integer sztype) {
		this.sztype = sztype;
	}

	public Integer getPrint() {
		return print;
	}

	public void setPrint(Integer print) {
		this.print = print;
	}

	public String getOrgnm() {
		return orgnm;
	}

	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}

	public String getChannm() {
		return channm;
	}

	public void setChannm(String channm) {
		this.channm = channm;
	}

}
