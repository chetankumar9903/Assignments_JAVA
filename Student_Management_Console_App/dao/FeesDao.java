package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.aurionpro.database.DBConnection;

public class FeesDao {
	private Connection conn;

	public FeesDao() {
		conn = DBConnection.getInstance().getConnection();
	}
/*	
Fees Management
	View Total Paid Fees
	View Total Pending Fees
	View Fees By Student
	View Fees By Course
	Update Fees Of A Course
	Total Earning
	Exit
*/

	
	 //1. View total paid fees 
    public double getTotalPaidFees() {
        String query = "SELECT SUM(paid_fee) AS total_paid FROM student_course";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble("total_paid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
 //2.  View total pending fees
    public double getTotalPendingFees() {
        String query = """
            SELECT SUM(c.total_fee - sc.paid_fee) AS total_pending
            FROM courses c
            JOIN student_course sc ON c.course_id = sc.course_id
            WHERE sc.is_active = TRUE
        """;
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble("total_pending");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
 //3. View fees by student
//    public void showFeesByStudent(int studentId) {
//        String query = """
//            SELECT c.course_name, c.total_fee, sc.paid_fee
//            FROM student_course sc
//            JOIN courses c ON sc.course_id = c.course_id
//            WHERE sc.student_id = ?
//        """;
//        try (PreparedStatement ps = conn.prepareStatement(query)) {
//            ps.setInt(1, studentId);
//            ResultSet rs = ps.executeQuery();
//            System.out.println("\nFees Details for Student ID: " + studentId);
//            System.out.printf("%-30s %-15s %-15s\n", "Course", "Total Fee", "Paid Fee");
//            while (rs.next()) {
//                System.out.printf("%-30s %-15.2f %-15.2f\n",
//                        rs.getString("course_name"),
//                        rs.getDouble("total_fee"),
//                        rs.getDouble("paid_fee"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    public void showFeesByStudent(int studentId) {
        String studentQuery = """
            SELECT s.roll_no, s.student_name, sp.mobile_no, sp.city
            FROM students s
            JOIN student_profile sp ON s.student_id = sp.student_id
            WHERE s.student_id = ?
        """;

        String feesQuery = """
            SELECT c.course_name, c.total_fee, sc.paid_fee
            FROM student_course sc
            JOIN courses c ON sc.course_id = c.course_id
            WHERE sc.student_id = ?
        """;

        try (
            PreparedStatement studentPs = conn.prepareStatement(studentQuery);
            PreparedStatement feesPs = conn.prepareStatement(feesQuery)
        ) {
            // Student Info
            studentPs.setInt(1, studentId);
            ResultSet studentRs = studentPs.executeQuery();

            if (studentRs.next()) {
                System.out.println("\n=== Student Information ===");
                System.out.printf("%-10s | %-20s | %-15s | %-15s | \n", "Roll No", "Name", "Mobile", "City");
                System.out.println("-----------------------------------------------------------------------");
                System.out.printf("%-10d | %-20s | %-15s | %-15s | \n",
                        studentRs.getInt("roll_no"),
                        studentRs.getString("student_name"),
                        studentRs.getString("mobile_no"),
                        studentRs.getString("city")
                );
            } else {
                System.out.println("No student found with ID: " + studentId);
                return;
            }

            // Fees Info
            feesPs.setInt(1, studentId);
            ResultSet feesRs = feesPs.executeQuery();

            System.out.println("\n=== Fee Details ===");
            System.out.printf("%-30s | %-15s | %-15s | %-15s | \n", "Course", "Total Fee", "Paid Fee", "Pending Fee");
            System.out.println("---------------------------------------------------------------------------------------");
            boolean hasFees = false;
            while (feesRs.next()) {
                hasFees = true;
                double total = feesRs.getDouble("total_fee");
                double paid = feesRs.getDouble("paid_fee");
                double pending = total - paid;

                System.out.printf("%-30s | %-15.2f | %-15.2f | %-15.2f | \n",
                        feesRs.getString("course_name"),
                        total,
                        paid,
                        pending
                );
            }

            if (!hasFees) {
                System.out.println("No fee records found for this student.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    //4.  View fees by course
//    public void showFeesByCourse(int courseId) {
//        String query = """
//            SELECT s.student_id, s.student_name, sc.paid_fee
//            FROM student_course sc
//            JOIN students s ON sc.student_id = s.student_id
//            WHERE sc.course_id = ?
//        """;
//        try (PreparedStatement ps = conn.prepareStatement(query)) {
//            ps.setInt(1, courseId);
//            ResultSet rs = ps.executeQuery();
//            System.out.println("\nFees by Students in Course ID: " + courseId);
//            System.out.printf("%-10s %-25s %-10s\n", "Student ID", "Name", "Paid Fee");
//            while (rs.next()) {
//                System.out.printf("%-10d %-25s %-10.2f\n",
//                        rs.getInt("student_id"),
//                        rs.getString("student_name"),
//                        rs.getDouble("paid_fee"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    public void showFeesByCourse(int courseId) {
        //  Show Course Details
        String courseQuery = "SELECT course_id, course_name, total_fee FROM courses WHERE course_id = ?";
        
        try (PreparedStatement ps1 = conn.prepareStatement(courseQuery)) {
            ps1.setInt(1, courseId);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                String courseName = rs1.getString("course_name");
                double totalFee = rs1.getDouble("total_fee");

                System.out.println("\n=== Course Details ===");
                System.out.printf("%-12s | %-30s | %-12s | \n", "Course ID", "Course Name", "Total Fee");
                System.out.println("--------------------------------------------------------------");
                System.out.printf("%-12d | %-30s | %-12.2f | \n", courseId, courseName, totalFee);

                // Show Students and their Fees
                String studentQuery = """
                    SELECT s.student_id, s.student_name, sc.paid_fee
                    FROM student_course sc
                    JOIN students s ON sc.student_id = s.student_id
                    WHERE sc.course_id = ?
                """;

                try (PreparedStatement ps2 = conn.prepareStatement(studentQuery)) {
                    ps2.setInt(1, courseId);
                    ResultSet rs2 = ps2.executeQuery();

                    System.out.println("\n=== Fees Paid by Students ===");
                    System.out.printf("%-12s | %-25s | %-12s | %-12s | \n", "Student ID", "Student Name", "Paid Fee", "Pending Fee");
                    System.out.println("-------------------------------------------------------------------------");

                    while (rs2.next()) {
                        int studentId = rs2.getInt("student_id");
                        String studentName = rs2.getString("student_name");
                        double paidFee = rs2.getDouble("paid_fee");
                        double pendingFee = totalFee - paidFee;

                        System.out.printf("%-12d %-25s %-12.2f %-12.2f\n",
                                studentId, studentName, paidFee, pendingFee);
                    }
                }
            } else {
                System.out.println("No course found with ID: " + courseId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    //5.  Update fee of a course
    public void updateCourseFee(int courseId, double newFee) {
//        if (newFee < 0) {
//            System.out.println("Course fee cannot be negative.");
//            return;
//        }

        String query = "UPDATE courses SET total_fee = ? WHERE course_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, newFee);
            ps.setInt(2, courseId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Course fee updated successfully.");
            } else {
                System.out.println("Failed to update course fee. Check course ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //6.  Total earnings
    public double getTotalEarning() {
        return getTotalPaidFees(); 
    }

    
    
    
    
    
    
    
    public void payPendingFees(Scanner sc) {
//        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();

        // Step 1: Fetch all enrolled courses with fee details
        String fetchQuery = "SELECT c.course_id, c.course_name, c.total_fee, sc.paid_fee " +
                            "FROM courses c " +
                            "JOIN student_course sc ON c.course_id = sc.course_id " +
                            "WHERE sc.student_id = ? AND sc.is_active = TRUE";

        try (PreparedStatement ps = conn.prepareStatement(fetchQuery)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            List<Integer> courseIds = new ArrayList<>();

            System.out.println("\n=== Enrolled Courses ===");
            System.out.printf("%-10s | %-20s | %-10s | %-10s | %-10s %n", "Course ID", "Course Name", "Total", "Paid", "Pending");
            System.out.println("------------------------------------------------------------------------");
            while (rs.next()) {
                found = true;
                int courseId = rs.getInt("course_id");
                String courseName = rs.getString("course_name");
                double total = rs.getDouble("total_fee");
                double paid = rs.getDouble("paid_fee");
                double pending = total - paid;

                courseIds.add(courseId);
                System.out.printf("%-10d | %-20s | %-10.2f | %-10.2f | %-10.2f %n", courseId, courseName, total, paid, pending);
            }

            if (!found) {
                System.out.println("No active courses found for this student.");
                return;
            }

            // Step 2: Ask course ID for which student wants to pay
            System.out.print("\nEnter Course ID to pay pending fee: ");
            int selectedCourseId = sc.nextInt();

            if (!courseIds.contains(selectedCourseId)) {
                System.out.println("Invalid course ID entered.");
                return;
            }

            // Step 3: Fetch current paid and total fee for selected course
            String pendingQuery = "SELECT c.total_fee, sc.paid_fee FROM courses c " +
                                  "JOIN student_course sc ON c.course_id = sc.course_id " +
                                  "WHERE sc.student_id = ? AND c.course_id = ?";

            try (PreparedStatement pendingStmt = conn.prepareStatement(pendingQuery)) {
                pendingStmt.setInt(1, studentId);
                pendingStmt.setInt(2, selectedCourseId);

                ResultSet result = pendingStmt.executeQuery();
                if (result.next()) {
                    double total = result.getDouble("total_fee");
                    double paid = result.getDouble("paid_fee");
                    double pending = total - paid;

                    System.out.print("Enter amount to pay (Pending: " + pending + "): ");
                    double payAmount = sc.nextDouble();

                    if (payAmount <= 0 || payAmount > pending) {
                        System.out.println("Invalid amount. Must be > 0 and ≤ pending fee.");
                        return;
                    }

                    // Step 4: Update paid fee
                    String updateQuery = "UPDATE student_course SET paid_fee = paid_fee + ? " +
                                         "WHERE student_id = ? AND course_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setDouble(1, payAmount);
                        updateStmt.setInt(2, studentId);
                        updateStmt.setInt(3, selectedCourseId);

                        int rows = updateStmt.executeUpdate();
                        if (rows > 0) {
                            System.out.println("Payment successful.");
                        } else {
                            System.out.println("Failed to update payment.");
                            return;
                        }
                    }

                    // Step 5: Show updated status
                    System.out.println("\n=== Updated Course Details ===");
                    try (PreparedStatement ps2 = conn.prepareStatement(fetchQuery)) {
                        ps2.setInt(1, studentId);
                        ResultSet updatedRs = ps2.executeQuery();

                        System.out.printf("%-10s | %-20s | %-10s | %-10s | %-10s %n", "Course ID", "Course Name", "Total", "Paid", "Pending");
                        System.out.println("-----------------------------------------------------------------------");
                        while (updatedRs.next()) {
                            int courseId = updatedRs.getInt("course_id");
                            String courseName = updatedRs.getString("course_name");
                            double total2 = updatedRs.getDouble("total_fee");
                            double paid2 = updatedRs.getDouble("paid_fee");
                            double pending2 = total2 - paid2;

                            System.out.printf("%-10d | %-20s | %-10.2f | %-10.2f | %-10.2f %n",
                                    courseId, courseName, total2, paid2, pending2);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    

}
