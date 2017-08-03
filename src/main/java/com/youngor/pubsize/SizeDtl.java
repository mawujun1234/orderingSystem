package com.youngor.pubsize;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.annotation.FieldDefine;
import com.youngor.pubsize.SizeDtl.PK;
import com.youngor.utils.BaseObject;

/**
 * 规格范围
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_sizedtl")
@IdClass(PK.class)
public class SizeDtl extends BaseObject{
	
	@Id
	@FieldDefine(title="上级类型",hidden=false)
	@Column(length=36)
	private String fszty;
	@Id
	@FieldDefine(title="上级代码",hidden=false)
	@Column(length=36)
	private String fszno;
	@Id
	@FieldDefine(title="规格类型",hidden=false)
	@Column(length=36)
	private String sizety;
	@Id
	@FieldDefine(title="规格代码",hidden=false)
	@Column(length=36)
	private String sizeno;
	
	@FieldDefine(title="订货会批号",hidden=false)
	@Column(length=36)
	private String ormtno;


	public static class PK implements Serializable {
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String fszty;
		private String fszno;
		private String sizety;
		private String sizeno;
		public String getSizety() {
			return sizety;
		}
		public void setSizety(String sizety) {
			this.sizety = sizety;
		}
		public String getSizeno() {
			return sizeno;
		}
		public void setSizeno(String sizeno) {
			this.sizeno = sizeno;
		}
		public String getFszty() {
			return fszty;
		}
		public void setFszty(String fszty) {
			this.fszty = fszty;
		}
		public String getFszno() {
			return fszno;
		}
		public void setFszno(String fszno) {
			this.fszno = fszno;
		}
	}

	public String getFszty() {
		return fszty;
	}

	public void setFszty(String fszty) {
		this.fszty = fszty;
	}

	public String getFszno() {
		return fszno;
	}

	public void setFszno(String fszno) {
		this.fszno = fszno;
	}

	public String getSizety() {
		return sizety;
	}

	public void setSizety(String sizety) {
		this.sizety = sizety;
	}

	public String getSizeno() {
		return sizeno;
	}

	public void setSizeno(String sizeno) {
		this.sizeno = sizeno;
	}

	public String getOrmtno() {
		return ormtno;
	}

	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}

	
}
