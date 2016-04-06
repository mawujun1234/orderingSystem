package com.youngor.org;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mawujun.utils.Assert;
import com.youngor.permission.User;

@Entity(name="t_position_org_user")
public class PositionOrgUser implements Serializable{
	/**
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@ManyToOne
	Position position;
	@Id
	@ManyToOne
	Org org;
	@Id
	@ManyToOne
	User user;
	
	public PositionOrgUser(){
		super();
	}
	
	public PositionOrgUser(Position position, Org org, User user) {
		super();
		Assert.notNull(position);
		Assert.notNull(org);
		Assert.notNull(user);
		this.position = position;
		this.org = org;
		this.user = user;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
