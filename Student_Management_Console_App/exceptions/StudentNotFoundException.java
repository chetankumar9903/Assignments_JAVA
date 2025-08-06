package com.aurionpro.exceptions;
 
public class StudentNotFoundException extends Exception{
 
	private int studentId;
 
	public StudentNotFoundException(int studentId) {
		super();
		this.studentId = studentId;
	}
	
	public String getMessage() {
 
		return "Student Id : "+studentId+" does not exists";
	}
}

