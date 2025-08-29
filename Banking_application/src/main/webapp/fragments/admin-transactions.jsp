<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="card shadow-sm  p-4 container">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4>All Transactions <span class="badge bg-info text-dark ms-2">${transactions != null ? fn:length(transactions):0}</span></h4>
        <a href="${pageContext.request.contextPath}/admin_dashboard?tab=transactions" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-clockwise me-1"></i> Refresh
        </a>
    </div>

    <!-- Filters Card -->
    <div class="card shadow-sm mb-4 p-3">
        <form method="post" class="row g-3 align-items-end">
            <input type="hidden" name="tab" value="transactions" />
            <div class="col-md-2">
                <label class="form-label small">From Date</label>
                <input type="date" class="form-control" name="fromDate" value="${fromDate}">
            </div>
            <div class="col-md-2">
                <label class="form-label small">To Date</label>
                <input type="date" class="form-control" name="toDate" value="${toDate}">
            </div>
            <div class="col-md-2">
                <label class="form-label small">Type</label>
                <select name="type" class="form-select">
                    <option value="">All Types</option>
                    <option value="TRANSFER" ${type=='TRANSFER' ? 'selected' : ''}>Transfer</option>
                    <option value="DEPOSIT" ${type=='DEPOSIT' ? 'selected' : ''}>Deposit</option>
                    <option value="WITHDRAWAL" ${type=='WITHDRAWAL' ? 'selected' : ''}>Withdrawal</option>
                </select>
            </div>
            <div class="col-md-2">
                <label class="form-label small">Status</label>
                <select name="status" class="form-select">
                    <option value="">All Status</option>
                    <option value="SUCCESS" ${status=='SUCCESS' ? 'selected' : ''}>Success</option>
                    <option value="PENDING" ${status=='PENDING' ? 'selected' : ''}>Pending</option>
                    <option value="FAILED" ${status=='FAILED' ? 'selected' : ''}>Failed</option>
                    <option value="REVERSED" ${status=='REVERSED' ? 'selected' : ''}>Reversed</option>
                </select>
            </div>
            <div class="col-md-2">
                <label class="form-label small">Account Number</label>
                <input type="text" name="accountNumber" class="form-control" value="${fn:escapeXml(accountNumber)}" placeholder="Account #">
            </div>
            <div class="col-md-2">
                <label class="form-label small">Customer Name</label>
                <input type="text" name="customerName" class="form-control" value="${fn:escapeXml(customerName)}" placeholder="Customer Name">
            </div>
            <div class="col-md-12 d-flex justify-content-start gap-2">
                <button class="btn btn-primary"><i class="bi bi-funnel-fill me-1"></i> Apply Filters</button>
                <a class="btn btn-outline-secondary" href="?tab=transactions"><i class="bi bi-x-circle me-1"></i> Reset</a>
            </div>
        </form>
    </div>

    <!-- Transactions Table -->
    <c:if test="${not empty transactions}">
        <div class="card shadow-sm">
            <div class="table-responsive">
                <table class="table table-hover table-bordered align-middle text-center mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Date</th>
                            <th>Type</th>
                            <th>Status</th>
                            <th>Amount</th>
                            <th>From Account</th>
                            <th>To Account</th>
                            <th>From Customer</th>
                            <th>To Customer</th>
                            <th>Initiated By</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="txn" items="${transactions}">
                            <tr>
                                <td class="fw-bold">${txn.txnId}</td>
                                <td style="min-width: 120px; white-space: nowrap;" ><fmt:formatDate value="${txn.txnDate}" pattern="dd-MMM-yyyy" /></td>
                                <td>
                                    <span class="badge bg-primary">${txn.type}</span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${txn.status=='SUCCESS'}">
                                            <span class="badge bg-success">${txn.status}</span>
                                        </c:when>
                                        <c:when test="${txn.status=='PENDING'}">
                                            <span class="badge bg-warning text-dark">${txn.status}</span>
                                        </c:when>
                                        <c:when test="${txn.status=='FAILED'}">
                                            <span class="badge bg-danger">${txn.status}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">${txn.status}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${txn.amount}</td>
                                <td>${txn.fromAccountNumber}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty txn.toAccountNumber}">${txn.toAccountNumber}</c:when>
                                        <c:otherwise>${txn.toExternalAccountNumber} (${txn.toExternalIfsc})</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${txn.fromCustomerName}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty txn.toCustomerName}">${txn.toCustomerName}</c:when>
                                        <c:otherwise>External</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${txn.initiatedBy}</td>
                                <td>${txn.description}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>

    <c:if test="${empty transactions}">
        <div class="alert alert-info shadow-sm">
            <i class="bi bi-info-circle me-1"></i> No transactions found.
        </div>
    </c:if>

</div>
