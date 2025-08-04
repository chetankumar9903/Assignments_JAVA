package com.aurionpro.services;

import java.sql.ResultSet;

import com.aurionpro.dao.TeacherDao;

public class TeacherServices {
	private TeacherDao teacherDao;
	 
	public TeacherServices() {
		teacherDao = new TeacherDao();
	}
 
			// 1. Add Teacher
			public boolean addTeacher(String teacherName, String teacherCity, String teacherMobNo, String teacherQualification,
					int teacherExp) {
 
				return teacherDao.addTeacher(teacherName, teacherCity, teacherMobNo, teacherQualification, teacherExp);
			}
 
			// 2. Show All teachers
			public ResultSet showAllTeachers() {
				return teacherDao.showAllTeachers();
			}
 
			// 3. Assign subject to teacher
			public boolean assignSubjectToTeacher(int teacherId, int subjectId) {
 
				return teacherDao.assignSubjectToTeacher(teacherId, subjectId);
			}
 
			// 4. Show subjects of a teacher
			public ResultSet showAllSubjectsOfTeacher(int teacherId) {
				return teacherDao.showAllSubjectsOfTeacher(teacherId);
			}
 
			// 5. View Teacher details
			public ResultSet viewTeacherDetails(int teacherId) {
				return teacherDao.viewTeacherDetails(teacherId);
			}
 
			// 6. delete a teacher
			public int deleteTeacher(int teacherId) {
				return teacherDao.deleteTeacher(teacherId);
			}
 
			// 7. Remove a subject
			public int deleteTeacherSubject(int teacherId, int subjectId) {
				return teacherDao.deleteTeacherSubject(teacherId,subjectId);
			}
 
}


