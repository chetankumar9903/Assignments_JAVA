	package com.aurionpro.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.LeaveApplication;
import com.aurionpro.model.LeaveApplyResult;
import com.aurionpro.model.LeaveSummary;
import com.aurionpro.model.LeaveType;
import com.aurionpro.model.User;
import com.aurionpro.service.LeaveService;


@WebServlet("/LeaveController")
public class LeaveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LeaveService leaveService ;
       
    
    public LeaveController() {
        super();
        if(leaveService ==null) {
        	leaveService = new LeaveService(); 
        }
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		 if (session == null || session.getAttribute("userId") == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }
        User user = (User) session.getAttribute("user");  

        int empId = user.getId();
        
        List<LeaveSummary> summary = leaveService.getLeaveSummary(empId);
        request.setAttribute("leaveSummary", summary);
                
        
    
        List<LeaveType> leaveTypes = leaveService.getAllLeaveTypes();
        request.setAttribute("leaveTypes", leaveTypes);
       

        
    
        List<LeaveApplication> leaves = leaveService.getLeavesByEmployee(empId);
        for (LeaveApplication leave : leaves) {
            leave.setLeaveType(leaveService.getLeaveTypeName(leave.getTypeId()));
        }
        request.setAttribute("leaves", leaves);
        
        
        
        String dateFromParam = request.getParameter("date_from");
        String dateToParam = request.getParameter("date_to");
        String reasonParam = request.getParameter("reason");
        String typeIdParam = request.getParameter("leave_type");
        String overrideReason = request.getParameter("override_reason");// optional, from JS popup
     

        if (dateFromParam != null && dateToParam != null && reasonParam != null && typeIdParam != null) {
            try {
                LocalDate from = LocalDate.parse(dateFromParam);
                LocalDate to = LocalDate.parse(dateToParam);
                int typeId = Integer.parseInt(typeIdParam);
                int noOfDays = (int) ChronoUnit.DAYS.between(from, to) + 1;

                LeaveApplication leave = new LeaveApplication(empId, from, to, noOfDays, reasonParam, typeId, overrideReason, "Pending");
             
                if (leaveService.hasOverlappingLeave(empId, from, to)) {
                    request.setAttribute("error", "You already have a leave application covering these dates.");
                    request.getRequestDispatcher("employeeDashboard.jsp").forward(request, response);
                    return;
                }
                
                LeaveApplyResult result = leaveService.applyLeave(leave);

                if (result == LeaveApplyResult.YEARLY_EXCEEDED) {
                    request.setAttribute("error", "You cannot apply for more than yearly allowed leaves.");
                    request.getRequestDispatcher("employeeDashboard.jsp").forward(request, response);
                    return;
                }

                if (result == LeaveApplyResult.MONTHLY_EXCEEDED && (overrideReason == null || overrideReason.isEmpty())) {
                    // show popup for override reason
                    request.setAttribute("quotaExceeded", true);
                    request.setAttribute("pendingLeave", leave);
                    request.setAttribute("noOfDays", noOfDays);
                    request.setAttribute("selectedTypeId", typeId);

                    request.getRequestDispatcher("employeeDashboard.jsp").forward(request, response);
                    return;
                }

                if (result == LeaveApplyResult.SUCCESS) {
                    session.setAttribute("successMsg", "Leave applied successfully!");
                    response.sendRedirect("employeeDashboard?msg=success");
                    return;
                }


            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("employeeDashboard?msg=error");
                return;
            }
        }

        request.getRequestDispatcher("employeeDashboard.jsp").forward(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
