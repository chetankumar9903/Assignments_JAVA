package com.aurionpro.controller;

import java.util.Scanner;

public class MainController {

    static Scanner scanner = new Scanner(System.in);

    public static void start() {
        int choice;

        System.out.println("====================================");
        System.out.println("   Welcome to Institute Portal");
        System.out.println("====================================");

        while (true) {
            System.out.println("\n-------- Main Menu --------");
            System.out.println("1. Student Management");
            System.out.println("2. Teacher Management");
            System.out.println("3. Course Management");
            System.out.println("4. Fees Management");
            System.out.println("5. Dashboard");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            // Input Validation
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number (1-4): ");
                scanner.next(); // consume invalid input
            }

            choice = scanner.nextInt();

           System.out.println(); // space for clarity

            switch (choice) {
                case 1:
                    StudentController.studentMenu();
                    break;
                case 2:
                    TeacherController.teacherMenu();
                    break;
                case 3:
                    CourseController.courseMenu();
                    break;
                case 4:
                	FeesController.feesMenu();
                	break;
                	
                case 5:
                	DashboardController.showDashboard();
                	break;
                case 6:
                    System.out.println("Thank you for using the portal. Goodbye!");
                    System.out.println("====================================");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}


