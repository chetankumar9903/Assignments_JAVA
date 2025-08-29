package com.aurionpro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.User;
import com.aurionpro.service.AccountApplicationService;


@WebServlet("/admin/reject")
public class RejectApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private AccountApplicationService accService =  new AccountApplicationService();
   
    public RejectApplicationServlet() {
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
	        if (authUser == null || !"ADMIN".equalsIgnoreCase(authUser.getRole())) {
	            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
	            return;
	        }

	        String appIdStr = request.getParameter("application_id");
	        String remarks = request.getParameter("remarks");
	        if (remarks == null) remarks = "";

	        if (appIdStr == null || appIdStr.isBlank()) {
	            response.sendRedirect(request.getContextPath() + "/admin_dashboard?error=Missing+application_id");
	            return;
	        }

	        try {
	            int appId = Integer.parseInt(appIdStr);
	            accService.rejectApplication(appId, authUser.getUserId(), remarks);
	            response.sendRedirect(request.getContextPath() + "/admin_dashboard?msg=Rejected+application+" + appId);
	        } catch (NumberFormatException nfe) {
	            response.sendRedirect(request.getContextPath() + "/admin_dashboard?error=Invalid+application_id");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            response.sendRedirect(request.getContextPath() + "/admin_dashboard?error=" + java.net.URLEncoder.encode(ex.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
	        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
