package com.aurionpro.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aurionpro.model.Beneficiary;
import com.aurionpro.service.BeneficiaryService;


@WebServlet("/ListBeneficiariesServlet")
public class ListBeneficiariesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private BeneficiaryService service = new BeneficiaryService();
       
    
    public ListBeneficiariesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		 try {
//	            int ownerAccountId = (int) request.getSession().getAttribute("accountId");
//	            List<Beneficiary> list = service.getBeneficiariesByOwner(ownerAccountId);
//	            request.setAttribute("beneficiaries", list);
//	            RequestDispatcher rd = request.getRequestDispatcher("beneficiary.jsp");
//	            rd.forward(request, response);
//	        } catch (Exception e) {
//	            throw new ServletException(e);
//	        }
		 try {
		        // Read account_id from request parameter
		        String accountIdParam = request.getParameter("account_id");
		        if (accountIdParam == null) {
		            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing account_id parameter");
		            return;
		        }

		        int ownerAccountId = Integer.parseInt(accountIdParam);

		        List<Beneficiary> list = service.getBeneficiariesByOwner(ownerAccountId);
		        request.setAttribute("beneficiaries", list);

		        RequestDispatcher rd = request.getRequestDispatcher("customer/beneficiary.jsp");
		        rd.forward(request, response);

		    } catch (NumberFormatException e) {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid account_id");
		    } catch (Exception e) {
		        throw new ServletException(e);
		    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
