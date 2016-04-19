package com.youngor.permission;

import java.util.Date;
import java.util.List;

public class UserVO extends User {
	
	private Date loginDate;
	
	private List<String> brandes;//可访问的品牌
	private List<String> classes;//可访问的大类
	

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public List<String> getBrandes() {
		return brandes;
	}

	public void setBrandes(List<String> brandes) {
		this.brandes = brandes;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

}
