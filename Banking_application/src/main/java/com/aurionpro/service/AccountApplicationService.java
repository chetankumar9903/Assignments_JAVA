package com.aurionpro.service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.aurionpro.dao.AccountApplicationDAO;

public class AccountApplicationService {
	private AccountApplicationDAO accDao ;
	
	public AccountApplicationService() {
		this.accDao = new AccountApplicationDAO(); 
	}
	
	public List<Map<String, Object>> listPendingApplications(String q, String dateFrom, String dateTo, Integer limit,
			Integer offset) {
		
			return accDao.listPendingApplications(q, dateFrom, dateTo, limit, offset);
		
	 }
	
	 public List<Map<String, Object>> getApprovedApplications(){
		 return accDao.getApprovedApplications();
	 }
	 
	public void approveApplication(int applicationId, int adminUserId) {
		 accDao.approveApplication(applicationId, adminUserId);
	 }
	
	public void rejectApplication(int applicationId, int adminUserId, String remarks) {
		accDao.rejectApplication(applicationId, adminUserId, remarks);
	}
	
	public int countPendingApplications(){
		return accDao.countPendingApplications();
	}
	
	public void submitApplication(int userId, String accountType, BigDecimal initialDeposit)  {
		accDao.createApplication(userId, accountType, initialDeposit);
    }
	
	 public Map<String,Object> getApplicationDetails(int applicationId){
		 return accDao.getApplicationDetails(applicationId);
	 }
	 
	 public List<Map<String, Object>> getApplicationsByStatusAndFilters(
		        String status, String dateFrom, String dateTo, String accountType) {
		 return accDao.getApplicationsByStatusAndFilters(status, dateFrom, dateTo, accountType);
	 }
	 
	 
	 // one per row
	 public List<Map<String, Object>> getApplicationsAggregatedByUser(
		        String status, String dateFrom, String dateTo, String accountType){
		 return accDao.getApplicationsAggregatedByUser(status, dateFrom, dateTo, accountType);
	 }
}



