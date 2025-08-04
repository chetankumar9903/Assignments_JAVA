package com.aurionpro.exceptions;

public class SubjectNotFoundException extends Exception{
	 
	private int subjectId;
 
	public SubjectNotFoundException(int subjectId) {
		super();
		this.subjectId = subjectId;
	}
	
	public String getMessage() {
 
		return "Subject Id : "+subjectId+" does not exists";
	}
}
