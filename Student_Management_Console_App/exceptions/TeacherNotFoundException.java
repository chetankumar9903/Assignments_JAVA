package com.aurionpro.exceptions;

public class TeacherNotFoundException extends Exception{
	 
	private int teacherId;
 
	public TeacherNotFoundException(int teacherId) {
		super();
		this.teacherId = teacherId;
	}
	
	@Override
	public String getMessage() {
		return "Teacher Id : "+teacherId+" does not exists";
	}
}
