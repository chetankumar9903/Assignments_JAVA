package com.aurionpro.services;

import java.util.Scanner;

import com.aurionpro.dao.FeesDao;

public class FeesServices {
	private FeesDao feesDao;

    public FeesServices() {
        this.feesDao = new FeesDao();
    }

    public void viewTotalPaidFees() {
        double total = feesDao.getTotalPaidFees();
        System.out.printf("Total Paid Fees: ₹%.2f\n", total);
    }

    public void viewTotalPendingFees() {
        double total = feesDao.getTotalPendingFees();
        System.out.printf("Total Pending Fees: ₹%.2f\n", total);
    }

    public void viewFeesByStudent(int studentId) {
        feesDao.showFeesByStudent(studentId);
    }

    public void viewFeesByCourse(int courseId) {
        feesDao.showFeesByCourse(courseId);
    }

    public void updateFees(int courseId, double amount) {
    	 if (amount < 0) {
             System.out.println("Course fee cannot be negative.");
             return;
         }
        feesDao.updateCourseFee(courseId, amount);
    }

    public void viewTotalEarning() {
        double total = feesDao.getTotalEarning();
        System.out.printf("Total Earning: ₹%.2f\n", total);
    }
    
    public void payPendingFees(Scanner sc) {
    	feesDao.payPendingFees(sc);
    }

}
