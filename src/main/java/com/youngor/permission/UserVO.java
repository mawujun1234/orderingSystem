package com.youngor.permission;

import java.util.Date;

public class UserVO extends User {
	
	private Date loginDate;

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

}
