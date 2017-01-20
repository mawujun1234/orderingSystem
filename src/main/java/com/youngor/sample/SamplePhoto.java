package com.youngor.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.utils.BaseObject;

@Entity
@Table(name="ord_sample_photo")
public class SamplePhoto extends BaseObject {
	@Id
	@FieldDefine(title="id",hidden=true)
	@Column(length=36)
	private String id;
	@FieldDefine(title="设计样衣编号",hidden=true)
	@Column(length=36)
	private String sampno;
	@FieldDefine(title="图片名",sort=50,hidden=false)
	@Column(length=160,nullable=false,updatable=true)
	private String photnm;
	@FieldDefine(title="图片描述",sort=50,hidden=false)
	@Column(length=80,nullable=false,updatable=true)
	private String photms;
	@FieldDefine(title="图片文件名",sort=50,hidden=false)
	@Column(length=80,nullable=false,updatable=true)
	private String imgnm;
	@FieldDefine(title="锁定状态",sort=50,hidden=false)
	private Integer photst=0;//：1：锁定；0：未锁定
	
	@FieldDefine(title="缩略图地址",sort=50,hidden=false)
	@Column(length=160,nullable=false,updatable=true)
	private String thumb;
	
	@Transient
	private String ormtno;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
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
		if(imgnm==null){
			return "../mobile/images/no_photo.jpg";
		}
		return imgnm;
	}
	public void setImgnm(String imgnm) {
		this.imgnm = imgnm;
	}
	public Integer getPhotst() {
		return photst;
	}
	public void setPhotst(Integer photst) {
		this.photst = photst;
	}
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}


}
