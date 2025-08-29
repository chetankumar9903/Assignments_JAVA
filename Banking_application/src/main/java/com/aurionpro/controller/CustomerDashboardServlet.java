package com.aurionpro.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.Account;
import com.aurionpro.model.User;
import com.aurionpro.service.AccountService;

@WebServlet("/customer_dashboard")
public class CustomerDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private  AccountService accountService = new AccountService();
       
    
    public CustomerDashboardServlet() {
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

        try {
            List<Account> accounts = accountService.getCustomerAccounts(auth.getUserId());
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("customer/customer_dashboard.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/customer/customer_dashboard.jsp?error=" +
                    java.net.URLEncoder.encode(ex.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
        }
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
