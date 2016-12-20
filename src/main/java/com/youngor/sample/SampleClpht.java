package com.youngor.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.generator.model.FieldDefine;

/**
 * 样衣搭配图片表
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity
@Table(name="ord_sample_clpht")
public class SampleClpht {
	@Id
//	@GeneratedValue(generator = "uuid")
//	@GenericGenerator(
//	        name = "uuid",
//	        strategy = "org.hibernate.id.UUIDGenerator"
//	    )
	@FieldDefine(title="",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String id;
	@FieldDefine(title="搭配代码",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String clppno;
	
	@FieldDefine(title="图片编号",sort=50,hidden=false)
	private Integer photso;
	@FieldDefine(title="图片名",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String photnm;
	@FieldDefine(title="图片描述",sort=50,hidden=false)
	@Column(length=50,nullable=true,updatable=true)
	private String photms;
	@FieldDefine(title="图片文件名",sort=50,hidden=false)
	@Column(length=100,nullable=true,updatable=true)
	private String imgnm;
	@Transient
	private String ormtno;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClppno() {
		return clppno;
	}
	public void setClppno(String clppno) {
		this.clppno = clppno;
	}
	public Integer getPhotso() {
		return photso;
	}
	public void setPhotso(Integer photso) {
		this.photso = photso;
	}
	public String getPhotnm() {
		return photnm;
	}
	public void setPhotnm(String photnm) {
		this.photnm = photnm;
	}
	public String getPhotms() {
		return photms;
	}
	public void setPhotms(String photms) {
		this.photms = photms;
	}
	public String getImgnm() {
		return imgnm;
	}
	public void setImgnm(String imgnm) {
		this.imgnm = imgnm;
	}
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}


}
