package com.aurionpro.controller;

import java.util.Scanner;

import com.aurionpro.services.FeesServices;

public class FeesController {
	
	public static void feesMenu() {
		Scanner sc = new Scanner(System.in);
		FeesServices feesService = new FeesServices();
		
		int choice;
        do {
            System.out.println("\n--- Fees Management ---");
            System.out.println("1. View Total Paid Fees");
            System.out.println("2. View Total Pending Fees");
            System.out.println("3. View Fees By Student");
            System.out.println("4. View Fees By Course");
            System.out.println("5. Update Fees Of A Course");
            System.out.println("6. View Total Earning");
            System.out.println("7. Pay Pending Fees");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            switch (choice) {
                case 1 : feesService.viewTotalPaidFees(); break;
                case 2 : feesService.viewTotalPendingFees(); break;
                case 3 : {
                    System.out.print("Enter student ID: ");
                    int studentId = sc.nextInt();
                    sc.nextLine(); // consume newline
                    feesService.viewFeesByStudent(studentId);
                    break;
                }
                case 4 : {
                    System.out.print("Enter course ID: ");
                    int courseId = sc.nextInt();
                    sc.nextLine();
                    feesService.viewFeesByCourse(courseId);
                    break;
                }
                case 5 : {
                    System.out.print("Enter course ID: ");
                    int courseId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    feesService.updateFees(courseId, amount);
                    break;
                }
                case 6 : feesService.viewTotalEarning();break;
                case 7 : feesService.payPendingFees(sc);break;
                case 8 : 
                	System.out.println("Exiting Fees Management...");
                	break;
                default : System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 8);
	}

}
