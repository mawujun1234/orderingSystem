package com.youngor.org;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mawujun.utils.Assert;

/**
 * 这个职位可访问的组织单元
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="t_position_org_access")
public class PositionOrgAccess implements Serializable{
	/**
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@ManyToOne
	Position position;
	@Id
	@ManyToOne
	@JoinColumn(name="orgno")
	Org org;

	
	public PositionOrgAccess(){
		super();
	}
	
	public PositionOrgAccess(Position position, Org org) {
		super();
		Assert.notNull(position);
		Assert.notNull(org);
		this.position = position;
		this.org = org;

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


}
