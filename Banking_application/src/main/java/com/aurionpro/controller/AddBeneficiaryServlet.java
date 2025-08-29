package com.aurionpro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aurionpro.model.Beneficiary;
import com.aurionpro.service.BeneficiaryService;


@WebServlet("/AddBeneficiaryServlet")
public class AddBeneficiaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BeneficiaryService service = new BeneficiaryService();
       
   
    public AddBeneficiaryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		try {
//            int ownerAccountId = (int) request.getSession().getAttribute("accountId");
//
//            Beneficiary b = new Beneficiary();
//            b.setOwnerAccountId(ownerAccountId);
//            b.setNickname(request.getParameter("nickname"));
//            b.setBeneficiaryName(request.getParameter("beneficiaryName"));
//            boolean isInternal = "internal".equals(request.getParameter("type"));
//            b.setInternal(isInternal);
//
//            if (isInternal) {
//                b.setBeneficiaryAccountId(Integer.parseInt(request.getParameter("internalAccountId")));
//            } else {
//                b.setBeneficiaryAccountNumber(request.getParameter("accountNumber"));
//                b.setBeneficiaryIfscCode(request.getParameter("ifsc"));
//            }
//
//            boolean success = service.addBeneficiary(b);
//            if (success) {
//                response.sendRedirect("ListBeneficiariesServlet?msg=added");
//            } else {
//                response.sendRedirect("ListBeneficiariesServlet?msg=failed");
//            }
//        } catch (Exception e) {
//            throw new ServletException(e);
//        }
		
		try {
	        // Read account ID from request parameter
	        String accountIdParam = request.getParameter("account_id");
	        if (accountIdParam == null) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing account_id parameter");
	            return;
	        }

	        int ownerAccountId = Integer.parseInt(accountIdParam);

	        Beneficiary b = new Beneficiary();
	        b.setOwnerAccountId(ownerAccountId);
	        b.setNickname(request.getParameter("nickname"));
	        b.setBeneficiaryName(request.getParameter("beneficiaryName"));

	        boolean isInternal = "internal".equals(request.getParameter("type"));
	        b.setInternal(isInternal);

	        if (isInternal) {
	            b.setBeneficiaryAccountId(Integer.parseInt(request.getParameter("internalAccountId")));
	        } else {
	            b.setBeneficiaryAccountNumber(request.getParameter("accountNumber"));
	            b.setBeneficiaryIfscCode(request.getParameter("ifsc"));
	        }

	        boolean success = service.addBeneficiary(b);
	        if (success) {
	            response.sendRedirect("ListBeneficiariesServlet?account_id=" + ownerAccountId + "&msg=added");
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
