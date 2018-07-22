package com.killjl.guanli.model;

import java.util.Date;

public class Token {
	private int userid;
	private int status;
	private Date delay;
	private String token;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getDelay() {
		return delay;
	}
	public void setDelay(Date delay) {
		this.delay = delay;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
