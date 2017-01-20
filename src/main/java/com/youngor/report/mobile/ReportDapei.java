package com.youngor.report.mobile;

import java.util.ArrayList;
import java.util.List;

public class ReportDapei implements Comparable<ReportDapei>{
	private String clppnm;//搭配名称
	private String imgnm;//搭配图片
	private Integer minnum;//搭配数量
	
	private List<ReportDapeiList> list;
	/**
	 * 计算最少套数
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 */
	public void calMinnnum(){
		if(list==null || list.size()==0){
			this.minnum=0;
		} else {
			this.minnum=10000000;
			for(ReportDapeiList aa:list){
				if(aa.getNum()==0){
					this.minnum=0;
					return;
				} if(this.minnum>aa.getNum()){
					this.minnum=aa.getNum();
				}
			}
		}
		
	}
	@Override
	public int compareTo(ReportDapei o) {
		if(this.getMinnum()>o.getMinnum()){
			return -1;
		} else if(this.getMinnum()<o.getMinnum()){
			return 1;
		}
		return 0;
	}
	

	public String getClppnm() {
		return clppnm;
	}

	public void setClppnm(String clppnm) {
		this.clppnm = clppnm;
	}

	public String getImgnm() {
		return imgnm;
	}

	public void setImgnm(String imgnm) {
		this.imgnm = imgnm;
	}


	public List<ReportDapeiList> getList() {
		return list;
	}

	public void setList(List<ReportDapeiList> list) {
		this.list = list;
	}
	public void addList(ReportDapeiList reportDapeiList) {
		if(this.list==null){
			this.list=new ArrayList<ReportDapeiList>();
		}
		this.list.add(reportDapeiList);
	}

	public Integer getMinnum() {
		return minnum;
	}

	public void setMinnum(Integer minnum) {
		this.minnum = minnum;
	}

	

}
