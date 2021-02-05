package com.nda.academy.timetable.model;

public class ClassInfo {
	
	private String time;
	private String day;
	private String subject;
	private String className;
	
	public ClassInfo(String time, String day, String subject, String className) {
		super();
		this.className = className;
		this.time = time;
		this.day = day;
		this.subject = subject;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
