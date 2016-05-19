package com.youngor.permission;

import java.util.Date;
import java.util.List;

import com.youngor.order.Ord;
import com.youngor.org.Org;

public class UserVO extends User {
	
	private Date loginDate;
	
	private List<String> brandes;//可访问的品牌
	private List<String> classes;//可访问的大类
	
	private List<Org> compes;//可访问的营销公司
	private List<Org> regnes;//可访问的区域
	
	private List<Org> currentOrges;//当前所属的组织单元
	
	private Ord ord;//现场订货的时候的订单,主订单，一个用户一次订货会只产生一个主订单
	
	/**
	 * 获取第一个组织单元，在订货会的时候一个账号只属于一个组织单元，否则就会报错，这里定义了这个规则，只能有一个组织单元才能登录
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Org getFirstCurrentOrg(){
		return currentOrges.get(0);
	}
	

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

	public List<Org> getCurrentOrges() {
		return currentOrges;
	}

	public void setCurrentOrges(List<Org> currentOrges) {
		this.currentOrges = currentOrges;
	}

	public Ord getOrd() {
		return ord;
	}

	public void setOrd(Ord ord) {
		this.ord = ord;
	}



}
