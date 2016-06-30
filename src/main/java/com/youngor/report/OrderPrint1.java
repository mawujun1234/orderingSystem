package com.youngor.report;

import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.ContextUtils;

/**
 * 二次订货前打吊牌用的
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public class OrderPrint1 {
	private String daxlmc = "";
	private String xilmc = "";
	private String dalmc = "";
	private String xiaolmc = "";
	private String yangybh = "";
	private String picmc="";//批次名称
	private String yxgsmc = "";
	private int shul = 0;
	private int lingsj =0;//零售价
	private String gongys="";
	
	private String spclno;
	private String sptyno;
	private String spseno;
	private String spbano;//批次
	private String spbseno;
	private String spsuno;//供应商

	
	private int yujsj = 0;//没用
	private int tongpsl = 0;//没用
	private String beiz;
	
	
	public void setYxgsmc(String yxgsmc) {
		if(yxgsmc!=null){
			this.yxgsmc =yxgsmc.replaceAll("公司", "");
		} else {
			this.yxgsmc =yxgsmc;
		}
		 
	}
	public void setSpclno(String spclno) {
		this.spclno = spclno;
		this.dalmc=PubCodeCache.getSpclno_name(this.spclno);
	}
	public void setSptyno(String sptyno) {
		this.sptyno = sptyno;
		this.xiaolmc=PubCodeCache.getSptyno_name(this.sptyno);
	}
	public void setSpseno(String spseno) {
		this.spseno = spseno;
		this.xilmc=PubCodeCache.getSpseno_name(this.spseno);
	}
	public void setSpbano(String spbano) {
		this.spbano = spbano;
		this.picmc=PubCodeCache.getSpbano_name(this.spbano);
	}
	public void setSpbseno(String spbseno) {
		this.spbseno = spbseno;
		this.daxlmc=PubCodeCache.getSpbseno_name(this.spbseno);
	}
	public void setSpsuno(String spsuno) {
		this.spsuno = spsuno;
		if(spsuno!=null && ContextUtils.getPubSuno(this.spsuno)!=null){
			this.gongys=ContextUtils.getPubSuno(this.spsuno).getIdsunm();
		}
		
	}
	
	public String getDaxlmc() {
		return daxlmc;
	}
	public void setDaxlmc(String daxlmc) {
		this.daxlmc = daxlmc;
	}
	public String getXilmc() {
		return xilmc;
	}
	public void setXilmc(String xilmc) {
		this.xilmc = xilmc;
	}
	public String getDalmc() {
		return dalmc;
	}
	public void setDalmc(String dalmc) {
		this.dalmc = dalmc;
	}
	public String getXiaolmc() {
		return xiaolmc;
	}
	public void setXiaolmc(String xiaolmc) {
		this.xiaolmc = xiaolmc;
	}
	public String getYangybh() {
		return yangybh;
	}
	public void setYangybh(String yangybh) {
		this.yangybh = yangybh;
	}
	public String getYxgsmc() {
		return yxgsmc;
	}
	
	public int getShul() {
		return shul;
	}
	public void setShul(int shul) {
		this.shul = shul;
	}
	public int getLingsj() {
		return lingsj;
	}
	public void setLingsj(int lingsj) {
		this.lingsj = lingsj;
	}
	public int getYujsj() {
		return yujsj;
	}
	public void setYujsj(int yujsj) {
		this.yujsj = yujsj;
	}
	public int getTongpsl() {
		return tongpsl;
	}
	public void setTongpsl(int tongpsl) {
		this.tongpsl = tongpsl;
	}
	public String getBeiz() {
		return beiz;
	}
	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}
	public String getPicmc() {
		return picmc;
	}
	public void setPicmc(String picmc) {
		this.picmc = picmc;
	}
	public String getGongys() {
		return gongys;
	}
	public void setGongys(String gongys) {
		this.gongys = gongys;
	}
	
}
