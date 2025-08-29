package com.aurionpro.service;

import java.sql.SQLException;
import java.util.List;

import com.aurionpro.dao.AccountDAO;
import com.aurionpro.model.Account;

public class AccountService {
	 private  AccountDAO accountDAO = new AccountDAO();

	    public List<Account> getCustomerAccounts(int customerId)  {
	        return accountDAO.findAccountsByCustomer(customerId);
	    }

	    public Account getAccountForCustomer(int accountId, int customerId) {
	        try {
				return accountDAO.findByIdForCustomer(accountId, customerId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    }

}
