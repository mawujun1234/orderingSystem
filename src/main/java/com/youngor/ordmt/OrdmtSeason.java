package com.youngor.ordmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.annotation.FieldDefine;
import com.youngor.ordmt.OrdmtSeason.PK;
import com.youngor.utils.BaseObject;

@Entity(name="ord_ordmt_season")
@IdClass(PK.class)
public class OrdmtSeason extends BaseObject{
	@Id
	@FieldDefine(title="订货会批号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String ormtno;
	@Id
	@FieldDefine(title="产品季节代码",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String seasno;
	
	
	
	public static class PK implements Serializable {
		private static final long serialVersionUID = 1L;

		private String ormtno;

        private String seasno;

		public String getOrmtno() {
			return ormtno;
		}

		public void setOrmtno(String ormtno) {
			this.ormtno = ormtno;
		}

		public String getSeasno() {
			return seasno;
		}

		public void setSeasno(String seasno) {
			this.seasno = seasno;
		}

        
    }
	
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getSeasno() {
		return seasno;
	}
	public void setSeasno(String seasno) {
		this.seasno = seasno;
	}

}
