package com.aurionpro.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.LeaveApplication;
import com.aurionpro.model.LeaveType;
import com.aurionpro.model.User;
import com.aurionpro.service.LeaveService;

@WebServlet("/AdminLeaveController")
public class AdminLeaveController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LeaveService leaveService = new LeaveService();

    public AdminLeaveController() { super(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }


        String action = request.getParameter("action");

        // Approve/Reject action
        if ("approve".equals(action) || "reject".equals(action)) {
            int leaveId = Integer.parseInt(request.getParameter("leaveId"));
            String status = "approve".equals(action) ? "Approved" : "Rejected";
            leaveService.updateLeaveStatus(leaveId, status);
            request.getSession().setAttribute("successMsg", "Leave " + status + " successfully!");
        }

        LocalDateTime now = LocalDateTime.now();
        List<LeaveType> allLeaveTypes = leaveService.getAllLeaveTypes();

        // Fetch all leave lists
        List<LeaveApplication> pendingLeaves = leaveService.getLeavesByStatus("Pending");
        List<LeaveApplication> approvedLeaves = leaveService.getLeavesByStatus("Approved");
        List<LeaveApplication> rejectedLeaves = leaveService.getLeavesByStatus("Rejected");
        List<LeaveApplication> sanctionedLeaves = leaveService.getSanctionedLeaves();

        List<LeaveApplication> limitExceeded = new ArrayList<>();

        // Process each list
        processLeaves(pendingLeaves, allLeaveTypes, now, limitExceeded, true);
        processLeaves(approvedLeaves, allLeaveTypes, now, limitExceeded, false);
        processLeaves(rejectedLeaves, allLeaveTypes, now, limitExceeded, false);
        processLeaves(sanctionedLeaves, allLeaveTypes, now, limitExceeded, false);

        // Set attributes for JSP
        request.setAttribute("pendingLeaves", pendingLeaves);
        request.setAttribute("approvedLeaves", approvedLeaves);
        request.setAttribute("rejectedLeaves", rejectedLeaves);
        request.setAttribute("sanctionedLeaves", sanctionedLeaves);
        request.setAttribute("limitExceededLeaves", limitExceeded);

        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }

    private void processLeaves(List<LeaveApplication> leaves, List<LeaveType> allLeaveTypes,
                               LocalDateTime now, List<LeaveApplication> limitExceeded, boolean checkNew) {

        for (LeaveApplication leave : leaves) {

            // Mark new leaves (for pending list)
            if (checkNew && leave.getAppliedOn() != null) {
                long minutesDiff = Duration.between(leave.getAppliedOn(), now).toMinutes();
                leave.setNewLeave(minutesDiff <= 10);
            }

            // Set leave type name
            LeaveType leaveType = allLeaveTypes.stream()
                    .filter(lt -> lt.getTypeId() == leave.getTypeId())
                    .findFirst()
                    .orElse(null);

            if (leaveType != null) {
                leave.setLeaveTypeName(leaveType.getTypeName());
            }

            // Check if leave exceeded limit (override reason)
            boolean exceeded = leave.getOverrideReason() != null && !leave.getOverrideReason().trim().isEmpty();
            leave.setExceedingLimit(exceeded);

            if (exceeded && !limitExceeded.contains(leave)) {
                limitExceeded.add(leave);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
