package com.aurionpro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aurionpro.service.BeneficiaryService;


@WebServlet("/DeleteBeneficiaryServlet")
public class DeleteBeneficiaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BeneficiaryService service = new BeneficiaryService();
       
  
    public DeleteBeneficiaryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		 try {
//	            int ownerAccountId = (int) request.getSession().getAttribute("accountId");
//	            int beneficiaryId = Integer.parseInt(request.getParameter("id"));
//
//	            service.deleteBeneficiary(beneficiaryId, ownerAccountId);
//	            response.sendRedirect("ListBeneficiariesServlet?msg=deleted");
//	        } catch (Exception e) {
//	            throw new ServletException(e);
//	        }
		
		try {
	        // Get the beneficiary ID from the request
	        int beneficiaryId = Integer.parseInt(request.getParameter("id"));

	        // Get the owner account ID from the request (sent via query param)
	        int ownerAccountId = Integer.parseInt(request.getParameter("account_id"));

	        // Call service to delete
	        boolean success = service.deleteBeneficiary(beneficiaryId, ownerAccountId);

	        // Redirect back to list with message
	        if (success) {
	            response.sendRedirect("ListBeneficiariesServlet?account_id=" + ownerAccountId + "&msg=deleted");
	        } else {
	            response.sendRedirect("ListBeneficiariesServlet?account_id=" + ownerAccountId + "&msg=failed");
	        }

	    } catch (Exception e) {
	        throw new ServletException(e);
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
