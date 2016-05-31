package com.youngor.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.youngor.order.CompPal.PK;

/**
 * 总共公司平衡
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_comp_pal")
@IdClass(PK.class)
public class CompPal {
	@Id
	@Column(length=50)
	private String ormtno;
	@Id
	@Column(length=50)
	private String sampno;
	@Column(length=50)
	private String paltpy;
	@Column(length=50)
	private String psmpno;
	@Column(length=50)
	private String ormark;
	
	public static class PK  implements Serializable{
		private String ormtno;
		private String sampno;
		
		public String getOrmtno() {
			return ormtno;
		}
		public void setOrmtno(String ormtno) {
			this.ormtno = ormtno;
		}
		public String getSampno() {
			return sampno;
		}
		public void setSampno(String sampno) {
			this.sampno = sampno;
		}
		
	}
	
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}
	public String getPaltpy() {
		return paltpy;
	}
	public void setPaltpy(String paltpy) {
		this.paltpy = paltpy;
	}
	public String getPsmpno() {
		return psmpno;
	}
	public void setPsmpno(String psmpno) {
		this.psmpno = psmpno;
	}
	public String getOrmark() {
		return ormark;
	}
	public void setOrmark(String ormark) {
		this.ormark = ormark;
	}

}
