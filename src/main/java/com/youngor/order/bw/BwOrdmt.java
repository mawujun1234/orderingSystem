package com.youngor.order.bw;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mawujun.generator.model.FieldDefine;

@Entity(name="ord_bw_ordmt")
public class BwOrdmt {
	@Id
	@Column(length=30)
	@FieldDefine(title="订货会子批次",sort=50,hidden=false)
	private String ormmno;//订货会批号+日期
	
	
	@Column(length=30)
	@FieldDefine(title="订货会批号",sort=50,hidden=false)
	private String ormtno;
	
	@Column(length=30)
	@FieldDefine(title="订货会子批次说明",sort=50,hidden=false)
	private String ormmnm;
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getOrmmno() {
		return ormmno;
	}
	public void setOrmmno(String ormmno) {
		this.ormmno = ormmno;
	}
	public String getOrmmnm() {
		return ormmnm;
	}
	public void setOrmmnm(String ormmnm) {
		this.ormmnm = ormmnm;
	}

}
