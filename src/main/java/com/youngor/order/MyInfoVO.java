package com.youngor.order;

public class MyInfoVO {
	private String orgnm;
	private Integer ormtqt_count;//已定数量
	private Integer ormtqs_count=0;//原始数量，如果原始数量不为0，表示已经确定过了
	private Integer spseno_count;//系类个数
	private Integer spclno_count;//大类数量
	private Integer sampno_count;
	
	/**
	 * 能不能按确认按钮
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Boolean getCanConfirm(){
		if(ormtqs_count==null || ormtqs_count==0){
			return true;
		} else {
			return false;
		}
	}
	
	public Integer getOrmtqt_count() {
		return ormtqt_count;
	}
	public void setOrmtqt_count(Integer ormtqt_count) {
		this.ormtqt_count = ormtqt_count;
	}
	public Integer getSpseno_count() {
		return spseno_count;
	}
	public void setSpseno_count(Integer spseno_count) {
		this.spseno_count = spseno_count;
	}
	public Integer getSpclno_count() {
		return spclno_count;
	}
	public void setSpclno_count(Integer spclno_count) {
		this.spclno_count = spclno_count;
	}

	public String getOrgnm() {
		return orgnm;
	}
	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}
	public Integer getSampno_count() {
		return sampno_count;
	}
	public void setSampno_count(Integer sampno_count) {
		this.sampno_count = sampno_count;
	}
	public Integer getOrmtqs_count() {
		return ormtqs_count;
	}
	public void setOrmtqs_count(Integer ormtqs_count) {
		this.ormtqs_count = ormtqs_count;
	}

}
