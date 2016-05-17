package com.youngor.permission;

import java.util.Date;
import java.util.List;

import com.youngor.org.Org;

public class UserVO extends User {
	
	private Date loginDate;
	
	private List<String> brandes;//可访问的品牌
	private List<String> classes;//可访问的大类
	
	private List<Org> compes;//可访问的营销公司
	private List<Org> regnes;//可访问的区域
	//private Position currentOrg;
	

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

	public List<Org> getCompes() {
		return compes;
	}

	public void setCompes(List<Org> compes) {
		this.compes = compes;
	}

	public List<Org> getRegnes() {
		return regnes;
	}

	public void setRegnes(List<Org> regnes) {
		this.regnes = regnes;
	}

}
