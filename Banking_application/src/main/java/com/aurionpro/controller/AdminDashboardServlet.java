package com.aurionpro.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.Transaction;
import com.aurionpro.model.TransactionFilter;
import com.aurionpro.model.User;
import com.aurionpro.model.UserCustomer;
import com.aurionpro.service.AccountApplicationService;
import com.aurionpro.service.TransactionService;
import com.aurionpro.service.UserService;


@WebServlet("/admin_dashboard")
public class AdminDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountApplicationService accService =  new AccountApplicationService();
       private UserService userService = new UserService();
       private TransactionService transservice = new TransactionService();
   
    public AdminDashboardServlet() {
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
        
       
        String tab = request.getParameter("tab");
        if (tab == null) tab = "inactive"; // default tab

        // Handle activation of a customer
        String activateUserId = request.getParameter("activate_user_id");
        if (activateUserId != null) {
            int userId = Integer.parseInt(activateUserId);
            boolean success = userService.verifyKycAndActivateCustomer(userId);
            String msg = success ? "Customer activated & KYC verified" : "Activation failed";
            String type = success ? "success" : "danger";
            response.sendRedirect(request.getContextPath() + "/admin_dashboard?tab=inactive"
                + "&msg=" + java.net.URLEncoder.encode(msg, "UTF-8")
                + "&type=" + type);
            return;
            //response.sendRedirect(request.getContextPath() + "/admin_dashboard?tab=inactive&msg=" + java.net.URLEncoder.encode(msg, "UTF-8"));
           // return;
        }

        // Handle rejection of a customer
        String rejectUserId = request.getParameter("reject_user_id");
        String remarks = request.getParameter("remarks");
        if (rejectUserId != null) {
            int userId = Integer.parseInt(rejectUserId);
            boolean success = userService.rejectCustomer(userId, remarks);
            String msg = success ? "Customer rejected successfully" : "Rejection failed";
            String type = success ? "success" : "danger";
            response.sendRedirect(request.getContextPath() + "/admin_dashboard?tab=inactive"
                + "&msg=" + java.net.URLEncoder.encode(msg, "UTF-8")
                + "&type=" + type);
            return;
            //response.sendRedirect(request.getContextPath() + "/admin_dashboard?tab=inactive&msg=" + java.net.URLEncoder.encode(msg, "UTF-8"));
            //return;
        }
        
        // Handle Inactive Customers Tab
        if ("inactive".equalsIgnoreCase(tab)) {
            List<UserCustomer> inactiveCustomers = userService.getInactiveAndKycPendingCustomers();
            request.setAttribute("inactiveCustomers", inactiveCustomers);
        }

        // Handle Pending Applications Tab
        if ("pending".equalsIgnoreCase(tab)) {
            String q = request.getParameter("q");
            String dateFrom = request.getParameter("date_from");
            String dateTo = request.getParameter("date_to");
            Integer limit = 50, offset = 0;
            try {
                String limitStr = request.getParameter("limit");
                String offsetStr = request.getParameter("offset");
                if (limitStr != null && !limitStr.isBlank()) limit = Integer.parseInt(limitStr);
                if (offsetStr != null && !offsetStr.isBlank()) offset = Integer.parseInt(offsetStr);
            } catch (NumberFormatException ignore) {}
//            if (limit == null) limit = 50;
//            if (offset == null) offset = 0;


            List<Map<String,Object>> pendingApps = accService.listPendingApplications(q, dateFrom, dateTo, limit, offset);
            int pendingCount = accService.countPendingApplications();

            request.setAttribute("pendingApps", pendingApps);
            request.setAttribute("pendingCount", pendingCount);
            request.setAttribute("q", q);
            request.setAttribute("date_from", dateFrom);
            request.setAttribute("date_to", dateTo);
            request.setAttribute("limit", limit);
            request.setAttribute("offset", offset);
        }
        
        
     // Handle Transactions Tab
        if ("transactions".equalsIgnoreCase(tab)) {
            TransactionFilter filter = new TransactionFilter();

            try {
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");
                String type = request.getParameter("type");
                String status = request.getParameter("status");
                String accountNumber = request.getParameter("accountNumber");
                String customerName = request.getParameter("customerName");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (fromDate != null && !fromDate.isEmpty()) filter.setFromDate(sdf.parse(fromDate));
                if (toDate != null && !toDate.isEmpty()) filter.setToDate(sdf.parse(toDate));
                if (type != null && !type.isEmpty()) filter.setTypes(Collections.singletonList(type));
                if (status != null && !status.isEmpty()) filter.setStatuses(Collections.singletonList(status));
                if (accountNumber != null && !accountNumber.isEmpty()) filter.setAccountNumber(accountNumber);
                if (customerName != null && !customerName.isEmpty()) filter.setCustomerName(customerName);

                List<Transaction> transactions = transservice.getAllTransactions(filter);
                request.setAttribute("transactions", transactions);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error loading transactions: " + e.getMessage());
            }
        }

        
        
        
     // Fetch Approved Applications Tab
        if ("application".equalsIgnoreCase(tab)) {
            // fetch approved applications using service
            List<Map<String, Object>> approvedCustomers = accService.getApprovedApplications();
            request.setAttribute("approvedCustomers", approvedCustomers);
//            System.out.println(approvedCustomers);
        }
       
        String status = request.getParameter("status");
        String dateFrom = request.getParameter("date_from");
        String dateTo = request.getParameter("date_to");
        String accountType = request.getParameter("account_type");
//        String kycStatus = request.getParameter("kyc_status");

        List<Map<String, Object>> applications = accService.getApplicationsByStatusAndFilters(status, dateFrom, dateTo, accountType);

        System.out.println(applications);
        request.setAttribute("applications", applications);
        request.setAttribute("status", status);
        request.setAttribute("account_type", accountType);
//        request.setAttribute("kyc_status", kycStatus);
        request.setAttribute("date_from", dateFrom);
        request.setAttribute("date_to", dateTo);
     
   
        
        /**
        String status = request.getParameter("status");
        String dateFrom = request.getParameter("date_from");
        String dateTo = request.getParameter("date_to");
        String accountType = request.getParameter("account_type");
        // String kycStatus = request.getParameter("kyc_status");

        // Use the new aggregated method
        List<Map<String, Object>> usersWithAccounts = accService.getApplicationsAggregatedByUser(
                status, dateFrom, dateTo, accountType);  // kycStatus can be passed if needed

        //System.out.println(usersWithAccounts);

        // Set attributes for JSP
        request.setAttribute("applications", usersWithAccounts);
        request.setAttribute("status", status);
        request.setAttribute("account_type", accountType);
        // request.setAttribute("kyc_status", kycStatus);
        request.setAttribute("date_from", dateFrom);
        request.setAttribute("date_to", dateTo);
**/



        
        


        request.setAttribute("activeTab", tab);
        request.getRequestDispatcher("/admin_dashboard.jsp").forward(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
