package com.youngor.order;

import java.util.List;

import com.youngor.pubcode.PubCodeCache;

public class SuitVO {
	private String suitno;//套件
	//private String suitnm;//套件名称,上衣，裤子等
	
	private Integer ormtqs;//原始数量
	
	private Double spftpr;//出厂价
	private Double sprtpr;//零售价
	

	
	private List<SizeVO> sizeVOs;
	/**
	 * 套件名称,上衣，裤子等
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public String getSuitno_name() {
		return PubCodeCache.getSuitno_name(this.getSuitno());
	}
	/**
	 * 获取规格比例的总数
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Double getSzrate_sum(){
		if(sizeVOs!=null){
			Double sum=0d;
			for(SizeVO sizeVO:sizeVOs){
				if(sizeVO.getSzrate()!=null){
					sum+=sizeVO.getSzrate();
				}
			}
			return sum;
		} else {
			return 0d;
		}
	}

	
	public String getSuitno() {
		return suitno;
	}
	public void setSuitno(String suitno) {
		this.suitno = suitno;
	}
	
	public Integer getOrmtqs() {
		return ormtqs;
	}
	public void setOrmtqs(Integer ormtqs) {
		this.ormtqs = ormtqs;
	}
	public Double getSpftpr() {
		return spftpr;
	}
	public void setSpftpr(Double spftpr) {
		this.spftpr = spftpr;
	}
	public Double getSprtpr() {
		return sprtpr;
	}
	public void setSprtpr(Double sprtpr) {
		this.sprtpr = sprtpr;
	}


	public List<SizeVO> getSizeVOs() {
		return sizeVOs;
	}
	public void setSizeVOs(List<SizeVO> sizeVOs) {
		this.sizeVOs = sizeVOs;
	}

	
	

}
