package com.aurionpro.model;

import java.util.List;

public class Course {
	private int courseId;
	private String courseName;
	private boolean isActive;
	private double totalFee;
	private List<Integer> subjects;

	
	
	public Course() {
		super();
	}

	public Course(String courseName, double totalFee) {
	    this.courseName = courseName;
	    this.totalFee = totalFee;
	}


	public Course(int courseId, String courseName,boolean isActive, double totalFee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.isActive = isActive;
        this.totalFee = totalFee;
    }


	public int getCourseId() {
        return courseId;
    }
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}



	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	 public boolean getIsActive() {
	        return isActive;
	    }
	 
	 public void setIsActive(boolean active) {
	        isActive = active;
	    }

	 public double getTotalFee() {
		    return totalFee;
		}

		public void setTotalFee(double totalFee) {
		    this.totalFee = totalFee;
		}


		 public void addSubject(int subjectId) {
		        subjects.add(subjectId);
		    }

		@Override
		public String toString() {
			return "Course [courseId=" + courseId + ", courseName=" + courseName + ", isActive=" + isActive
					+ ", totalFee=" + totalFee + "]";
		}

}
