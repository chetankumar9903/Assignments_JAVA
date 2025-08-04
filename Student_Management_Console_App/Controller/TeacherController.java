package com.aurionpro.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.aurionpro.services.TeacherServices;

public class TeacherController {

	public static void teacherMenu() {
		TeacherServices teacherService = new TeacherServices();
		ResultSet resultSet = null;
		Scanner scanner = new Scanner(System.in);
 
		while (true) {
			System.out.println("\nChoose an option:");
			System.out.println("1. Add New Teacher");
			System.out.println("2. Show All Teachers");
			System.out.println("3. Assign Subject to Teacher");
			System.out.println("4. Show subjects of a Teacher");
			System.out.println("5. Search a Teacher");
			System.out.println("6. Delete a Teacher");
			System.out.println("7. Remove A Subject");
			System.out.println("8. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // consume newline
			int teacherId;
 
			switch (choice) {
			case 1:
				System.out.println("Enter Teacher Details :-");
				System.out.print("Enter Teacher name: ");
				String teacherName = scanner.nextLine();			
				System.out.print("Enter Teacher city: ");
				String teacherCity = scanner.nextLine();
				System.out.print("Enter Teacher mobile number: ");
				String teacherMobNo = scanner.nextLine();
				System.out.print("Enter Teacher Qualification: ");
				String teacherQualification = scanner.nextLine();
				System.out.print("Enter Teacher experience: ");
				int teacherExp = scanner.nextInt();
				boolean added = teacherService.addTeacher(teacherName,teacherCity,teacherMobNo,teacherQualification,teacherExp);
				if(added)
				{
					System.out.println("1 Teacher added successfully.");
				}
				else
				{
					System.out.println("Teacher could not be added.");
				}
				break;
			case 2:				
			    try {
			        resultSet = teacherService.showAllTeachers();

			        if (resultSet == null || !resultSet.isBeforeFirst()) {    
			            System.out.println("❌ No Teachers Exist"); 
			            continue;
			        }

			        System.out.println("\n=========== List of Teachers ===========");
			        System.out.printf("%-12s | %-25s%n", "Teacher ID", "Name");
			        System.out.println("-----------------------------------------");

			        while (resultSet.next()) {
			            int teacherid = resultSet.getInt(1);
			            String name = resultSet.getString(2);
			            System.out.printf("%-12d | %-25s%n", teacherid, name);
			        }

			        System.out.println("=========================================\n");

			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			    break;

			case 3:
				System.out.print("Enter Teacher ID to assign subject : ");
				teacherId = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Enter Subject ID: ");
				int subjectId = scanner.nextInt();
				boolean assigned = teacherService.assignSubjectToTeacher(teacherId, subjectId);
				if(assigned)
				{
					System.out.println("Subject successfully assigned to the Teacher.");
				}
				else
				{
					System.out.println("Subject not assigned to the Teacher.");
				}
				break;
			case 4:
				try {
					System.out.print("Enter Teacher ID to view all subjects: ");
					teacherId = scanner.nextInt();	
					resultSet = teacherService.showAllSubjectsOfTeacher(teacherId);;
					if (resultSet == null || !resultSet.isBeforeFirst() ) {    
					    System.out.println("No Subjects Exists for Teacher Id : "+teacherId); 
					    continue;
					}
					System.out.println("Subjects taught by teacher id : " + teacherId);
					while (resultSet.next()) {
						System.out.println("\t- "+resultSet.getString(1));
					}
					System.out.println();
				} catch (SQLException e) {
					e.printStackTrace();
				}			
				break;
			case 5:
			    try {
			        System.out.print("Enter Teacher ID to view details: ");
			        teacherId = scanner.nextInt();

			        resultSet = teacherService.viewTeacherDetails(teacherId);

			        if (resultSet == null || !resultSet.isBeforeFirst()) {    
			            System.out.println("❌ No details found for Teacher ID: " + teacherId); 
			            continue;
			        }

			        System.out.println("\n============ Teacher Details ============");
			        System.out.printf("%-12s | %-20s | %-15s | %-15s | %-20s | %-10s%n",
			                          "Teacher ID", "Name", "City", "Mobile No", "Qualification", "Experience");
			        System.out.println("-------------------------------------------------------------------------------------------------------------------");

			        while (resultSet.next()) {
			            int id = resultSet.getInt(1);
			            String name = resultSet.getString(2);
			            String city = resultSet.getString(3);
			            String mobile = resultSet.getString(4);
			            String qualification = resultSet.getString(5);
			            int experience = resultSet.getInt(6);

			            System.out.printf("%-12d | %-20s | %-15s | %-15s | %-20s | %-10d%n",
			                              id, name, city, mobile, qualification, experience);
			        }

			        System.out.println("=====================================================================================================================\n");

			    } catch (SQLException e) {
			        e.printStackTrace();
			    }			
			    break;

			case 6:
				System.out.print("Enter Teacher ID to delete: ");
                teacherId = scanner.nextInt();
                int rowsAffected = teacherService.deleteTeacher(teacherId);
                if (rowsAffected > 0) {
					System.out.println("Teacher with ID: " + teacherId + " deleted successfully.");
				} else {
					System.out.println("Delete operation unsuccessfull.");
				}
				break;
			case 7:
				System.out.print("Enter Teacher ID : ");
                teacherId = scanner.nextInt();
                System.out.println("Enter Subject Id to delete for Teacher Id : "+teacherId);
                subjectId = scanner.nextInt();
                rowsAffected = teacherService.deleteTeacherSubject(teacherId,subjectId);
                if (rowsAffected > 0) {
					System.out.println("Subject with ID: " + subjectId + " deleted successfully for Teacher Id : "+teacherId);
				} else {
					System.out.println("Subject with ID: " + subjectId + " doesnt exists for Teacher Id : "+teacherId);
				}
				break;
			case 8:
				System.out.println("Exiting...");
				return;
			default:
				System.out.println("Invalid Choice!");
			}
		}
	}

}
