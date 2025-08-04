package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.Course;

public class CourseDao {

	private Connection conn;

	public CourseDao() {
		conn = DBConnection.getInstance().getConnection();
	}

	// 1. Add course

//    public void addCourse(String courseName) {
//    	 if (courseExists(courseName)) {
//             System.out.println("Course already exists with name: " + courseName);
//             return;
//         }
//    	
//        try {
//            PreparedStatement stmt = conn.prepareStatement("INSERT INTO courses (course_name) VALUES (?)");
//            stmt.setString(1, courseName);
//            stmt.executeUpdate();
//            System.out.println("Course added successfully.");
//        } catch (Exception e) {
//            System.out.println("Failed to add course: " + e.getMessage());
//        }
//    }   reactivateCourse(course.getCourseName())

	public void addCourse(Course course) {
	    String checkQuery = "SELECT course_id, is_active FROM courses WHERE course_name = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
	        stmt.setString(1, course.getCourseName());
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            int courseId = rs.getInt("course_id");
	            boolean isActive = rs.getBoolean("is_active");

	            if (isActive) {
	                System.out.println("Course already exists and is active.");
	            } else {
	            	reactivateCourse(course.getCourseName());
	                // Reactivate and update total_fee
	                	            }
	        } else {
	            // Course doesn't exist, insert new one
	            String insertQuery = "INSERT INTO courses (course_name, total_fee, is_active) VALUES (?, ?, true)";
	            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
	                insertStmt.setString(1, course.getCourseName());
	                insertStmt.setDouble(2, course.getTotalFee());

	                int rowsInserted = insertStmt.executeUpdate();
	                if (rowsInserted > 0) {
	                    System.out.println("Course added successfully.");
	                } else {
	                    System.out.println("Failed to add course.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error adding course: " + e.getMessage());
	    }
	}


	// 2. View all courses

//    public void showAllCourses() {
//        try {
//            Statement stmt = conn.createStatement();
	////            ResultSet rs = stmt.executeQuery("SELECT * FROM courses ORDER BY course_id");

//            ResultSet rs = stmt.executeQuery("SELECT * FROM courses WHERE is_active = true ORDER BY course_id");
//            System.out.println("\n======== All Courses: ========");
	////            while (rs.next()) {
////                System.out.println("ID: " + rs.getInt("course_id") + " | Name: " + rs.getString("course_name"));
////            }
//            
//            System.out.printf("%-10s %-30s%n", "Course ID", "Course Name");
//            System.out.println("--------------------------------");
//
//            while (rs.next()) {
//                int id = rs.getInt("course_id");
//                String name = rs.getString("course_name");
//                System.out.printf("%-10d %-30s%n", id, name);
//            }
//        } catch (Exception e) {
//            System.out.println("Failed to view courses: " + e.getMessage());
//        }
//    }

