package com.aurionpro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.User;
import com.aurionpro.service.UserService;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private UserService userService = new UserService();
       
  
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Show login page if no params
        String username = request.getParameter("username");
        if (username == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        String password = request.getParameter("password");
        String role = request.getParameter("role");

//        if (username.isBlank() || password.isBlank()) {
//            response.sendRedirect("login.jsp?error=Missing+username+or+password");
//            return;
//        }
        
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            response.sendRedirect("login.jsp?error=Missing+username+or+password");
            return;
        }

        User user = userService.login(username, password, role);
        if (user != null) {
        	
            HttpSession session = request.getSession(true);
            session.setAttribute("authUser", user);

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect("admin_dashboard"); // map to admin servlet/page later
            } else {
                response.sendRedirect("customer_dashboard"); // map to customer servlet/page later
            }
        } else {
            response.sendRedirect("login.jsp?error=Invalid+credentials+or+account+not+active");
        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
	}

}
