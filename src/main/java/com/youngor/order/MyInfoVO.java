package com.youngor.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyInfoVO {
	private String orgnm;
//	private Double plmtam;//指标金额
//	private Integer plmtqt;//指标数量
	
	//private Double spftpr;//出厂价
	//private Double sprtpr;//零售价
	private BigDecimal ormtam;//实订金额
	private Integer ormtqt;//实订数量
	
	private Integer qymtqt;//区域指标数量
	private BigDecimal qymtam;//区域指标金额
	
	
	public String getOrgnm() {
		return orgnm;
	}
	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}

	public Integer getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(Integer ormtqt) {
		this.ormtqt = ormtqt;
	}
	public BigDecimal getOrmtam() {
		if(ormtam==null){
			return new BigDecimal(0);
		}
		return ormtam.setScale(2, RoundingMode.HALF_UP);
	}
	public void setOrmtam(BigDecimal ormtam) {
		this.ormtam = ormtam;
	}
	public Integer getQymtqt() {
		return qymtqt;
	}
	public void setQymtqt(Integer qymtqt) {
		this.qymtqt = qymtqt;
	}
	public BigDecimal getQymtam() {
		if(ormtam==null){
			return new BigDecimal(0);
		}
		return qymtam.setScale(2, RoundingMode.HALF_UP);
	}
	public void setQymtam(BigDecimal qymtam) {
		this.qymtam = qymtam;
	}


	
//	private Integer ormtqt_count;//已定数量
//	//private Integer ormtqs_count=0;//原始数量，如果原始数量不为0，表示已经确定过了
//	private Integer spseno_count;//系类个数
//	private Integer spclno_count;//大类数量
//	private Integer sampno_count;
//	
	//private Boolean canConfirm;//能不能按确认按钮，如果存在了审批单号，就说名已经确认过了
	
//	/**
//	 * 能不能按确认按钮
//	 * @author mawujun qq:16064988 mawujun1234@163.com
//	 * @return
//	 */
//	public Boolean getCanConfirm(){
//		if(ormtqs_count==null || ormtqs_count==0){
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
	

}
