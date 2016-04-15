package com.youngor.sample;

/**
 * 用于在设计样衣资料的时候显示用的
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public class SamplePlanDesignVO extends SamplePlanVO{

	private String sampno;//设计样衣id
	private String sampnm;//设计样衣编码
	
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}
	public String getSampnm() {
		return sampnm;
	}
	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}
}
