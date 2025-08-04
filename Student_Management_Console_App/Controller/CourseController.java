package com.aurionpro.controller;

import java.util.Scanner;

import com.aurionpro.model.Admin;
import com.aurionpro.model.Course;
import com.aurionpro.services.CourseServices;

public class CourseController {

	public static void courseMenu() {
	    CourseServices courseService = new CourseServices();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add New Course");
            System.out.println("2. Show All Courses");
            System.out.println("3. Add Subject to Course");
            System.out.println("4. Search a Course");
            System.out.println("5. Delete a Course");
            System.out.println("6. Reactivate a Course");
            System.out.println("7. View Inactive Courses");
            System.out.println("8. View Course Details");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
//                    System.out.print("Enter course name: ");
//                    String courseName = sc.nextLine();
//                    courseService.addCourse(courseName);
//                    break;
                	System.out.print("Enter course name: ");
                    String courseName = sc.nextLine();

                    System.out.print("Enter course fee: ");
                    double courseFee = sc.nextDouble();
                    sc.nextLine(); // consume newline

                    Course newCourse = new Course(courseName, courseFee);
                    courseService.addCourse(newCourse);
                    Admin.addCourse(newCourse);
                    break;
                case 2:
                    courseService.showAllCourses();
                    break;
                case 3:
//                    System.out.print("Enter Course ID: ");
                	System.out.print("Enter course ID to add subject in: ");
                    int cid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Subject Name: ");
                    String subName = sc.nextLine();
                    courseService.addSubjectToCourse(cid, subName);
                    break;
                    
                case 4:
                	System.out.println("\nSearch Course By: ");
                	System.out.println("1. Course ID \n2. Course Name");
                	System.out.print("Enter your choice: ");
                	int ch = sc.nextInt();

                	if(ch ==1) {
                		System.out.print("Enter course ID: ");
                        int id = sc.nextInt();
                        courseService.searchCourseById(id);
                	}else if(ch==2) {
                		sc.nextLine(); // Clear buffer
                        System.out.print("Enter course name: ");
                        String name = sc.nextLine();
                        courseService.searchCourseByName(name);
                	}
                	 else
                         System.out.println("Invalid option.");
                     break;
                case 5:
                    System.out.print("Enter Course ID to delete: ");
                    int delId = sc.nextInt();
                    courseService.deleteCourse(delId);
                    break;
                    
                case 6:
                    System.out.print("Enter course name to reactivate: ");
                    String crName = sc.nextLine();
                    courseService.reactivateCourse(crName); // service layer method
                    break;
                    
                case 7:
                	courseService.displayInactiveCourses();
                    break;
                case 8:
                    courseService.showAllCourses();
                    System.out.print("Enter Course ID to view details: ");
                    int viewId = sc.nextInt();
                    System.out.println("1. View Subjects\n2. View Students");
                    System.out.print("Enter your choice: ");
                    int subChoice = sc.nextInt();
                    if (subChoice == 1)
                        courseService.viewSubjectsInCourse(viewId);
                    else if (subChoice == 2)
                        courseService.viewStudentsInCourse(viewId);
                    else
                        System.out.println("Invalid option.");
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
		
		
	}

}
