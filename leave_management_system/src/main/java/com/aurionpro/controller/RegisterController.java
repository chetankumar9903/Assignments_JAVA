package com.aurionpro.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aurionpro.service.UserService;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
       
    
    public RegisterController() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String fullName = request.getParameter("full_name");
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        String dept = request.getParameter("dept");
	        String role = request.getParameter("role");

	        boolean success = userService.registerUser(fullName, username, password, dept, role);

	        if (success) {
	            response.sendRedirect("login.jsp?msg=registered");
//	        	 request.setAttribute("successMessage", "Registration successful! Please login "+ username);
//	        	    RequestDispatcher rd = request.getRequestDispatcher("login.jsp?msg=registered");
//	        	    rd.forward(request, response);
	        } else {
//	        	 request.setAttribute("errorMessage", "Username already exists! Please choose another one.");
//	        	    RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
//	        	    rd.forward(request, response);
        	    response.sendRedirect("register.jsp?error=exists");	        	    
	        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
