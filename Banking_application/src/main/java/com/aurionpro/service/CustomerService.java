package com.aurionpro.service;

import com.aurionpro.dao.CustomerDAO;
import com.aurionpro.model.CustomerProfile;

public class CustomerService {
	private CustomerDAO cusDao = new CustomerDAO();
	
	public CustomerProfile getProfileByUserId(int userId) {
		return cusDao.getProfileByUserId(userId);
	}

	 public boolean updateProfile(CustomerProfile profile) {
		 return cusDao.updateProfile(profile);
	 }
	 
	 public boolean changePassword(int userId, String oldPassword, String newPassword) {
		 return cusDao.changePassword(userId, oldPassword, newPassword);
	 }
	 
	 public boolean isKycApproved(int customerId) {
		 return cusDao.isKycApproved(customerId);
	 }
}

