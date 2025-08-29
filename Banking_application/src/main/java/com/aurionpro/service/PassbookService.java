package com.aurionpro.service;


import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aurionpro.dao.PassbookDAO;
import com.aurionpro.model.PassbookEntry;

public class PassbookService {
	private  PassbookDAO passbookDAO = new PassbookDAO();

    public List<PassbookEntry> getPassbook(int accountId, String type, String status, String fromDate, String toDate,
                                           Integer limit, Integer offset, HttpServletRequest request) {
        if (limit == null) limit = 2;
        if (offset == null) offset = 0;
        return passbookDAO.listPassbook(accountId, type, status, fromDate, toDate, limit, offset, request);
    }
    
    /**
    public List<PassbookEntry> listPassbookwithoutlimit(
            int accountId, Date startDate, Date endDate,
            String type, String status,
            Double minAmount, Double maxAmount,
            String search){
    	return passbookDAO.getPassbook(accountId, startDate, endDate, type, status, minAmount, maxAmount, search);
    }
    **/

}
