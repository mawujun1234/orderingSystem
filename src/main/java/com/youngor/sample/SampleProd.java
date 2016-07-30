package com.youngor.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.mawujun.generator.model.FieldDefine;
import com.youngor.sample.SampleProd.PK;
/**
 * 同个样衣编号 不同套件的货号名称必须一致
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
@Entity(name="ord_sample_prod")
@IdClass(PK.class)
public class SampleProd {
	@Id
	@FieldDefine(title="订货会批号",sort=50,hidden=true)
	@Column(length=30,nullable=false,updatable=false)
	private String ormtno;
	@Id
	@FieldDefine(title="设计样衣编号",hidden=false)
	@Column(length=36)
	private String sampno;
	@Id
	@FieldDefine(title="套件",hidden=false)
	@Column(length=6)
	private String suitno;
	
	
	
	@FieldDefine(title="序号",hidden=false)
	@Column(length=36)
	private Integer prseqm;//可编辑
	@FieldDefine(title="货号代码",hidden=false)
	@Column(length=36)
	private String prodno;
	@FieldDefine(title="货号名称",hidden=false)
	@Column(length=36)
	private String prodnm;
	@FieldDefine(title="货号简称",hidden=false)
	@Column(length=36)
	private String prodds;
	@FieldDefine(title="规格系列",hidden=false)
	@Column(length=36)
	private String sizegp;//注意不是订货系统中自己建的规格范围，而是drp中的规格系列
	@FieldDefine(title="类别",hidden=false)
	@Column(length=36)
	private String prtype;//基础属性，在大类下
	@FieldDefine(title="商品性质",hidden=false)
	@Column(length=36)
	private String prprpt;//基础属性
	@FieldDefine(title="渠道限制",hidden=false)
	@Column(length=36)
	private String prorgd;//基础属性
	@FieldDefine(title="单位",hidden=false)
	@Column(length=36)
	private String prunit;//ord_sample_unms
	@FieldDefine(title="面料",hidden=false)
	@Column(length=36)
	private String mateno;//主面料中的供应商面料货号
	@FieldDefine(title="对照码",hidden=false)
	@Column(length=36)
	private String prdcno;//人工输的
	
	@FieldDefine(title="出厂价",hidden=false)
	private String prftpr;
//	@FieldDefine(title="供应价",hidden=false)
//	private String prsupr;
	@FieldDefine(title="零售价",hidden=false)
	private String prrtpr;
	@FieldDefine(title="成本价",hidden=false)
	private String prctpr;//预计成本价
	@FieldDefine(title="加工费",hidden=false)
	private String prmtam;//自己填
	@FieldDefine(title="面料费",hidden=false)
	private String prmlam;//面聊单价*面料用料
	@FieldDefine(title="合同价",hidden=false)
	private String prorpr;//自己填
	@FieldDefine(title="包装辅料费",hidden=false)
	private String prflam;//包装辅料费+服饰配料
	
	
	//=================================下面这些都暂时先隐藏掉
	@FieldDefine(title="打印系列",hidden=false)
	@Column(length=50)
	private String prprnt;//先不显示
	@FieldDefine(title="其他",hidden=false)
	@Column(length=50)
	private String prqtnm;
	@FieldDefine(title="品名",hidden=false)
	@Column(length=50)
	private String prname;
	@FieldDefine(title="安全标准",hidden=false)
	@Column(length=50)
	private String prsasd;
	@FieldDefine(title="执行标准",hidden=false)
	@Column(length=50)
	private String prdosd;
	@FieldDefine(title="洗涤图标",hidden=false)
	@Column(length=50)
	private String prwapt;
	@FieldDefine(title="洗涤说明",hidden=false)
	@Column(length=100)
	private String prwash;
	//================================
	
	
	@FieldDefine(title="备注",hidden=false)
	@Column(length=50)
	private String prmark;
	
	public PK geetPK(){
		PK pk=new PK();
		pk.setOrmtno(ormtno);
		pk.setSampno(sampno);
		pk.setSuitno(suitno);
		return pk;
	}
	
	public static class PK implements Serializable {
		/**
		 * @author mawujun qq:16064988 mawujun1234@163.com
		 */
		private static final long serialVersionUID = 1L;
		private String ormtno;
		private String sampno;
		private String suitno;
		
		public String getOrmtno() {
			return ormtno;
		}
		public void setOrmtno(String ormtno) {
			this.ormtno = ormtno;
		}
		public String getSampno() {
			return sampno;
		}
		public void setSampno(String sampno) {
			this.sampno = sampno;
		}
		public String getSuitno() {
			return suitno;
		}
		public void setSuitno(String suitno) {
			this.suitno = suitno;
		}
		
	}

	public String getOrmtno() {
		return ormtno;
	}

	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}

	public String getSampno() {
		return sampno;
	}

	public void setSampno(String sampno) {
		this.sampno = sampno;
	}

	public String getSuitno() {
		return suitno;
	}

	public void setSuitno(String suitno) {
		this.suitno = suitno;
	}

	public Integer getPrseqm() {
		return prseqm;
	}

	public void setPrseqm(Integer prseqm) {
		this.prseqm = prseqm;
	}

	public String getProdno() {
		return prodno;
	}

	public void setProdno(String prodno) {
		this.prodno = prodno;
	}

	public String getProdnm() {
		return prodnm;
	}

	public void setProdnm(String prodnm) {
		this.prodnm = prodnm;
	}

	public String getProdds() {
		return prodds;
	}

	public void setProdds(String prodds) {
		this.prodds = prodds;
	}

	public String getSizegp() {
		return sizegp;
	}

	public void setSizegp(String sizegp) {
		this.sizegp = sizegp;
	}

	public String getPrtype() {
		return prtype;
	}

	public void setPrtype(String prtype) {
		this.prtype = prtype;
	}

	public String getPrprpt() {
		return prprpt;
	}

	public void setPrprpt(String prprpt) {
		this.prprpt = prprpt;
	}

	public String getProrgd() {
		return prorgd;
	}

	public void setProrgd(String prorgd) {
		this.prorgd = prorgd;
	}

	public String getPrunit() {
		return prunit;
	}

	public void setPrunit(String prunit) {
		this.prunit = prunit;
	}

	public String getMateno() {
		return mateno;
	}

	public void setMateno(String mateno) {
		this.mateno = mateno;
	}

	public String getPrdcno() {
		return prdcno;
	}

	public void setPrdcno(String prdcno) {
		this.prdcno = prdcno;
	}

	public String getPrftpr() {
		return prftpr;
	}

	public void setPrftpr(String prftpr) {
		this.prftpr = prftpr;
	}


	public String getPrrtpr() {
		return prrtpr;
	}

	public void setPrrtpr(String prrtpr) {
		this.prrtpr = prrtpr;
	}

	public String getPrctpr() {
		return prctpr;
	}

	public void setPrctpr(String prctpr) {
		this.prctpr = prctpr;
	}

	public String getPrmtam() {
		return prmtam;
	}

	public void setPrmtam(String prmtam) {
		this.prmtam = prmtam;
	}

	public String getPrmlam() {
		return prmlam;
	}

	public void setPrmlam(String prmlam) {
		this.prmlam = prmlam;
	}

	public String getProrpr() {
		return prorpr;
	}

	public void setProrpr(String prorpr) {
		this.prorpr = prorpr;
	}

	public String getPrflam() {
		return prflam;
	}

	public void setPrflam(String prflam) {
		this.prflam = prflam;
	}

	public String getPrprnt() {
		return prprnt;
	}

	public void setPrprnt(String prprnt) {
		this.prprnt = prprnt;
	}

	public String getPrqtnm() {
		return prqtnm;
	}

	public void setPrqtnm(String prqtnm) {
		this.prqtnm = prqtnm;
	}

	public String getPrname() {
		return prname;
	}

	public void setPrname(String prname) {
		this.prname = prname;
	}

	public String getPrsasd() {
		return prsasd;
	}

	public void setPrsasd(String prsasd) {
		this.prsasd = prsasd;
	}

	public String getPrdosd() {
		return prdosd;
	}

	public void setPrdosd(String prdosd) {
		this.prdosd = prdosd;
	}

	public String getPrwapt() {
		return prwapt;
	}

	public void setPrwapt(String prwapt) {
		this.prwapt = prwapt;
	}

	public String getPrwash() {
		return prwash;
	}

	public void setPrwash(String prwash) {
		this.prwash = prwash;
	}

	public String getPrmark() {
		return prmark;
	}

	public void setPrmark(String prmark) {
		this.prmark = prmark;
	}

}
