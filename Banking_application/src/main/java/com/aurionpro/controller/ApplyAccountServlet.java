package com.aurionpro.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.User;
import com.aurionpro.service.AccountApplicationService;


@WebServlet("/customer/applyAccount")
public class ApplyAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private  AccountApplicationService service = new AccountApplicationService();
       
    
    public ApplyAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("authUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please+login");
            return;
        }
        User auth = (User) session.getAttribute("authUser");
        if (!"CUSTOMER".equalsIgnoreCase(auth.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Customers only.");
            return;
        }

        String accountType = request.getParameter("account_type"); // SAVINGS/CURRENT
        String initStr = request.getParameter("initial_deposit");

        if (accountType == null || accountType.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/customer/apply_account.jsp?error=Select+account+type");
            return;
        }

        try {
            BigDecimal init = (initStr == null || initStr.isBlank()) ? BigDecimal.ZERO : new BigDecimal(initStr);
            if (init.compareTo(BigDecimal.ZERO) < 0) {
                response.sendRedirect(request.getContextPath() + "/customer/apply_account.jsp?error=Initial+deposit+cannot+be+negative");
                return;
            }
            service.submitApplication(auth.getUserId(), accountType, init);

            response.sendRedirect(request.getContextPath() + "/customer_dashboard?msg=Application+submitted+for+" + accountType);
        } catch (NumberFormatException nfe) {
            response.sendRedirect(request.getContextPath() + "/customer/apply_account.jsp?error=Invalid+amount");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/customer/apply_account.jsp?error=" +
                java.net.URLEncoder.encode(ex.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
