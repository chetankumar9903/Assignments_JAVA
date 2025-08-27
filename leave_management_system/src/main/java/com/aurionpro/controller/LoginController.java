package com.aurionpro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.User;
import com.aurionpro.service.UserService;


@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
       
    
    public LoginController() {
        super();	
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        String role = request.getParameter("role");

	        String dbRole = userService.validateUser(username, password, role);

	        if (dbRole != null) {
	            // create session
	            HttpSession session = request.getSession();
	            session.setAttribute("username", username);
	            session.setAttribute("role", dbRole);
	            
	            User user = userService.getUserByUsername(username);
	            
	            session.setAttribute("userId", user.getId()); // optional
	               
	       /**     
	            int userId = userService.getUserIdByUsername(username);  
	            session.setAttribute("userId", userId);
	            
	            String department = userService.getUserDepartment(username); // optional if dept in DB
	            String fullname = userService.getUserFullname(username);

	            User user = new User(userId, username, dbRole, department,fullname);
	            **/

	            session.setAttribute("user", user);

	            Cookie ck = new Cookie("username", username);
	            ck.setMaxAge(60 * 60 * 24); // 1 day
	            response.addCookie(ck);
	           
	            session.setAttribute("successMsg", "Login successful! Welcome " + user.getFullname());

	           
	            if ("ADMIN".equalsIgnoreCase(dbRole)) {
//	                response.sendRedirect("adminDashboard.jsp");
	            	response.sendRedirect("AdminLeaveController");
	            } else {
//	                response.sendRedirect("employeeDashboard.jsp");
	            	response.sendRedirect("employeeDashboard");
	            }

	        } else {
	           
	            HttpSession session = request.getSession();
	            session.setAttribute("errorMsg", "Invalid details!");
	            response.sendRedirect("login.jsp");
	        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
