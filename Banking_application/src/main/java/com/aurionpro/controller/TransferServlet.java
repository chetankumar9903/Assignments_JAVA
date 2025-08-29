package com.aurionpro.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.Beneficiary;
import com.aurionpro.model.TransferStatus;
import com.aurionpro.model.User;
import com.aurionpro.service.BeneficiaryService;
import com.aurionpro.service.TransferService;


@WebServlet("/customer_transfer")
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TransferService transferService = new TransferService();
    private BeneficiaryService beneficiaryService = new BeneficiaryService();
       
  
    public TransferServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
//			// Read account_id from request parameter
//	        String accountIdParam = request.getParameter("from_account_id");
//	        if (accountIdParam == null) {
//	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing account_id parameter");
//	            return;
//	        }
//
//	        int ownerAccountId = Integer.parseInt(accountIdParam);
			
			
		 HttpSession session = request.getSession(false);
	        User loggedIn = (User) session.getAttribute("authUser");

	        if (loggedIn == null || !"CUSTOMER".equals(loggedIn.getRole())) {
	            response.sendRedirect("login.jsp?msg=unauthorized");
	            return;
	        }
	        int ownerAccountId = beneficiaryService.getAccountIdByCustomer(loggedIn.getUserId());
	       
	        String beneficiaryIdStr = request.getParameter("beneficiaryId");
	        String amountStr = request.getParameter("amount");
	        String description = request.getParameter("description");

	        if (beneficiaryIdStr == null || amountStr == null) {
	            // load beneficiary list
	        	System.out.println(ownerAccountId);
	            List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesByOwner(ownerAccountId);
//	            System.out.println("DONE");
	            System.out.println(beneficiaries);
	            request.setAttribute("beneficiaries", beneficiaries);
	            RequestDispatcher rd = request.getRequestDispatcher("customer/transfer_form.jsp");
	            rd.forward(request, response);
	            return;
	        }

	        int beneficiaryId = Integer.parseInt(beneficiaryIdStr);
	        double amount = Double.parseDouble(amountStr);
	        
	        System.out.println(beneficiaryId+" "+amount);

//	        boolean success = transferService.transferMoney(loggedIn.getUserId(), beneficiaryId, amount, description);
//
//	        if (success) {
//	        	response.sendRedirect("customer_dashboard?msg=transferSuccess");
//	        
//	        } else {
//	            response.sendRedirect("customer_transfer?msg=transferFailed");
//	        }
	        
	        
	        
	        // with error message
	        TransferStatus result = transferService.transfer(loggedIn.getUserId(), beneficiaryId, amount, description);

	        if (result == TransferStatus.SUCCESS) {
	            response.sendRedirect("customer_dashboard?msg=transferSuccess");
	        } else {
	            request.setAttribute("errorMsg", result.getMessage());
	            List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesByOwner(ownerAccountId);
	            request.setAttribute("beneficiaries", beneficiaries);
	            RequestDispatcher rd = request.getRequestDispatcher("customer/transfer_form.jsp");
	            rd.forward(request, response);
	        }

	        
		}catch (Exception ex) {
            ex.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		 
	}

}