	public List<Course> showAllCourses() {
	    List<Course> courses = new ArrayList<>();
	    String sql = "SELECT * FROM courses WHERE is_active = 1 ORDER BY course_id";

	    try (PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            int id = rs.getInt("course_id");
	            String name = rs.getString("course_name");
	            double totalFee = rs.getDouble("total_fee");
	            boolean isActive = rs.getBoolean("is_active");

	            courses.add(new Course(id, name, isActive,totalFee));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return courses;
	}

	// 3. Delete a course
//    public void deleteCourse(int courseId) {
//        try {
//            PreparedStatement stmt = conn.prepareStatement("DELETE FROM courses WHERE course_id = ?");
//            stmt.setInt(1, courseId);
//            int rowsAffected = stmt.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("Course with ID: "+courseId+" deleted successfully.");
//            } else {
//                System.out.println("No such course found.");
//            }
//        } catch (Exception e) {
//            System.out.println("Failed to delete course: " + e.getMessage());
//        }
//    }

	public void deleteCourse(int courseId) {
		String query = "UPDATE courses SET is_active = false WHERE course_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, courseId);
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Course with ID: " + courseId + " has been deleted.");
			} else {
				System.out.println("No such course found or already inactive.");
			}
		} catch (Exception e) {
			System.out.println("Failed to delete course: " + e.getMessage());
		}
	}

	// 4 (a). view subjects in a course

	public void viewSubjectsInCourse(int courseId) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM subjects WHERE course_id = ?");
			stmt.setInt(1, courseId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (!rs.isBeforeFirst()) { // Check if ResultSet is empty
					System.out.println("\nNo subjects found for Course ID " + courseId + ".");
					return;
				}
//            ResultSet rs = stmt.executeQuery();

				System.out.println("\n===== Subjects in Course " + courseId + ": =====");
				System.out.println("--------------------------------------------------");
				System.out.printf("| %-12s | %-30s |\n", "Subject ID", "Subject Name");
				System.out.println("--------------------------------------------------");

				while (rs.next()) {
					int id = rs.getInt("subject_id");
					String name = rs.getString("subject_name");
					System.out.printf("| %-12d | %-30s |\n", id, name);
				}
				System.out.println("--------------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println("Failed to view subjects: " + e.getMessage());
		}
	}

	// 4 (b). view students in a course

	public void viewStudentsInCourse(int courseId) {
		String query = "SELECT DISTINCT s.student_id, s.student_name FROM students s "
				+ "JOIN student_all_marks m ON s.student_id = m.student_id "
				+ "JOIN subjects sub ON m.subject_id = sub.subject_id " + "WHERE sub.course_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, courseId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.isBeforeFirst()) { // Check if ResultSet is empty
					System.out.println("\nNo students found for Course ID " + courseId + ".");
					return;
				}
				System.out.println("\n===== Students in Course ID " + courseId + ": =====");
				System.out.println("--------------------------------------------------");
				System.out.printf("| %-12s | %-30s |\n", "Student ID", "Student Name");
				System.out.println("--------------------------------------------------");

				while (rs.next()) {
					int id = rs.getInt("student_id");
					String name = rs.getString("student_name");
					System.out.printf("| %-12d | %-30s |\n", id, name);
				}
				System.out.println("--------------------------------------------------");
			}
		} catch (SQLException e) {
			System.out.println("Error viewing students: " + e.getMessage());
		}
	}

	// 5. add subject in a course

	public void addSubjectToCourse(int courseId, String subjectName) {
		if (subjectExistsInCourse(subjectName, courseId)) {
			System.out.println(subjectName + " Subject already exists in this course.");
			return;
		}
		String query = "INSERT INTO subjects (subject_name, course_id) VALUES (?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, subjectName);
			pstmt.setInt(2, courseId);
			pstmt.executeUpdate();
			System.out.println("Subject added successfully.");
		} catch (SQLException e) {
			System.out.println("Error adding subject: " + e.getMessage());
		}
	}

	// check for course already exist during addition of course
	private boolean courseExists(String courseName) {
		String checkQuery = "SELECT * FROM courses WHERE course_name = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(checkQuery);
			stmt.setString(1, courseName);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next(); // If any record exists, course already exists
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// check for already exit subject in particular course
	private boolean subjectExistsInCourse(String subjectName, int courseId) {
		String checkQuery = "SELECT * FROM subjects WHERE subject_name = ? AND course_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(checkQuery);
			stmt.setString(1, subjectName);
			stmt.setInt(2, courseId);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next(); // Subject already exists for the given course
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void reactivateCourse(String courseName) {
		String checkQuery = "SELECT course_id, is_active FROM courses WHERE course_name = ?";

		try {
			PreparedStatement stmt = conn.prepareStatement(checkQuery);
			stmt.setString(1, courseName);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int courseId = rs.getInt("course_id");
				boolean isActive = rs.getBoolean("is_active");

				if (isActive) {
					System.out.println("Course '" + courseName + "' is already active.");
				} else {
					String updateQuery = "UPDATE courses SET is_active = true WHERE course_id = ?";
					try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
						updateStmt.setInt(1, courseId);
						int rowsUpdated = updateStmt.executeUpdate();

						if (rowsUpdated > 0) {
							System.out.println("Course '" + courseName + "' reactivated successfully.");
						} else {
							System.out.println("Reactivation failed.");
						}
					}
				}
			} else {
				System.out.println("Course '" + courseName + "' does not exist.");
			}

		} catch (SQLException e) {
			System.out.println("Error during reactivation: " + e.getMessage());
		}
	}

	public List<Course> getAllInactiveCourses() {
		List<Course> inactiveCourses = new ArrayList<>();
		String query = "SELECT * FROM courses WHERE is_active = false";

		try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Course course = new Course();
				course.setCourseId(rs.getInt("course_id"));
				course.setCourseName(rs.getString("course_name"));
				course.setTotalFee(rs.getDouble("total_fee"));
				course.setIsActive(rs.getBoolean("is_active"));

				inactiveCourses.add(course);
			}

		} catch (SQLException e) {
			System.out.println("Error fetching inactive courses: " + e.getMessage());
		}

		return inactiveCourses;
	}

	
	// Search by Course ID
	public Course getCourseById(int courseId) {
	    String query = "SELECT * FROM courses WHERE course_id = ?";
	    Course course = null;
	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, courseId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                course = new Course(
	                    rs.getInt("course_id"),
	                    rs.getString("course_name"),
	                    rs.getBoolean("is_active"),
	                    rs.getDouble("total_fee")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching course by ID: " + e.getMessage());
	    }
	    return course;
	}

	// Search by Course Name
	public Course getCourseByName(String name) {
	    String query = "SELECT * FROM courses WHERE course_name = ?";
	    Course course = null;
	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, name);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                course = new Course(
	                    rs.getInt("course_id"),
	                    rs.getString("course_name"),
	                    rs.getBoolean("is_active"),
	                    rs.getDouble("total_fee")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching course by name: " + e.getMessage());
	    }
	    return course;
	}

}
