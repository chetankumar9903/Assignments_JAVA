package com.aurionpro.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LeaveApplication {
    private int leaveId;
    private int employeeId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int noOfDays;
    private String reason;
    private String status;
    private LocalDateTime appliedOn;
    private int typeId;
    private String overrideReason;
    private String leaveType; // add getter & setter


   
   



	private String employeeName;
    private String department;
    private boolean exceedingLimit;

   
    public LeaveApplication(int leaveId, int employeeId, LocalDate dateFrom, LocalDate dateTo, int noOfDays,
                            String reason, String status, LocalDateTime appliedOn) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.noOfDays = noOfDays;
        this.reason = reason;
        this.status = status;
        this.appliedOn = appliedOn;
    }

   
    public LeaveApplication(int employeeId, LocalDate dateFrom, LocalDate dateTo, int noOfDays, String reason) {
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.noOfDays = noOfDays;
        this.reason = reason;
    }
    
    public LeaveApplication(int employeeId, LocalDate dateFrom, LocalDate dateTo, int noOfDays, String reason,int typeId, String overrideReason, String status) {
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.noOfDays = noOfDays;
        this.reason = reason;
        this.typeId = typeId;
        this.overrideReason = overrideReason;
        this.status =status;
    }

    public LeaveApplication() {}

    
    public int getLeaveId() { return leaveId; }
    public void setLeaveId(int leaveId) { this.leaveId = leaveId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public LocalDate getDateFrom() { return dateFrom; }
    public void setDateFrom(LocalDate dateFrom) { this.dateFrom = dateFrom; }

    public LocalDate getDateTo() { return dateTo; }
    public void setDateTo(LocalDate dateTo) { this.dateTo = dateTo; }

    public int getNoOfDays() { return noOfDays; }
    public void setNoOfDays(int noOfDays) { this.noOfDays = noOfDays; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getAppliedOn() { return appliedOn; }
    public void setAppliedOn(LocalDateTime appliedOn) { this.appliedOn = appliedOn; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getDateFromFormatted() {
        return dateFrom.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    }

    public String getDateToFormatted() {
        return dateTo.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    }

    public String getAppliedOnFormatted() {
//        return appliedOn.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
    	return (appliedOn != null) 
    		    ? appliedOn.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm")) 
    		    : "Not Available";
    }
    
    private boolean newLeave; // add this

    public boolean isNewLeave() {
        return newLeave;
    }

    public void setNewLeave(boolean newLeave) {
        this.newLeave = newLeave;
    }
    
    public int getTypeId() {
		return typeId;
	}


	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}


	public String getOverrideReason() {
		return overrideReason;
	}


	public void setOverrideReason(String overrideReason) {
		this.overrideReason = overrideReason;
	}
	
	public String getLeaveType() {
		return leaveType;
	}


	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
    
	public boolean isExceedingLimit() { return exceedingLimit; }
    public void setExceedingLimit(boolean exceedingLimit) { this.exceedingLimit = exceedingLimit; }

    private String leaveTypeName;

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }
    
   
}
