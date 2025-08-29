<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${activeTab=='application'}">
    <div class="card shadow-sm p-4 mb-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h4 class=" mb-0">View All Customers <span class="badge bg-warning text-dark ms-2">${applications != null ? fn:length(applications):0}</h4>
            <a href="${pageContext.request.contextPath}/admin_dashboard?tab=application" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-clockwise me-1"></i> Refresh
            </a>
        </div>
        
        <!-- Filter Form -->
        <form method="post" action="${pageContext.request.contextPath}/admin_dashboard?tab=application" class="row g-3 align-items-end mb-4">
            <div class="col-md-2">
                <label for="status" class="form-label small">Application Status</label>
                <select name="status" id="status" class="form-select">
                    <option value="">All</option>
                    <option value="SUBMITTED" ${status=='SUBMITTED' ? 'selected' : ''}>Submitted</option>
                    <option value="APPROVED" ${status=='APPROVED' ? 'selected' : ''}>Approved</option>
                    <option value="REJECTED" ${status=='REJECTED' ? 'selected' : ''}>Rejected</option>
                </select>
            </div>
            <div class="col-md-2">
                <label for="date_from" class="form-label small">From</label>
                <input type="date" name="date_from" id="date_from" class="form-control" value="${date_from}">
            </div>
            <div class="col-md-2">
                <label for="date_to" class="form-label small">To</label>
                <input type="date" name="date_to" id="date_to" class="form-control" value="${date_to}">
            </div>
            <div class="col-md-2">
                <label for="account_type" class="form-label small">Account Type</label>
                <select name="account_type" id="account_type" class="form-select">
                    <option value="">All</option>
                    <option value="SAVINGS" ${account_type=='SAVINGS' ? 'selected' : ''}>Savings</option>
                    <option value="CURRENT" ${account_type=='CURRENT' ? 'selected' : ''}>Current</option>
                </select>
            </div>
            <div class="col-md-2 d-grid">
                <button type="submit" class="btn btn-primary"><i class="bi bi-funnel-fill me-1"></i> Filter</button>
            </div>
            <div class="col-md-2 d-grid">
                <a href="${pageContext.request.contextPath}/admin_dashboard?tab=application" class="btn btn-outline-secondary"><i class="bi bi-x-circle me-1"></i> Reset</a>
            </div>
        </form>

        <!-- Customers Table -->
        <div class="table-responsive">
            <table class="table table-hover table-bordered align-middle mb-0 text-center">
                <thead class="table-primary">
                    <tr>
                        <th>Full Name</th>
                        <th>Account Number</th>
                        <th>Bank Name</th>
                        <th>IFSC Code</th>
                        <th>Account Type</th>
                        <th>Balance</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty applications}">
                            <c:forEach var="cust" items="${applications}">
                                <tr>
                                    <td class="fw-bold">${cust.first_name} ${cust.last_name}</td>
                                    <td>${cust.account_number}</td>
                                    <td>${cust.bank_name}</td>
                                    <td>${cust.ifsc_code}</td>
                                    <td>${cust.account_type}</td>
                                    <td>${cust.balance}</td>
                                    <td>
                                        <span class="badge 
                                            ${cust.status == 'APPROVED' ? 'bg-success' : 
                                              (cust.status == 'REJECTED' ? 'bg-danger' : 'bg-warning text-dark')}">
                                            ${cust.status}
                                        </span>
                                    </td>
                                    <td>
                                        <a class="btn btn-outline-primary btn-sm"
                                           href="javascript:void(0);"
                                           onclick='openApprovedDetailsModal({
                                               userId: "${cust.user_id}",
                                               username: "${cust.username}",
                                               email: "${cust.email}",
                                               phone: "${cust.phone}",
                                               firstName: "${cust.first_name}",
                                               lastName: "${cust.last_name}",
                                               dob: "${cust.dob}",
                                               address: "${cust.address_line1} ${cust.address_line2}, ${cust.city}, ${cust.state}, ${cust.postal_code}",
                                               govtIdType: "${cust.govt_id_type}",
                                               govtIdValue: "${cust.govt_id_value}",
                                               kyc: "${cust.kyc_status}",
                                               applicationId: "${cust.application_id}",
                                               initialDeposit: "${cust.initial_deposit}",
                                               appCreated: "${cust.app_created}",
                                               status: "${cust.status}",
                                               reviewedBy: "${cust.reviewed_by}",
                                               reviewedByName: "${cust.reviewed_by_name}",
                                               reviewedAt: "${cust.reviewed_at}",
                                               remarks: "${cust.remarks}",
                                               account: {
                                                   accountId: "${cust.account_id}",
                                                   accountNumber: "${cust.account_number}",
                                                   accountType: "${cust.account_type}",
                                                   balance: "${cust.balance}",
                                                   accountStatus: "${cust.account_status}",
                                                   ifsc: "${cust.ifsc_code}",
                                                   bankName: "${cust.bank_name}",
                                                   branchName: "${cust.branch_name}",
                                                   branchCity: "${cust.branch_city}"
                                               }
                                           })'>
                                           <i class="bi bi-eye me-1"></i> Details
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8" class="text-center text-muted py-3">No approved customers found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
