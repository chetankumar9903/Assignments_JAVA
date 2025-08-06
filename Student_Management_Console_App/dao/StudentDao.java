package com.aurionpro.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.Student;

public class StudentDao {
    private final Connection connection;

    public StudentDao() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public void addStudent(Student student) {
        String studentQuery = "INSERT INTO students (roll_no, student_name, age) VALUES (?, ?, ?)";
        String profileQuery = "INSERT INTO student_profile (student_id, city, mobile_no) VALUES (?, ?, ?)";

        try (
            PreparedStatement stmtStudent = connection.prepareStatement(studentQuery, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmtProfile = connection.prepareStatement(profileQuery)
        ) {


            stmtStudent.setInt(1, student.getRollNo());
            stmtStudent.setString(2, student.getStudentName());
            stmtStudent.setInt(3, student.getAge());
           // stmtStudent.setDouble(4, student.getPercentage());
            

            int rows = stmtStudent.executeUpdate();

            if (rows > 0) {
                ResultSet rs = stmtStudent.getGeneratedKeys();
                if (rs.next()) {
                    int studentId = rs.getInt(1);

                    stmtProfile.setInt(1, studentId);
                    stmtProfile.setString(2, student.getCity());
                    stmtProfile.setString(3, student.getMobileNo());
                    

                    stmtProfile.executeUpdate();

                    System.out.println("Student added successfully.");
                } else {

                    System.out.println("Failed to retrieve student ID.");
                }
            } else {

                System.out.println("Failed to insert student.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        } 
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = """
            SELECT s.*, sp.student_profile_id, sp.city, sp.mobile_no
            FROM students s
            JOIN student_profile sp ON s.student_id = sp.student_id
            WHERE s.is_active = true AND sp.is_active = true
        """;

        try (
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("student_id"),
                    rs.getInt("roll_no"),
                    rs.getString("student_name"),
                    rs.getInt("age"),
                    rs.getDouble("percentage"),
                    
                    rs.getInt("student_profile_id"),
                    rs.getString("city"),
                    rs.getString("mobile_no")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }

        return students;
    }

    public Student getStudentById(int id) {
        String query = """
            SELECT s.*, sp.student_profile_id, sp.city, sp.mobile_no
            FROM students s
            JOIN student_profile sp ON s.student_id = sp.student_id
            WHERE s.student_id = ? AND s.is_active = true AND sp.is_active = true
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Student(
                    rs.getInt("student_id"),
                    rs.getInt("roll_no"),
                    rs.getString("student_name"),
                    rs.getInt("age"),
                    rs.getDouble("percentage"),
                    rs.getBoolean("is_active"),
                    rs.getInt("student_profile_id"),
                    rs.getString("city"),
                    rs.getString("mobile_no")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching student by ID: " + e.getMessage());
        }

        return null;
    }

    public void deleteStudent(int id) {
        String query1 = "UPDATE students SET is_active = false WHERE student_id = ?";
        String query2 = "UPDATE student_profile SET is_active = false WHERE student_id = ?";

        try (
            PreparedStatement stmt1 = connection.prepareStatement(query1);
            PreparedStatement stmt2 = connection.prepareStatement(query2)
        ) {
            stmt1.setInt(1, id);
            stmt2.setInt(1, id);
            stmt1.executeUpdate();
            stmt2.executeUpdate();

            System.out.println("Student soft-deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    public int getStudentIdByRollNo(Integer rollNo) {
        int studentId = -1;
        String query = "SELECT student_id FROM students WHERE roll_no = ? AND is_active = true";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rollNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                studentId = rs.getInt("student_id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching student ID by roll number: " + e.getMessage());
        }

        return studentId;
    }

    public void updateStudent(Student student) {
        String query = "UPDATE students SET roll_no = ?, student_name = ?, age = ?, percentage = ? WHERE student_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, student.getRollNo());
            stmt.setString(2, student.getStudentName());
            stmt.setInt(3, student.getAge());
            stmt.setDouble(4, student.getPercentage());
            stmt.setInt(5, student.getStudentId());
            stmt.executeUpdate();

            System.out.println("Student updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public void assignCourse(int studentId, int courseId) {
        String query = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
            System.out.println("Course assigned to student.");
        } catch (SQLException e) {
            System.out.println("Error assigning course: " + e.getMessage());
        }
    }

    public List<String> getAssignedCoursesByStudentId(int studentId) {
        List<String> courses = new ArrayList<>();
        String query = """
            SELECT c.course_name
            FROM courses c
            JOIN student_course sc ON c.course_id = sc.course_id
            WHERE sc.student_id = ? AND c.is_active = true AND sc.is_active = true
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(rs.getString("course_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving courses: " + e.getMessage());
        }

        return courses;
    }

    public List<String> getStudentGrades(int studentId) {
        List<String> grades = new ArrayList<>();
        String query = """
            SELECT s.subject_name, m.marks,
            CASE
                WHEN m.marks >= 90 THEN 'A+'
                WHEN m.marks >= 80 THEN 'A'
                WHEN m.marks >= 70 THEN 'B+'
                WHEN m.marks >= 60 THEN 'B'
                WHEN m.marks >= 50 THEN 'C'
                WHEN m.marks >= 33 THEN 'D'
                ELSE 'F'
            END AS grade
            FROM student_all_marks m
            JOIN subjects s ON s.subject_id = m.subject_id
            WHERE m.student_id = ? AND m.is_active = true
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String subject = rs.getString("subject_name");
                int marks = rs.getInt("marks");
                String grade = rs.getString("grade");

                String line = String.format("%-29s | %-6d | %-5s", subject, marks, grade);
                grades.add(line);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving student grades: " + e.getMessage());
        }

        // Add overall grade
        query = "SELECT percentage FROM students WHERE student_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            double avg_percentage = 0;
            if (rs.next()) {
                avg_percentage = rs.getDouble("percentage");
            }

            grades.add(String.format("\nAverage Percentage : %.2f%%", avg_percentage));

            String overallGrade;
            if (avg_percentage >= 90) overallGrade = "A+";
            else if (avg_percentage >= 80) overallGrade = "A";
            else if (avg_percentage >= 70) overallGrade = "B+";
            else if (avg_percentage >= 60) overallGrade = "B";
            else if (avg_percentage >= 50) overallGrade = "C";
            else if (avg_percentage >= 33) overallGrade = "D";
            else overallGrade = "F";

            grades.add("Overall Grade       : " + overallGrade);

        } catch (SQLException e) {
            System.out.println("Error retrieving overall grade: " + e.getMessage());
        }

        return grades;
    }

    
    public boolean isStudentExists(int rollNo) {
        String query = "SELECT student_id FROM students WHERE roll_no = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, rollNo);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if student exists
        } catch (SQLException e) {
            System.out.println("Error checking student existence: " + e.getMessage());
            return false;
        }
    }

}

