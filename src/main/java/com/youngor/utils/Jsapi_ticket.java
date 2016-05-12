package com.youngor.utils;

import java.util.Date;

public class Jsapi_ticket {
	private String ticket;
	private int expires_in;
	private Date createDate;
	
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
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
