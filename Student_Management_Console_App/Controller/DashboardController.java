package com.aurionpro.controller;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.model.Dashboard;
import com.aurionpro.services.DashboardServices;

public class DashboardController {


    public static void showDashboard() {
    	DashboardServices dashboardService = new DashboardServices();
    	List<Dashboard> entries = dashboardService.getDashboardEntries();
    	
    	System.out.println("\n=====================================================================================");
    	System.out.println("                               STUDENT DASHBOARD OVERVIEW                          ");
    	System.out.println("=====================================================================================");

    	// Header
    	System.out.printf("%-6s %-12s %-18s %-20s %-12s %-13s %-12s %-40s %-40s%n",
    	        "Sr No", "Student ID", "Name", "Course", "Paid Fees", "Pending Fees", "Total Fees", "Subjects", "Teachers");

    	System.out.println("=".repeat(200));

    	// Rows
    	for (Dashboard e : entries) {
    	    List<String> subjectsWrapped = wrapText(e.getSubjects(), 40);
    	    List<String> teachersWrapped = wrapText(e.getTeachers(), 40);
    	    int lines = Math.max(subjectsWrapped.size(), teachersWrapped.size());

    	    for (int i = 0; i < lines; i++) {
    	        if (i == 0) {
    	            System.out.printf("%-6d %-12d %-18s %-20s %-12.2f %-13.2f %-12.2f %-40s %-40s%n",
    	                    e.getSrNo(), e.getStudentId(), e.getStudentName(), e.getCourseName(),
    	                    e.getPaidFee(), e.getPendingFee(), e.getTotalFee(),
    	                    subjectsWrapped.get(0), teachersWrapped.get(0));
    	        } else {
    	            System.out.printf("%-6s %-12s %-18s %-20s %-12s %-13s %-12s %-40s %-40s%n",
    	                    "", "", "", "", "", "", "",
    	                    (i < subjectsWrapped.size() ? subjectsWrapped.get(i) : ""),
    	                    (i < teachersWrapped.size() ? teachersWrapped.get(i) : ""));
    	        }
    	    }
    	    System.out.println("-".repeat(200));
    	}
    }
    
    public static List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        if (text == null || text.isEmpty()) return lines;

        String[] words = text.split(", ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() + 2 > maxWidth) {
                lines.add(line.toString().trim());
                line = new StringBuilder();
            }
            line.append(word).append(", ");
        }
        if (!line.isEmpty()) {
            lines.add(line.toString().trim());
        }
        return lines;
    }
}
