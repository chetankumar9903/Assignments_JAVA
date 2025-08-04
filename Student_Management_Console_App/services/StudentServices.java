package com.aurionpro.services;


import java.util.ArrayList;
import java.util.List;

import com.aurionpro.dao.StudentDao;
import com.aurionpro.model.Student;
 
public class StudentServices {

    private StudentDao studentDao;
 
    public StudentServices() {

        this.studentDao = new StudentDao();

    }
 
    // Add a new student

    public void addStudent(Student student) {

        studentDao.addStudent(student);

    }
 
    // Show all active students

//    public void showAllStudents() {
//
//        List<Student> students = studentDao.getAllStudents();
//
//        for (Student student : students) {
//
//            printStudentDetails(student);
//
//            System.out.println("---------------------------");
//
//        }
//
//    }
    public void showAllStudents() {
        List<Student> students = studentDao.getAllStudents();

        // Header
        System.out.printf("%-11s | %-8s | %-15s | %-3s | %-10s | %-12s | %-12s%n",
                "Student ID", "Roll No", "Name", "Age", "Percentage", "City", "Mobile No");
        System.out.println("-".repeat(85));

        // Rows
        for (Student student : students) {
            System.out.printf("%-11d | %-8d | %-15s | %-3d | %-10.2f | %-12s | %-12s%n",
                    student.getStudentId(),
                    student.getRollNo(),
                    student.getStudentName(),
                    student.getAge(),
                    student.getPercentage(),
                    student.getCity(),
                    student.getMobileNo());
        }
    }
 
    // Search by roll number

    public void searchStudentByRollNo(int rollNo) {
        int studentId = studentDao.getStudentIdByRollNo(rollNo);

        if (studentId == -1) {
            System.out.println("Student not found with roll number: " + rollNo);
            return;
        }

        Student student = studentDao.getStudentById(studentId);

        if (student != null) {
            // Print header
            System.out.printf("%-11s | %-8s | %-15s | %-3s | %-10s | %-12s | %-12s%n",
                    "Student ID", "Roll No", "Name", "Age", "Percentage", "City", "Mobile No");
            System.out.println("-".repeat(85));

            // Print row
            System.out.printf("%-11d | %-8d | %-15s | %-3d | %-10.2f | %-12s | %-12s%n",
                    student.getStudentId(),
                    student.getRollNo(),
                    student.getStudentName(),
                    student.getAge(),
                    student.getPercentage(),
                    student.getCity(),
                    student.getMobileNo());
        } else {
            System.out.println("No active student found with roll number: " + rollNo);
        }
    }

 
    // Soft delete a student

    public void deleteStudentByRollNo(int rollNo) {

        int studentId = studentDao.getStudentIdByRollNo(rollNo);

        if (studentId == -1) {

            System.out.println("Student not found with roll number: " + rollNo);

            return;

        }
 
        studentDao.deleteStudent(studentId);

    }
 
    // Assign a course

    public void assignCourseToStudent(int rollNo, int courseId) {

        int studentId = studentDao.getStudentIdByRollNo(rollNo);

        if (studentId == -1) {

            System.out.println("Student with roll number " + rollNo + " not found.");

            return;

        }
 
        studentDao.assignCourse(studentId, courseId);

    }
 
    // View assigned courses

    public void viewAssignedCourses(int rollNo) {

        int studentId = studentDao.getStudentIdByRollNo(rollNo);

        if (studentId == -1) {

            System.out.println("Student with roll number " + rollNo + " not found.");

            return;

        }
 
        List<String> courses = studentDao.getAssignedCoursesByStudentId(studentId);

        if (courses.isEmpty()) {

            System.out.println("No courses assigned.");

        } else {

            System.out.println("Courses assigned:");

            for (String course : courses) {

                System.out.println("- " + course);

            }

        }

    }
 
    // View student profile

    public void viewStudentProfile(int rollNo) {
    	
    	if (rollNo<0) {
    		 System.out.println("Invalid roll number. Please enter a positive number.");
    		 return;
    	}

        int studentId = studentDao.getStudentIdByRollNo(rollNo);

        if (studentId == -1) {
            System.out.println("Student not found with roll number: " + rollNo);
            return;
        }

        Student student = studentDao.getStudentById(studentId);

        if (student != null) {
            System.out.println("\n====== Student Profile ======");
            System.out.printf("%-10s | %-20s | %-5s | %-15s | %-12s%n", 
                              "Roll No", "Name", "Age", "City", "Mobile No");
            System.out.println("-----------------------------------------------------------------------");
            System.out.printf("%-10d | %-20s | %-5d | %-15s | %-12s%n",
                              student.getRollNo(),
                              student.getStudentName(),
                              student.getAge(),
                              student.getCity(),
                              student.getMobileNo());
            System.out.println("========================================================================\n");
        } else {
            System.out.println("No profile found.");
        }
    }

 
    // View grades

    public void viewStudentGrades(int rollNo) {
        int studentId = studentDao.getStudentIdByRollNo(rollNo);

        if (studentId == -1) {
            System.out.println("Student with roll number " + rollNo + " not found.");
            return;
        }

        List<String> grades = studentDao.getStudentGrades(studentId);

        if (grades.isEmpty()) {
            System.out.println("No grades available.");
        } else {
            System.out.println("+-------------------------------+--------+-------+");
            System.out.println("| Subject                       | Marks  | Grade |");
            System.out.println("+-------------------------------+--------+-------+");

            List<String> summaryLines = new ArrayList<>();

            for (String grade : grades) {
                if (grade.startsWith("Average Percentage") || grade.startsWith("Overall Grade")) {
                    summaryLines.add(grade); 
                } else {
                    System.out.printf("| %-29s |\n", grade); 
                }
            }

//            System.out.println("+-------------------------------+--------+-------+");

            // Print summaries now
            for (String summary : summaryLines) {
                System.out.println(summary);
            }
        }
    }


    
 
    // Utility method to print student details

    private void printStudentDetails(Student student) {

        System.out.println("Student ID: " + student.getStudentId());

        System.out.println("Roll No: " + student.getRollNo());

        System.out.println("Name: " + student.getStudentName());

        System.out.println("Age: " + student.getAge());

        System.out.println("Percentage: " + student.getPercentage());

        System.out.println("Active: " + student.isActive());

        System.out.println("City: " + student.getCity());

        System.out.println("Mobile No: " + student.getMobileNo());

    }
    
    public boolean isStudentExists(int rollNo) {
        return studentDao.isStudentExists(rollNo);
    }


}

 
