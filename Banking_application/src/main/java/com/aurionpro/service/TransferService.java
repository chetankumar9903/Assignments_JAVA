package com.aurionpro.service;

import com.aurionpro.dao.TransferDAO;
import com.aurionpro.model.TransferStatus;

public class TransferService {
	 private TransferDAO dao = new TransferDAO();

	 /**
	    
	     // Performs transfer and returns TransferResult
	     
	    public TransferResult transfer(int fromAccountId, String toAccountNumber, double amount, String narration) {
	        try {
	            if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
	            if (toAccountNumber == null || toAccountNumber.isBlank())
	                throw new IllegalArgumentException("Destination account required");
	            return dao.transfer(fromAccountId, toAccountNumber, amount, narration);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return new TransferResult(-1, "Error: " + ex.getMessage());
	        }
	    }
	    **/

	 
	    /**
	     * Transfer funds
	     * @param fromAccountId source account
	     * @param toAccountId   destination account (internal) or null
	     * @param toAcctNum     destination account number (external)
	     * @param toIfsc        IFSC (external)
	     */
	 
	 /**
	    public TransferResult transfer(int fromAccountId, Integer toAccountId, String toAcctNum, String toIfsc, double amount, int userId, String narration) {

	        if(amount <= 0) return new TransferResult(-1, "Amount must be > 0");

	        try {
	            if(toAccountId != null) {
	                // Internal transfer
	                return dao.internalTransfer(fromAccountId, toAccountId, amount, userId, narration);
	            } else if(toAcctNum != null && toIfsc != null) {
	                // External transfer
	                return dao.externalTransfer(fromAccountId, toAcctNum, toIfsc, amount, userId, narration);
	            } else {
	                return new TransferResult(-1, "Destination account invalid");
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return new TransferResult(-1, "Transfer failed: " + ex.getMessage());
	        }
	    }
	    **/
//	 
//	 public boolean transferMoney(int userId, int beneficiaryId, double amount, String description) {
//	        return dao.transfer(userId, beneficiaryId, amount, description);
//	    }
//	 
	 
//	 with status like which error
	 public TransferStatus transfer(int customerId, int beneficiaryId, double amount, String description) {
		    return dao.transfer(customerId, beneficiaryId, amount, description);
		}

}
