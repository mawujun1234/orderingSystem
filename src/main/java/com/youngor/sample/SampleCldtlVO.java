package com.youngor.sample;

import com.youngor.pubcode.PubCodeCache;

public class SampleCldtlVO extends SampleCldtl {
	private String ormtno;
	private String bradno;
	private String sampnm;
	private String clppnm;//搭配编号
	private String sptyno;//大类
	private String imgnm;//搭配图片
	
	//样衣的图片
	private String sampno_imgnm;
	private String sampno_thumb;
	
	public String getSptyno_name() {
		return PubCodeCache.getSptyno_name(this.getSptyno());
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
	public String getSampnm() {
		return sampnm;
	}
	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}
	public String getClppnm() {
		return clppnm;
	}
	public void setClppnm(String clppnm) {
		this.clppnm = clppnm;
	}

	public String getSptyno() {
		return sptyno;
	}

	public void setSptyno(String sptyno) {
		this.sptyno = sptyno;
	}

	public String getImgnm() {
		return imgnm;
	}

	public void setImgnm(String imgnm) {
		this.imgnm = imgnm;
	}

	public String getSampno_imgnm() {
		return sampno_imgnm;
	}

	public void setSampno_imgnm(String sampno_imgnm) {
		this.sampno_imgnm = sampno_imgnm;
	}

	public String getSampno_thumb() {
		return sampno_thumb;
	}

	public void setSampno_thumb(String sampno_thumb) {
		this.sampno_thumb = sampno_thumb;
	}

}
