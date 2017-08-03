package com.youngor.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.annotation.FieldDefine;

/**
 * 订货类型
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_ordty")
public class Ordty {
	@Id
	@Column(length=40)
	@FieldDefine(title="代码")
	private String ortyno;
	@Column(length=40)
	@FieldDefine(title="名称")
	private String ortynm;
	@Column(length=140)
	@FieldDefine(title="描述")
	private String ortyds;
	@Column(length=140)
	@FieldDefine(title="备注")
	private String ortymk;
	@FieldDefine(title="状态")
	private Integer stat;
	
	public String getOrtyno() {
		return ortyno;
	}
	public void setOrtyno(String ortyno) {
		this.ortyno = ortyno;
	}
	public String getOrtynm() {
		return ortynm;
	}
	public void setOrtynm(String ortynm) {
		this.ortynm = ortynm;
	}
	public String getOrtyds() {
		return ortyds;
	}
	public void setOrtyds(String ortyds) {
		this.ortyds = ortyds;
	}
	public String getOrtymk() {
		return ortymk;
	}
	public void setOrtymk(String ortymk) {
		this.ortymk = ortymk;
	}
	public Integer getStat() {
		return stat;
	}
	public void setStat(Integer stat) {
		this.stat = stat;
	}


}
