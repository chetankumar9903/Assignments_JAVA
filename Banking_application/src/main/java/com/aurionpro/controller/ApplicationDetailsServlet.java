package com.aurionpro.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.User;
import com.aurionpro.service.AccountApplicationService;


@WebServlet("/ApplicationDetailsServlet")
public class ApplicationDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountApplicationService accService = new AccountApplicationService();
       
    
    public ApplicationDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("authUser") == null) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please+login+as+admin");
	            return;
	        }

	        User authUser = (User) session.getAttribute("authUser");
	        if (!"ADMIN".equalsIgnoreCase(authUser.getRole())) {
	            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
	            return;
	        }

	        String appIdStr = request.getParameter("application_id");
	        if (appIdStr == null) {
	            response.sendRedirect(request.getContextPath() + "/admin_dashboard?tab=pending");
	            return;
	        }

	        int applicationId = Integer.parseInt(appIdStr);

	        // Check if an action is requested (approve/reject)
	        String action = request.getParameter("action");
	        if (action != null) {
	            String msg = "";
	            String type = "success";
	            try {
	                if ("APPROVE".equalsIgnoreCase(action)) {
	                    accService.approveApplication(applicationId, authUser.getUserId());
	                    msg = "Application approved successfully.";
	                } else if ("REJECT".equalsIgnoreCase(action)) {
	                    String remarks = request.getParameter("remarks");
	                    accService.rejectApplication(applicationId, authUser.getUserId(), remarks);
	                    msg = "Application rejected successfully.";
	                }
	            } catch (Exception ex) {
	                msg = URLEncoder.encode(ex.getMessage(), "UTF-8");
	                type = "danger";
	            }
	            response.sendRedirect(request.getContextPath() + "/admin_dashboard?tab=pending&msg=" + msg + "&type=" + type);
	            return;
	        }

	        // Get application + customer details
	        Map<String, Object> appDetails = accService.getApplicationDetails(applicationId);
	        if (appDetails == null) {
	            response.sendRedirect(request.getContextPath() + "/admin_dashboard?tab=pending&msg=Application+not+found&type=danger");
	            return;
	        }
	        //System.out.println(appDetails);

	        request.setAttribute("appDetails", appDetails);
	        request.getRequestDispatcher("application_details.jsp").forward(request, response);
	    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
