package com.nda.academy.timetable.model;

public class TeacherTimeTable {

	private String className;
	private String day;
	private String time;
	private String subject;

	public TeacherTimeTable(String subject, String className, String day, String time) {
		super();
		this.subject = subject;
		this.className = className;
		this.day = day;
		this.time = time;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
