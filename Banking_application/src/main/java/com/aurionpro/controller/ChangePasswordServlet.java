package com.aurionpro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.User;
import com.aurionpro.service.CustomerService;


@WebServlet("/changepassword")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private CustomerService customerService = new CustomerService();	
       
    
    public ChangePasswordServlet() {
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

	        int userId = auth.getUserId();

	        // ✅ Check KYC
	        boolean isApproved = customerService.isKycApproved(userId);
	        if (!isApproved) {
	            request.setAttribute("errorMsg", "You cannot change password until KYC is approved.");
	            request.getRequestDispatcher("customer/profile.jsp").forward(request, response);
	            return;
	        }

	        // ✅ Handle form submission only if parameters exist
	        String oldPassword = request.getParameter("oldPassword");
	        String newPassword = request.getParameter("newPassword");
	        String confirmPassword = request.getParameter("confirmPassword");

	        if (oldPassword == null || newPassword == null || confirmPassword == null) {
	            // Means direct GET request without form submission
	            request.getRequestDispatcher("customer/profile.jsp").forward(request, response);
	            return;
	        }

	        if (!newPassword.equals(confirmPassword)) {
	            request.setAttribute("errorMsg", "New Password and Confirm Password do not match!");
	            request.getRequestDispatcher("customer/profile.jsp").forward(request, response);
	            return;
	        }

	        boolean changed = customerService.changePassword(userId, oldPassword, newPassword);
	        if (changed) {
	        	
	            request.setAttribute("successMsg", "Password updated successfully!");
	        } else {
	        	
	            request.setAttribute("errorMsg", "Old password is incorrect!");
	        }

	        request.getRequestDispatcher("customer_profile").forward(request, response);
	    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		 
	    
	}

}
