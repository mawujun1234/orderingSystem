package com.youngor.utils;

import java.util.Date;

public class AccessToken {
	private String access_token;
	private int expires_in;
	//获取这个accestoken的时间
	private Date createDate;
//	//自己定义的定期获取accesstoken的时间间隔，要比7200小
//	private int expires_get;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
		
	}
	/**
	 * 判断是否已经过期,提前2分钟过期
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public boolean isExpires(){
		if((System.currentTimeMillis()/1000-createDate.getTime()/1000)>=(expires_in-120)){
			return true;
		}
		return false;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
