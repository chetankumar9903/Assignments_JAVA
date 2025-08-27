package com.aurionpro.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aurionpro.dao.LeaveBalanceDAO;
import com.aurionpro.dao.LeaveDao;
import com.aurionpro.dao.LeaveTypeDAO;
import com.aurionpro.model.LeaveApplication;
import com.aurionpro.model.LeaveApplyResult;
import com.aurionpro.model.LeaveSummary;
import com.aurionpro.model.LeaveType;

public class LeaveService {
	
	private LeaveDao leaveDao = new LeaveDao();
	private LeaveBalanceDAO balanceDAO = new LeaveBalanceDAO();
	
    private LeaveTypeDAO typeDAO = new LeaveTypeDAO();
	
//	  public boolean applyLeave(LeaveApplication leave) {
//		  return leaveDao.applyLeave(leave);
//	  }
    
    // Apply leave with quota check without yearly check
//    public boolean applyLeave(LeaveApplication leave) {
//        String yearMonth = leave.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy-MM"));
//        int used = balanceDAO.getUsedLeaves(leave.getEmployeeId(), leave.getTypeId(), yearMonth);
//        int limit = typeDAO.getLeaveTypeById(leave.getTypeId()).getMonthlyLimit();
//
//        // Quota check
//        if (used + leave.getNoOfDays() > limit) {
//            // quota exceeded → must provide override reason
//            if (leave.getOverrideReason() == null || leave.getOverrideReason().trim().isEmpty()) {
//                return false; // front-end should prompt for reason
//            }
//        }
//
//        // Apply leave
//        boolean applied = leaveDao.applyLeave(leave);
//        if (applied) {
//            // update leave balances
//            balanceDAO.updateUsedLeaves(leave.getEmployeeId(), leave.getTypeId(), yearMonth, used + leave.getNoOfDays());
//        }
//        return applied;
//    }
    
    
    
//    public boolean applyLeave(LeaveApplication leave) {
//        String yearMonth = leave.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy-MM"));
//        String year = leave.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy"));
//
//        // --- Monthly check ---
//        int usedMonthly = balanceDAO.getUsedLeaves(leave.getEmployeeId(), leave.getTypeId(), yearMonth);
//        int monthlyLimit = typeDAO.getLeaveTypeById(leave.getTypeId()).getMonthlyLimit();
//
//        // --- Yearly check ---
//        Map<Integer, Integer> yearlyLeaves = balanceDAO.getYearlyUsedLeaves(leave.getEmployeeId(), year);
//        int usedYearly = yearlyLeaves.getOrDefault(leave.getTypeId(), 0);
//        int yearlyLimit = monthlyLimit * 12;
//
//        // --- Validation ---
//        // Check yearly first
//        if (usedYearly + leave.getNoOfDays() > yearlyLimit) {
//            // No override allowed → hard stop
//            return false; // show error "Yearly limit exceeded"
//        }
//
//        // Then check monthly
//        if (usedMonthly + leave.getNoOfDays() > monthlyLimit) {
//            // quota exceeded → must provide override reason
//            if (leave.getOverrideReason() == null || leave.getOverrideReason().trim().isEmpty()) {
//                return false; // frontend should prompt for reason
//            }
//        }
//
//        // --- Apply leave ---
//        boolean applied = leaveDao.applyLeave(leave);
//        if (applied) {
//            // update balances
//            balanceDAO.updateUsedLeaves(leave.getEmployeeId(), leave.getTypeId(), yearMonth, usedMonthly + leave.getNoOfDays());
//        }
//        return applied;
//    }

    
    
    public LeaveApplyResult applyLeave(LeaveApplication leave) {
        String yearMonth = leave.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String year = leave.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy"));

        int usedMonthly = balanceDAO.getUsedLeaves(leave.getEmployeeId(), leave.getTypeId(), yearMonth);
        int monthlyLimit = typeDAO.getLeaveTypeById(leave.getTypeId()).getMonthlyLimit();

        Map<Integer, Integer> yearlyLeaves = balanceDAO.getYearlyUsedLeaves(leave.getEmployeeId(), year);
        int usedYearly = yearlyLeaves.getOrDefault(leave.getTypeId(), 0);
        int yearlyLimit = monthlyLimit * 12;

       
        if (usedYearly + leave.getNoOfDays() > yearlyLimit) {
            return LeaveApplyResult.YEARLY_EXCEEDED;
        }

       
        if (usedMonthly + leave.getNoOfDays() > monthlyLimit) {
            if (leave.getOverrideReason() == null || leave.getOverrideReason().trim().isEmpty()) {
                return LeaveApplyResult.MONTHLY_EXCEEDED;
            }
        }

       
        boolean applied = leaveDao.applyLeave(leave);
        if (applied) {
            balanceDAO.updateUsedLeaves(leave.getEmployeeId(), leave.getTypeId(), yearMonth, usedMonthly + leave.getNoOfDays());
            return LeaveApplyResult.SUCCESS;
        }
        return LeaveApplyResult.SUCCESS; 
    }

    

