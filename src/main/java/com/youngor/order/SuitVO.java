package com.youngor.order;

import java.util.List;

import com.youngor.pubcode.PubCodeCache;

public class SuitVO {
	private String sampno;
	private String sampnm;
	private String suitno;//套件
	//private String suitno_suffix;//套件的后缀，表示是标准箱还是单规
	//private String suitnm;//套件名称,上衣，裤子等
	//private String mtorno;
	
	private Integer ormtqt;//确认数量
	
	
	private Double spftpr;//出厂价
	private Double sprtpr;//零售价
	

	//private String suitno_name;
	private List<SizeVO> sizeVOs;//=new ArrayList<SizeVO>();
	/**
	 * 检查这个套件下面的所有规格的比例是否设置过，如果这个比例都没有设置过，那就默认比例为1
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	public void checkSzrateZero(){
		if(sizeVOs==null){
			return;
		}
		Double temp=0d;
		for(SizeVO sizevo:sizeVOs){
			if(sizevo.getSzrate()!=null){
				temp+=sizevo.getSzrate();
			}
		}
		if(temp>0d){
			
		} else {
			for(SizeVO sizevo:sizeVOs){
				sizevo.setSzrate(1d);
			}
		}
	}
	
	public SuitVO(){
		super();
	}
	/**
	 * 套件名称,上衣，裤子等
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public String getSuitno_name() {
		String suitno=this.getSuitno();
//		if(suitno.indexOf("_PRDPK")!=-1){
//			suitno=suitno.split("_")[0];
//		} 
		return PubCodeCache.getSuitno_name(suitno);
	}
//	public String getSuitno_suffix() {
//		if(suitno.indexOf("_PRDPK")!=-1){
//			return "箱";
//		} else {
//			return "数量";
//		}
//	}
	public Integer getOrmtqt() {
		if(sizeVOs!=null && (ormtqt==null || ormtqt==0)){
			ormtqt= geetOrmtqt_sum();
			return ormtqt;
		} else {
			//ormtqt=0;
			return ormtqt;
		}
	}
	public int geetOrmtqt_sum() {
		Integer sum=0;
		for(SizeVO sizeVO:sizeVOs){
			if(sizeVO.getOrszqt()!=null){
				sum+=sizeVO.getOrszqt()*sizeVO.getSizeqt();
			}
		}
		return sum;
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
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}

	public void setOrmtqt(Integer ormtqt) {
		this.ormtqt = ormtqt;
	}
	public String getSampnm() {
		return sampnm;
	}
	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}

}
