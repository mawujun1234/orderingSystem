package com.youngor.permission;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mawujun.utils.Assert;
import com.youngor.pubcode.PubCode;


@Entity(name = "t_role_brand")
public class RoleBrand implements Serializable {
	/**
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="itno")
	private PubCode pubCode;

	@Id
	@ManyToOne
	private Role role;

	public RoleBrand() {

	}

	public RoleBrand(PubCode pubCode, Role role) {
		Assert.notNull(pubCode);
		Assert.notNull(role);
		this.pubCode = pubCode;
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public PubCode getPubCode() {
		return pubCode;
	}

	public void setPubCode(PubCode pubCode) {
		this.pubCode = pubCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pubCode == null) ? 0 : pubCode.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleBrand other = (RoleBrand) obj;
		if (pubCode == null) {
			if (other.pubCode != null)
				return false;
		} else if (!pubCode.equals(other.pubCode))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	
}
