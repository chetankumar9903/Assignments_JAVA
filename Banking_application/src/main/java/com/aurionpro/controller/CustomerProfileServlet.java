package com.aurionpro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.CustomerProfile;
import com.aurionpro.model.User;
import com.aurionpro.service.CustomerService;


@WebServlet("/customer_profile")
public class CustomerProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 private CustomerService customerService = new CustomerService();
   
    public CustomerProfileServlet() {
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

	        // check if form submission happened (detect by presence of parameter)
	        if (request.getParameter("update") != null) {
	            // collect form data and update
	            CustomerProfile profile = new CustomerProfile();
	            profile.setUserId(userId);
	            profile.setFirstName(request.getParameter("first_name"));
	            profile.setLastName(request.getParameter("last_name"));
	            profile.setDob(request.getParameter("dob"));
	            profile.setEmail(request.getParameter("email"));
	            profile.setPhone(request.getParameter("phone"));
	            profile.setAddressLine1(request.getParameter("address1"));
	            profile.setAddressLine2(request.getParameter("address2"));
	            profile.setCity(request.getParameter("city"));
	            profile.setState(request.getParameter("state"));
	            profile.setPostalCode(request.getParameter("postal"));

	            boolean updated = customerService.updateProfile(profile);
	            if (updated) {
	                response.sendRedirect(request.getContextPath() + "/customer_profile?msg=Profile+updated+successfully");
	                return;
	            } else {
	                response.sendRedirect(request.getContextPath() + "/customer_profile?error=Update+failed");
	                return;
	            }
	        }

	        // If not updating → just fetch and display profile
	        CustomerProfile profile = customerService.getProfileByUserId(userId);
	        request.setAttribute("profile", profile);
	        request.getRequestDispatcher("/customer/profile.jsp").forward(request, response);
	    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
