package com.youngor.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.generator.model.FieldDefine;

/**
 * 样衣搭配明细表
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity
@Table(name="ord_sample_cldtl")
@IdClass(SampleCldtl.PK.class)
public class SampleCldtl {
	@Id
	@FieldDefine(title="搭配代码",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String clppno;
	@Id
	@FieldDefine(title="样衣编号",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String sampno;
	
	@FieldDefine(title="搭配样衣序号",sort=50,hidden=false)
	private String clspno;
	@FieldDefine(title="搭配说明",sort=50,hidden=false)
	@Column(length=100,nullable=true,updatable=true)
	private String clspmk;
	
	public static class PK implements Serializable {
		private static final long serialVersionUID = 1L;
	
		private String clppno;
	
	    private String sampno;

		public String getClppno() {
			return clppno;
		}

		public void setClppno(String clppno) {
			this.clppno = clppno;
		}

		public String getSampno() {
			return sampno;
		}

		public void setSampno(String sampno) {
			this.sampno = sampno;
		}
	

	
	    
	}

	public String getClppno() {
		return clppno;
	}

	public void setClppno(String clppno) {
		this.clppno = clppno;
	}

	public String getSampno() {
		return sampno;
	}

	public void setSampno(String sampno) {
		this.sampno = sampno;
	}

	public String getClspno() {
		return clspno;
	}

	public void setClspno(String clspno) {
		this.clspno = clspno;
	}

	public String getClspmk() {
		return clspmk;
	}

	public void setClspmk(String clspmk) {
		this.clspmk = clspmk;
	}

}
