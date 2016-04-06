package com.youngor.org;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mawujun.utils.Assert;

@Entity(name = "t_org_org")
public class OrgOrg  implements Serializable{
	/**
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="parent_no",nullable=false)
	private Org parent;

	@Id
	@ManyToOne
	@JoinColumn(name="child_no")
	private Org child;
	
	@Id
	@Column(length=15,nullable=false)
	@Enumerated(EnumType.STRING)
	private Dim dim;

	public OrgOrg() {

	}

	public OrgOrg(Org parent, Org child) {
		Assert.notNull(parent);
		Assert.notNull(child);
		this.parent = parent;
		this.child = child;
	}

	public Org getParent() {
		return parent;
	}

	public void setParent(Org parent) {
		this.parent = parent;
	}

	public Org getChild() {
		return child;
	}

	public void setChild(Org child) {
		this.child = child;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		OrgOrg other = (OrgOrg) obj;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

	public Dim getDim() {
		return dim;
	}

	public void setDim(Dim dim) {
		this.dim = dim;
	}


}
