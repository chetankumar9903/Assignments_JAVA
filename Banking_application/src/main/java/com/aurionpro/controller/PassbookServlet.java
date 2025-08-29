package com.aurionpro.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.Account;
import com.aurionpro.model.PassbookEntry;
import com.aurionpro.model.User;
import com.aurionpro.service.AccountService;
import com.aurionpro.service.PassbookService;


@WebServlet("/customer_passbook")
public class PassbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private  AccountService accountService = new AccountService();
    private  PassbookService passbookService = new PassbookService();
       
    
    public PassbookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("authUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please+login");
            return;
        }
        User auth = (User) session.getAttribute("authUser");
        if (!"CUSTOMER".equalsIgnoreCase(auth.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Customers only.");
            return;
        }

        String accountIdStr = request.getParameter("account_id");
        if (accountIdStr == null || accountIdStr.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/customer_dashboard?error=Select+an+account");
            return;
        }

        try {
            int accountId = Integer.parseInt(accountIdStr);
            System.out.println("Passbook debug");
            Account account = accountService.getAccountForCustomer(accountId, auth.getUserId());
//            System.out.println(account);
           
            System.out.println(accountId +" "+ auth.getUserId());
            if (account == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid account.");
                return;
            }

            String type = request.getParameter("type");     // TRANSFER/DEPOSIT/WITHDRAWAL
            String status = request.getParameter("status"); // SUCCESS/FAILED/PENDING/REVERSED
            String dateFrom = request.getParameter("date_from");
            String dateTo = request.getParameter("date_to");
            Integer limit = null, offset = null;
            try {
                String l = request.getParameter("limit");
                String o = request.getParameter("offset");
                if (l != null && !l.isBlank()) limit = Integer.parseInt(l);
                if (o != null && !o.isBlank()) offset = Integer.parseInt(o);
            } catch (NumberFormatException ignore) {}

            List<PassbookEntry> entries = passbookService.getPassbook(accountId, type, status, dateFrom, dateTo, limit, offset,request);
            System.out.println(entries);

            request.setAttribute("account", account);
            request.setAttribute("passbook", entries);
            request.setAttribute("type", type);
            request.setAttribute("status", status);
            request.setAttribute("date_from", dateFrom);
            request.setAttribute("date_to", dateTo);
            request.setAttribute("limit", limit == null ? 2 : limit);
            request.setAttribute("offset", offset == null ? 0 : offset);
            request.setAttribute("hasNext", request.getAttribute("hasNext"));

            request.getRequestDispatcher("/customer/passbook.jsp").forward(request, response);

        } catch (NumberFormatException nfe) {
            response.sendRedirect(request.getContextPath() + "/customer_dashboard?error=Invalid+account");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/customer/passbook.jsp?error=" +
                java.net.URLEncoder.encode(ex.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
        }
        
        /**
		
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("authUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please+login");
            return;
        }
        User auth = (User) session.getAttribute("authUser");
        if (!"CUSTOMER".equalsIgnoreCase(auth.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Customers only.");
            return;
        }

        String accountIdStr = request.getParameter("account_id");
        if (accountIdStr == null || accountIdStr.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/customer_dashboard?error=Select+an+account");
            return;
        }

        try {
        	 int accountId = Integer.parseInt(accountIdStr);
        	 System.out.println("Passbook debug");
             Account account = accountService.getAccountForCustomer(accountId, auth.getUserId());
//             System.out.println(account);
            
             System.out.println(accountId +" "+ auth.getUserId());
             if (account == null) {
                 response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid account.");
                 return;
             }


	        // Read filters from query params (optional)
	        String type = request.getParameter("type");
	        String status = request.getParameter("status");
	        String search = request.getParameter("search");

	        Date startDate = null, endDate = null;
	        Double minAmount = null, maxAmount = null;

	        try {
	            if (request.getParameter("startDate") != null && !request.getParameter("startDate").isEmpty()) {
	                startDate = java.sql.Date.valueOf(request.getParameter("startDate"));
	            }
	            if (request.getParameter("endDate") != null && !request.getParameter("endDate").isEmpty()) {
	                endDate = java.sql.Date.valueOf(request.getParameter("endDate"));
	            }
	            if (request.getParameter("minAmount") != null && !request.getParameter("minAmount").isEmpty()) {
	                minAmount = Double.parseDouble(request.getParameter("minAmount"));
	            }
	            if (request.getParameter("maxAmount") != null && !request.getParameter("maxAmount").isEmpty()) {
	                maxAmount = Double.parseDouble(request.getParameter("maxAmount"));
	            }
	        } catch (NumberFormatException ignore){
	            // ignore parsing errors
	        }

	       
	    List<PassbookEntry> entries = passbookService.listPassbookwithoutlimit( accountId, startDate, endDate, type,
	    		status, minAmount, maxAmount, search);
	    System.out.println(entries);
	    
	            request.setAttribute("entries", entries);
	            request.setAttribute("account", account);
	            request.setAttribute("type", type);
	            request.setAttribute("status", status);
	            request.setAttribute("date_from", startDate);
	            request.setAttribute("date_to", endDate);
	            
	            request.getRequestDispatcher("/customer/passbook.jsp").forward(request, response);

	        } catch (NumberFormatException nfe) {
	            response.sendRedirect(request.getContextPath() + "/customer_dashboard?error=Invalid+account");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            response.sendRedirect(request.getContextPath() + "/customer/passbook.jsp?error=" +
	                java.net.URLEncoder.encode(ex.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
	        }	
        **/
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
