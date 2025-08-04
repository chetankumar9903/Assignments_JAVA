package com.aurionpro.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
	private int teacherId;
	private String TeacherName;
	private int isActive;
	private String city;
	private String mobileNumber;
	private String qualification;
	private int experience;
	private List<Integer> subjects;
	public Teacher() {
 
	}
	public Teacher(int teacherId, String teacherName, int isActive, String city, String mobileNumber,
			String qualification, int experience) {
		super();
		this.teacherId = teacherId;
		TeacherName = teacherName;
		this.isActive = isActive;
		this.city = city;
		this.mobileNumber = mobileNumber;
		this.qualification = qualification;
		this.experience = experience;
		this.subjects = new ArrayList<Integer>();
	}
 
	public int getTeacherId() {
		return teacherId;
	}
 
	public String getTeacherName() {
		return TeacherName;
	}
 
	public int getIsActive() {
		return isActive;
	}
 
	public String getCity() {
		return city;
	}
 
	public String getMobileNumber() {
		return mobileNumber;
	}
 
	public String getQualification() {
		return qualification;
	}
 
	public int getExperience() {
		return experience;
	}
 
	public List<Integer> getSubjects() {
		return subjects;
	}
 
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
 
	public void setTeacherName(String teacherName) {
		TeacherName = teacherName;
	}
 
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
 
	public void setCity(String city) {
		this.city = city;
	}
 
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
 
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
 
	public void setExperience(int experience) {
		this.experience = experience;
	}
 
	public void setSubjects(List<Integer> subjects) {
		this.subjects = subjects;
	}
	public void addSubject(int subjectId)
	{
		subjects.add(subjectId);
	}
	public void removeSubject(int subjectId)
	{
		subjects.removeIf(id -> id == subjectId);
	}

}
