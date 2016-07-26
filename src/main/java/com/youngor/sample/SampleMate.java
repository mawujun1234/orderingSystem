package com.youngor.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.sample.SampleMate.PK;

/**
 * 样衣面料表
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity
@Table(name="ord_sample_mate")
@IdClass(PK.class)
public class SampleMate {

	@Id
	@FieldDefine(title="设计样衣代码",sort=50,hidden=false)
	@Column(length=36,nullable=false,updatable=false)
	private String sampno;
	@Id
	@FieldDefine(title="面料编号",sort=50,hidden=false)
	@Column(nullable=false,updatable=false)
	private Integer mateso;//1为主面料,就是说第一个输入的为主面料，其他的输入的就是2，3按照序号来
	@FieldDefine(title="供应商",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String mtsuno;
	@FieldDefine(title="供应商面料货号",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String mateno;
	@FieldDefine(title="面料品牌",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String mtbrad;
	@FieldDefine(title="进口/国产",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String mttype;
	@FieldDefine(title="面料成分",sort=50,hidden=false)
	@Column(length=500,nullable=false,updatable=true)
	private String mtcomp;
	@FieldDefine(title="纱支规格",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String yarmct;
	@FieldDefine(title="克重/密度",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String gramwt;
	@FieldDefine(title="后整理",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String aftrmt;
	@FieldDefine(title="门幅",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String width;
	@FieldDefine(title="面料单价",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String mtpupr;
	@FieldDefine(title="单件用料",sort=50,hidden=false)
	@Column(length=50,nullable=false,updatable=true)
	private String mtcnqt;
	@FieldDefine(title="锁定状态",sort=50,hidden=true)
	private Integer matest;//锁定状态：1：锁定；0：未锁定
	
	@Transient
	private String mtsuno_name;
	
	public void setMateno(String mateno) {
		if(mateno!=null){
			this.mateno = mateno.toUpperCase();
		}
	}
	
	public static class PK implements Serializable {
		private static final long serialVersionUID = 1L;

		private String sampno;

        private Integer mateso;

		public String getSampno() {
			return sampno;
		}

		public void setSampno(String sampno) {
			this.sampno = sampno;
		}

		public Integer getMateso() {
			return mateso;
		}

		public void setMateso(Integer mateso) {
			this.mateso = mateso;
		}
	}
	
	public PK getPk(){
		PK pk=new PK();
		pk.setMateso(this.getMateso());
		pk.setSampno(this.getSampno());
		return pk;
	}
	


	public String getSampno() {
		return sampno;
	}

	public void setSampno(String sampno) {
		this.sampno = sampno;
	}

	public Integer getMateso() {
		return mateso;
	}

	public void setMateso(Integer mateso) {
		this.mateso = mateso;
	}

	public String getMtsuno() {
		return mtsuno;
	}

	public void setMtsuno(String mtsuno) {
		this.mtsuno = mtsuno;
	}

	public String getMateno() {
		return mateno;
	}

	

	public String getMtbrad() {
		return mtbrad;
	}

	public void setMtbrad(String mtbrad) {
		this.mtbrad = mtbrad;
	}

	public String getMttype() {
		return mttype;
	}

	public void setMttype(String mttype) {
		this.mttype = mttype;
	}

	public String getMtcomp() {
		return mtcomp;
	}

	public void setMtcomp(String mtcomp) {
		this.mtcomp = mtcomp;
	}

	public String getYarmct() {
		return yarmct;
	}

	public void setYarmct(String yarmct) {
		this.yarmct = yarmct;
	}

	public String getGramwt() {
		return gramwt;
	}

	public void setGramwt(String gramwt) {
		this.gramwt = gramwt;
	}

	public String getAftrmt() {
		return aftrmt;
	}

	public void setAftrmt(String aftrmt) {
		this.aftrmt = aftrmt;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getMtpupr() {
		return mtpupr;
	}

	public void setMtpupr(String mtpupr) {
		this.mtpupr = mtpupr;
	}

	public String getMtcnqt() {
		return mtcnqt;
	}

	public void setMtcnqt(String mtcnqt) {
		this.mtcnqt = mtcnqt;
	}

	public Integer getMatest() {
		return matest;
	}

	public void setMatest(Integer matest) {
		this.matest = matest;
	}



	public String getMtsuno_name() {
		return mtsuno_name;
	}



	public void setMtsuno_name(String mtsuno_name) {
		this.mtsuno_name = mtsuno_name;
	}
}
