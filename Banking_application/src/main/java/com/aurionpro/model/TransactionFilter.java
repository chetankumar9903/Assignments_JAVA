package com.aurionpro.model;

import java.util.Date;
import java.util.List;

public class TransactionFilter {
	private Date fromDate;
    private Date toDate;
    private List<String> types;      // e.g., TRANSFER, DEPOSIT, WITHDRAWAL
    private List<String> statuses;   // SUCCESS, PENDING, FAILED, REVERSED
    private String accountNumber;    // from/to
    private String customerName;  	
    
    
    // Getters and Setters
    public Date getFromDate() { return fromDate; }
    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

    public Date getToDate() { return toDate; }
    public void setToDate(Date toDate) { this.toDate = toDate; }

    public List<String> getTypes() { return types; }
    public void setTypes(List<String> types) { this.types = types; }

    public List<String> getStatuses() { return statuses; }
    public void setStatuses(List<String> statuses) { this.statuses = statuses; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

}
