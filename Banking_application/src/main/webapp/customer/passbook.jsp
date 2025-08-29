<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Passbook</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-light">
<div class="container my-5">

  <!-- Header Card -->
  <div class="card shadow-sm border-0 mb-4">
    <div class="card-body d-flex flex-wrap justify-content-between align-items-center">
      <div>
        <h4 class="mb-1 text-primary">Passbook - Account </h4>
        <p class="mb-0 text-muted">Account Number: <strong>${account.accountNumber}</strong></p>
      </div>
      <div class="text-end">
        <h5 class="mb-1 text-center">Balance</h5>
        <h4 class="text-success fw-bold">₹ ${account.balance}</h4>
      </div>
      <div>
        <a class="btn btn-outline-secondary mt-2 mt-sm-0" href="${pageContext.request.contextPath}/customer_dashboard">← Back to Dashboard</a>
      </div>
    </div>
  </div>

  <!-- Filter Card -->
  <div class="card shadow-sm border-0 mb-4">
    <div class="card-body">
      <h5 class="mb-3 text-secondary">Filter Transactions</h5>
      <form class="row g-3" method="get" action="${pageContext.request.contextPath}/customer_passbook">
        <input type="hidden" name="account_id" value="${account.accountId}"/>

        <div class="col-md-3">
          <label class="form-label">Type</label>
          <select class="form-select" name="type">
            <option value="">All</option>
            <option value="TRANSFER"  <c:if test="${type=='TRANSFER'}">selected</c:if>>Transfer</option>
            <option value="DEPOSIT"   <c:if test="${type=='DEPOSIT'}">selected</c:if>>Deposit</option>
            <option value="WITHDRAWAL" <c:if test="${type=='WITHDRAWAL'}">selected</c:if>>Withdrawal</option>
          </select>
        </div>

        <div class="col-md-3">
          <label class="form-label">Status</label>
          <select class="form-select" name="status">
            <option value="">All</option>
            <option value="SUCCESS"  <c:if test="${status=='SUCCESS'}">selected</c:if>>Success</option>
            <option value="FAILED"   <c:if test="${status=='FAILED'}">selected</c:if>>Failed</option>
            <option value="PENDING"  <c:if test="${status=='PENDING'}">selected</c:if>>Pending</option>
            <option value="REVERSED" <c:if test="${status=='REVERSED'}">selected</c:if>>Reversed</option>
          </select>
        </div>

        <div class="col-md-2">
          <label class="form-label">From Date</label>
          <input type="date" class="form-control" name="date_from" value="${date_from}"/>
        </div>

        <div class="col-md-2">
          <label class="form-label">To Date</label>
          <input type="date" class="form-control" name="date_to" value="${date_to}"/>
        </div>

        <div class="col-12 d-flex gap-2">
          <button type="submit" class="btn btn-primary">Apply Filters</button>
          <a href="${pageContext.request.contextPath}/customer_passbook?account_id=${account.accountId}" class="btn btn-outline-secondary">Reset</a>
        </div>
      </form>
    </div>
  </div>

  <!-- Transactions Table -->
  <div class="card shadow-sm border-0">
    <div class="card-body">
      <h5 class="mb-3 text-secondary">Transaction History</h5>
      <div class="table-responsive">
        <table class="table table-hover align-middle">
          <thead class="table-primary">
            <tr>
              <th>Date</th>
              <th>Type</th>
              <th>From</th>
              <th>To</th>
              <th>Narration</th>
              <th>Debit</th>
              <th>Credit</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${not empty passbook}">
                <c:forEach var="r" items="${passbook}">
                  <tr>
                    <td>${r.txnDate}</td>
                    <td><span class="badge bg-info">${r.type}</span></td>
                     <td>
          <c:choose>
            <c:when test="${not empty r.fromAccountId}">Acc# ${r.fromAccountId}</c:when>
            <c:otherwise>-</c:otherwise>
          </c:choose>
        </td>
        <td>
          <c:choose>
            <c:when test="${not empty r.toAccountId}">Acc# ${r.toAccountId}</c:when>
            <c:when test="${not empty r.toExternalAccount}">${r.toExternalAccount} (${r.toExternalIfsc})</c:when>
            <c:otherwise>-</c:otherwise>
          </c:choose>
        </td>
                    <td>${r.narration}</td>
                    <td class="text-danger fw-semibold">
                      <c:out value="${r.debit != null ? r.debit : ''}"/>
                    </td>
                    <td class="text-success fw-semibold">
                      <c:out value="${r.credit != null ? r.credit : ''}"/>
                    </td>
                    <td>
                      <c:choose>
                        <c:when test="${r.status=='SUCCESS'}"><span class="badge bg-success">Success</span></c:when>
                        <c:when test="${r.status=='FAILED'}"><span class="badge bg-danger">Failed</span></c:when>
                        <c:when test="${r.status=='PENDING'}"><span class="badge bg-warning text-dark">Pending</span></c:when>
                        <c:when test="${r.status=='REVERSED'}"><span class="badge bg-secondary">Reversed</span></c:when>
                        <c:otherwise>${r.status}</c:otherwise>
                      </c:choose>
                    </td>
                  </tr>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <tr><td colspan="6" class="text-center text-muted py-3">No transactions found.</td></tr>
              </c:otherwise>
            </c:choose>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <!-- Pagination -->
  <c:if test="${not empty passbook}">
    <div class="d-flex justify-content-between align-items-center mt-3">
      <c:set var="prevOffset" value="${offset - limit}"/>
      <c:set var="nextOffset" value="${offset + limit}"/>

      <c:if test="${prevOffset >= 0}">
        <a class="btn btn-outline-primary"
           href="${pageContext.request.contextPath}/customer_passbook?account_id=${account.accountId}&type=${type}&status=${status}&date_from=${date_from}&date_to=${date_to}&limit=${limit}&offset=${prevOffset}">
          « Previous
        </a>
      </c:if>

      <c:if test="${hasNext}">
        <a class="btn btn-outline-primary ms-auto"
           href="${pageContext.request.contextPath}/customer_passbook?account_id=${account.accountId}&type=${type}&status=${status}&date_from=${date_from}&date_to=${date_to}&limit=${limit}&offset=${nextOffset}">
          Next »
        </a>
      </c:if>
    </div>
  </c:if>
</div>
</body>
</html>
