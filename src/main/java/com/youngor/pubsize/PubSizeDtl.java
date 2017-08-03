package com.youngor.pubsize;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.annotation.FieldDefine;
import com.youngor.pubsize.PubSizeDtl.PK;
import com.youngor.utils.BaseObject;

/**
 * 规格范围
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_pub_sizedtl")
@IdClass(PK.class)
public class PubSizeDtl{
//	@Id
//	@GeneratedValue(generator = "uuid")
//	@GenericGenerator(
//	        name = "uuid",
//	        strategy = "org.hibernate.id.UUIDGenerator"
//	    )
//	@FieldDefine(title="id",hidden=true)
//	@Column(length=36)
//	private String id;
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
//	@FieldDefine(title="规格名称",hidden=false)
//	@Column(length=36)
//	private String sizenm;
	
	@FieldDefine(title="数量",hidden=false)
	private Integer sizeqt;//主要用于包装的时候，定义这个包装里要包含多少数量
	@FieldDefine(title="备注",hidden=false)
	@Column(length=100)
	private String sizemk;
	@FieldDefine(title="状态",hidden=false)
	private Integer sizest=1;//0：作废；1：有效；
	@FieldDefine(title="当季状态",hidden=false)
	private Integer szsast=1;//1：当季；0：非当季

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

	public Integer getSizeqt() {
		return sizeqt;
	}

	public void setSizeqt(Integer sizeqt) {
		this.sizeqt = sizeqt;
	}

	public String getSizemk() {
		return sizemk;
	}

	public void setSizemk(String sizemk) {
		this.sizemk = sizemk;
	}

	public Integer getSizest() {
		return sizest;
	}

	public void setSizest(Integer sizest) {
		this.sizest = sizest;
	}

	public Integer getSzsast() {
		return szsast;
	}

	public void setSzsast(Integer szsast) {
		this.szsast = szsast;
	}
	
	
}
