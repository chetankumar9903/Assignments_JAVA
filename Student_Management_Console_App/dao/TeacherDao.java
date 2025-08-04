package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
import com.aurionpro.database.DBConnection;
import com.aurionpro.exceptions.SubjectNotFoundException;
import com.aurionpro.exceptions.TeacherNotFoundException;
import com.aurionpro.model.Admin;
import com.aurionpro.model.Teacher;
 
public class TeacherDao {
	private Connection connection;
	private PreparedStatement preparedStatement;
	public TeacherDao() {
		connection = DBConnection.getInstance().getConnection();
	}
	// 1. Add Teacher
 
		public boolean addTeacher(String teacherName, String teacherCity, String teacherMobNo, String teacherQualification,
				int teacherExp) {
 
			String insertSQL = "INSERT INTO teachers (teacher_name) VALUES (?)";
			try {
				// insert into teachers table
				preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, teacherName);
				preparedStatement.executeUpdate();
 
				// insert into teacher_profile table
				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int teacherId = generatedKeys.getInt(1);
					preparedStatement = connection.prepareStatement(
							"INSERT INTO teacher_profile (city, mobile_no, qualification, experience, teacher_id) VALUES (?,?,?,?,?)");
					preparedStatement.setString(1, teacherCity);
					preparedStatement.setString(2, teacherMobNo);
					preparedStatement.setString(3, teacherQualification);
					preparedStatement.setInt(4, teacherExp);
					preparedStatement.setInt(5, teacherId);
					preparedStatement.executeUpdate();
					// creating Teacher obj
					Teacher teacher = new Teacher(teacherId,teacherName,1,teacherCity,teacherMobNo,teacherQualification,teacherExp);
//					Admin.addTeacher(teacher);
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}
 
		// 2. Show All teachers
 
		public ResultSet showAllTeachers() {
			ResultSet resultSet = null;
			try {
				preparedStatement = connection.prepareStatement("Select * from teachers where is_active = 1");
				resultSet = preparedStatement.executeQuery();
				return resultSet;
			} catch (SQLException e) {
 
				e.printStackTrace();
			}
			return resultSet;
		}
 
		// 3. Assign subject to teacher
		public boolean assignSubjectToTeacher(int teacherId, int subjectId) {
 
			try {
				if(!teacherExists(teacherId))
					throw new TeacherNotFoundException(teacherId);
				if(!subjectExists(subjectId))
					throw new SubjectNotFoundException(subjectId);
				preparedStatement = connection.prepareStatement("insert into teacher_subject (teacher_id, subject_id) values (?,?)");
				preparedStatement.setInt(1, teacherId);
				preparedStatement.setInt(2, subjectId);
				preparedStatement.executeUpdate();
				// adding subject to the subjects list if teacher
//				Teacher teacher = Admin.getTeacher(teacherId);
//				teacher.addSubject(subjectId);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (TeacherNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (SubjectNotFoundException e) {
				System.out.println(e.getMessage());
			}
			return false;
		}
 
		// 4. Show subjects of a teacher
 
		public ResultSet showAllSubjectsOfTeacher(int teacherId) {
			ResultSet resultSet = null;
			try {
				if(!teacherExists(teacherId))
					throw new TeacherNotFoundException(teacherId);
				preparedStatement = connection.prepareStatement(
						"select s.subject_name from teacher_subject ts inner join subjects s on ts.subject_id=s.subject_id where ts.teacher_id = ? and ts.is_active = 1");
				preparedStatement.setInt(1, teacherId);
				resultSet = preparedStatement.executeQuery();
				return resultSet;
			} catch (SQLException e) {
 
				e.printStackTrace();
			} catch (TeacherNotFoundException e) {
				System.out.println(e.getMessage());
			}
			return resultSet;
		}
 
		// 5. View Teacher details
		public ResultSet viewTeacherDetails(int teacherId) {
			ResultSet resultSet = null;
			try {
				if(!teacherExists(teacherId))
					throw new TeacherNotFoundException(teacherId);
				preparedStatement = connection.prepareStatement(
						"select t.teacher_id, teacher_name, city, mobile_no, qualification, experience from teachers t inner join teacher_profile tp on t.teacher_id=tp.teacher_id where t.teacher_id=? and t.is_active = 1 and tp.is_active = 1");
				preparedStatement.setInt(1, teacherId);
				resultSet = preparedStatement.executeQuery();
				return resultSet;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (TeacherNotFoundException e) {
				System.out.println(e.getMessage());
			}
			return resultSet;
		}
 
		// 6. delete a teacher
		public int deleteTeacher(int teacherId) {
			try {
				if(!teacherExists(teacherId))
					throw new TeacherNotFoundException(teacherId);
				//deleting from teachers
				preparedStatement = connection.prepareStatement("UPDATE teachers SET is_active = 0 WHERE teacher_id = ?");
				preparedStatement.setInt(1, teacherId);
				int rowsAffected = preparedStatement.executeUpdate();
				//deleting from teacher_profile
				preparedStatement = connection.prepareStatement("UPDATE teacher_profile SET is_active = 0 WHERE teacher_id = ?");
				preparedStatement.setInt(1, teacherId);
				preparedStatement.executeUpdate();
				//deleting from teacher_subject
				preparedStatement = connection.prepareStatement("UPDATE teacher_subject SET is_active = 0 WHERE teacher_id = ?");
				preparedStatement.setInt(1, teacherId);
				preparedStatement.executeUpdate();
				return rowsAffected;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (TeacherNotFoundException e) {
				System.out.println(e.getMessage());
			}
			return -1;
		}
		// 7. Remove a Subject
		public int deleteTeacherSubject(int teacherId, int subjectId) {
			try {
				if(!teacherExists(teacherId))
					throw new TeacherNotFoundException(teacherId);
				if(!subjectExists(subjectId))
					throw new SubjectNotFoundException(subjectId);
				//deleting from teacher_subject
				preparedStatement = connection.prepareStatement("UPDATE teacher_subject SET is_active = 0 WHERE teacher_id = ? and subject_id = ?");
				preparedStatement.setInt(1, teacherId);
				preparedStatement.setInt(2, subjectId);
				int rowsAffected = preparedStatement.executeUpdate();	
				// deleting subject from teacher obj
//				Admin.getTeacher(teacherId).removeSubject(subjectId);
				return rowsAffected;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (TeacherNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (SubjectNotFoundException e) {
				System.out.println(e.getMessage());
			}
			return -1;
		}
 
//		 check for Teacher already exist 
		private boolean teacherExists(int teacherId) {
			String checkQuery = "SELECT * FROM teachers WHERE teacher_id = ? and is_active = 1";
			try {
				preparedStatement = connection.prepareStatement(checkQuery);
				preparedStatement.setInt(1, teacherId);
				try (ResultSet rs = preparedStatement.executeQuery()) {
					return rs.next(); // If any record exists, course already exists
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
 
		// check for already exit subject in particular course
		private boolean subjectExists(int subjectId) {
			String checkQuery = "SELECT * FROM subjects WHERE subject_id = ? and is_active = 1";
			try {
				preparedStatement = connection.prepareStatement(checkQuery);
				preparedStatement.setInt(1, subjectId);
				try (ResultSet rs = preparedStatement.executeQuery()) {
					return rs.next(); // Subject already exists
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
 
 
}
