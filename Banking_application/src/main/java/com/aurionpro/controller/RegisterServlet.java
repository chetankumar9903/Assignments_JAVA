package com.aurionpro.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aurionpro.model.Customer;
import com.aurionpro.model.User;
import com.aurionpro.service.UserService;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
       
    
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // if this is initial open of page, forward to register.jsp
        String username = request.getParameter("username");
        if (username == null) {
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // form submission (method=get per requirement)
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        // customer-specific fields
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String dobStr = request.getParameter("dob"); // yyyy-MM-dd
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String postal = request.getParameter("postal_code");
        String govtType = request.getParameter("govt_id_type");
        String govtValue = request.getParameter("govt_id_value");

        // basic validation
        if (username.isBlank() || password.length() < 4) {
            response.sendRedirect("register.jsp?error=Validation+failed:+username+and+password+requirements");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // plain text as requested
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role == null ? "CUSTOMER" : role.toUpperCase());

        Customer customer = new Customer();
        if (dobStr != null && !dobStr.isBlank()) {
            customer.setDob(Date.valueOf(dobStr));
        }
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddressLine1(address1);
        customer.setAddressLine2(address2);
        customer.setCity(city);
        customer.setState(state);
        customer.setPostalCode(postal);
        customer.setGovtIdType(govtType);
        customer.setGovtIdValue(govtValue);

        int userId = userService.registerUser(user, customer);
        if (userId > 0) {
            // success: show login with message
            response.sendRedirect("login.jsp?msg=Registration+successful.+Waiting+for+approval");
        } else {
            response.sendRedirect("register.jsp?error=Registration+failed");
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		 
	}

}
