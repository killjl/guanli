package com.killjl.guanli.model;

public class Class {
	private String classname;
	private String department;
	private int prefercount;
	private int point;
	private String tcrname;
	private String time;
	private String place;
	private String year;
	private String major;
	private String testtime;
	private String classid;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getClassname() {
		return classname;
	}
	public Class setClassname(String classname) {
		this.classname = classname;
		return this;
	}
	public String getDepartment() {
		return department;
	}
	public Class setDepartment(String department) {
		this.department = department;
		return this;
	}
	public int getPrefercount() {
		return prefercount;
	}
	public Class setPrefercount(int prefercount) {
		this.prefercount = prefercount;
		return this;
	}
	public int getPoint() {
		return point;
	}
	public Class setPoint(int point) {
		this.point = point;
		return this;
	}
	public String getTcrname() {
		return tcrname;
	}
	public Class setTcrname(String tcrname) {
		this.tcrname = tcrname;
		return this;
	}
	public String getTime() {
		return time;
	}
	public Class setTime(String time) {
		this.time = time;
		return this;
	}
	public String getPlace() {
		return place;
	}
	public Class setPlace(String place) {
		this.place = place;
		return this;
	}
	public String getYear() {
		return year;
	}
	public Class setYear(String year) {
		this.year = year;
		return this;
	}
	public String getMajor() {
		return major;
	}
	public Class setMajor(String major) {
		this.major = major;
		return this;
	}
	public String getTesttime() {
		return testtime;
	}
	public Class setTesttime(String testtime) {
		this.testtime = testtime;
		return this;
	}


}
