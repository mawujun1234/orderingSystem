package com.youngor.permission;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordToken1 extends UsernamePasswordToken {
	private Boolean isscan;
	
	

	public UsernamePasswordToken1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordToken1(String username, char[] password, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordToken1(String username, char[] password, boolean rememberMe) {
		super(username, password, rememberMe);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordToken1(String username, char[] password, String host) {
		super(username, password, host);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordToken1(String username, char[] password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordToken1(String username, String password, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordToken1(String username, String password, boolean rememberMe) {
		super(username, password, rememberMe);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordToken1(String username, String password, String host) {
		super(username, password, host);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordToken1(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	public Boolean getIsscan() {
		return isscan;
	}

	public void setIsscan(Boolean isscan) {
		this.isscan = isscan;
	}

}
