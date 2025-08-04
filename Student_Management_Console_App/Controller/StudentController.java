package com.aurionpro.controller;

import java.util.Scanner;
import com.aurionpro.model.Admin;
import com.aurionpro.model.Student;
import com.aurionpro.services.StudentServices;
 
public class StudentController {
    public static void studentMenu() {
        Scanner scanner = new Scanner(System.in);
        StudentServices studentServices = new StudentServices();
 
        int choice;
        do {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add New Student");
            System.out.println("2. Show All Students");
            System.out.println("3. Search Student by Roll No");
            System.out.println("4. Delete Student by Roll No");
            System.out.println("5. Assign Course to Student");
            System.out.println("6. View Assigned Courses");
            System.out.println("7. View Student Profile");
            System.out.println("8. View Student Grades");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
 
            switch (choice) {
                case 1:
//                    Student student = new Student();
//                    System.out.print("Enter Roll No: ");
//                    student.setRollNo(scanner.nextInt());
//                    scanner.nextLine();
//                    System.out.print("Enter Name: ");
//                    student.setStudentName(scanner.nextLine());
//                    System.out.print("Enter Age: ");
//                    student.setAge(scanner.nextInt());
//                    System.out.print("Enter Percentage: ");
//                    student.setPercentage(scanner.nextDouble());
//                    scanner.nextLine();
//                    System.out.print("Enter City: ");
//                    student.setCity(scanner.nextLine());
//                    System.out.print("Enter Mobile No: ");
//                    student.setMobileNo(scanner.nextLine());
//                    studentServices.addStudent(student);
////                    Admin.addStudent(student);
//                    break;
                	 Student student = new Student();
                	System.out.print("Enter Roll No: ");
                    int rollno = scanner.nextInt();
                    scanner.nextLine();

                    if (studentServices.isStudentExists(rollno)) {
                        System.out.println("Student with this roll number already exists.");
                        break;
                    }

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    if (!name.matches("^[A-Za-z ]+$")) {
                        System.out.println("Name must not contain digits or special characters.");
                        break;
                    }

                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    if (age <= 18 || age >= 22) {
                        System.out.println("Age must be between 18 and 22.");
                        break;
                    }

                    System.out.print("Enter Percentage: ");
                    double percentage = scanner.nextDouble();
                    if (percentage <= 0 || percentage >= 100) {
                        System.out.println("Percentage must be between 0 and 100.");
                        break;
                    }

                    scanner.nextLine();  // Consume leftover newline

                    System.out.print("Enter City: ");
                    String city = scanner.nextLine();

                    System.out.print("Enter Mobile No: ");
                    String mobile = scanner.nextLine();
                    if (!mobile.matches("\\d{10}")) {
                        System.out.println("Mobile number must be exactly 10 digits.");
                        break;
                    }

                    // Set validated data to student object
                    student.setRollNo(rollno);
                    student.setStudentName(name);
                    student.setAge(age);
                    student.setPercentage(percentage);
                    student.setCity(city);
                    student.setMobileNo(mobile);

                    studentServices.addStudent(student);
                    break;
 
                case 2:
                    studentServices.showAllStudents();
                    break;
 
                case 3:
                	System.out.print("Enter Roll No to Search: ");
                	int roll_no = scanner.nextInt();

                	if (roll_no <= 0) {
                	    System.out.println("Invalid roll number. Please enter a positive number.");
                	    break;
                	}

                	studentServices.searchStudentByRollNo(roll_no);
                	break;
 
                case 4:
                	System.out.print("Enter Roll No to Search: ");
                	int roll = scanner.nextInt();

                	if (roll <= 0) {
                	    System.out.println("Invalid roll number. Please enter a positive number.");
                	    break;
                	}

                	studentServices.deleteStudentByRollNo(roll);
                	break;
                    
 
                case 5:
                    System.out.print("Enter Roll No to Assign Course: ");
                    int rollNo = scanner.nextInt();
                    if (rollNo <= 0) {
                	    System.out.println("Invalid roll number. Please enter a positive number.");
                	    break;
                	}

                    System.out.print("Enter Course ID: ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    studentServices.assignCourseToStudent(rollNo, courseId);
                    break;
 
                case 6:
                    System.out.print("Enter Roll No to View Assigned Courses: ");
                    studentServices.viewAssignedCourses(scanner.nextInt());
                    break;
 
                case 7:
                    System.out.print("Enter Roll No to View Profile: ");
                    studentServices.viewStudentProfile(scanner.nextInt());
                    break;
 
                case 8:
                    System.out.print("Enter Roll No to View Grades: ");
                    studentServices.viewStudentGrades(scanner.nextInt());
                    break;
 
                case 9:
                    System.out.println("Exiting Student Management System...");
                   return;
 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
 
        } while (choice != 9);
 
        
    }
}