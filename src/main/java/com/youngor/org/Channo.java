package com.youngor.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.generator.model.FieldDefine;

@Entity(name="t_org_chancl")
public class Channo {
	@Id
	@Column(length=15)
	@FieldDefine(title="渠道类型代码")
	private String channo;
	@Column(length=50)
	@FieldDefine(title="渠道类型名称")
	private String channm;
	@Column(length=10)
	@FieldDefine(title="排序")
	private String chanso;
	@FieldDefine(title="渠道类型名称")
	private Integer stat;
	public String getChanno() {
		return channo;
	}
	public void setChanno(String channo) {
		this.channo = channo;
	}
	public String getChannm() {
		return channm;
	}
	public void setChannm(String channm) {
		this.channm = channm;
	}
	public String getChanso() {
		return chanso;
	}
	public void setChanso(String chanso) {
		this.chanso = chanso;
	}
	public Integer getStat() {
		return stat;
	}
	public void setStat(Integer stat) {
		this.stat = stat;
	}
	
}
