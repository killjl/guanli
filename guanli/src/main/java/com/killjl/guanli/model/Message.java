package com.killjl.guanli.model;

import java.util.Date;

public class Message {
	private int id;
	private int fromid;
	private int toid;
	private String content;
	private Date createdate;
	private int hasread;
	private String conversationid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFromid() {
		return fromid;
	}
	public void setFromid(int fromid) {
		this.fromid = fromid;
	}
	public int getToid() {
		return toid;
	}
	public void setToid(int toid) {
		this.toid = toid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public int getHasread() {
		return hasread;
	}
	public void setHasread(int hasread) {
		this.hasread = hasread;
	}
	public String getConversationid() {
		return conversationid;
	}
	public void setConversationid(String conversationid) {
		this.conversationid = conversationid;
	}

}
