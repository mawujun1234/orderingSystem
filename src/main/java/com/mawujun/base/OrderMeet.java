package com.mawujun.base;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="t_ordermeet")
public class OrderMeet {
	@Id
	private UUID id;
	@Column(length=20,nullable=false,unique=true,updatable=false)
	private String code;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=60,nullable=false)
	private String name;
	
	
}