    public boolean updateLeave(LeaveApplication leave, int oldNoOfDays, int oldTypeId, LocalDate oldDateFrom) {
        boolean updated = leaveDao.updateLeave(leave);
        if (updated) {
            String oldYearMonth = oldDateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            String newYearMonth = leave.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy-MM"));

            // decrement old leave balance
            balanceDAO.decrementUsedLeaves(leave.getEmployeeId(), oldTypeId, oldYearMonth, oldNoOfDays);

            // increment new leave balance
            int used = balanceDAO.getUsedLeaves(leave.getEmployeeId(), leave.getTypeId(), newYearMonth);
            balanceDAO.updateUsedLeaves(leave.getEmployeeId(), leave.getTypeId(), newYearMonth, used + leave.getNoOfDays());
        }
        return updated;
    }

   
    public boolean deleteLeave(LeaveApplication leave) {
        boolean deleted = leaveDao.deleteLeave(leave.getLeaveId(), leave.getEmployeeId());
        if (deleted) {
            String yearMonth = leave.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            balanceDAO.decrementUsedLeaves(leave.getEmployeeId(), leave.getTypeId(), yearMonth, leave.getNoOfDays());
        }
        return deleted;
    }

    
    public List<LeaveSummary> getLeaveSummary(int empId) {
        String year = String.valueOf(LocalDate.now().getYear());
        Map<Integer, Integer> used = balanceDAO.getYearlyUsedLeaves(empId, year);

        List<LeaveSummary> summaryList = new ArrayList<>();
        typeDAO.getAllLeaveTypes().forEach(type -> {
            int yearlyLimit = type.getMonthlyLimit() * 12;
            int usedCount = used.getOrDefault(type.getTypeId(), 0);
            summaryList.add(new LeaveSummary(type.getTypeName(), usedCount, yearlyLimit));
        });

        return summaryList;
    }

    
    
	  
	  public List<LeaveApplication> getLeavesByEmployee(int empId){
		  return leaveDao.getLeavesByEmployee(empId);
	  }

	  public List<LeaveApplication> getAllLeaves(){
		  return leaveDao.getAllLeaves();
	  }
	  
//	  public boolean updateLeaveStatus(int leaveId, String status) {
//		  return leaveDao.updateLeaveStatus(leaveId, status);
//	  }
	  
	// LeaveService.java
	  public boolean updateLeaveStatus(int leaveId, String status) {
	     

	      
	      LeaveApplication leave = leaveDao.getLeaveById(leaveId);
	      if (leave == null) return false;

	      int empId = leave.getEmployeeId();
	      int typeId = leave.getTypeId();
	      int noOfDays = leave.getNoOfDays();
	      String yearMonth = leave.getDateFrom().getYear() + "-" + 
	                         String.format("%02d", leave.getDateFrom().getMonthValue());

	     
	      if ("Rejected".equals(status)) {
	          balanceDAO.decrementUsedLeaves(empId, typeId, yearMonth, noOfDays);
	      }

	    
	      return leaveDao.updateLeaveStatus(leaveId, status);
	  }

	  
	  
	  public List<LeaveApplication> getSanctionedLeaves(){
		  return leaveDao.getSanctionedLeaves();
	  }
	  
	  public List<LeaveApplication> getLeavesByStatus(String status){
		  return leaveDao.getLeavesByStatus(status);
	  }
	  
	  public boolean hasOverlappingLeave(int empId, LocalDate from, LocalDate to) {
		  return leaveDao.hasOverlappingLeave(empId, from, to);
	  }
	  
	
	  public List<LeaveType> getAllLeaveTypes() {
	      return typeDAO.getAllLeaveTypes();
	  }

	  public String getLeaveTypeName(int typeId) {
	      return typeDAO.getLeaveTypeById(typeId).getTypeName();
	  }
	  
	  
	  public int getUsedLeaves(int empId, int typeId, String yearMonth) {
		  return balanceDAO.getUsedLeaves(empId, typeId, yearMonth);
	  }
	  
	  public LeaveType getLeaveTypeById(int typeId) {
		return  typeDAO.getLeaveTypeById(typeId);
	  }

}
