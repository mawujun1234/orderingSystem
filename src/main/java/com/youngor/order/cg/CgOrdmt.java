package com.youngor.order.cg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.utils.BaseObject;

@Entity(name="ORD_CG_ORDMT")
@IdClass(CgOrdmt.PK.class)
public class CgOrdmt extends BaseObject{
	@Id
	@FieldDefine(title="订货会批号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String ormtno;
	@Id
	@FieldDefine(title="订货会采购子批次",sort=50,hidden=false)
	@Column(length=30,nullable=false,updatable=true)
	private String orcgno;
	@FieldDefine(title="订货会采购子批次名称",sort=50,hidden=false)
	@Column(length=30,nullable=true,updatable=true)
	private String orcgnm;
	
	
	public static class PK implements Serializable{
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String ormtno;
		private String orcgno;
		
		
		public PK() {
			super();
			// TODO Auto-generated constructor stub
		}


		public String getOrmtno() {
			return ormtno;
		}


		public void setOrmtno(String ormtno) {
			this.ormtno = ormtno;
		}


		public String getOrcgno() {
			return orcgno;
		}


		public void setOrcgno(String orcgno) {
			this.orcgno = orcgno;
		}
		
	}


	public String getOrmtno() {
		return ormtno;
	}


	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}


	public String getOrcgno() {
		return orcgno;
	}


	public void setOrcgno(String orcgno) {
		this.orcgno = orcgno;
	}


	public String getOrcgnm() {
		return orcgnm;
	}


	public void setOrcgnm(String orcgnm) {
		this.orcgnm = orcgnm;
	}

}
