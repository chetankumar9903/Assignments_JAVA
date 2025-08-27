package com.aurionpro.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.LeaveApplication;
import com.aurionpro.model.LeaveSummary;
import com.aurionpro.model.LeaveType;
import com.aurionpro.model.User;
import com.aurionpro.service.LeaveService;


@WebServlet("/employeeDashboard")
public class EmployeeDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LeaveService leaveService ;
       
   
    public EmployeeDashboardController() {
        super();
        // TODO Auto-generated constructor stub
        if(leaveService ==null) {
        	leaveService = new LeaveService(); 
        }
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || !"employee".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<LeaveSummary> summary = leaveService.getLeaveSummary(user.getId());
        request.setAttribute("leaveSummary", summary);
        
        
        List<LeaveType> leaveTypes = leaveService.getAllLeaveTypes();
        request.setAttribute("leaveTypes", leaveTypes);
        
        
        List<LeaveApplication> myLeaves = leaveService.getLeavesByEmployee(user.getId());

        request.setAttribute("leaves", myLeaves);
        RequestDispatcher rd = request.getRequestDispatcher("employeeDashboard.jsp");
        rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
