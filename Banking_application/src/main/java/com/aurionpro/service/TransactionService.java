package com.aurionpro.service;

import java.sql.SQLException;
import java.util.List;

import com.aurionpro.dao.TransactionDAO;
import com.aurionpro.model.Transaction;
import com.aurionpro.model.TransactionFilter;

public class TransactionService {
	private TransactionDAO txnDAO = new TransactionDAO();

    public List<Transaction> getAllTransactions(TransactionFilter filter){
        return txnDAO.fetchTransactions(filter);
    }
}
